package com.travismosley.armadafleetadmiral.data.contract;

import android.provider.BaseColumns;

import com.travismosley.armadafleetadmiral.data.contract.table.ObjectiveTableContract;
import com.travismosley.armadafleetadmiral.data.contract.table.UpgradeTableContract;
import com.travismosley.armadafleetadmiral.data.contract.table.VehicleTableContract;

/**
 * Contract for the Armada component database
 */
public class FleetDatabaseContract {

    public static final int DATABASE_VERSION = 1;

    public FleetDatabaseContract() {}

    public static abstract class FleetTable implements BaseColumns{
        // Table to store saved fleets
        public static final String TABLE_NAME = "saved_fleet";
        public static final String NAME = "name";
        public static final String FACTION_ID = "faction_id";
        public static final String COMMANDER_ID = "commander_id";
    }

    public static abstract class ShipBuildTable implements BaseColumns{
        // Table to store ship builds
        public static final String TABLE_NAME = "ship_build";
        public static final String FLAGSHIP = "flagship";
    }

    public static abstract class FleetShipBuild implements BaseColumns{
        // Table to link ship builds to fleets
        public static final String TABLE_NAME = "fleet_ship_build";
        public static final String FLEET_ID = "fleet_id";
        public static final String SHIP_BUILD_ID = "ship_build_id";
    }

    public static abstract class FleetSquadrons implements BaseColumns{
        public static final String TABLE_NAME = "fleet_squadrons";
        public static final String FLEET_ID = "fleet_id";
        public static final String SQUADRON_ID = "squadron_id";
        public static final String COUNT = "count";
    }

    public static abstract class ShipBuildUpgrades implements BaseColumns{
        public static final String TABLE_NAME = "ship_build_upgrades";
        public static final String SHIP_BUILD_ID = "ship_build_id";
        public static final String UPGRADE_ID = "upgrade_id";
    }

}
