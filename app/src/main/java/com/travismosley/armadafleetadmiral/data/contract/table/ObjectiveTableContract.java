package com.travismosley.armadafleetadmiral.data.contract.table;

import android.provider.BaseColumns;

/**
 * Table Contract for Objective tables
 */
public interface ObjectiveTableContract extends BaseColumns {
    String TITLE = "title";
    String DESCRIPTION = "description";
    String VICTORY_POINTS = "victory_points";
    String TYPE_ID = "type_id";
    String TYPE_NAME = "type_name";
}
