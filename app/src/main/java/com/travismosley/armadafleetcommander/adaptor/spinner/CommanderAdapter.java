package com.travismosley.armadafleetcommander.adaptor.spinner;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.list.ComponentListAdapter;
import com.travismosley.armadafleetcommander.adaptor.list.UpgradesAdapter;
import com.travismosley.armadafleetcommander.data.ArmadaDatabaseFacade;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.upgrade.Commander;

/**
 * An ArrayAdapter for ship titles
 */
public class CommanderAdapter extends ComponentListAdapter<Commander> {

    private final static String LOG_TAG = UpgradesAdapter.class.getSimpleName();
    private final Fleet mFleet;

    public CommanderAdapter(Context context, Fleet fleet) {
        super(context, new ArmadaDatabaseFacade(context).getCommandersForFaction(fleet.mFactionId));
        mFleet = fleet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflateView(convertView, parent);
        if(position == 0){
            populateView(view, null);
        }else{
            Commander titleUpgrade = getItem(position);
            populateView(view, titleUpgrade);
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public void populateView(View commanderView, Commander commander) {

        Log.d(LOG_TAG, "populateView");
        TextView nameView = (TextView) commanderView.findViewById(R.id.txt_upgrade_title);
        TextView pointsView = (TextView) commanderView.findViewById(R.id.txt_point_cost);

        if (commander == null){
            nameView.setText("No commander selected...");
            pointsView.setVisibility(View.INVISIBLE);
        } else {
            nameView.setText(commander.title());
            pointsView.setText(Integer.toString(commander.pointCost()));
            pointsView.setVisibility(View.VISIBLE);
        }
    }

    protected int getItemLayoutId(){
        Log.d(LOG_TAG, "getItemLayoutId");
        return R.layout.list_item_upgrade;
    }

    /*
    This class dynamically generates the first item in the spinner, which acts as a
    "Select None" item. Because of that, all the superclass methods that index the list
    of objects directly will be off-by-one. These overridden methods account for that.

    These overrides adjust that
     */

    @Override
    public int getCount(){
        return super.getCount() + 1;
    }

    @Override
    public int getPosition(Commander item) {
        int pos = super.getPosition(item);
        if (pos == -1){
            return pos;
        } else {
            return pos + 1;
        }
    }

    @Override
    public Commander getItem(int position){
        if (position == 0){
            return null;
        }
        return super.getItem(position - 1);
    }
}
