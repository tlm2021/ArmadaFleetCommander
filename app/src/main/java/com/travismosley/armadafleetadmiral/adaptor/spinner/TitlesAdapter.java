package com.travismosley.armadafleetadmiral.adaptor.spinner;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.adaptor.list.UpgradesAdapter;
import com.travismosley.armadafleetadmiral.data.ComponentDatabaseFacade;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.upgrade.TitleUpgrade;

/**
 * An ArrayAdapter for ship titles
 */
public class TitlesAdapter extends ComponentSpinnerAdapter<TitleUpgrade> {

    private final static String LOG_TAG = UpgradesAdapter.class.getSimpleName();
    private final Ship mShip;

    public TitlesAdapter(Context context, Ship ship, Fleet fleet) {
        super(context, ComponentDatabaseFacade.getInstance(context).getTitlesForShipClass(ship.vehicleClassId()));
        mShip = ship;
    }

    public void populateView(View titleView, TitleUpgrade title) {
        Log.d(LOG_TAG, "populateView");
        TextView nameView = (TextView) titleView.findViewById(R.id.txt_upgrade_title);
        TextView pointsView = (TextView) titleView.findViewById(R.id.txt_upgrade_points);

        if (title == null){
            Log.d(LOG_TAG, "populateView: nullView");
            nameView.setText(mShip.title());
            pointsView.setVisibility(View.INVISIBLE);
        } else {
            nameView.setText(title.title());
            pointsView.setText(String.format("%d", title.pointCost()));
            pointsView.setVisibility(View.VISIBLE);
        }
    }

    protected int getItemLayoutId(){
        Log.d(LOG_TAG, "getItemLayoutId");
        return R.layout.list_item_upgrade;
    }
}
