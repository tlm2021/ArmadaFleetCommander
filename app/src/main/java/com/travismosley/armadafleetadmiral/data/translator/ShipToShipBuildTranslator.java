package com.travismosley.armadafleetadmiral.data.translator;

import android.content.Context;

import com.travismosley.armadafleetadmiral.data.FleetDatabaseFacade;
import com.travismosley.armadafleetadmiral.data.FleetDatabaseHelper;

/**
 * Created by Travis on 2/11/2016.
 */
public class ShipToShipBuildTranslator {

    private final FleetDatabaseFacade mDbHelper;

    public ShipToShipBuildTranslator(Context context){
        mDbHelper = FleetDatabaseFacade.getInstance(context);
    }
}
