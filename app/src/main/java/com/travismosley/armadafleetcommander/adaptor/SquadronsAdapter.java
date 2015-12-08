package com.travismosley.armadafleetcommander.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.game.components.Squadron;

import java.util.List;

/**
 * ArrayAdaptor for Squadron lists
 */
public class SquadronsAdapter extends ArrayAdapter<Squadron> {
    /* Adapts a Squadron objects for a ListView */

    private final Context mContext;
    private List<Squadron> mSquadrons;

    public SquadronsAdapter(Context context, List<Squadron> squadrons) {
        super(context, -1, squadrons);
        mContext = context;
        mSquadrons = squadrons;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Squadron squad = mSquadrons.get(position);
        View squadView;

        // Recycle the view if possible
        if (convertView != null) {
            squadView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        if (squad.isUnique()){
            squadView.setBackgroundColor(Color.parseColor("#FFFFC9AF"));
        }

        return squadView;

    }
}
