package com.travismosley.armadafleetadmiral.data;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class FleetDatabaseFacadeTest extends AndroidTestCase{

    private final String LOG_TAG = FleetDatabaseFacadeTest.class.getSimpleName();
    private FleetDatabaseFacade mFleetDatabase;

    private static abstract class TestValues{
        public static int SHIP_ID = 10;
        public static String SHIP_CUSTOM_TITLE = "My Custom Title";
        public static String SHIP_NO_CUSTOM_TITLE = null;

        public static int TITLE_UPGRADE_ID = 20;
        public static int UPGRADE_ID_ONE = 25;
        public static int UPGRADE_ID_TWO = 30;
    }

    @Override
    protected void setUp() throws Exception{
        Log.i(LOG_TAG, "Running Setup");
        super.setUp();

        getContext().deleteDatabase(FleetDatabaseContract.DATABASE_NAME);
        mFleetDatabase = new FleetDatabaseFacade(getContext());
    }

    @Override
    protected void tearDown() throws Exception{
        Log.i(LOG_TAG, "Running tearDown");
        super.tearDown();
    }

    private void test_storeShipBuild(){
        Ship ship = Mockito.mock(Ship.class);

        Mockito.when(ship.id()).thenReturn(TestValues.SHIP_ID);
        Mockito.when(ship.hasCustomTitle()).thenReturn(false);
        Mockito.when(ship.hasTitleUpgrade()).thenReturn(false);
        Mockito.when(ship.getEquippedUpgrades()).thenReturn(new ArrayList<Upgrade>(){});

        long shipBuildId = mFleetDatabase.getOrAddShipBuild(ship);
        Assert.assertTrue(shipBuildId != -1);
        Assert.assertEquals(shipBuildId, mFleetDatabase.getOrAddShipBuild(ship));
    }
}
