package com.travismosley.armadafleetadmiral.data.contract;

import android.provider.BaseColumns;

import com.travismosley.armadafleetadmiral.data.contract.table.UpgradeTableContract;
import com.travismosley.armadafleetadmiral.data.contract.table.VehicleTableContract;

/**
 * Contract for the Armada component database
 */
public class ArmadaDatabaseContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "armada_fleet_commander.db";

    public ArmadaDatabaseContract() {}

    public static abstract class SquadronTable implements VehicleTableContract{

        public static final String TABLE_NAME = "squadron_view";
        public static final String IS_UNIQUE = "is_unique";
    }

    public static abstract class ShipTable implements VehicleTableContract {

        public static final String TABLE_NAME = "ship_view";
    }

    public static abstract class UpgradeTable implements UpgradeTableContract{
        public static final String TABLE_NAME = "upgrade_view";
    }

    public static abstract class ShipUpgradeSlotsTable implements BaseColumns{
        public static final String TABLE_NAME = "ship_upgrade_slots_view";
        public static final String SHIP_ID = "ship_id";
        public static final String UPGRADE_TYPE_ID = "upgrade_type_id";
        public static final String UPGRADE_TYPE_NAME = "upgrade_type_name";
    }

    public static abstract class ShipTitleTable implements UpgradeTableContract {
        public static final String TABLE_NAME = "ship_title_view";
    }

    public static abstract class CommanderTable implements UpgradeTableContract{
        public static final String TABLE_NAME = "commander_view";
    }

}
