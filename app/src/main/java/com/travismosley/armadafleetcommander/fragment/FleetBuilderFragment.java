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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.travismosley.android.ui.event.SwipeEvent;
import com.travismosley.android.ui.listener.OnSwipeListener;
import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.ShipsAdapter;
import com.travismosley.armadafleetcommander.adaptor.SquadronsAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.components.Ship;
import com.travismosley.armadafleetcommander.game.components.Squadron;

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

    private class SquadSwipeListener extends OnSwipeListener{

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
    }

    public FleetBuilderFragment(){

    }

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

        return mFleetFragment;
    }

    public void addSquadron(Squadron squadron){

        Log.d(LOG_TAG, "addSquadron for " + squadron);
        mSquadronsAdaptor.addComponent(squadron);

    }

    public void removeSquadron(final int position){
        Log.d(LOG_TAG, "removeSquadron for position " + position);

        // create the animation
        Animation anim = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
        anim.setDuration(250);

        // Add a listener to update the squadron list when done
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSquadronsAdaptor.removeComponent(position);
                updateFleetPoints();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // Start the animation
        mSquadListView.getChildAt(position).startAnimation(anim);
    }

    public void addShip(Ship ship) {

        Log.d(LOG_TAG, "addShip for " + ship);
        mShipsAdaptor.addComponent(ship);

    }

    public void removeShip(final int position){
        Log.d(LOG_TAG, "removeShip for position " + position);

        // create the animation
        Animation anim = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
        anim.setDuration(250);

        // Add a listener to update the squadron list when done
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mShipsAdaptor.removeComponent(position);
                updateFleetPoints();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // Start the animation
        mShipListView.getChildAt(position).startAnimation(anim);
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
