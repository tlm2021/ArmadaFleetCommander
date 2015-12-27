package com.travismosley.armadafleetcommander.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.travismosley.android.ui.event.SwipeEvent;
import com.travismosley.android.ui.listener.OnSwipeListener;
import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.ComponentListAdapter;
import com.travismosley.armadafleetcommander.adaptor.ShipsAdapter;
import com.travismosley.armadafleetcommander.adaptor.SquadronsAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.Ship;
import com.travismosley.armadafleetcommander.game.component.Squadron;

/**
 * A placeholder fragment containing a simple view.
 */

public class FleetBuilderFragment extends Fragment {

    private final static String LOG_TAG = FleetBuilderFragment.class.getSimpleName();

    public Fleet mFleet;
    private SquadronsAdapter mSquadronsAdaptor;
    private ShipsAdapter mShipsAdaptor;
    private View mFleetFragment;

    private ListView mSquadListView;
    private ListView mShipListView;

    OnAddSquadronListener mAddSquadronCallback;
    OnAddShipListener mAddShipCallback;
    OnShipClickedListener mOnShipClickedCallback;

    private class SquadSwipeListener extends OnSwipeListener{

        // Listen for swipe events and trigger the appropriate actions
        // These should return false if we don't do any event handling, true otherwise
        public boolean onSwipeUp(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeUp");
            return true;}

        public boolean onSwipeDown(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeDown");
            return true;
        }

        public boolean onSwipeLeft(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeLeft");
            return true;
        }

        public boolean onSwipeRight(View v, SwipeEvent event){

            Log.d(LOG_TAG, "Running onSwipeRight");
            // The view will be our squadron list
            ListView view = (ListView) v;

            // Get the position of the item swiped and remove it
            int swipePos = view.pointToPosition((int) event.sourceX(), (int) event.sourceY());
            if (swipePos >= 0){
                removeSquadron(swipePos);
            }
            return true;
        }
    }

    private class ShipSwipeListener extends OnSwipeListener{

        // Listen for swipe events and trigger the appropriate actions
        // These should return false if we don't do any event handling, true otherwise
        public boolean onSwipeUp(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeUp");
            return false;}

        public boolean onSwipeDown(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeDown");
            return false;
        }

        public boolean onSwipeLeft(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeLeft");
            return false;
        }

        public boolean onSwipeRight(View v, SwipeEvent event){

            Log.d(LOG_TAG, "Running onSwipeRight");
            // The view will be our squadron list
            ListView view = (ListView) v;

            // Get the position of the item swiped and remove it
            int swipePos = view.pointToPosition((int) event.sourceX(), (int) event.sourceY());
            if (swipePos >= 0){
                removeShip(swipePos);
            }
            return true;
        }
    }

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

        // Add the click listener for the add_squadron button
        Button btnAddSquadron = (Button) mFleetFragment.findViewById(R.id.btn_add_squadron);
        btnAddSquadron.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View btn){mAddSquadronCallback.onAddSquadron();
            }
        });

        // Get a SquadronAdapater, and set it on the list
        mSquadListView = (ListView) mFleetFragment.findViewById(R.id.list_squadrons);
        mSquadronsAdaptor = new SquadronsAdapter(getActivity(), mFleet.mSquadrons);

        mSquadListView.setAdapter(mSquadronsAdaptor);
        mSquadListView.setOnTouchListener(new SquadSwipeListener());

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
        mShipListView.setOnTouchListener(new ShipSwipeListener());
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
            mSquadronsAdaptor.addComponent(squadron);
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
        transitionOutComponent(position, mSquadListView);
    }

    public void removeShip(final int position){
        Log.d(LOG_TAG, "removeShip for position " + position);
        transitionOutComponent(position, mShipListView);
    }

    private void transitionOutComponent(final int position, final ListView listView){

        // create the animation
        Animation anim = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
        anim.setDuration(250);

        final ComponentListAdapter adapter = (ComponentListAdapter) listView.getAdapter();

        // Add a listener to update the squadron list when done
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                adapter.removeComponent(position);
                updateFleetPoints();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Start the animation
        listView.getChildAt(position).startAnimation(anim);
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
