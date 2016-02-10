package com.travismosley.armadafleetadmiral.data.contract;

import android.provider.BaseColumns;

import com.travismosley.armadafleetadmiral.data.contract.table.BaseTableContract;
import com.travismosley.armadafleetadmiral.data.contract.table.ObjectiveTableContract;
import com.travismosley.armadafleetadmiral.data.contract.table.UpgradeTableContract;
import com.travismosley.armadafleetadmiral.data.contract.table.VehicleTableContract;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Contract for the Armada component database
 */

public class FleetDatabaseContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fleets.db";
    public FleetDatabaseContract() {}

    public static abstract class FleetTable extends BaseTableContract{

        // Table to store saved fleets
        public static final String TABLE_NAME = "saved_fleet";
        public static final String NAME = "name";
        public static final String FACTION_ID = "faction_id";
        public static final String COMMANDER_ID = "commander_id";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID          + " INTEGER PRIMARY KEY," +
                        NAME         + " TEXT, " +
                        FACTION_ID   + " INTEGER, " +
                        COMMANDER_ID + " INTEGER" +
                " )";
    }

    public static abstract class ShipBuildTable extends BaseTableContract{

        // Table to store ship builds
        public static final String TABLE_NAME = "ship_build";
        public static final String FLAGSHIP = "flagship";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID      + " INTEGER PRIMARY KEY," +
                        FLAGSHIP + " INTEGER " +
                " )";
    }

    public static abstract class FleetShipBuildTable extends BaseTableContract{

        // Table to link ship builds to fleets
        public static final String TABLE_NAME = "fleet_ship_build";
        public static final String FLEET_ID = "fleet_id";
        public static final String SHIP_BUILD_ID = "ship_build_id";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        FleetTable.getForeignKeyColumnSql(FLEET_ID) + "," +
                        ShipBuildTable.getForeignKeyColumnSql(SHIP_BUILD_ID) +
                " )";
    }

    public static abstract class FleetSquadronsTable extends BaseTableContract{

        public static final String TABLE_NAME = "fleet_squadrons";
        public static final String FLEET_ID = "fleet_id";
        public static final String SQUADRON_ID = "squadron_id";
        public static final String COUNT = "count";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID         + " INTEGER PRIMARY KEY," +
                        FleetTable.getForeignKeyColumnSql(FLEET_ID) +
                        SQUADRON_ID + " INTEGER " +
                        COUNT       + " INTEGER" +
                " )";
    }

    public static abstract class ShipBuildUpgradesTable extends BaseTableContract{
        public static final String TABLE_NAME = "ship_build_upgrades";
        public static final String SHIP_BUILD_ID = "ship_build_id";
        public static final String UPGRADE_ID = "upgrade_id";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID           + " INTEGER PRIMARY KEY," +
                        ShipBuildTable.getForeignKeyColumnSql(SHIP_BUILD_ID) +
                        UPGRADE_ID    + " INTEGER " +
                " )";
    }

    public static final ArrayList<String> getOnCreateSqlCommands(){
        ArrayList<String> commands = new ArrayList<>();
        commands.add(FleetTable.SQL_CREATE_TABLE);
        commands.add(ShipBuildTable.SQL_CREATE_TABLE);
        commands.add(ShipBuildUpgradesTable.SQL_CREATE_TABLE);
        commands.add(FleetShipBuildTable.SQL_CREATE_TABLE);
        commands.add(FleetSquadronsTable.SQL_CREATE_TABLE);

        return commands;
    }

}
