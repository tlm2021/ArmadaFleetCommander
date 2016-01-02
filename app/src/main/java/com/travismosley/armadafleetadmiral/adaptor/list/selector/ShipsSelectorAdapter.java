package com.travismosley.armadafleetadmiral.adaptor.list.selector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.travismosley.armadafleetadmiral.adaptor.list.ShipsAdapter;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;

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

    public void populateView(View shipView, Ship ship) {
        super.populateView(shipView, ship);

        // Check if the user is allowed to add the squadron
        if (!mFleet.canAddComponent(ship)){
            shipView.setBackgroundColor(Color.parseColor("#717171"));
        }
    }
}
