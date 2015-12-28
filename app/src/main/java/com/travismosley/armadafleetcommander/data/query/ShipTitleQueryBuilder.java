package com.travismosley.armadafleetcommander.data.query;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipTitleTable;
import com.travismosley.armadafleetcommander.data.query.base.BaseUpgradeQueryBuilder;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */

public class ShipTitleQueryBuilder extends BaseUpgradeQueryBuilder {

    private final String LOG_TAG = ShipTitleQueryBuilder.class.getSimpleName();

    protected String getTableName(){
        return ShipTitleTable.TABLE_NAME;
    }

    @Override
    protected String getOrderBy(){
        return ShipTitleTable.POINT_COST + "," + ShipTitleTable.TITLE;
    }

    private String getShipClassWhereClause(int shipClassId){
        return ShipTitleTable.SHIP_CLASS_ID + "=" + String.valueOf(shipClassId);
    }

    public String queryWhereShipClassId(int shipClassId){
        return queryWhere(getShipClassWhereClause(shipClassId));
    }

}
