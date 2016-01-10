package com.travismosley.armadafleetadmiral.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.fragment.FleetBuilderFragment;
import com.travismosley.armadafleetadmiral.fragment.ShipDetailFragment;
import com.travismosley.armadafleetadmiral.fragment.listener.OnComponentSelectedListener;
import com.travismosley.armadafleetadmiral.fragment.listener.OnUpgradeSelectedListener;
import com.travismosley.armadafleetadmiral.fragment.selector.ComponentSelectorFragment;
import com.travismosley.armadafleetadmiral.fragment.selector.ShipSelectorFragment;
import com.travismosley.armadafleetadmiral.fragment.selector.SquadronSelectorFragment;
import com.travismosley.armadafleetadmiral.fragment.selector.UpgradeSelectorFragment;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.Squadron;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetadmiral.game.component.upgrade.UpgradeSlot;

public class FleetBuilderActivity extends AppCompatActivity
        implements FleetBuilderFragment.OnAddSquadronListener,
                   FleetBuilderFragment.OnAddShipListener,
                   FleetBuilderFragment.OnShipClickedListener,
                   ShipDetailFragment.OnUpgradeSlotClickedListener {

    private final static String LOG_TAG = FleetBuilderActivity.class.getSimpleName();

    public Fleet mFleet;
    private FleetBuilderFragment mFleetFrag;

    private ShipSelectedListener mShipSelectedListener = new ShipSelectedListener();
    private SquadronSelectedListener mSquadronSelectedListener = new SquadronSelectedListener();
    private UpgradeSelectedListener mUpgradeSelectedListener = new UpgradeSelectedListener();


    private class ShipSelectedListener implements OnComponentSelectedListener<Ship> {

        @Override
        public void onComponentSelected(Ship ship) {
            getFragmentManager().popBackStack();
            mFleetFrag.addComponent(ship);
        }
    }

    private class SquadronSelectedListener implements OnComponentSelectedListener<Squadron> {

        @Override
        public void onComponentSelected(Squadron squadron) {
            getFragmentManager().popBackStack();
            mFleetFrag.addComponent(squadron);
        }
    }

    private class UpgradeSelectedListener implements OnUpgradeSelectedListener {

        @Override
        public void onComponentSelected(Upgrade upgrade, UpgradeSlot slot){
            if (mFleet.canAddComponent(upgrade)){
                slot.equip(upgrade);
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_builder);

        Bundle args = getIntent().getExtras();
        mFleet = new Fleet(args.getInt(getString(R.string.key_faction_id)));

        if (mFleet.mFactionId == 0){
            this.setTheme(R.style.AppThemeRebel);
        } else{
            this.setTheme(R.style.AppThemeEmpire);
        }

        mFleetFrag = createFleetFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.fleet_builder_root, mFleetFrag)
                .commit();
    }

    private FleetBuilderFragment createFleetFragment(){
        FleetBuilderFragment fleetFragment = new FleetBuilderFragment();
        Bundle args = getIntent().getExtras();
        if (args == null){
            args = new Bundle();
        }
        args.putParcelable(getString(R.string.key_fleet), mFleet);
        fleetFragment.setArguments(args);
        Transition slide = new Slide(Gravity.RIGHT);
        fleetFragment.setEnterTransition(slide);
        fleetFragment.setExitTransition(slide);
        fleetFragment.setReenterTransition(slide);
        fleetFragment.setReenterTransition(slide);
        fleetFragment.setAllowEnterTransitionOverlap(false);
        fleetFragment.setAllowReturnTransitionOverlap(false);
        return fleetFragment;
    }

    // Open up the squadron list when needed
    public void onAddSquadron(){
        Log.d(LOG_TAG, "onAddSquadron");

        // Initialize the squadron fragment
        SquadronSelectorFragment squadronFragment = SquadronSelectorFragment.newInstance(mFleet);
        squadronFragment.setSelectionListener(mSquadronSelectedListener);
        transitionToFragment(squadronFragment);
    }

    // Open up the squadron list when needed
    public void onAddShip(){
        Log.d(LOG_TAG, "onAddShip");

        // Initialize the squadron fragment
        ShipSelectorFragment shipFragment = ShipSelectorFragment.newInstance(mFleet);
        shipFragment.setSelectionListener(mShipSelectedListener);
        transitionToFragment(shipFragment);
    }

    // Open up the upgrade list when needed
    public void onUpgradeSlotClicked(UpgradeSlot slot){
        Log.d(LOG_TAG, "onUpgradeSlotClicked");

        UpgradeSelectorFragment upgradeSelector = UpgradeSelectorFragment.newInstance(slot, mFleet);
        upgradeSelector.setSelectionListener(mUpgradeSelectedListener);
        transitionToFragment(upgradeSelector);
    }

    public void onShipClicked(Ship ship){
        Log.d(LOG_TAG, "onShipClicked for " + ship);

        ShipDetailFragment shipDetailFragment = ShipDetailFragment.newInstance(ship, mFleet);
        transitionToFragment(shipDetailFragment, new Slide(Gravity.RIGHT));
    }

    public void transitionToFragment(Fragment fragment){
        transitionToFragment(fragment, new Slide(Gravity.LEFT));
    }

    public void transitionToFragment(Fragment fragment, Transition transition){

        fragment.setEnterTransition(transition);
        fragment.setReturnTransition(transition);
        fragment.setExitTransition(transition);
        fragment.setReenterTransition(transition);
        fragment.setAllowEnterTransitionOverlap(false);
        fragment.setAllowReturnTransitionOverlap(false);

        // Replace the current fragment
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fleet_builder_root, fragment)
                .addToBackStack(null)
                .commit();
    }
}