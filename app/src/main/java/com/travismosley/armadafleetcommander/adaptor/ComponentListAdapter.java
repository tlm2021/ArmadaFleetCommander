package com.travismosley.armadafleetcommander.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.travismosley.armadafleetcommander.game.components.GameComponent;

import java.util.List;

/**
 * ArrayAdaptor for Ship lists
 */
public abstract class ComponentListAdapter<ComponentType extends GameComponent> extends ArrayAdapter<ComponentType> {

    /* Adapts a List of GameComponent objects for a ListView */

    private final static String LOG_TAG = ComponentListAdapter.class.getSimpleName();

    private final Context mContext;
    private List<ComponentType> mComponents;

    public ComponentListAdapter(Context context, List<ComponentType> componentList) {
        super(context, -1, componentList);

        Log.d(LOG_TAG, "Initialize for " + componentList);
        mContext = context;
        mComponents = componentList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View componentView;

        // Recycle the view if possible
        if (convertView != null) {
            componentView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            componentView = inflater.inflate(getItemLayoutId(), parent, false);
        }

        return componentView;
    }

    protected abstract int getItemLayoutId();

    protected ComponentType getComponentForPosition(int position){
        return mComponents.get(position);
    }

    public void addComponent(ComponentType component) {
        Log.d(LOG_TAG, "addComponent on " + component);
        mComponents.add(component);
        notifyDataSetChanged();
    }

    public void removeComponent(int position){
        Log.d(LOG_TAG, "Removing component at position " + position);
        mComponents.remove(position);
        notifyDataSetChanged();
    }
}