package com.travismosley.armadafleetadmiral.adaptor.list;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.game.component.Squadron;

import java.util.ArrayList;
import java.util.Map;

/**
 * ArrayAdaptor for Squadron lists
 */

public class SquadronsAdapter extends ComponentListAdapter<Squadron> {
    /* Adapts a Squadron objects for a ListView */

    Map<Squadron, Integer> mSquadCounts;

    private final static String LOG_TAG = SquadronsAdapter.class.getSimpleName();

    public SquadronsAdapter(Context context, Map<Squadron,Integer> squadCounts) {

        super(context, new ArrayList<>(squadCounts.keySet()));
        mSquadCounts = squadCounts;
    }

    public void populateView(View squadView, Squadron squad) {

        // Set the squadron name
        TextView nameView = (TextView) squadView.findViewById(R.id.txt_squadron_title);
        nameView.setText(squad.title());

        // Set the hull value
        TextView hullView = (TextView) squadView.findViewById(R.id.txt_squadron_hull);
        hullView.setText(String.format("%d", squad.hull()));

        // Set the speed value
        TextView speedView = (TextView) squadView.findViewById(R.id.txt_squadron_speed);
        speedView.setText(String.format("%d", squad.maxSpeed()));

        // Set the point value
        TextView pointsView = (TextView) squadView.findViewById(R.id.txt_squadron_points);
        pointsView.setText(String.format("%d", squad.pointCost()));

        if (squad.isUnique()){
            squadView.setBackgroundColor(Color.parseColor("#FFFFC9"));
        } else {
            squadView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    protected int getItemLayoutId(){
        return R.layout.list_item_squadron;
    }
}
