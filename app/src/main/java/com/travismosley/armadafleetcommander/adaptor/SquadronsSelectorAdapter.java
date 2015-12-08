package com.travismosley.armadafleetcommander.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.components.Squadron;

import java.util.List;

/**
 * A SquadronAdaptor for the selection UI, which has special handling for unique squadrons,
 * that are already in the fleet.
 */
public class SquadronsSelectorAdapter extends SquadronsAdapter {
    private Fleet mFleet;
    private List<Squadron> mSquadrons;

    public SquadronsSelectorAdapter(Context context, List<Squadron> squadrons, Fleet fleet) {
        super(context, squadrons);
        mSquadrons = squadrons;
        mFleet = fleet;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Squadron squad = mSquadrons.get(position);
        View squadView = super.getView(position, convertView, parent);

        // If the fleet already has the squadron, and the squadron is unique, don't let the user re-add it
        if (squad.isUnique() && mFleet.hasSquadron(squad.mSquadronId)){
            squadView.setBackgroundColor(Color.parseColor("#717171"));
        }

        return squadView;

    }
}
