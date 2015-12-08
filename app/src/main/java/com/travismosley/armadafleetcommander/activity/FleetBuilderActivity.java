package com.travismosley.armadafleetcommander.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.fragment.FleetBuilderFragment;
import com.travismosley.armadafleetcommander.fragment.SquadronSelectorFragment;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.components.Squadron;

public class FleetBuilderActivity extends AppCompatActivity
    implements SquadronSelectorFragment.OnSquadronSelectedListener,
               FleetBuilderFragment.OnAddSquadronClickedListener {

    public Fleet mFleet;
    private FleetBuilderFragment mFleetFrag;

    private final static String LOG_TAG = FleetBuilderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_builder);

        mFleet = new Fleet();
        mFleetFrag = createFleetFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fleet_builder_fragment_container, mFleetFrag).commit();

    }

    private FleetBuilderFragment createFleetFragment(){
        FleetBuilderFragment fleetFragment = new FleetBuilderFragment();
        Bundle args = getIntent().getExtras();
        if (args == null){
            args = new Bundle();
        }
        args.putParcelable("KEY_FLEET", mFleet);
        fleetFragment.setArguments(args);
        return fleetFragment;
    }

    public void onSquadronSelected(Squadron squadron){

        mFleetFrag.addSquadron(squadron);

        getSupportFragmentManager().popBackStackImmediate();
    }

    // Open up the squadron list when needed
    public void onAddSquadronClicked(){

        // Initialize the squadron fragment
        SquadronSelectorFragment squadronFragment = new SquadronSelectorFragment();
        squadronFragment.setArguments(getIntent().getExtras());

        // Replace the current fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fleet_builder_fragment_container, squadronFragment);

        // Let the user use the back button, and return
        transaction.addToBackStack(null);
        transaction.commit();

        FleetBuilderFragment fleetFragment = (FleetBuilderFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_fleet);

    }

}
