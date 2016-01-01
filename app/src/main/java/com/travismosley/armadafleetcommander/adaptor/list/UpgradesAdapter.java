package com.travismosley.armadafleetcommander.adaptor.list;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.game.component.upgrade.Upgrade;

import java.util.List;

/**
 * ArrayAdaptor for Upgrade lists
 */
public class UpgradesAdapter extends ComponentListAdapter<Upgrade> {
    /* Adapts a Squadron objects for a ListView */

    private final static String LOG_TAG = UpgradesAdapter.class.getSimpleName();

    public UpgradesAdapter(Context context, List<Upgrade> upgrades) {
        super(context, upgrades);
    }

    public void populateView(View upgradeView, Upgrade upgrade) {

        // Set the squadron name
        TextView nameView = (TextView) upgradeView.findViewById(R.id.txt_upgrade_title);
        nameView.setText(upgrade.title());

        // Set the point value
        TextView pointsView = (TextView) upgradeView.findViewById(R.id.txt_point_cost);
        pointsView.setText(Integer.toString(upgrade.pointCost()));
    }

    protected int getItemLayoutId(){
        return R.layout.list_item_upgrade;
    }
}