package com.travismosley.armadafleetcommander.data;

import android.content.Context;

import com.travismosley.android.data.database.logging.ColumnIndexLogger;
import com.travismosley.armadafleetcommander.data.factory.ComponentFactory;
import com.travismosley.armadafleetcommander.data.factory.ShipFactory;
import com.travismosley.armadafleetcommander.data.query.CommanderQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.ShipQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.ShipTitleQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.ShipUpgradeSlotQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.SquadronQueryBuilder;
import com.travismosley.armadafleetcommander.data.query.UpgradeQueryBuilder;
import com.travismosley.armadafleetcommander.game.component.Ship;
import com.travismosley.armadafleetcommander.game.component.Squadron;
import com.travismosley.armadafleetcommander.game.component.upgrade.Commander;
import com.travismosley.armadafleetcommander.game.component.upgrade.TitleUpgrade;
import com.travismosley.armadafleetcommander.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetcommander.game.component.upgrade.UpgradeSlot;

import java.util.List;

/**
 * A Facade class for extracting Armada information from the database as
 * internal classes. Helps out with encapsulation and type conversion;
 */

public class ArmadaDatabaseFacade{

    private static final String LOG_TAG = ArmadaDatabaseFacade.class.getSimpleName();
    private final Context mContext;

    private static final ColumnIndexLogger mIdxLogger = new ColumnIndexLogger(LOG_TAG);

    public ArmadaDatabaseFacade(Context context) {
        mContext = context;
    }

    private List<Squadron> getSquadronsForQuery(String query){

        // Get a Squadron factory, and feed it the query
        ComponentFactory<Squadron> factory = new ComponentFactory<>(mContext);
        return factory.getForQuery(query, Squadron.class);
    }

    private List<Ship> getShipsForQuery(String query) {

        // Get a Ship factory, and feed it the query
        ShipFactory factory = new ShipFactory(mContext);
        return factory.getForQuery(query);
    }

    private List<UpgradeSlot> getUpgradeSlotsForQuery(String query){

        // Get an UpgradeSlot factory and feed it the query
        ComponentFactory<UpgradeSlot> factory = new ComponentFactory<>(mContext);
        return factory.getForQuery(query, UpgradeSlot.class);
    }

    private List<Upgrade> getUpgradesForQuery(String query){

        // Get an Upgrade factory, and feed it the query
        ComponentFactory<Upgrade> factory = new ComponentFactory<>(mContext);
        return factory.getForQuery(query, Upgrade.class);
    }

    private List<TitleUpgrade> getTitleUpgradesForQuery(String query){

        // Get a TitleUpgrade factory and feed it the query
        ComponentFactory<TitleUpgrade> factory = new ComponentFactory<>(mContext);
        return factory.getForQuery(query, TitleUpgrade.class);
    }

    private List<Commander> getCommandersForQuery(String query){

        // Get a Commander factory and feed it the query
        ComponentFactory<Commander> factory = new ComponentFactory<>(mContext);
        return factory.getForQuery(query, Commander.class);
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
