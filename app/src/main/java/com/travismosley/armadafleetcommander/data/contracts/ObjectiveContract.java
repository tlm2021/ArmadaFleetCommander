package com.travismosley.armadafleetcommander.data.contracts;

import android.provider.BaseColumns;

/**
 * Created by Travis on 30/11/2015.
 */

public final class ObjectiveContract {

    public ObjectiveContract() {}

    public static abstract class ObjectiveEntry implements BaseColumns{
        public static final String TABLE_NAME = "objective";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_TEXT = "text";
    }
}
