package com.travismosley.armadafleetcommander.adaptor.list.selector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.travismosley.armadafleetcommander.adaptor.list.UpgradesAdapter;
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

    public void populateView(View upgradeView, Upgrade upgrade) {
        super.populateView(upgradeView, upgrade);

        // Check if the user is allowed to add the squadron
        if (!mFleet.canAddComponent(upgrade)){
            upgradeView.setBackgroundColor(Color.parseColor("#717171"));
        }
    }
}
