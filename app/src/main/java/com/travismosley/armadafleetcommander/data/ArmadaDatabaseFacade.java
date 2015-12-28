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
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.UpgradeTable;
import com.travismosley.armadafleetcommander.data.query.ShipQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.ShipUpgradeSlotQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.SquadronQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.UpgradeQueryBuilder;
import com.travismosley.armadafleetcommander.game.component.Ship;
import com.travismosley.armadafleetcommander.game.component.Squadron;
import com.travismosley.armadafleetcommander.game.component.upgrade.Upgrade;
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

    /* Squadron Queries */

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
        mIdxLogger.logIndex(SquadronTable._ID, cursor);
        mIdxLogger.logIndex(SquadronTable.TITLE, cursor);
        mIdxLogger.logIndex(SquadronTable.CLASS_TITLE, cursor);
        mIdxLogger.logIndex(SquadronTable.IS_UNIQUE, cursor);
        mIdxLogger.logIndex(SquadronTable.HULL, cursor);
        mIdxLogger.logIndex(SquadronTable.SPEED, cursor);
        mIdxLogger.logIndex(SquadronTable.POINT_COST, cursor);

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

    /* Ship Queries */

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
        mIdxLogger.logIndex(ShipTable._ID, cursor);
        mIdxLogger.logIndex(ShipTable.TITLE, cursor);
        mIdxLogger.logIndex(ShipTable.CLASS_TITLE, cursor);
        mIdxLogger.logIndex(ShipTable.HULL, cursor);
        mIdxLogger.logIndex(ShipTable.SPEED, cursor);
        mIdxLogger.logIndex(ShipTable.POINT_COST, cursor);

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

    /* Upgrade Slot Queries */

    private List<UpgradeSlot> getUpgradeSlotsForQuery(String query){
        SQLiteDatabase db = mArmadaDbHelper.getDatabase();
        Cursor cursor = (Cursor) db.rawQuery(query, null);
        List<UpgradeSlot> upgradeSlots = new ArrayList<>();

        Log.d(LOG_TAG, "Found " + cursor.getCount() + " upgrade slots.");
        Log.d(LOG_TAG, "Column Indices:");
        mIdxLogger.logIndex(ShipUpgradeSlotsTable.SHIP_ID, cursor);
        mIdxLogger.logIndex(ShipUpgradeSlotsTable.UPGRADE_TYPE_ID, cursor);
        mIdxLogger.logIndex(ShipUpgradeSlotsTable.UPGRADE_TYPE_NAME, cursor);

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
        ShipUpgradeSlotQueryBuilder queryBuilder = new ShipUpgradeSlotQueryBuilder();
        return getUpgradeSlotsForQuery(queryBuilder.queryWhereShipId(shipId));
    }

    /* Upgrade Queries */

    public List<Upgrade> getUpgradesForTypeAndFaction(int typeId, int factionId){
        UpgradeQueryBuilder queryBuilder = new UpgradeQueryBuilder();
        return getUpgradesForQuery(queryBuilder.queryWhereTypeIdAndFactionId(typeId, factionId));
    }

    private List<Upgrade> getUpgradesForQuery(String query){
        SQLiteDatabase db = mArmadaDbHelper.getDatabase();
        Cursor cursor = (Cursor) db.rawQuery(query, null);
        List<Upgrade> upgrades = new ArrayList<>();

        Log.d(LOG_TAG, "Found " + cursor.getCount() + " upgrades.");
        Log.d(LOG_TAG, "Column Indices:");
        mIdxLogger.logIndex(UpgradeTable._ID, cursor);
        mIdxLogger.logIndex(UpgradeTable.FACTION_ID, cursor);
        mIdxLogger.logIndex(UpgradeTable.TYPE_ID, cursor);
        mIdxLogger.logIndex(UpgradeTable.TYPE_NAME, cursor);
        mIdxLogger.logIndex(UpgradeTable.TITLE, cursor);
        mIdxLogger.logIndex(UpgradeTable.TEXT, cursor);
        mIdxLogger.logIndex(UpgradeTable.IS_UNIQUE, cursor);
        mIdxLogger.logIndex(UpgradeTable.POINT_COST, cursor);

        for (int i=0; i < cursor.getCount(); i++){
            Upgrade upgrade = new Upgrade();
            cursor.moveToPosition(i);
            upgrade.populate(cursor);
            upgrades.add(upgrade);
        }

        cursor.close();
        db.close();
        return upgrades;
    }

}
