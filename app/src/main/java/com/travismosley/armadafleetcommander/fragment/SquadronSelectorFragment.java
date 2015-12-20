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
import com.travismosley.armadafleetcommander.adaptor.SquadronsSelectorAdapter;
import com.travismosley.armadafleetcommander.data.ArmadaDatabaseFacade;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.components.Squadron;


/**
 * A fragment for selecting squadrons
 */
public class SquadronSelectorFragment extends ListFragment {

    private final String LOG_TAG = SquadronSelectorFragment.class.getSimpleName();
    private Fleet mFleet;

    OnSquadronSelectedListener mCallback;

    public interface OnSquadronSelectedListener{
        void onSquadronSelected(Squadron squadron);
    }

    private ArmadaDatabaseFacade mArmadaDb;

    // Public constructor
    public SquadronSelectorFragment() {
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

        View squadSelectorView = inflater.inflate(R.layout.fragment_squadron_selector, container, false);

        // Show the currently used squad points
        TextView squadPointsText = (TextView) squadSelectorView.findViewById(R.id.squad_points_used);
        squadPointsText.setText(String.valueOf(mFleet.squadronPoints()));

        // Show the max allowed squad points
        TextView squadPointsLimit = (TextView) squadSelectorView.findViewById(R.id.squad_points_allowed);
        squadPointsLimit.setText(String.valueOf(mFleet.squadronPointLimit()));

        setListAdapter(new SquadronsSelectorAdapter(
                getActivity(),
                mArmadaDb.getSquadronsForFaction(mFleet.mFactionId),
                mFleet));

        return squadSelectorView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        // Make sure the attaching activity has implemented the interface
        try{
            mCallback = (OnSquadronSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSquadronSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView view, View v, int position, long id){
        super.onListItemClick(view, v, position, id);
        Log.i(LOG_TAG, "Called onListItemClick");

        Squadron squad = (Squadron) this.getListAdapter().getItem(position);

        // We only to add the squadron if they selected a non-unique squadron,
        // Or a unique one that isn't already in the fleet
        if (mFleet.canAddSquadron(squad)){
            mCallback.onSquadronSelected((Squadron) this.getListAdapter().getItem(position));
        }
    }

}
