package com.travismosley.armadafleetcommander.fragment.selector;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.data.ArmadaDatabaseFacade;
import com.travismosley.armadafleetcommander.fragment.listener.OnComponentSelectedListener;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.GameComponent;

/**
 * A fragment for selecting squadrons
 */

public abstract class ComponentSelectorFragment<ComponentType extends GameComponent> extends ListFragment {

    private final String LOG_TAG = ComponentSelectorFragment.class.getSimpleName();

    protected Fleet mFleet;
    protected ArmadaDatabaseFacade mArmadaDb;

    OnComponentSelectedListener<ComponentType> mListener;

    // Public constructor
    public ComponentSelectorFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mArmadaDb = new ArmadaDatabaseFacade(getActivity());
        mFleet = getArguments().getParcelable(getString(R.string.key_fleet));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getLayoutFragmentId(), container, false);
    }

    @Override
    public void onListItemClick(ListView view, View v, int position, long id){
        super.onListItemClick(view, v, position, id);
        Log.i(LOG_TAG, "Called onListItemClick");

        if (mListener != null){
            ComponentType component = (ComponentType) this.getListAdapter().getItem(position);
            mListener.onComponentSelected(component);
        }
    }

    public void setSelectionListener(OnComponentSelectedListener<ComponentType> listener){
        mListener = listener;
    }

    protected int getLayoutFragmentId(){
        return R.layout.fragment_component_selector;
    }
}
