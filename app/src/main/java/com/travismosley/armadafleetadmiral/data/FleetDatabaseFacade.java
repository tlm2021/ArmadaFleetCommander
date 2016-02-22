package com.travismosley.armadafleetadmiral.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract.FleetShipView;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract.FleetShipBuildTable;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract.FleetSquadronsTable;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract.FleetTable;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract.ShipBuildTable;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract.ShipBuildUpgradesTable;
import com.travismosley.armadafleetadmiral.data.query.FleetQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.FleetShipQueryBuilder;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Facade for extracting Armada information from the fleet database
 */
public class FleetDatabaseFacade {

    private static final String LOG_TAG = FleetDatabaseFacade.class.getSimpleName();
    private static FleetDatabaseFacade mInstance;
    private static FleetDatabaseHelper mFleetDbHelper;
    private static ComponentDatabaseFacade mComponentDbFacade;

    private SQLiteDatabase mDb;

    protected FleetDatabaseFacade(Context context){
        mFleetDbHelper = new FleetDatabaseHelper(context);
        mComponentDbFacade = new ComponentDatabaseFacade(context);
    }

    public static FleetDatabaseFacade getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FleetDatabaseFacade(context);
        }
        return mInstance;
    }

    public List<Integer> getUpgradeIdsForShipBuild(int shipBuildId){

        List<Integer> upgradeIds = new ArrayList<>();
        SQLiteDatabase db = mFleetDbHelper.getReadableDatabase();

        String query = "SELECT " + ShipBuildUpgradesTable.UPGRADE_ID +
                " FROM " + ShipBuildUpgradesTable.TABLE_NAME +
                " WHERE " + ShipBuildUpgradesTable.SHIP_BUILD_ID +
                " = " + String.valueOf(shipBuildId);

        Log.d(LOG_TAG, "getUpgradeIdsForShipBuild query: " + query);
        Cursor cursor = (Cursor) db.rawQuery(query, null);

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            upgradeIds.add(cursor.getInt(ShipBuildUpgradesTable.UPGRADE_ID));
        }

        cursor.close();
        db.close();

        Collections.sort(upgradeIds);

        return upgradeIds;
    }

    public int getBuildForUpgrades(List<Upgrade> buildUpgrades){
        /*
        SELECT ship_build_id
        FROM ship_build_upgrade
        WHERE ship_build_upgrade.upgrade_id IN (11, 12)
        GROUP BY ship_build_upgrade.ship_build_id
        HAVING COUNT(*) = 2
        INTERSECT
        SELECT ship_build_id
        FROM ship_build_upgrade
        GROUP BY ship_build_upgrade.ship_build_id
        HAVING COUNT(*) = 2;
         */

        String idList = "(" + StringUtils.join(buildUpgrades, ",") + ")";

        String query =
                // Match ships that have all the upgrades in the list
                "SELECT " + ShipBuildUpgradesTable.SHIP_BUILD_ID +
                        " FROM " + ShipBuildUpgradesTable.TABLE_NAME +
                        " WHERE " + ShipBuildUpgradesTable.UPGRADE_ID + " IN " + idList +
                        " GROUP BY " + ShipBuildUpgradesTable.SHIP_BUILD_ID +
                        " HAVING COUNT(*) = " + String.valueOf(buildUpgrades.size()) +
                        // And only the upgrades in the list
                        " INTERSECT " +
                        " SELECT " + ShipBuildUpgradesTable.SHIP_BUILD_ID +
                        " FROM " + ShipBuildUpgradesTable.TABLE_NAME +
                        " GROUP BY " + ShipBuildUpgradesTable.SHIP_BUILD_ID +
                        " HAVING COUNT(*) = " + String.valueOf(buildUpgrades.size());

        Log.d(LOG_TAG, "getBuildForUpgrades query: " + query);

        SQLiteDatabase db = mFleetDbHelper.getReadableDatabase();
        Cursor cursor = (Cursor) db.rawQuery(query, null);
        cursor.moveToPosition(0);

        int matchedShipBuildId = cursor.getInt(ShipBuildUpgradesTable.SHIP_BUILD_ID);

        cursor.close();
        db.close();
        return matchedShipBuildId;
    }

    public long getOrAddShipBuild(Ship ship){
        long buildId = getBuildForUpgrades(ship.getEquippedUpgrades());
        if (buildId == -1){
            buildId = addShipBuild(ship);
        }
        return buildId;
    }

    private long addShipBuild(Ship ship){

        ContentValues values = new ContentValues();

        // Add the custom title (may be null) and upgrade id if available
        values.put(ShipBuildTable.SHIP_ID, ship.id());
        values.put(ShipBuildTable.CUSTOM_TITLE, ship.customTitle());
        if (ship.hasTitleUpgrade()){
            values.put(ShipBuildTable.TITLE_UPGRADE_ID, ship.titleUpgrade().id());
        }

        long shipBuildId = getDatabase().insert(ShipBuildTable.TABLE_NAME, null, values);

        values.clear();
        values.put(ShipBuildUpgradesTable.SHIP_BUILD_ID, shipBuildId);
        closeDatabase();

        List<Upgrade> shipUpgrades = ship.getEquippedUpgrades();
        for (int i=0; i < shipUpgrades.size(); i++){
            values.put(ShipBuildUpgradesTable.UPGRADE_ID, shipUpgrades.get(i).id());
            getDatabase().insert(ShipBuildUpgradesTable.TABLE_NAME, null, values);
        }

        closeDatabase();
        return shipBuildId;
    }

    public long addFleet(Fleet fleet){

        // Create a new fleet
        ContentValues values = new ContentValues();
        values.put(FleetTable.NAME, fleet.name());
        values.put(FleetTable.COMMANDER_ID, fleet.commander().id());
        values.put(FleetTable.FACTION_ID, fleet.mFactionId);
        values.put(FleetTable.FLEET_POINT_LIMIT, fleet.fleetPointLimit());
        values.put(FleetTable.FLEET_POINT_TOTAL, fleet.fleetPoints());

        long fleetId = getDatabase().insert(FleetTable.TABLE_NAME, null, values);


        // Add the ship builds
        values.clear();
        values.put(FleetShipBuildTable.FLEET_ID, fleetId);

        for (int i=0; i < fleet.mShips.size(); i++){
            Ship ship = fleet.mShips.get(i);
            values.put(FleetShipBuildTable.SHIP_BUILD_ID, getOrAddShipBuild(fleet.mShips.get(i)));
            values.put(FleetShipBuildTable.FLAGSHIP, ship.isFlagship() ? 1 : 0);
            getDatabase().insert(FleetShipBuildTable.TABLE_NAME, null, values);
        }

        values.clear();
        values.put(FleetSquadronsTable.FLEET_ID, fleetId);

        // Add the squadron counts
        Iterator<Map.Entry<Integer, Integer>> squadCountIterator = fleet.squadronCounts().entrySet().iterator();

        while (squadCountIterator.hasNext()){
            Map.Entry<Integer, Integer> squadCount = squadCountIterator.next();
            values.put(FleetSquadronsTable.SQUADRON_ID, squadCount.getKey());
            values.put(FleetSquadronsTable.COUNT, squadCount.getValue());
            getDatabase().insert(FleetSquadronsTable.TABLE_NAME, null, values);
        }

        closeDatabase();
        return fleetId;
    }

    public Fleet getFleet(int fleetId){
        SQLiteDatabase db = getDatabase();
        FleetQueryBuilder fleetQueryBuilder = new FleetQueryBuilder();
        Cursor cursor = (Cursor) db.rawQuery(fleetQueryBuilder.queryWhereFleetId(fleetId), null);

        Fleet fleet = new Fleet();
        fleet.populate(cursor);

        fleet.mShips = getShipsForFleet(fleetId);

        return fleet;
    }

    public List<Ship> getShipsForFleet(int fleetId){

        ArrayList<Ship> shipList = new ArrayList<>();
        SQLiteDatabase db = getDatabase();

        FleetShipQueryBuilder shipQueryBuilder = new FleetShipQueryBuilder();
        Cursor cursor = (Cursor) db.rawQuery(shipQueryBuilder.queryWhereFleetId(fleetId), null);

        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Ship ship = mComponentDbFacade.getShipForShipId(cursor.getInt(FleetShipView.SHIP_ID));
            shipList.add(ship);
        }

        return shipList;
    }

    private SQLiteDatabase getDatabase(){
        if (mDb == null) {
            mDb = mFleetDbHelper.getWritableDatabase();
        }
        return mDb;
    }

    private void closeDatabase(){
        if (mDb != null) {
            mDb.close();
            mDb = null;
        }
    }
}