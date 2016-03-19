package com.travismosley.armadafleetadmiral.adaptor.spinner;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.adaptor.list.UpgradesAdapter;
import com.travismosley.armadafleetadmiral.data.ComponentDatabaseFacade;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Commander;

/**
 * An ArrayAdapter for ship titles
 */
public class CommanderAdapter extends ComponentSpinnerAdapter<Commander> {

    private final static String LOG_TAG = UpgradesAdapter.class.getSimpleName();

    public CommanderAdapter(Context context, Fleet fleet) {
        super(context, ComponentDatabaseFacade.getInstance(context).getCommandersForFaction(fleet.mFactionId));
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public void populateView(View commanderView, Commander commander) {

        TextView nameView = (TextView) commanderView.findViewById(R.id.txt_upgrade_title);
        TextView pointsView = (TextView) commanderView.findViewById(R.id.txt_upgrade_points);

        if (commander == null){
            nameView.setText("No commander selected...");
            pointsView.setVisibility(View.INVISIBLE);
        } else {
            nameView.setText(commander.title());
            pointsView.setText(String.format("%d", commander.pointCost()));
            pointsView.setVisibility(View.VISIBLE);
        }
    }

    protected int getItemLayoutId(){
        Log.d(LOG_TAG, "getItemLayoutId");
        return R.layout.list_item_upgrade;
    }
}
