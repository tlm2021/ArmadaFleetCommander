package com.travismosley.armadafleetcommander.adaptor.selector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetcommander.adaptor.UpgradesAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.upgrade.Upgrade;

import java.util.List;

/**
 * A ShipsAdaptor for the selection UI, which has special handling for unique squadrons,
 * that are already in the fleet.
 */

public class UpgradesSelectorAdapter extends UpgradesAdapter {
    private Fleet mFleet;

    public UpgradesSelectorAdapter(Context context, List<Upgrade> upgrades, Fleet fleet) {
        super(context, upgrades);
        mFleet = fleet;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View shipView = super.getView(position, convertView, parent);
        Upgrade upgrade = getItem(position);

        // Check if the user is allowed to add the squadron
        if (!mFleet.canAddComponent(upgrade)){
            shipView.setBackgroundColor(Color.parseColor("#717171"));
        }

        return shipView;

    }
}
