package com.travismosley.armadafleetadmiral.data.contract;

import com.travismosley.android.data.database.contract.BaseTableContract;

import java.util.ArrayList;

/**
 * Contract for the Armada component database
 */

public abstract class FleetDatabaseContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fleets.db";

    public static class FleetTable extends BaseTableContract{

        // Table to store saved fleets
        public static final String TABLE_NAME = "fleet";
        public static final String NAME = "name";
        public static final String FACTION_ID = "faction_id";
        public static final String COMMANDER_ID = "commander_id";
        public static final String FLEET_POINT_LIMIT = "point_limit";
        public static final String FLEET_POINT_TOTAL = "point_total";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID          + " INTEGER PRIMARY KEY," +
                        NAME         + " TEXT NOT NULL, " +
                        FACTION_ID   + " INTEGER NOT NULL, " +
                        COMMANDER_ID + " INTEGER NOT NULL, " +
                        FLEET_POINT_LIMIT + " INTEGER NOT NULL, " +
                        FLEET_POINT_TOTAL + " INTEGER NOT NULL" +
                " )";
    }

    public static class ShipBuildTable extends BaseTableContract{

        // Table to store ship builds
        public static final String TABLE_NAME = "ship_build";
        public static final String SHIP_ID = "ship_id";
        public static final String CUSTOM_TITLE = "custom_title";
        public static final String TITLE_UPGRADE_ID = "title_upgrade_id";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID     + " INTEGER PRIMARY KEY, " +
                        SHIP_ID + " INTERGER NOT NULL, " +
                        CUSTOM_TITLE + " STRING, " +
                        TITLE_UPGRADE_ID + " INTEGER" +
                " )";
    }

    public static class FleetShipBuildTable extends BaseTableContract{

        // Table to link ship builds to fleets
        public static final String TABLE_NAME = "fleet_ship_build";
        public static final String FLEET_ID = "fleet_id";
        public static final String SHIP_BUILD_ID = "ship_build_id";
        public static final String FLAGSHIP = "flagship";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        getForeignKeyColumnSql(FLEET_ID, FleetTable.TABLE_NAME) + ", " +
                        getForeignKeyColumnSql(SHIP_BUILD_ID, ShipBuildTable.TABLE_NAME) + ", " +
                        FLAGSHIP + " INTEGER " +
                        " )";
    }

    public static class FleetSquadronsTable extends BaseTableContract{

        public static final String TABLE_NAME = "fleet_squadrons";
        public static final String FLEET_ID = "fleet_id";
        public static final String SQUADRON_ID = "squadron_id";
        public static final String COUNT = "count";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID         + " INTEGER PRIMARY KEY," +
                        getForeignKeyColumnSql(FLEET_ID, FleetTable.TABLE_NAME) + ", " +
                        SQUADRON_ID + " INTEGER " + ", " +
                        COUNT       + " INTEGER" +
                        " )";
    }

    public static abstract class ShipBuildUpgradesTable extends BaseTableContract{

        public static final String TABLE_NAME = "ship_build_upgrades";
        public static final String SHIP_BUILD_ID = "ship_build_id";
        public static final String UPGRADE_ID = "upgrade_id";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID        + " INTEGER PRIMARY KEY, " +
                        getForeignKeyColumnSql(SHIP_BUILD_ID, ShipBuildTable.TABLE_NAME) + ", " +
                        UPGRADE_ID + " INTEGER " +
                        " )";
    }

    public static abstract class FleetShipView{
        public static final String VIEW_NAME = "fleet_ship_view";
        public static final String FLEET_ID = "fleet_id";
        public static final String SHIP_BUILD_ID = "ship_build_id";
        public static final String SHIP_ID = "ship_id";
        public static final String CUSTOM_TITLE = "custom_title";
        public static final String TITLE_UPGRADE_ID = "title_upgrade_id";

        public static final String SQL_CREATE_VIEW =
                "CREATE VIEW " + VIEW_NAME + " AS " +
                        "SELECT " + FleetTable.TABLE_NAME + "." + FleetTable._ID +
                            " AS " + FLEET_ID + ", " +
                        ShipBuildTable.TABLE_NAME + "." + ShipBuildTable._ID +
                            " AS " + SHIP_BUILD_ID + ", " +
                        ShipBuildTable.TABLE_NAME + "." + ShipBuildTable.SHIP_ID +
                            " AS " + SHIP_ID + ", " +
                        ShipBuildTable.TABLE_NAME + "." + ShipBuildTable.CUSTOM_TITLE +
                            " AS " + CUSTOM_TITLE + ", " +
                        ShipBuildTable.TABLE_NAME + "." + ShipBuildTable.TITLE_UPGRADE_ID +
                            " AS " + TITLE_UPGRADE_ID +
                        " FROM " + FleetTable.TABLE_NAME +
                        " JOIN " + FleetShipBuildTable.TABLE_NAME +
                            " ON " + FleetShipBuildTable.TABLE_NAME + "." + FleetShipBuildTable.FLEET_ID +
                                " = " + FleetTable.TABLE_NAME + "." + FleetTable._ID +
                        " JOIN " + ShipBuildTable.TABLE_NAME +
                            " ON " + FleetShipBuildTable.TABLE_NAME + "." + FleetShipBuildTable.SHIP_BUILD_ID +
                                " = " + ShipBuildTable.TABLE_NAME + "." + ShipBuildTable._ID;
    }

    public static ArrayList<String> getOnCreateSqlCommands(){
        ArrayList<String> commands = new ArrayList<>();
        commands.add(FleetTable.SQL_CREATE_TABLE);
        commands.add(ShipBuildTable.SQL_CREATE_TABLE);
        commands.add(ShipBuildUpgradesTable.SQL_CREATE_TABLE);
        commands.add(FleetShipBuildTable.SQL_CREATE_TABLE);
        commands.add(FleetSquadronsTable.SQL_CREATE_TABLE);
        commands.add(FleetShipView.SQL_CREATE_VIEW);

        return commands;
    }

}
