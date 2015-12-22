package com.travismosley.armadafleetcommander.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.ShipsSelectorAdapter;
import com.travismosley.armadafleetcommander.adaptor.SquadronsSelectorAdapter;
import com.travismosley.armadafleetcommander.data.ArmadaDatabaseFacade;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.components.Ship;
import com.travismosley.armadafleetcommander.game.components.Squadron;


/**
 * A fragment for selecting squadrons
 */
public class ShipSelectorFragment extends ListFragment {

    private final String LOG_TAG = ShipSelectorFragment.class.getSimpleName();
    private Fleet mFleet;

    OnShipSelectedListener mCallback;

    public interface OnShipSelectedListener{
        void onShipSelected(Ship ship);
    }

    private ArmadaDatabaseFacade mArmadaDb;

    // Public constructor
    public ShipSelectorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mArmadaDb = new ArmadaDatabaseFacade(getActivity());
        mFleet = getArguments().getParcelable(getString(R.string.key_fleet));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        View shipSelectorView = inflater.inflate(R.layout.fragment_component_selector, container, false);

        setListAdapter(new ShipsSelectorAdapter(
                getActivity(),
                mArmadaDb.getShipsForFaction(mFleet.mFactionId),
                mFleet));

        return shipSelectorView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        // Make sure the attaching activity has implemented the interface
        try{
            mCallback = (ShipSelectorFragment.OnShipSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShipSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView view, View v, int position, long id){
        super.onListItemClick(view, v, position, id);
        Log.i(LOG_TAG, "Called onListItemClick");

        Ship ship = (Ship) this.getListAdapter().getItem(position);

        // We only to add the ship if they selected a non-unique squadron,
        // Or a unique one that isn't already in the fleet
        if (mFleet.canAddShip(ship)){
            mCallback.onShipSelected(ship);
        }
    }

}
