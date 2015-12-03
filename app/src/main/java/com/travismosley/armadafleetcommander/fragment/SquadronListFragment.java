package com.travismosley.armadafleetcommander.fragment;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.data.ArmadaDatabaseFacade;
import com.travismosley.armadafleetcommander.game.components.Squadron;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SquadronListFragment extends Fragment {

    private SquadronsAdapter mResultsAdapter;
    private ArmadaDatabaseFacade mArmadaDb;

    public SquadronListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mArmadaDb = new ArmadaDatabaseFacade(getActivity());

        // Inflate the fragment_squadron_list and fetch the list view
        View squadronView = inflater.inflate(R.layout.fragment_squadron_list, container, false);
        ListView squadronResultView = (ListView) squadronView.findViewById(R.id.listView_squadrons);

        // Get a SquadronAdapater, and set it on the list
        SquadronsAdapter squadronAdapter = new SquadronsAdapter(getActivity(),
                                                                mArmadaDb.getSquadrons());
        squadronResultView.setAdapter(squadronAdapter);
        return squadronView;
    }


    private class SquadronsAdapter extends ArrayAdapter<Squadron> {
        /* Adapts a Squadron objects for a ListView */

        private final Context context;
        private List<Squadron> squadrons;

        public SquadronsAdapter(Context context, List<Squadron> squadrons){
            super(context, -1, squadrons);
            this.context = context;
            this.squadrons = squadrons;
        }

        public View getView(int position, View convertView, ViewGroup parent){

            Squadron squad = squadrons.get(position);
            View squadView;

            // Recycle the view if possible
            if (convertView != null){
                squadView = convertView;
            } else{
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                squadView = inflater.inflate(R.layout.list_item_squadron, parent, false);
            }

            // Set the squadron name
            TextView nameView = (TextView) squadView.findViewById(R.id.list_item_squadron_title);
            nameView.setText(squad.mTitle);

            // Set the squadron class name
            TextView classView = (TextView) squadView.findViewById(R.id.list_item_squadron_class);
            classView.setText(squad.mClass);

            // Set the hull value
            TextView hullView = (TextView) squadView.findViewById(R.id.list_item_squadron_hull);
            hullView.setText((Integer.toString(squad.mHull)));

            // Set the speed value
            TextView speedView = (TextView) squadView.findViewById(R.id.list_item_squadron_speed);
            speedView.setText(Integer.toString(squad.mSpeed));

            // Set the point value
            TextView pointsView = (TextView) squadView.findViewById(R.id.list_item_squadron_points);
            pointsView.setText(Integer.toString(squad.mPointCost));

            return squadView;

        }
    }
}
