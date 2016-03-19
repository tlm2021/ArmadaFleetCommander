package com.travismosley.armadafleetadmiral.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.adaptor.list.ComponentListAdapter;
import com.travismosley.armadafleetadmiral.game.component.Squadron;

import java.util.ArrayList;
import java.util.Map;

/**
 * ListAdapter modified to support a map of a list of components and an instance count
 */
public class SquadronCountsAdapter extends ComponentListAdapter<Squadron> {

    public Map<Squadron, Integer> mSquadronCounts;

    public SquadronCountsAdapter(Context context, Map<Squadron, Integer> squadCounts) {

        super(context, new ArrayList<>(squadCounts.keySet()));
        mSquadronCounts = squadCounts;
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

        if (squad.isUnique()) {
            squadView.setBackgroundColor(Color.parseColor("#FFFFC9"));
        } else {
            squadView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    protected int getItemLayoutId() {
        return R.layout.list_item_squadron;
    }

    public int getCount(int position) {
        Squadron squad = super.getItem(position);
        return getCount(squad);
    }

    public int getCount(Squadron squad) {
        if (mSquadronCounts.containsKey(squad)) {
            return mSquadronCounts.get(squad);
        } else {
            return 0;
        }
    }
}
