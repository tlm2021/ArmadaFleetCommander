package com.travismosley.armadafleetadmiral.adaptor.spinner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetadmiral.adaptor.list.ComponentListAdapter;
import com.travismosley.armadafleetadmiral.adaptor.list.UpgradesAdapter;
import com.travismosley.armadafleetadmiral.game.component.GameComponent;

import java.util.List;

/**
 * An ArrayAdapter for ship titles
 */
public abstract class ComponentSpinnerAdapter<Component extends GameComponent> extends ComponentListAdapter<Component> {

    private final static String LOG_TAG = UpgradesAdapter.class.getSimpleName();

    public ComponentSpinnerAdapter(Context context, List<Component> componentList) {
        super(context, componentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflateView(convertView, parent);
        if (position == 0) {
            populateView(view, null);
        } else {
            Component component = getItem(position);
            populateView(view, component);
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    /*
    This class dynamically generates the first item in the spinner, which acts as a
    "Select None" item. Because of that, all the superclass methods that index the list
    of objects directly will be off-by-one. These overridden methods account for that.

    These overrides adjust that
     */

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public int getPosition(Component item) {
        int pos = super.getPosition(item);
        if (pos == -1) {
            return pos;
        } else {
            return pos + 1;
        }
    }

    @Override
    public Component getItem(int position) {
        if (position == 0) {
            return null;
        }
        return super.getItem(position - 1);
    }
}
