package com.travismosley.armadafleetcommander.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.SquadronsAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.components.Squadron;

/**
 * A placeholder fragment containing a simple view.
 */

public class FleetBuilderFragment extends Fragment {

    public Fleet mFleet;
    OnAddSquadronClickedListener mAddSquadronCallback;

    // onAddSquadronClicked callback
    public interface OnAddSquadronClickedListener{
        void onAddSquadronClicked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mFleet = getArguments().getParcelable("KEY_FLEET");
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        // Make sure the attaching activity has implemented the interface
        try{
            mAddSquadronCallback = (OnAddSquadronClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddSquadronClickedListener");
        }
    }

    public FleetBuilderFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate our view
        View fleetFragment = inflater.inflate(R.layout.fragment_fleet, container, false);

        /// Add the click listener for the add_squadron button
        Button btnAddSquadron = (Button) fleetFragment.findViewById(R.id.btn_add_squadron);
        btnAddSquadron.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View btn){
                mAddSquadronCallback.onAddSquadronClicked();
            }
        });

        // Get a SquadronAdapater, and set it on the list
        ListView fleetList = (ListView) fleetFragment.findViewById(R.id.listView_selected_squadrons);
        fleetList.setAdapter(new SquadronsAdapter(getActivity(), mFleet.mSquadrons));

        return fleetFragment;
    }

    public void addSquadron(Squadron squadron){
        mFleet.mSquadrons.add(squadron);
    }


}
