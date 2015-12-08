package com.travismosley.armadafleetcommander.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.travismosley.armadafleetcommander.adaptor.SquadronsAdapter;
import com.travismosley.armadafleetcommander.data.ArmadaDatabaseFacade;
import com.travismosley.armadafleetcommander.game.components.Squadron;

/**
 * A fragment for selecting and squadrons
 */
public class SquadronSelectorFragment extends ListFragment {

    private final String LOG_TAG = SquadronSelectorFragment.class.getSimpleName();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setListAdapter(new SquadronsAdapter(getActivity(), mArmadaDb.getSquadrons()));
        return super.onCreateView(inflater, container, savedInstanceState);
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
        mCallback.onSquadronSelected((Squadron) this.getListAdapter().getItem(position));
    }

}
