package com.travismosley.armadafleetcommander.fragment.selector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetcommander.adaptor.ShipsSelectorAdapter;
import com.travismosley.armadafleetcommander.game.components.Ship;


/**
 * A fragment for selecting squadrons
 */
public class ShipSelectorFragment extends ComponentSelectorFragment<Ship> {

    private final String LOG_TAG = ShipSelectorFragment.class.getSimpleName();

    // Public constructor
    public ShipSelectorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
