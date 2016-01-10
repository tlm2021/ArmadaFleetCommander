package com.travismosley.armadafleetadmiral.data;

import android.content.Context;

import com.travismosley.armadafleetadmiral.data.factory.ComponentFactory;
import com.travismosley.armadafleetadmiral.data.query.CommanderQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.ShipQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.ShipTitleQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.ShipUpgradeSlotQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.SquadronQueryBuilder;
import com.travismosley.armadafleetadmiral.data.query.UpgradeQueryBuilder;
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

public class ArmadaDatabaseFacade{

    private static final String LOG_TAG = ArmadaDatabaseFacade.class.getSimpleName();
    private static ArmadaDatabaseFacade mInstance;
    private final ArmadaDatabaseHelper mDbHelper;

    protected ArmadaDatabaseFacade(Context context) {
        mDbHelper = new ArmadaDatabaseHelper(context);
    }

    public static ArmadaDatabaseFacade getInstance(Context context){
        if (mInstance == null) {
            mInstance = new ArmadaDatabaseFacade(context);
        }
        return mInstance;
    }

    private List<Squadron> getSquadronsForQuery(String query){

        // Get a Squadron factory, and feed it the query
        ComponentFactory<Squadron> factory = new ComponentFactory<>();
        return factory.getForQuery(query, mDbHelper.getDatabase(), Squadron.class);
    }

    private List<Ship> getShipsForQuery(String query) {

        // Get a Ship factory, and feed it the query
        ComponentFactory<Ship> factory = new ComponentFactory<>();
        List<Ship> shipList = factory.getForQuery(query, mDbHelper.getDatabase(), Ship.class);

        for (int i = 0; i < shipList.size(); i++) {
            Ship ship = shipList.get(i);
            ship.setUpgradeSlots(getUpgradeSlotsForShip(ship.id()));
        }

        return shipList;
    }

    private List<UpgradeSlot> getUpgradeSlotsForQuery(String query){

        // Get an UpgradeSlot factory and feed it the query
        ComponentFactory<UpgradeSlot> factory = new ComponentFactory<>();
        return factory.getForQuery(query, mDbHelper.getDatabase(), UpgradeSlot.class);
    }

    private List<Upgrade> getUpgradesForQuery(String query){

        // Get an Upgrade factory, and feed it the query
        ComponentFactory<Upgrade> factory = new ComponentFactory<>();
        return factory.getForQuery(query, mDbHelper.getDatabase(), Upgrade.class);
    }

    private List<TitleUpgrade> getTitleUpgradesForQuery(String query){

        // Get a TitleUpgrade factory and feed it the query
        ComponentFactory<TitleUpgrade> factory = new ComponentFactory<>();
        return factory.getForQuery(query, mDbHelper.getDatabase(), TitleUpgrade.class);
    }

    private List<Commander> getCommandersForQuery(String query){

        // Get a Commander factory and feed it the query
        ComponentFactory<Commander> factory = new ComponentFactory<>();
        return factory.getForQuery(query, mDbHelper.getDatabase(), Commander.class);
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


    /* Ship Queries */

    public List<Ship> getShips() {
        ShipQueryBuilder queryBuilder = new ShipQueryBuilder();
        return getShipsForQuery(queryBuilder.queryAll());
    }

    public List<Ship> getShipsForFaction(int factionId){
        ShipQueryBuilder queryBuilder = new ShipQueryBuilder();
        return getShipsForQuery(queryBuilder.queryWhereFactionId(factionId));
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
}
