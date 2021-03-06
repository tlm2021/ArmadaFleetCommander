package com.travismosley.armadafleetadmiral.fragment.selector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.adaptor.list.FleetAdapter;
import com.travismosley.armadafleetadmiral.data.FleetDatabaseFacade;
import com.travismosley.armadafleetadmiral.game.Fleet;

import java.util.ArrayList;
import java.util.List;

public class FleetSelectorFragment extends Fragment {

    private static final String LOG_TAG = FleetSelectorFragment.class.getSimpleName();

    private static final String KEY_FACTION_ID = "KEY_FACTION_ID";
    protected FleetDatabaseFacade mFleetDb;
    private int mFactionId;

    private FleetAdapter mFleetsAdapter;

    // Fleet Build Selector interface
    private OnFleetBuilderRequestedListener mFleetSelectedListener;

    public FleetSelectorFragment() {}

    protected static Bundle getBundleForNewInstance(int factionId){
        Bundle args = new Bundle();
        args.putInt(KEY_FACTION_ID, factionId);
        return args;
    }

    public static FleetSelectorFragment newInstance(int mFactionId) {
        FleetSelectorFragment fragment = new FleetSelectorFragment();
        fragment.setArguments(getBundleForNewInstance(mFactionId));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFleetDb = FleetDatabaseFacade.getInstance(getActivity());
        mFactionId = getArguments().getInt(KEY_FACTION_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreateView");
        // Inflate the layout for this fragment
        View fleetSelectorFragment = inflater.inflate(R.layout.fragment_fleet_selector, container, false);

        Button btnNewFleet = (Button) fleetSelectorFragment.findViewById(R.id.btn_new_fleet);
        btnNewFleet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fleet fleet = new Fleet(mFactionId);
                mFleetSelectedListener.onFleetBuilderRequested(fleet);
            }
        });

        final ListView fleetListView = (ListView) fleetSelectorFragment.findViewById(R.id.list_fleets);
        List<Fleet> fleets = mFleetDb.getFleetsForFaction(mFactionId);

        for (int i=0; i < fleets.size(); i++){
            Log.d(LOG_TAG, fleets.get(i).name());
        }
        mFleetsAdapter = new FleetAdapter(getActivity(), new ArrayList<Fleet>());
        fleetListView.setAdapter(mFleetsAdapter);

        fleetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fleet fleet = mFleetsAdapter.getItem(position);
                mFleetSelectedListener.onFleetBuilderRequested(fleet);
            }
        });
        return fleetSelectorFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFleetBuilderRequestedListener) {
            mFleetSelectedListener = (OnFleetBuilderRequestedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFleetBuilderRequestedListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mFleetsAdapter.clear();
        mFleetsAdapter.addAll(mFleetDb.getFleetsForFaction(mFactionId));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFleetSelectedListener = null;
    }

    public interface OnFleetBuilderRequestedListener {
        void onFleetBuilderRequested(Fleet fleet);
    }
}
