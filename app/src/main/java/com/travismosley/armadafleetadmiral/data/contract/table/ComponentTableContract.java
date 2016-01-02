package com.travismosley.armadafleetadmiral.data.contract.table;

import android.provider.BaseColumns;

/**
 * Base contract class for all views containing game-components
 */
public interface ComponentTableContract extends BaseColumns{

    String TITLE = "title";
    String POINT_COST = "point_cost";
    String FACTION_ID = "faction_id";

}
