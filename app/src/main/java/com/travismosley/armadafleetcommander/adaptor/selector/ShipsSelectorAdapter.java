package com.travismosley.armadafleetcommander.adaptor.selector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetcommander.adaptor.ShipsAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.Ship;

import java.util.List;

/**
 * A ShipsAdaptor for the selection UI, which has special handling for unique squadrons,
 * that are already in the fleet.
 */
public class ShipsSelectorAdapter extends ShipsAdapter {
    private Fleet mFleet;

    public ShipsSelectorAdapter(Context context, List<Ship> ships, Fleet fleet) {
        super(context, ships);
        mFleet = fleet;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View shipView = super.getView(position, convertView, parent);
        Ship ship = getItem(position);

        // Check if the user is allowed to add the squadron
        if (!mFleet.canAddComponent(ship)){
            shipView.setBackgroundColor(Color.parseColor("#717171"));
        }

        return shipView;

    }
}
