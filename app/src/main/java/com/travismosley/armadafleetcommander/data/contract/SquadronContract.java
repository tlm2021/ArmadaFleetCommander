package com.travismosley.armadafleetcommander.data.contract;

import android.provider.BaseColumns;

/**
 * Contract for fetching Squadron information from the DB
 */
public class SquadronContract {

    public SquadronContract() {}

    public static abstract class SquadronEntry implements BaseColumns{
        public static final String TABLE_NAME = "squadron_view";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POINT_COST = "point_cost";
        public static final String COLUMN_NAME_IS_UNIQUE = "is_unique";
        public static final String COLUMN_NAME_CLASS_TITLE = "class_title";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_HULL = "hull";
        public static final String COLUMN_NAME_FACTION_TITLE = "faction_title";
    }

}