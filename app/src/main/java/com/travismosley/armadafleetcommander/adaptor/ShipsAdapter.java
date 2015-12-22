package com.travismosley.armadafleetcommander.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.game.components.Ship;
import com.travismosley.armadafleetcommander.game.components.Squadron;

import java.util.List;

/**
 * ArrayAdaptor for Ship lists
 */
public class ShipsAdapter extends ArrayAdapter<Ship> {
    /* Adapts a Squadron objects for a ListView */

    private final static String LOG_TAG = ShipsAdapter.class.getSimpleName();

    private final Context mContext;
    public List<Ship> mShips;

    public ShipsAdapter(Context context, List<Ship> ships) {
        super(context, -1, ships);

        Log.d(LOG_TAG, "Initialize for " + ships);
        mContext = context;
        mShips = ships;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Ship ship = mShips.get(position);
        View shipView;

        // Recycle the view if possible
        if (convertView != null) {
            shipView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            shipView = inflater.inflate(R.layout.list_item_ship, parent, false);
        }

        // Set the squadron name
        TextView nameView = (TextView) shipView.findViewById(R.id.txt_ship_title);
        nameView.setText(ship.mTitle);

        // Set the squadron class name
        TextView classView = (TextView) shipView.findViewById(R.id.txt_ship_class);
        classView.setText(ship.mClass);

        // Set the hull value
        TextView hullView = (TextView) shipView.findViewById(R.id.txt_ship_hull);
        hullView.setText((Integer.toString(ship.mHull)));

        // Set the speed value
        TextView speedView = (TextView) shipView.findViewById(R.id.txt_ship_speed);
        speedView.setText(Integer.toString(ship.mSpeed));

        // Set the point value
        TextView pointsView = (TextView) shipView.findViewById(R.id.txt_ship_points);
        pointsView.setText(Integer.toString(ship.mPointCost));

        return shipView;
    }

    public void addShip(Ship ship) {
        Log.d(LOG_TAG, "addShip on " + ship);
        mShips.add(ship);
        notifyDataSetChanged();
    }

    public void removeShip(int position){
        Log.d(LOG_TAG, "Removing ship at position " + position);
        mShips.remove(position);
        notifyDataSetChanged();
    }
}