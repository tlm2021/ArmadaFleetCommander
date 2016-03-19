package com.travismosley.armadafleetadmiral.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.travismosley.android.ui.Gravity;
import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.data.FleetDatabaseFacade;
import com.travismosley.armadafleetadmiral.fragment.FleetOverviewFragment;
import com.travismosley.armadafleetadmiral.fragment.ShipDetailFragment;
import com.travismosley.armadafleetadmiral.fragment.listener.OnComponentSelectedListener;
import com.travismosley.armadafleetadmiral.fragment.listener.OnUpgradeSelectedListener;
import com.travismosley.armadafleetadmiral.fragment.selector.ShipSelectorFragment;
import com.travismosley.armadafleetadmiral.fragment.selector.SquadronSelectorFragment;
import com.travismosley.armadafleetadmiral.fragment.selector.UpgradeSelectorFragment;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.Squadron;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetadmiral.game.component.upgrade.UpgradeSlot;

public class FleetBuilderActivity extends AppCompatActivity
        implements FleetOverviewFragment.OnAddSquadronListener,
                   FleetOverviewFragment.OnAddShipListener,
                   FleetOverviewFragment.OnShipClickedListener,
                   ShipDetailFragment.OnUpgradeSlotClickedListener {

    private final static String LOG_TAG = FleetBuilderActivity.class.getSimpleName();

    public Fleet mFleet;
    private FleetOverviewFragment mFleetFrag;

    private ShipSelectedListener mShipSelectedListener = new ShipSelectedListener();
    private SquadronSelectedListener mSquadronSelectedListener = new SquadronSelectedListener();
    private UpgradeSelectedListener mUpgradeSelectedListener = new UpgradeSelectedListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_builder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle args = getIntent().getExtras();
        mFleet = args.getParcelable(getString(R.string.key_fleet));

        assert mFleet != null;
        if (mFleet.mFactionId == 0) {
            this.setTheme(R.style.AppThemeRebel);
        } else {
            this.setTheme(R.style.AppThemeEmpire);
        }

        updateFleetName("Death Legion Zeta");

        mFleetFrag = createFleetFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fleet_builder_root, mFleetFrag)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "Creating options Menu");
        getMenuInflater().inflate(R.menu.menu_fleet_builder, menu);

        MenuItemCompat.OnActionExpandListener renameExpandListener = new MenuItemCompat.OnActionExpandListener() {

            protected EditText getView(MenuItem item) {

                return (EditText) MenuItemCompat.getActionView(item);
            }

            protected String getText(MenuItem item) {
                return getView(item).getText().toString();
            }

            protected void updateText(MenuItem item) {
                getView(item).setText(mFleet.name());
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d(LOG_TAG, this.getClass().getSimpleName() + ": onMenuItemActionExpand");
                updateText(item);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.d(LOG_TAG, this.getClass().getSimpleName() + ": onMenuItemActionCollapse");
                updateFleetName(getText(item));
                return true;
            }
        };

        MenuItem renameMenuItem = menu.findItem(R.id.action_rename);
        MenuItemCompat.setOnActionExpandListener(renameMenuItem, renameExpandListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:

                onSaveFleet();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private FleetOverviewFragment createFleetFragment() {
        FleetOverviewFragment fleetFragment = new FleetOverviewFragment();
        Bundle args = getIntent().getExtras();
        if (args == null) {
            args = new Bundle();
        }
        args.putParcelable(getString(R.string.key_fleet), mFleet);
        fleetFragment.setArguments(args);
        return fleetFragment;
    }

    public void onSaveFleet() {
        if (mFleet.commander() == null){
            Toast.makeText(this, "Unable to save fleet without a commander.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Saving fleet " + mFleet.name(), Toast.LENGTH_SHORT).show();
        FleetDatabaseFacade fleetDatabase = FleetDatabaseFacade.getInstance(this);

        if (mFleet.id() != null){
            if (fleetDatabase.hasFleet(mFleet.id())){
                fleetDatabase.updateFleet(mFleet);
            } else{
                mFleet.setId(fleetDatabase.addFleet(mFleet));
            }
        }

        Toast.makeText(this, "Saved " + mFleet.name(), Toast.LENGTH_SHORT).show();
    }

    public void updateFleetName(String newName) {
        mFleet.setName(newName);
        setTitle(newName);
    }

    // Open up the squadron list when needed
    public void onAddSquadron() {
        Log.d(LOG_TAG, "onAddSquadron");

        // Initialize the squadron fragment
        SquadronSelectorFragment squadronFragment = SquadronSelectorFragment.newInstance(mFleet);
        squadronFragment.setSelectionListener(mSquadronSelectedListener);
        transitionToFragment(squadronFragment);
    }

    // Open up the squadron list when needed
    public void onAddShip() {
        Log.d(LOG_TAG, "onAddShip");

        // Initialize the squadron fragment
        ShipSelectorFragment shipFragment = ShipSelectorFragment.newInstance(mFleet);
        shipFragment.setSelectionListener(mShipSelectedListener);
        transitionToFragment(shipFragment);
    }

    // Open up the upgrade list when needed
    public void onUpgradeSlotClicked(UpgradeSlot slot) {
        Log.d(LOG_TAG, "onUpgradeSlotClicked");

        UpgradeSelectorFragment upgradeSelector = UpgradeSelectorFragment.newInstance(slot, mFleet);
        upgradeSelector.setSelectionListener(mUpgradeSelectedListener);
        transitionToFragment(upgradeSelector);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onShipClicked(Ship ship) {
        Log.d(LOG_TAG, "onShipClicked for " + ship);

        ShipDetailFragment shipDetailFragment = ShipDetailFragment.newInstance(ship, mFleet);
        transitionToFragment(shipDetailFragment, new Slide(Gravity.end()));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void transitionToFragment(Fragment fragment) {
        transitionToFragment(fragment, new Slide(Gravity.start()));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void transitionToFragment(Fragment fragment, Transition transition) {

        // Replace the current fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fleet_builder_root, fragment)
                .addToBackStack(null)
                .commit();

        fragment.setEnterTransition(transition);
        fragment.setReturnTransition(transition);
        fragment.setExitTransition(transition);
        fragment.setReenterTransition(transition);
        fragment.setAllowEnterTransitionOverlap(false);
        fragment.setAllowReturnTransitionOverlap(false);
    }

    /* Component Selection Listeners */

    private class ShipSelectedListener implements OnComponentSelectedListener<Ship> {

        @Override
        public void onComponentSelected(Ship ship) {
            getSupportFragmentManager().popBackStack();
            mFleetFrag.addComponent(ship);
        }
    }

    private class SquadronSelectedListener implements OnComponentSelectedListener<Squadron> {

        @Override
        public void onComponentSelected(Squadron squadron) {
            getSupportFragmentManager().popBackStack();
            mFleetFrag.addComponent(squadron);
        }
    }

    private class UpgradeSelectedListener implements OnUpgradeSelectedListener {

        @Override
        public void onComponentSelected(Upgrade upgrade, UpgradeSlot slot) {
            if (mFleet.canAddComponent(upgrade)) {
                getSupportFragmentManager().popBackStack();
                slot.equip(upgrade);
            }
        }
    }
}