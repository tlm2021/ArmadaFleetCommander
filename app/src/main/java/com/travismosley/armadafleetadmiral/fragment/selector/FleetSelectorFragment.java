package com.travismosley.armadafleetadmiral.fragment.selector;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.adaptor.list.FleetAdapter;
import com.travismosley.armadafleetadmiral.data.FleetDatabaseFacade;
import com.travismosley.armadafleetadmiral.game.Fleet;

import java.util.List;

public class FleetSelectorFragment extends Fragment {

    private static final String LOG_TAG = FleetSelectorFragment.class.getSimpleName();

    private static final String KEY_FACTION_ID = "KEY_FACTION_ID";

    private int mFactionId;
    protected FleetDatabaseFacade mFleetDb;

    // Fleet Build Selector interface
    private OnFleetBuilderRequestedListener mFleetSelectedListener;

    public interface OnFleetBuilderRequestedListener{
        void onFleetBuilderRequested(Fleet fleet);
    }


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
        ListView fleetListView = (ListView) fleetSelectorFragment.findViewById(R.id.list_fleets);
        List<Fleet> fleets = mFleetDb.getFleetsForFaction(mFactionId);

        for (int i=0; i < fleets.size(); i++){
            Log.d(LOG_TAG, fleets.get(i).name());
        }

        fleetListView.setAdapter(new FleetAdapter(getActivity(), mFleetDb.getFleetsForFaction(mFactionId)));

        return fleetSelectorFragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onFleetClicked(Uri uri){
        // Fill this in later
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFleetBuilderRequestedListener) {
            mFleetSelectedListener = (OnFleetBuilderRequestedListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFleetBuilderRequestedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFleetSelectedListener = null;
    }
}
