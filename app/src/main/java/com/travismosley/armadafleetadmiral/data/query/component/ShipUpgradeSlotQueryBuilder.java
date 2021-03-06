package com.travismosley.armadafleetadmiral.data.query.component;

import com.travismosley.android.data.database.QueryBuilder;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract.ShipUpgradeSlotsTable;

import java.util.Arrays;
import java.util.List;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public class ShipUpgradeSlotQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = ShipUpgradeSlotQueryBuilder.class.getSimpleName();

    protected String getTableName(){
        return ShipUpgradeSlotsTable.TABLE_NAME;
    }

    protected List<String> getColumns(){
        return Arrays.asList(ShipUpgradeSlotsTable.SHIP_ID,
                ShipUpgradeSlotsTable.UPGRADE_TYPE_ID,
                ShipUpgradeSlotsTable.TITLE);
    }

    protected String getOrderBy(){
        return ShipUpgradeSlotsTable.SHIP_ID;
    }

    public String queryWhereShipId(int shipId){
        return queryWhere(ShipUpgradeSlotsTable.SHIP_ID + "=" + String.valueOf(shipId));
    }

}
