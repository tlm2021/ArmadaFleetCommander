package com.travismosley.armadafleetcommander.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.travismosley.android.ui.event.SwipeEvent;
import com.travismosley.android.ui.listener.OnSwipeListener;
import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.SquadronsAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.components.Squadron;

/**
 * A placeholder fragment containing a simple view.
 */

public class FleetBuilderFragment extends Fragment {

    private final static String LOG_TAG = FleetBuilderFragment.class.getSimpleName();

    public Fleet mFleet;
    private SquadronsAdapter mSquadronsAdaptor;

    OnAddSquadronListener mAddSquadronCallback;

    private class SwipeListener extends OnSwipeListener{

        // Listen for swipe events and trigger the appropriate actions
        // These should return false if we don't do any event handling, true otherwise
        public boolean onSwipeUp(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeUp");
            return false;}
        public boolean onSwipeDown(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeDown");
            return false;
        };
        public boolean onSwipeLeft(View v, SwipeEvent event){
            Log.d(LOG_TAG, "Running onSwipeLeft");
            return false;
        };

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

    // onAddSquadronClicked callback
    public interface OnAddSquadronListener{
        void onAddSquadron();
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
                mAddSquadronCallback.onAddSquadron();
            }
        });

        // Get a SquadronAdapater, and set it on the list
        ListView fleetList = (ListView) fleetFragment.findViewById(R.id.listView_selected_squadrons);
        mSquadronsAdaptor = new SquadronsAdapter(getActivity(), mFleet.mSquadrons);
        fleetList.setAdapter(mSquadronsAdaptor);

        fleetList.setOnTouchListener(new SwipeListener());

        return fleetFragment;
    }

    public void addSquadron(Squadron squadron){

        Log.d(LOG_TAG, "Adding squadron " + squadron);
        mFleet.addSquadron(squadron);
    }

    public void removeSquadron(int position){

        Log.d(LOG_TAG, "Calling removeSquadron");
        mSquadronsAdaptor.removeSquadronAtPos(position);
    }


}
