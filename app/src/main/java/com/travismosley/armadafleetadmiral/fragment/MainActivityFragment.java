package com.travismosley.armadafleetadmiral.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.travismosley.armadafleetadmiral.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    OnFleetBuilderRequestedCallback mFleetBuilderRequestedCallback;

    // onAddSquadronClicked callback
    public interface OnFleetBuilderRequestedCallback{
        void onFleetBuilderRequested(int factionId);
    }

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        // Make sure the attaching activity has implemented the interface
        try{
            mFleetBuilderRequestedCallback = (OnFleetBuilderRequestedCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFleetBuilderRequestedCallback");
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
                mFleetBuilderRequestedCallback.onFleetBuilderRequested(0);
            }
        });

        imperialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFleetBuilderRequestedCallback.onFleetBuilderRequested(1);
            }
        });

        return view;
    }


}
