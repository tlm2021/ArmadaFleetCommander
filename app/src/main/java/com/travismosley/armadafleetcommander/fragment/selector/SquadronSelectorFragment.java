package com.travismosley.armadafleetcommander.fragment.selector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetcommander.adaptor.SquadronsSelectorAdapter;
import com.travismosley.armadafleetcommander.game.components.Squadron;


/**
 * A fragment for selecting squadrons
 */
public class SquadronSelectorFragment extends ComponentSelectorFragment<Squadron> {

    private final String LOG_TAG = SquadronSelectorFragment.class.getSimpleName();

    // Public constructor
    public SquadronSelectorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        // Create the view in the super class
        View squadSelectorView = super.onCreateView(inflater, container, savedInstanceState);

        // Set the list adapter
        setListAdapter(new SquadronsSelectorAdapter(
                getActivity(),
                mArmadaDb.getSquadronsForFaction(mFleet.mFactionId),
                mFleet));

        return squadSelectorView;
    }
}
