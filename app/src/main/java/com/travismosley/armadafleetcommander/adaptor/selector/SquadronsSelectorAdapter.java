package com.travismosley.armadafleetcommander.adaptor.selector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetcommander.adaptor.SquadronsAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.Squadron;

import java.util.List;

/**
 * A SquadronAdaptor for the selection UI, which has special handling for unique squadrons,
 * that are already in the fleet.
 */

public class SquadronsSelectorAdapter extends SquadronsAdapter {
    private Fleet mFleet;

    public SquadronsSelectorAdapter(Context context, List<Squadron> squadrons, Fleet fleet) {
        super(context, squadrons);
        mFleet = fleet;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View squadView = super.getView(position, convertView, parent);
        Squadron squad = getItem(position);

        // Check if the user is allowed to add the squadron
        if (!mFleet.canAddComponent(squad)){
            squadView.setBackgroundColor(Color.parseColor("#717171"));
        }

        return squadView;

    }
}
