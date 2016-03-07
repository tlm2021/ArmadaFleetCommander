package com.travismosley.armadafleetadmiral.data;

import android.content.Context;

import com.travismosley.armadafleetadmiral.data.factory.ComponentFactory;
import com.travismosley.armadafleetadmiral.data.query.component.CommanderQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ObjectiveQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ShipQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ShipTitleQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ShipUpgradeSlotQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.SquadronQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.UpgradeQueryBuilder;
import com.travismosley.armadafleetadmiral.game.component.Objective;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.Squadron;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Commander;
import com.travismosley.armadafleetadmiral.game.component.upgrade.TitleUpgrade;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetadmiral.game.component.upgrade.UpgradeSlot;

import java.util.List;

/**
 * A Facade class for extracting Armada information from the database as
 * internal classes. Helps out with encapsulation and type conversion;
 */

public class ComponentDatabaseFacade {

    private static final String LOG_TAG = ComponentDatabaseFacade.class.getSimpleName();
    private static ComponentDatabaseFacade mInstance;
    private final ComponentDatabaseHelper mDbHelper;

    protected ComponentDatabaseFacade(Context context) {
        mDbHelper = new ComponentDatabaseHelper(context);
    }

    public static ComponentDatabaseFacade getInstance(Context context){
        if (mInstance == null) {
            mInstance = new ComponentDatabaseFacade(context);
        }
        return mInstance;
    }

    private Squadron getSquadronForQuery(String query){

        // Get a Squadron factory, and feed it the query
        ComponentFactory<Squadron> factory = new ComponentFactory<>();
        return factory.getSingleForQuery(query, mDbHelper.getDatabase(), Squadron.class);

    }

    private List<Squadron> getSquadronsForQuery(String query){

        // Get a Squadron factory, and feed it the query
        ComponentFactory<Squadron> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), Squadron.class);
    }

    private Ship getShipForQuery(String query){

        // Get a Ship factory, and feed it the query
        ComponentFactory<Ship> factory = new ComponentFactory<>();
        Ship ship = factory.getSingleForQuery(query, mDbHelper.getDatabase(), Ship.class);
        ship.setUpgradeSlots(getUpgradeSlotsForShip(ship.id()));
        return ship;
    }

    private List<Ship> getShipsForQuery(String query) {

        // Get a Ship factory, and feed it the query
        ComponentFactory<Ship> factory = new ComponentFactory<>();
        List<Ship> shipList = factory.getAllForQuery(query, mDbHelper.getDatabase(), Ship.class);

        for (int i = 0; i < shipList.size(); i++) {
            Ship ship = shipList.get(i);
            ship.setUpgradeSlots(getUpgradeSlotsForShip(ship.id()));
        }

        return shipList;
    }

    private List<UpgradeSlot> getUpgradeSlotsForQuery(String query){

        // Get an UpgradeSlot factory and feed it the query
        ComponentFactory<UpgradeSlot> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), UpgradeSlot.class);
    }

    private List<Upgrade> getUpgradesForQuery(String query){

        // Get an Upgrade factory, and feed it the query
        ComponentFactory<Upgrade> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), Upgrade.class);
    }

    private List<TitleUpgrade> getTitleUpgradesForQuery(String query){

        // Get a TitleUpgrade factory and feed it the query
        ComponentFactory<TitleUpgrade> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), TitleUpgrade.class);
    }

    private List<Commander> getCommandersForQuery(String query){

        // Get a Commander factory and feed it the query
        ComponentFactory<Commander> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), Commander.class);
    }

    private List<Objective> getObjectivesForQuery(String query){
        ComponentFactory<Objective> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), Objective.class);
    }

    /* Squadron Queries */

    public List<Squadron> getSquadrons() {
        SquadronQueryBuilder queryBuilder = new SquadronQueryBuilder();
        return getSquadronsForQuery(queryBuilder.queryAll());
    }

    public List<Squadron> getSquadronsForFaction(int factionId){
        SquadronQueryBuilder queryBuilder = new SquadronQueryBuilder();
        return getSquadronsForQuery(queryBuilder.queryWhereFactionId(factionId));
    }

    public Squadron getSquadronForSquadronId(int squadId){
        SquadronQueryBuilder queryBuilder = new SquadronQueryBuilder();
        return getSquadronForQuery(queryBuilder.queryWhereSquadronId(squadId));
    }

    /* Ship Queries */

    public List<Ship> getShips() {
        ShipQueryBuilder queryBuilder = new ShipQueryBuilder();
        return getShipsForQuery(queryBuilder.queryAll());
    }

    public List<Ship> getShipsForFaction(int factionId){
        ShipQueryBuilder queryBuilder = new ShipQueryBuilder();
        return getShipsForQuery(queryBuilder.queryWhereFactionId(factionId));
    }

    public Ship getShipForShipId(int shipId){
        ShipQueryBuilder queryBuilder = new ShipQueryBuilder();
        return getShipForQuery(queryBuilder.queryWhereShipId(shipId));
    }

    /* Upgrade Slot Queries */


    public List<UpgradeSlot> getUpgradeSlotsForShip(int shipId){
        ShipUpgradeSlotQueryBuilder queryBuilder = new ShipUpgradeSlotQueryBuilder();
        return getUpgradeSlotsForQuery(queryBuilder.queryWhereShipId(shipId));
    }

    /* Upgrade Queries */

    public List<Upgrade> getUpgradesForTypeAndFaction(int typeId, int factionId){
        UpgradeQueryBuilder queryBuilder = new UpgradeQueryBuilder();
        return getUpgradesForQuery(queryBuilder.queryWhereTypeIdAndFactionId(typeId, factionId));
    }

    public List<TitleUpgrade> getTitlesForShipClass(int shipClassId){
        ShipTitleQueryBuilder queryBuilder = new ShipTitleQueryBuilder();
        return getTitleUpgradesForQuery(queryBuilder.queryWhereShipClassId(shipClassId));
    }

    public List<Commander> getCommandersForFaction(int factionId){
        CommanderQueryBuilder queryBuilder = new CommanderQueryBuilder();
        return getCommandersForQuery(queryBuilder.queryWhereFactionId(factionId));
    }

    /* Objective Queries */

    public List<Objective> getObjectivesForType(int typeId){
        ObjectiveQueryBuilder queryBuilder = new ObjectiveQueryBuilder();
        return getObjectivesForQuery(queryBuilder.queryWhereTypeId(typeId));
    }
}
