package com.travismosley.armadafleetadmiral.fragment;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.activity.FleetBuilderActivity;
import com.travismosley.armadafleetadmiral.adaptor.SquadronCountsAdapter;
import com.travismosley.armadafleetadmiral.adaptor.list.ShipsAdapter;
import com.travismosley.armadafleetadmiral.adaptor.spinner.CommanderAdapter;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.Squadron;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Commander;

/**
 * A Fragment for adding ships, objectives, squadrons, and commanders to a view
 */

public class FleetOverviewFragment extends Fragment {

    private final static String LOG_TAG = FleetOverviewFragment.class.getSimpleName();
    OnAddSquadronListener mAddSquadronCallback;
    OnAddShipListener mAddShipCallback;
    OnShipClickedListener mOnShipClickedCallback;
    private SquadronCountsAdapter mSquadronsAdapter;
    private ShipsAdapter mShipsAdaptor;
    private View mFleetFragment;
    private ListView mSquadListView;
    private ListView mShipListView;

    public FleetOverviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        // Make sure the attaching activity has implemented the interface
        try{
            mAddSquadronCallback = (OnAddSquadronListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnAddSquadronListener");
        }

        try{
            mAddShipCallback = (OnAddShipListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnAddShipListener");
        }

        try{
            mOnShipClickedCallback = (OnShipClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnShipClickedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate our view
        mFleetFragment = inflater.inflate(R.layout.fragment_fleet, container, false);
        updateFleetPoints();

        // Get a CommanderAdapter and set it on the commander spinner
        Spinner spnCommander = (Spinner) mFleetFragment.findViewById(R.id.spinner_commander);

        CommanderAdapter commanderAdapter = new CommanderAdapter(getActivity(), getFleet());
        spnCommander.setAdapter(commanderAdapter);

        spnCommander.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    getFleet().clearCommander();
                } else {
                    Log.d(LOG_TAG, "Setting commander: " + parent.getItemAtPosition(position));
                    getFleet().setCommander((Commander) parent.getItemAtPosition(position));
                }
                updateFleetPoints();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (getFleet().commander() != null) {
            spnCommander.setSelection(commanderAdapter.getPosition(getFleet().commander()), false);
        }

        // Add the click listener for the add_squadron button
        Button btnAddSquadron = (Button) mFleetFragment.findViewById(R.id.btn_add_squadron);
        btnAddSquadron.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View btn){mAddSquadronCallback.onAddSquadron();
            }
        });

        // Get a SquadronAdapter, and set it on the list
        mSquadListView = (ListView) mFleetFragment.findViewById(R.id.list_squadrons);
        mSquadronsAdapter = new SquadronCountsAdapter(getActivity(), getFleet().squadronCounts());
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
        mShipsAdaptor = new ShipsAdapter(getActivity(), getFleet().mShips);

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

    private Fleet getFleet(){
        FleetBuilderActivity activity = (FleetBuilderActivity) getActivity();
        return activity.mFleet;
    }

    public boolean addComponent(Squadron squadron){

        Log.d(LOG_TAG, "addComponent for " + squadron);
        if (getFleet().canAddComponent(squadron)) {
            mSquadronsAdapter.addComponent(squadron);
            return true;
        }
        return false;
    }

    public boolean addComponent(Ship ship) {

        Log.d(LOG_TAG, "addComponent for " + ship);
        if (getFleet().canAddComponent(ship)) {
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
        maxFleetPointsView.setText(String.valueOf(getFleet().fleetPointLimit()));

        TextView usedFleetPointsView = (TextView) mFleetFragment.findViewById(R.id.txt_fleet_points_used);
        usedFleetPointsView.setText(String.valueOf(getFleet().fleetPoints()));

        // Set the squadron point values
        TextView maxSquadPointsView = (TextView) mFleetFragment.findViewById(R.id.txt_squad_points_allowed);
        maxSquadPointsView.setText(String.valueOf(getFleet().squadronPointLimit()));

        TextView usedSquadPointsView = (TextView) mFleetFragment.findViewById(R.id.txt_squad_points_used);
        usedSquadPointsView.setText(String.valueOf(getFleet().squadronPoints()));
    }

    // onAddSquadron callback
    public interface OnAddSquadronListener {
        void onAddSquadron();
    }

    // onAddShip callback
    public interface OnAddShipListener {
        void onAddShip();
    }

    // onShipClicked callback
    public interface OnShipClickedListener {
        void onShipClicked(Ship ship);
    }
}
