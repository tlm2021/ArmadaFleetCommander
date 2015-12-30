package com.travismosley.armadafleetcommander.adaptor.list;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.travismosley.android.ui.utils.TextViewUtils;
import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.game.component.Ship;

import java.util.List;

/**
 * ArrayAdaptor for Ship lists
 */
public class ShipsAdapter extends ComponentListAdapter<Ship>{
    /* Adapts a Squadron objects for a ListView */

    private final static String LOG_TAG = ShipsAdapter.class.getSimpleName();

    public ShipsAdapter(Context context, List<Ship> ships) {
        super(context, ships);
    }

    protected void populateView(View shipView, Ship ship){

        // Set the ship title and class name
        TextView nameView = (TextView) shipView.findViewById(R.id.txt_ship_title);
        TextView classView = (TextView) shipView.findViewById(R.id.txt_ship_class);

        if (ship.hasTitleUpgrade()) {
            nameView.setText(ship.titleUpgrade().title());
            classView.setText(ship.title());
            classView.setVisibility(View.VISIBLE);
        } else {
            nameView.setText(ship.title());
            classView.setVisibility(View.INVISIBLE);
        }

        TextViewUtils.fitText(nameView);

        // Set the hull value
        TextView hullView = (TextView) shipView.findViewById(R.id.txt_ship_hull);
        hullView.setText((Integer.toString(ship.hull())));

        // Set the speed value
        TextView speedView = (TextView) shipView.findViewById(R.id.txt_ship_speed);
        speedView.setText(Integer.toString(ship.maxSpeed()));

        // Set the point value
        TextView pointsView = (TextView) shipView.findViewById(R.id.txt_ship_points);
        pointsView.setText(Integer.toString(ship.pointCost()));
    }

    protected int getItemLayoutId(){
        return R.layout.list_item_ship;
    }
}