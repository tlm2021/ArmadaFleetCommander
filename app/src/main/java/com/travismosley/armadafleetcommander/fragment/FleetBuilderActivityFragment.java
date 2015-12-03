package com.travismosley.armadafleetcommander.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.game.Fleet;

/**
 * A placeholder fragment containing a simple view.
 */

public class FleetBuilderActivityFragment extends Fragment {

    private Fleet mFleet;

    public FleetBuilderActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fleetFragment = inflater.inflate(R.layout.fragment_fleet, container, false);

        Button btnAddSquadron = (Button) fleetFragment.findViewById(R.id.btn_add_squadron);
        btnAddSquadron.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View btn){
                addSquadronFragment();
            }
        });

        return fleetFragment;
    }

    public void addSquadronFragment(){
        if (getActivity().findViewById(R.id.fragment_squadron_list) != null){
            return;
        }

        SquadronListFragment squadronFragment = new SquadronListFragment();
        squadronFragment.setArguments(getActivity().getIntent().getExtras());
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_fleet_builder_container, squadronFragment).commit();
    }
}
