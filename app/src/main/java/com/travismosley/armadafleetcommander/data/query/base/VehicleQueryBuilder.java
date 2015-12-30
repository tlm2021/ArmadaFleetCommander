package com.travismosley.armadafleetcommander.data.query.base;

import com.travismosley.armadafleetcommander.data.contract.table.VehicleTableContract;

import java.util.List;

/**
 * A helper class around SQLiteQUeryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public abstract class VehicleQueryBuilder extends ComponentQueryBuilder {

    private final String LOG_TAG = VehicleQueryBuilder.class.getSimpleName();

    @Override
    protected List<String> getColumns(){
        List<String> columns = super.getColumns();
        columns.add(VehicleTableContract.CLASS_TITLE);
        columns.add(VehicleTableContract.CLASS_ID);
        columns.add(VehicleTableContract.HULL);
        columns.add(VehicleTableContract.SPEED);
        return columns;
    }

    @Override
    protected String getOrderBy(){
        return VehicleTableContract.CLASS_ID + "," + VehicleTableContract.TITLE;
    }
}
