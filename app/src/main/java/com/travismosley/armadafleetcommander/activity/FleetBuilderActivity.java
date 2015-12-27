package com.travismosley.armadafleetcommander.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.fragment.FleetBuilderFragment;
import com.travismosley.armadafleetcommander.fragment.ShipDetailFragment;
import com.travismosley.armadafleetcommander.fragment.listener.OnComponentSelectedListener;
import com.travismosley.armadafleetcommander.fragment.selector.ComponentSelectorFragment;
import com.travismosley.armadafleetcommander.fragment.selector.ShipSelectorFragment;
import com.travismosley.armadafleetcommander.fragment.selector.SquadronSelectorFragment;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.Ship;
import com.travismosley.armadafleetcommander.game.component.Squadron;

public class FleetBuilderActivity extends AppCompatActivity
        implements FleetBuilderFragment.OnAddSquadronListener,
                   FleetBuilderFragment.OnAddShipListener,
                   FleetBuilderFragment.OnShipClickedListener {

    private final static String LOG_TAG = FleetBuilderActivity.class.getSimpleName();

    public Fleet mFleet;
    private FleetBuilderFragment mFleetFrag;

    private ShipSelectedListener mShipSelectedListener = new ShipSelectedListener();
    private SquadronSelectedListener mSquadronSelectedListener = new SquadronSelectedListener();


    private class ShipSelectedListener implements OnComponentSelectedListener<Ship> {

        @Override
        public void onComponentSelected(Ship ship) {
            if (mFleetFrag.addComponent(ship)) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }

    private class SquadronSelectedListener implements OnComponentSelectedListener<Squadron> {

        @Override
        public void onComponentSelected(Squadron squadron) {
            if (mFleetFrag.addComponent(squadron)) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_builder);

        Bundle args = getIntent().getExtras();
        mFleet = new Fleet(args.getInt(getString(R.string.key_faction_id)));

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
        args.putParcelable(getString(R.string.key_fleet), mFleet);
        fleetFragment.setArguments(args);
        return fleetFragment;
    }

    // Open up the squadron list when needed
    public void onAddSquadron(){
        Log.d(LOG_TAG, "onAddSquadron");

        // Initialize the squadron fragment
        SquadronSelectorFragment squadronFragment = new SquadronSelectorFragment();
        squadronFragment.setSelectionListener(mSquadronSelectedListener);
        transitionToSelector(squadronFragment);
    }

    // Open up the squadron list when needed
    public void onAddShip(){
        Log.d(LOG_TAG, "onAddShip");

        // Initialize the squadron fragment
        ShipSelectorFragment shipFragment = new ShipSelectorFragment();
        shipFragment.setSelectionListener(mShipSelectedListener);
        transitionToSelector(shipFragment);
    }

    public void onShipClicked(Ship ship){
        Log.d(LOG_TAG, "onShipClicked for " + ship);
        ShipDetailFragment shipDetailFragment = new ShipDetailFragment();

        Bundle args = getIntent().getExtras();
        if (args == null){
            args = new Bundle();
        }
        args.putParcelable(getString(R.string.key_ship), ship);
        shipDetailFragment.setArguments(args);

        // Replace the current fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fleet_builder_fragment_container, shipDetailFragment);

        // Let the user use the back button, and return
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void transitionToSelector(ComponentSelectorFragment selectorFragment){

        Bundle args = getIntent().getExtras();
        if (args == null){
            args = new Bundle();
        }
        args.putParcelable(getString(R.string.key_fleet), mFleet);
        selectorFragment.setArguments(args);

        // Replace the current fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fleet_builder_fragment_container, selectorFragment);

        // Let the user use the back button, and return
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
