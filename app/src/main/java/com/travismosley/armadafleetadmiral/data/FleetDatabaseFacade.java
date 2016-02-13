package com.travismosley.armadafleetadmiral.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract.ShipBuildUpgradesTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Facade for extracting Armada information from the fleet database
 */
public class FleetDatabaseFacade {

    private static final String LOG_TAG = FleetDatabaseFacade.class.getSimpleName();
    private static FleetDatabaseFacade mInstance;
    private static FleetDatabaseHelper mDbHelper;

    protected FleetDatabaseFacade(Context context){
        mDbHelper = new FleetDatabaseHelper(context);
    }

    public static FleetDatabaseFacade getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FleetDatabaseFacade(context);
        }
        return mInstance;
    }

    private List<Integer> getUpgradeIdsForShipBuild(int shipBuildId){

        List<Integer> upgradeIds = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = (Cursor) db.rawQuery(
                "SELECT " + ShipBuildUpgradesTable.UPGRADE_ID +
                        " FROM " + ShipBuildUpgradesTable.TABLE_NAME +
                        " WHERE " + ShipBuildUpgradesTable.SHIP_BUILD_ID +
                        " = " + String.valueOf(shipBuildId), null);

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            upgradeIds.add(cursor.getInt(ShipBuildUpgradesTable.UPGRADE_ID));
        }
        Collections.sort(upgradeIds);

        return upgradeIds;
    }

}
