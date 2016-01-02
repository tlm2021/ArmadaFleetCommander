package com.travismosley.armadafleetadmiral.adaptor.list.selector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.travismosley.armadafleetadmiral.adaptor.list.SquadronsAdapter;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Squadron;

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

    public void populateView(View squadView, Squadron squad) {
        super.populateView(squadView, squad);

        // Check if the user is allowed to add the squadron
        if (!mFleet.canAddComponent(squad)){
            squadView.setBackgroundColor(Color.parseColor("#717171"));
        }
    }
}
