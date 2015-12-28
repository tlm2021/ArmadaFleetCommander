package com.travismosley.armadafleetcommander.adaptor;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.game.component.GameComponent;

import java.util.List;

/**
 * ArrayAdaptor for Ship lists
 */
public abstract class ComponentListAdapter<ComponentType extends GameComponent> extends ArrayAdapter<ComponentType> {

    /* Adapts a List of GameComponent objects for a ListView */

    private final static String LOG_TAG = ComponentListAdapter.class.getSimpleName();

    private List<ComponentType> mComponents;

    public ComponentListAdapter(Context context, List<ComponentType> componentList) {
        super(context, -1, componentList);

        Log.d(LOG_TAG, "Initialize for " + componentList);
        mComponents = componentList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View componentView;

        // Recycle the view if possible
        if (convertView != null) {
            componentView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            componentView = inflater.inflate(getItemLayoutId(), parent, false);
        }

        return componentView;
    }

    protected abstract int getItemLayoutId();

    @Override
    public ComponentType getItem(int position){
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

    public void correctWidth(TextView textView)
    {

        Paint paint = new Paint();
        Rect bounds = new Rect();

        paint.setTypeface(textView.getTypeface());
        float textSize = textView.getTextSize();
        paint.setTextSize(textSize);
        String text = textView.getText().toString();
        paint.getTextBounds(text, 0, text.length(), bounds);

        textView.measure(0, 0);

        while (bounds.width() > textView.getMeasuredWidth())
        {
            if (textSize <= 0){
                throw new RuntimeException("Failed to find correct text size.");
            }

            textSize--;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}