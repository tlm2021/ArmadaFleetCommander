package com.travismosley.armadafleetcommander.adaptor.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.travismosley.armadafleetcommander.game.component.GameComponent;

import java.util.List;

/**
 * ArrayAdaptor for Ship lists
 */
public abstract class ComponentListAdapter<Component extends GameComponent> extends ArrayAdapter<Component> {

    /* Adapts a List of GameComponent objects for a ListView */

    private final static String LOG_TAG = ComponentListAdapter.class.getSimpleName();

    private List<Component> mComponents;

    public ComponentListAdapter(Context context,
                                List<Component> componentList) {
        super(context, -1, componentList);

        Log.d(LOG_TAG, "Initialize for " + componentList);
        mComponents = componentList;
    }

    protected View inflateView(View convertView, ViewGroup parent){
        View view;

        // Recycle the view if possible
        if (convertView != null) {
            view = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(getItemLayoutId(), parent, false);
        }

        return view;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflateView(convertView, parent);
        Component component = getItem(position);
        populateView(view, component);

        return view;
    }

    protected abstract int getItemLayoutId();
    protected abstract void populateView(View view, Component component);

    @Override
    public Component getItem(int position){
        return mComponents.get(position);
    }

    @Override
    public int getPosition(Component component){

        // Can't just check mComponents for indexOf
        for (int i=0; i < mComponents.size(); i++){
            if (mComponents.get(i).id() == component.id()){
                return i;
            }
        }
        return -1;
    }

    public void addComponent(Component component) {
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