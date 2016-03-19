package com.travismosley.armadafleetadmiral.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.travismosley.armadafleetadmiral.R;


public class MainActivityFragment extends Fragment {

    OnFleetSelectorRequestedListener mFleetListSelectedCallback;

    public interface OnFleetSelectorRequestedListener{
        void onFleetListRequested(int factionId);
    }

    public MainActivityFragment() {}

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        // Make sure the attaching activity has implemented the interface
        try{
            mFleetListSelectedCallback = (OnFleetSelectorRequestedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFleetListRequestedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button rebelBtn = (Button) view.findViewById(R.id.btn_build_rebel);
        Button imperialBtn = (Button) view.findViewById(R.id.btn_build_imperial);

        rebelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFleetListSelectedCallback.onFleetListRequested(0);
            }
        });

        imperialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFleetListSelectedCallback.onFleetListRequested(1);
            }
        });

        return view;
    }
}
