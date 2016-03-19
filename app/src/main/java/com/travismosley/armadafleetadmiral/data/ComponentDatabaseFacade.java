package com.travismosley.armadafleetadmiral.data;

import android.content.Context;

import com.travismosley.armadafleetadmiral.data.factory.ComponentFactory;
import com.travismosley.armadafleetadmiral.data.query.component.CommanderQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ObjectiveQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ShipDefenseTokenQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ShipQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ShipTitleQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.ShipUpgradeSlotQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.SquadronDefenseTokenQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.SquadronKeywordQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.SquadronQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.component.UpgradeQueryBuilder;
import com.travismosley.armadafleetadmiral.game.component.DefenseToken;
import com.travismosley.armadafleetadmiral.game.component.Objective;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.Squadron;
import com.travismosley.armadafleetadmiral.game.component.SquadronKeyword;
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
        List<Squadron> squadList = factory.getAllForQuery(query, mDbHelper.getDatabase(), Squadron.class);

        for (int i = 0; i < squadList.size(); i++) {
            Squadron squad = squadList.get(i);
            squad.setDefenseTokens(getDefenseTokensForSquadron(squad.id()));
            squad.setKeywords(getSquadronKeywordsForSquadron(squad.id()));
        }

        return squadList;

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
            ship.setDefenseTokens(getDefenseTokensForShip(ship.id()));
        }

        return shipList;
    }

    private List<SquadronKeyword> getSquadronKeywordsForQuery(String query) {
        // Get a SquadronKeyword factory and feed it the query
        ComponentFactory<SquadronKeyword> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), SquadronKeyword.class);
    }

    private List<UpgradeSlot> getUpgradeSlotsForQuery(String query){

        // Get an UpgradeSlot factory and feed it the query
        ComponentFactory<UpgradeSlot> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), UpgradeSlot.class);
    }

    private Upgrade getUpgradeForQuery(String query) {

        // Get an UpgradeFactory, and feed it the query
        ComponentFactory<Upgrade> factory = new ComponentFactory<>();
        return factory.getSingleForQuery(query, mDbHelper.getDatabase(), Upgrade.class);
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

    private Commander getCommanderForQuery(String query){
        ComponentFactory<Commander> factory = new ComponentFactory<>();
        return factory.getSingleForQuery(query, mDbHelper.getDatabase(), Commander.class);
    }

    private List<Objective> getObjectivesForQuery(String query){

        // Get a Commander factory and feed it the query
        ComponentFactory<Objective> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), Objective.class);
    }

    private List<DefenseToken> getDefenseTokensForQuery(String query) {

        // Get a DefenseToken factory and feed it the query
        ComponentFactory<DefenseToken> factory = new ComponentFactory<>();
        return factory.getAllForQuery(query, mDbHelper.getDatabase(), DefenseToken.class);
    }

    private DefenseToken getDefenseTokenForQuery(String query) {

        // Get a DefenseToken factory and feed it the query
        ComponentFactory<DefenseToken> factory = new ComponentFactory<>();
        return factory.getSingleForQuery(query, mDbHelper.getDatabase(), DefenseToken.class);
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

    public Squadron getSquadronForId(int squadId) {
        SquadronQueryBuilder queryBuilder = new SquadronQueryBuilder();
        return getSquadronForQuery(queryBuilder.queryWhereId(squadId));
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

    public Ship getShipForId(int shipId) {
        ShipQueryBuilder queryBuilder = new ShipQueryBuilder();
        return getShipForQuery(queryBuilder.queryWhereId(shipId));
    }

    /* Squadron Keyword Queries */
    public List<SquadronKeyword> getSquadronKeywordsForSquadron(int squadronId) {
        SquadronKeywordQueryBuilder queryBuilder = new SquadronKeywordQueryBuilder();
        return getSquadronKeywordsForQuery(queryBuilder.queryWhereSquadronId(squadronId));
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

    public Upgrade getUpgradeForId(int upgradeId) {
        UpgradeQueryBuilder queryBuilder = new UpgradeQueryBuilder();
        return getUpgradeForQuery(queryBuilder.queryWhereId(upgradeId));
    }

    public List<TitleUpgrade> getTitlesForShipClass(int shipClassId){
        ShipTitleQueryBuilder queryBuilder = new ShipTitleQueryBuilder();
        return getTitleUpgradesForQuery(queryBuilder.queryWhereShipClassId(shipClassId));
    }

    public List<Commander> getCommandersForFaction(int factionId){
        CommanderQueryBuilder queryBuilder = new CommanderQueryBuilder();
        return getCommandersForQuery(queryBuilder.queryWhereFactionId(factionId));
    }

    public Commander getCommanderForId(int commanderId){
        CommanderQueryBuilder queryBuilder = new CommanderQueryBuilder();
        return getCommanderForQuery(queryBuilder.queryWhereId(commanderId));
    }

    /* Objective Queries */

    public List<Objective> getObjectivesForType(int typeId){
        ObjectiveQueryBuilder queryBuilder = new ObjectiveQueryBuilder();
        return getObjectivesForQuery(queryBuilder.queryWhereTypeId(typeId));
    }

    public List<DefenseToken> getDefenseTokensForShip(int shipId) {
        ShipDefenseTokenQueryBuilder queryBuilder = new ShipDefenseTokenQueryBuilder();
        return getDefenseTokensForQuery(queryBuilder.queryWhereShipId(shipId));
    }

    public List<DefenseToken> getDefenseTokensForSquadron(int squadronId) {
        SquadronDefenseTokenQueryBuilder queryBuilder = new SquadronDefenseTokenQueryBuilder();
        return getDefenseTokensForQuery(queryBuilder.queryWhereSquadronId(squadronId));
    }
}
