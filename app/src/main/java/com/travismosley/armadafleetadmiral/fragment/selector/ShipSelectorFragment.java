package com.travismosley.armadafleetadmiral.fragment.selector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetadmiral.adaptor.list.selector.ShipsSelectorAdapter;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.Ship;


/**
 * A fragment for selecting squadrons
 */
public class ShipSelectorFragment extends ComponentSelectorFragment<Ship> {

    private static final String LOG_TAG = ShipSelectorFragment.class.getSimpleName();

    // Public constructor
    public ShipSelectorFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public static ShipSelectorFragment newInstance(Fleet fleet){
        ShipSelectorFragment shipSelector = new ShipSelectorFragment();
        shipSelector.setArguments(getBundleForNewInstance(fleet));
        return shipSelector;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View shipSelectorView = super.onCreateView(inflater, container, savedInstanceState);

        setListAdapter(new ShipsSelectorAdapter(
                getActivity(),
                mArmadaDb.getShipsForFaction(mFleet.mFactionId),
                mFleet));

        return shipSelectorView;
    }
}
