package com.travismosley.armadafleetadmiral.adaptor.spinner;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.adaptor.list.ComponentListAdapter;
import com.travismosley.armadafleetadmiral.adaptor.list.UpgradesAdapter;
import com.travismosley.armadafleetadmiral.data.ArmadaDatabaseFacade;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.upgrade.TitleUpgrade;

/**
 * An ArrayAdapter for ship titles
 */
public class TitlesAdapter extends ComponentListAdapter<TitleUpgrade> {

    private final static String LOG_TAG = UpgradesAdapter.class.getSimpleName();
    private final Fleet mFleet;
    private final Ship mShip;

    public TitlesAdapter(Context context, Ship ship, Fleet fleet) {
        super(context, ArmadaDatabaseFacade.getInstance(context).getTitlesForShipClass(ship.vehicleClassId()));
        mShip = ship;
        mFleet = fleet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflateView(convertView, parent);
        if(position == 0){
            populateView(view, null);
        }else{
            TitleUpgrade titleUpgrade = getItem(position);
            populateView(view, titleUpgrade);
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public void populateView(View titleView, TitleUpgrade title) {
        Log.d(LOG_TAG, "populateView");
        TextView nameView = (TextView) titleView.findViewById(R.id.txt_upgrade_title);
        TextView pointsView = (TextView) titleView.findViewById(R.id.txt_point_cost);

        if (title == null){
            nameView.setText(mShip.title());
            pointsView.setVisibility(View.INVISIBLE);
        } else {
            nameView.setText(title.title());
            pointsView.setText(Integer.toString(title.pointCost()));
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
    public int getPosition(TitleUpgrade item) {
        int pos = super.getPosition(item);
        if (pos == -1){
            return pos;
        } else {
            return pos + 1;
        }
    }

    @Override
    public TitleUpgrade getItem(int position){
        if (position == 0){
            return null;
        }
        return super.getItem(position - 1);
    }
}
