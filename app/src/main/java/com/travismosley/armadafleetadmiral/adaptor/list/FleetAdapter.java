package com.travismosley.armadafleetadmiral.adaptor.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.travismosley.android.ui.adapter.ArrayAdapter;
import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.game.Fleet;

import java.util.List;

/**
 * ArrayAdaptor for Ship lists
 */
public class FleetAdapter extends ArrayAdapter<Fleet> {
    /* Adapts a Squadron objects for a ListView */

    private final static String LOG_TAG = FleetAdapter.class.getSimpleName();

    private List<Fleet> mFleetList;

    public FleetAdapter(Context context, List<Fleet> fleetList) {
        super(context, fleetList);
        mFleetList = fleetList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflateView(convertView, parent);
        Fleet fleet = mFleetList.get(position);

        // Set the fleet name and points
        TextView nameView = (TextView) view.findViewById(R.id.txt_fleet_name);
        TextView pointsView = (TextView) view.findViewById(R.id.txt_fleet_points);

        // Set the fleet name
        nameView.setText(fleet.name());
        pointsView.setText(Integer.toString(fleet.fleetPoints()));

        return view;
    }

    protected int getItemLayoutId(){
        return R.layout.list_item_fleet;
    }
}