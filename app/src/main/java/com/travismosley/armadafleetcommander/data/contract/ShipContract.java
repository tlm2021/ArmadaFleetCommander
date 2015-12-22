package com.travismosley.armadafleetcommander.data.contract;

import android.provider.BaseColumns;

/**
 * Created by Travis on 22/12/2015.
 */
public class ShipContract {

    public ShipContract(){}

    public static abstract class ShipEntry implements BaseColumns {
        public static final String TABLE_NAME = "ship_view";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POINT_COST = "point_cost";
        public static final String COLUMN_NAME_CLASS_TITLE = "class_title";
        public static final String COLUMN_NAME_HULL = "hull";
        public static final String COLUMN_NAME_SPEED = "max_speed";
        public static final String COLUMN_NAME_FACTION_ID = "faction_id";
    }
}
