package com.travismosley.armadafleetcommander.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.game.components.Ship;

import java.util.List;

/**
 * ArrayAdaptor for Ship lists
 */
public class ShipsAdapter extends ComponentListAdapter<Ship> {
    /* Adapts a Squadron objects for a ListView */

    private final static String LOG_TAG = ShipsAdapter.class.getSimpleName();

    public ShipsAdapter(Context context, List<Ship> ships) {
        super(context, ships);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View shipView = super.getView(position, convertView, parent);
        Ship ship = getComponentForPosition(position);

        // Set the squadron name
        TextView nameView = (TextView) shipView.findViewById(R.id.txt_ship_title);
        nameView.setText(ship.title());

        // Set the squadron class name
        TextView classView = (TextView) shipView.findViewById(R.id.txt_ship_class);
        classView.setText(ship.vehicleClass());

        // Set the hull value
        TextView hullView = (TextView) shipView.findViewById(R.id.txt_ship_hull);
        hullView.setText((Integer.toString(ship.hull())));

        // Set the speed value
        TextView speedView = (TextView) shipView.findViewById(R.id.txt_ship_speed);
        speedView.setText(Integer.toString(ship.maxSpeed()));

        // Set the point value
        TextView pointsView = (TextView) shipView.findViewById(R.id.txt_ship_points);
        pointsView.setText(Integer.toString(ship.pointCost()));

        return shipView;
    }

    protected int getItemLayoutId(){
        return R.layout.list_item_ship;
    }
}