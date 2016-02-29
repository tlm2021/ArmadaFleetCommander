package com.travismosley.android.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * An ArrayAdapter with some extra convenience methods for common operations
 */
public abstract class ArrayAdapter<T> extends android.widget.ArrayAdapter<T> {

    private final static String LOG_TAG = ArrayAdapter.class.getSimpleName();

    protected abstract int getItemLayoutId();

    public ArrayAdapter(Context context, List<T> itemList) {
        super(context, -1, itemList);
        Log.d(LOG_TAG, "Initialize for " + itemList);
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



}
