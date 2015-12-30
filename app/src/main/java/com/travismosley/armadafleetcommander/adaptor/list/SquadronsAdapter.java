package com.travismosley.armadafleetcommander.adaptor.list;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.game.component.Squadron;

import java.util.List;

/**
 * ArrayAdaptor for Squadron lists
 */

public class SquadronsAdapter extends ComponentListAdapter<Squadron> {
    /* Adapts a Squadron objects for a ListView */

    private final static String LOG_TAG = SquadronsAdapter.class.getSimpleName();

    public SquadronsAdapter(Context context, List<Squadron> squadrons) {
        super(context, squadrons);
    }

    public void populateView(View squadView, Squadron squad) {

        // Set the squadron name
        TextView nameView = (TextView) squadView.findViewById(R.id.squadron_title);
        nameView.setText(squad.title());

        // Set the squadron class name
        TextView classView = (TextView) squadView.findViewById(R.id.squadron_class);
        classView.setText(squad.vehicleClass());

        // Set the hull value
        TextView hullView = (TextView) squadView.findViewById(R.id.squadron_hull);
        hullView.setText((Integer.toString(squad.hull())));

        // Set the speed value
        TextView speedView = (TextView) squadView.findViewById(R.id.squadron_speed);
        speedView.setText(Integer.toString(squad.maxSpeed()));

        // Set the point value
        TextView pointsView = (TextView) squadView.findViewById(R.id.squadron_points);
        pointsView.setText(Integer.toString(squad.pointCost()));

        if (squad.isUnique()){
            squadView.setBackgroundColor(Color.parseColor("#FFFFC9"));
        } else {
            squadView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    protected int getItemLayoutId(){
        return R.layout.list_item_squadron;
    }
}
