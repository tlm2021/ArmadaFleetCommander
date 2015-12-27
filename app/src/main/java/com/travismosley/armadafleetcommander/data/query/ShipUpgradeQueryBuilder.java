package com.travismosley.armadafleetcommander.data.query;

import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipUpgradeSlotsTable;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public class ShipUpgradeQueryBuilder {

    private final String LOG_TAG = ShipUpgradeQueryBuilder.class.getSimpleName();

    private SQLiteQueryBuilder mQueryBuilder;
    private String[] mColumns = {
            ShipUpgradeSlotsTable.COLUMN_NAME_SHIP_ID,
            ShipUpgradeSlotsTable.COLUMN_NAME_UPGRADE_TYPE_ID,
            ShipUpgradeSlotsTable.COLUMN_NAME_UPGRADE_TYPE_NAME
    };

    private final static String mGroupBy = null;
    private final static String mOrderBy = ShipUpgradeSlotsTable.COLUMN_NAME_SHIP_ID;

    public ShipUpgradeQueryBuilder(){
        mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(ShipUpgradeSlotsTable.TABLE_NAME);
    }

    private String queryWhere(String whereClause){

        String query = mQueryBuilder.buildQuery(
                mColumns,     // SELECT <List>
                whereClause,  // WHERE <String>
                mGroupBy,     // GROUP BY <String>
                null,         // HAVING <String>
                mOrderBy,     // ORDER BY <String>
                null          // LIMIT <String>
        );
        Log.d(LOG_TAG, "Built query: " + query);
        return query;
    }

    public String queryAll(){
        // passing null returns all rows
        return queryWhere(null);
    }

    public String queryWhereShipId(int shipId){
        return queryWhere(ShipUpgradeSlotsTable.COLUMN_NAME_SHIP_ID + "=" + String.valueOf(shipId));
    }

}
