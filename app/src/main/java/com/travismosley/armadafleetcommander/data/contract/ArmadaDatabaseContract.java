package com.travismosley.armadafleetcommander.data.contract;

import android.provider.BaseColumns;

/**
 * Contract for the Armada component database
 */
public class ArmadaDatabaseContract {

    public static final int DATABASE_VERSION   = 1;
    public static final String DATABASE_NAME = "armada_fleet_commander.db";

    public ArmadaDatabaseContract() {}

    public static abstract class SquadronTable implements BaseColumns{
        public static final String TABLE_NAME = "squadron_view";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POINT_COST = "point_cost";
        public static final String COLUMN_NAME_IS_UNIQUE = "is_unique";
        public static final String COLUMN_NAME_CLASS_TITLE = "class_title";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_HULL = "hull";
        public static final String COLUMN_NAME_FACTION_ID = "faction_id";
    }

    public static abstract class ShipTable implements BaseColumns {
        public static final String TABLE_NAME = "ship_view";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POINT_COST = "point_cost";
        public static final String COLUMN_NAME_CLASS_TITLE = "class_title";
        public static final String COLUMN_NAME_HULL = "hull";
        public static final String COLUMN_NAME_SPEED = "max_speed";
        public static final String COLUMN_NAME_FACTION_ID = "faction_id";
    }

    public static abstract class ObjectiveTable implements BaseColumns{
        public static final String TABLE_NAME = "objective";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_TEXT = "text";
    }

}