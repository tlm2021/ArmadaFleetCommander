package com.travismosley.armadafleetadmiral.data.factory;

import android.content.Context;
import android.util.Log;

import com.travismosley.armadafleetadmiral.data.ArmadaDatabaseFacade;
import com.travismosley.armadafleetadmiral.game.component.Ship;

import java.util.List;

/**
 * ComponentFactory class for Ship objects, with some extra handling for upgrade slots
 */
public class ShipFactory extends ComponentFactory<Ship>{

    private static final String LOG_TAG = ShipFactory.class.getSimpleName();

    private Context mContext;

    public ShipFactory(Context context){
        super(context);
        mContext = context;
    }

    public List<Ship> getForQuery(String query){

        List<Ship> shipList = super.getForQuery(query, Ship.class);
        Log.d(LOG_TAG, "getForQuery");
        ArmadaDatabaseFacade armadaDb = new ArmadaDatabaseFacade(mContext);

        for (int i = 0; i < shipList.size(); i++) {
            Ship ship = shipList.get(i);
            ship.setUpgradeSlots(armadaDb.getUpgradeSlotsForShip(ship.id()));
        }

        return shipList;
    }
}
