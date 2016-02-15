package com.travismosley.armadafleetadmiral.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.adaptor.list.ShipsAdapter;
import com.travismosley.armadafleetadmiral.adaptor.list.SquadronsAdapter;
import com.travismosley.armadafleetadmiral.adaptor.spinner.CommanderAdapter;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.Squadron;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Commander;

/**
 * A placeholder fragment containing a simple view.
 */

public class FleetBuilderFragment extends Fragment {

    private final static String LOG_TAG = FleetBuilderFragment.class.getSimpleName();

    public Fleet mFleet;
    private SquadronsAdapter mSquadronsAdapter;
    private ShipsAdapter mShipsAdaptor;
    private View mFleetFragment;

    private ListView mSquadListView;
    private ListView mShipListView;

    OnAddSquadronListener mAddSquadronCallback;
    OnAddShipListener mAddShipCallback;
    OnShipClickedListener mOnShipClickedCallback;

    // onAddSquadron callback
    public interface OnAddSquadronListener{
        void onAddSquadron();
    }

    // onAddShip callback
    public interface OnAddShipListener{
        void onAddShip();
    }

    // onShipClicked callback
    public interface OnShipClickedListener{
        void onShipClicked(Ship ship);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFleet = getArguments().getParcelable(getString(R.string.key_fleet));
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        // Make sure the attaching activity has implemented the interface
        try{
            mAddSquadronCallback = (OnAddSquadronListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddSquadronListener");
        }

        try{
            mAddShipCallback = (OnAddShipListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddShipListener");
        }

        try{
            mOnShipClickedCallback = (OnShipClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShipClickedListener");
        }
    }

    public FleetBuilderFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate our view
        mFleetFragment = inflater.inflate(R.layout.fragment_fleet, container, false);
        updateFleetPoints();

        // Get a CommanderAdapter and set it on the commander spinner
        Spinner spnCommander = (Spinner) mFleetFragment.findViewById(R.id.spinner_commander);
        spnCommander.setAdapter(new CommanderAdapter(getActivity(), mFleet));
        spnCommander.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mFleet.clearCommander();
                } else {
                    Log.d(LOG_TAG, "Setting commander: " + parent.getItemAtPosition(position));
                    mFleet.setCommander((Commander) parent.getItemAtPosition(position));
                }
                updateFleetPoints();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Add the click listener for the add_squadron button
        Button btnAddSquadron = (Button) mFleetFragment.findViewById(R.id.btn_add_squadron);
        btnAddSquadron.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View btn){mAddSquadronCallback.onAddSquadron();
            }
        });

        // Get a SquadronAdapter, and set it on the list
        mSquadListView = (ListView) mFleetFragment.findViewById(R.id.list_squadrons);
        mSquadronsAdapter = new SquadronsAdapter(getActivity(), mFleet.mSquadrons);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mSquadronsAdapter);
        animationAdapter.setAbsListView(mSquadListView);
        mSquadListView.setAdapter(animationAdapter);

        // Add the click listener for the add_squadron button
        Button btnAddShip = (Button) mFleetFragment.findViewById(R.id.btn_add_ship);
        btnAddShip.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View btn) {
                mAddShipCallback.onAddShip();
            }
        });

        // Get a ShipsAdapter and set it on the list
        mShipListView = (ListView) mFleetFragment.findViewById(R.id.list_ships);
        mShipsAdaptor = new ShipsAdapter(getActivity(), mFleet.mShips);

        mShipListView.setAdapter(mShipsAdaptor);
        mShipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ship ship = (Ship) mShipListView.getItemAtPosition(position);
                mOnShipClickedCallback.onShipClicked(ship);
            }
        });

        return mFleetFragment;
    }

    public boolean addComponent(Squadron squadron){

        Log.d(LOG_TAG, "addComponent for " + squadron);
        if (mFleet.canAddComponent(squadron)) {
            mSquadronsAdapter.addComponent(squadron);
            return true;
        }
        return false;
    }

    public boolean addComponent(Ship ship) {

        Log.d(LOG_TAG, "addComponent for " + ship);
        if (mFleet.canAddComponent(ship)) {
            mShipsAdaptor.addComponent(ship);
            return true;
        }
        return false;
    }

    public void removeSquadron(final int position){
        Log.d(LOG_TAG, "removeSquadron for position " + position);
    }

    public void removeShip(final int position){
        Log.d(LOG_TAG, "removeShip for position " + position);
    }

    public void updateFleetPoints(){

        // Set the fleet point values
        TextView maxFleetPointsView = (TextView) mFleetFragment.findViewById(R.id.txt_fleet_point_allowed);
        maxFleetPointsView.setText(String.valueOf(mFleet.fleetPointLimit()));

        TextView usedFleetPointsView = (TextView) mFleetFragment.findViewById(R.id.txt_fleet_points_used);
        usedFleetPointsView.setText(String.valueOf(mFleet.fleetPoints()));

        // Set the squadron point values
        TextView maxSquadPointsView = (TextView) mFleetFragment.findViewById(R.id.txt_squad_points_allowed);
        maxSquadPointsView.setText(String.valueOf(mFleet.squadronPointLimit()));

        TextView usedSquadPointsView = (TextView) mFleetFragment.findViewById(R.id.txt_squad_points_used);
        usedSquadPointsView.setText(String.valueOf(mFleet.squadronPoints()));
    }
}
