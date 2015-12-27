package com.travismosley.armadafleetcommander.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.android.data.database.cursor.CursorFactory;
import com.travismosley.android.data.database.logging.ColumnIndexLogger;
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipTable;
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipUpgradeSlotsTable;
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.SquadronTable;
import com.travismosley.armadafleetcommander.data.query.ShipQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.ShipUpgradeQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.SquadronQueryBuilder;
import com.travismosley.armadafleetcommander.game.component.Ship;
import com.travismosley.armadafleetcommander.game.component.Squadron;
import com.travismosley.armadafleetcommander.game.component.upgrade.UpgradeSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * A Facade class for extracting Armada information from the database as
 * internal classes. Helps out with encapsulation and type conversion;
 */

public class ArmadaDatabaseFacade{

    private static final String LOG_TAG = ArmadaDatabaseFacade.class.getSimpleName();
    private ArmadaDatabaseHelper mArmadaDbHelper;
    private final ColumnIndexLogger mIdxLogger = new ColumnIndexLogger(LOG_TAG);

    public ArmadaDatabaseFacade(Context context) {
        mArmadaDbHelper = new ArmadaDatabaseHelper(context);
        mArmadaDbHelper.setCursorFactory(new CursorFactory());
    }

    public List<Squadron> getSquadrons() {
        SquadronQueryBuilder queryBuilder = new SquadronQueryBuilder();
        return getSquadronsForQuery(queryBuilder.queryAll());
    }

    public List<Squadron> getSquadronsForFaction(int factionId){
        SquadronQueryBuilder queryBuilder = new SquadronQueryBuilder();
        return getSquadronsForQuery(queryBuilder.queryWhereFactionId(factionId));
    }

    private List<Squadron> getSquadronsForQuery(String query){
        SQLiteDatabase db = mArmadaDbHelper.getDatabase();
        Cursor cursor = (Cursor) db.rawQuery(query, null);
        List<Squadron> squadrons = new ArrayList<>();

        Log.d(LOG_TAG, "Found " + cursor.getCount() + " squadrons.");

        Log.d(LOG_TAG, "Column Indices:");
        mIdxLogger.logIndex(SquadronTable.COLUMN_NAME_ID, cursor);
        mIdxLogger.logIndex(SquadronTable.COLUMN_NAME_TITLE, cursor);
        mIdxLogger.logIndex(SquadronTable.COLUMN_NAME_CLASS_TITLE, cursor);
        mIdxLogger.logIndex(SquadronTable.COLUMN_NAME_IS_UNIQUE, cursor);
        mIdxLogger.logIndex(SquadronTable.COLUMN_NAME_HULL, cursor);
        mIdxLogger.logIndex(SquadronTable.COLUMN_NAME_SPEED, cursor);
        mIdxLogger.logIndex(SquadronTable.COLUMN_NAME_POINT_COST, cursor);

        for (int i = 0; i < cursor.getCount(); i++) {
            Squadron squad = new Squadron();
            cursor.moveToPosition(i);
            squad.populate(cursor);
            squadrons.add(squad);
        }

        cursor.close();
        db.close();
        return squadrons;
    }

    public List<Ship> getShips() {
        ShipQueryBuilder queryBuilder = new ShipQueryBuilder();
        return getShipsForQuery(queryBuilder.queryAll());
    }

    private List<Ship> getShipsForQuery(String query) {
        SQLiteDatabase db = mArmadaDbHelper.getDatabase();
        Cursor cursor = (Cursor) db.rawQuery(query, null);
        List<Ship> ships = new ArrayList<>();

        Log.d(LOG_TAG, "Found " + cursor.getCount() + " squadrons.");

        Log.d(LOG_TAG, "Column Indices:");
        mIdxLogger.logIndex(ShipTable.COLUMN_NAME_ID, cursor);
        mIdxLogger.logIndex(ShipTable.COLUMN_NAME_TITLE, cursor);
        mIdxLogger.logIndex(ShipTable.COLUMN_NAME_CLASS_TITLE, cursor);
        mIdxLogger.logIndex(ShipTable.COLUMN_NAME_HULL, cursor);
        mIdxLogger.logIndex(ShipTable.COLUMN_NAME_SPEED, cursor);
        mIdxLogger.logIndex(ShipTable.COLUMN_NAME_POINT_COST, cursor);

        for (int i = 0; i < cursor.getCount(); i++) {
            Ship ship = new Ship();
            cursor.moveToPosition(i);
            ship.populate(cursor);
            ship.setUpgradeSlots(getUpgradeSlotsForShip(ship.id()));

            ships.add(ship);
        }

        cursor.close();
        db.close();
        return ships;
    }

    public List<Ship> getShipsForFaction(int factionId){
        ShipQueryBuilder queryBuilder = new ShipQueryBuilder();
        return getShipsForQuery(queryBuilder.queryWhereFactionId(factionId));
    }

    private List<UpgradeSlot> getUpgradeSlotsForQuery(String query){
        SQLiteDatabase db = mArmadaDbHelper.getDatabase();
        Cursor cursor = (Cursor) db.rawQuery(query, null);
        List<UpgradeSlot> upgradeSlots = new ArrayList<>();

        Log.d(LOG_TAG, "Found " + cursor.getCount() + " upgrade slots.");
        Log.d(LOG_TAG, "Column Indices:");
        mIdxLogger.logIndex(ShipUpgradeSlotsTable.COLUMN_NAME_SHIP_ID, cursor);
        mIdxLogger.logIndex(ShipUpgradeSlotsTable.COLUMN_NAME_UPGRADE_TYPE_ID, cursor);
        mIdxLogger.logIndex(ShipUpgradeSlotsTable.COLUMN_NAME_UPGRADE_TYPE_NAME, cursor);

        for (int i = 0; i < cursor.getCount(); i++) {
            UpgradeSlot upgradeSlot = new UpgradeSlot();
            cursor.moveToPosition(i);
            upgradeSlot.populate(cursor);
            upgradeSlots.add(upgradeSlot);
        }

        cursor.close();
        db.close();
        return upgradeSlots;
    }

    public List<UpgradeSlot> getUpgradeSlotsForShip(int shipId){
        ShipUpgradeQueryBuilder queryBuilder = new ShipUpgradeQueryBuilder();
        return getUpgradeSlotsForQuery(queryBuilder.queryWhereShipId(shipId));
    }

    // Some helper methods for fetching values from the cursor

}
