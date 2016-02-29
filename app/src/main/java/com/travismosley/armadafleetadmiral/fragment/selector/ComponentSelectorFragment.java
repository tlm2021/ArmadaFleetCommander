package com.travismosley.armadafleetadmiral.fragment.selector;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.data.ComponentDatabaseFacade;
import com.travismosley.armadafleetadmiral.fragment.listener.OnComponentSelectedListener;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.GameComponent;

/**
 * A fragment for selecting squadrons
 */

public abstract class ComponentSelectorFragment<ComponentType extends GameComponent> extends ListFragment {

    private static final String LOG_TAG = ComponentSelectorFragment.class.getSimpleName();
    private static final String KEY_FLEET = "KEY_FLEET";

    protected Fleet mFleet;
    protected ComponentDatabaseFacade mArmadaDb;

    OnComponentSelectedListener<ComponentType> mListener;

    // Public constructor
    public ComponentSelectorFragment() {}

    protected static Bundle getBundleForNewInstance(Fleet fleet){
        Bundle args = new Bundle();
        args.putParcelable(KEY_FLEET, fleet);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mArmadaDb = ComponentDatabaseFacade.getInstance(getActivity());
        mFleet = getArguments().getParcelable(KEY_FLEET);
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