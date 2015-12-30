package com.travismosley.armadafleetcommander.fragment;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.list.UpgradeSlotsAdapter;
import com.travismosley.armadafleetcommander.adaptor.spinner.TitlesAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.Ship;
import com.travismosley.armadafleetcommander.game.component.upgrade.TitleUpgrade;
import com.travismosley.armadafleetcommander.game.component.upgrade.UpgradeSlot;


public class ShipDetailFragment extends Fragment {

    private static final String LOG_TAG = ShipDetailFragment.class.getSimpleName();
    private static final String KEY_SHIP = "KEY_SHIP";
    private static final String KEY_FLEET = "KEY_FLEET";

    private Ship mShip;
    private Fleet mFleet;

    OnUpgradeSlotClickedListener mOnUpgradeSlotClickedCallback;
    View mShipDetailView;

    // onAddSquadron callback
    public interface OnUpgradeSlotClickedListener{
        void onUpgradeSlotClicked(UpgradeSlot slot);
    }

    public ShipDetailFragment() {
    }

    public static ShipDetailFragment newInstance(Ship ship, Fleet fleet){

        ShipDetailFragment shipDetailFragment = new ShipDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_SHIP, ship);
        args.putParcelable(KEY_FLEET, fleet);
        shipDetailFragment.setArguments(args);
        return shipDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShip = getArguments().getParcelable(KEY_SHIP);
            mFleet = getArguments().getParcelable(KEY_FLEET);
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            mOnUpgradeSlotClickedCallback = (OnUpgradeSlotClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnUpgradeSlotClickedListener");
        }
    }

    public void updateClassDisplay(){
        TextView classView = (TextView) mShipDetailView.findViewById(R.id.txt_ship_class);
        if (mShip.hasTitleUpgrade()) {
            classView.setText(mShip.title());
            classView.setVisibility(View.VISIBLE);
        } else {
            classView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mShipDetailView = inflater.inflate(R.layout.fragment_ship_detail, container, false);

        Spinner spinnerTitle = (Spinner) mShipDetailView.findViewById(R.id.spinner_ship_title);
        TitlesAdapter titlesAdapter = new TitlesAdapter(getActivity(), mShip, mFleet);
        spinnerTitle.setAdapter(titlesAdapter);

        if (mShip.hasTitleUpgrade()){
            Log.d(LOG_TAG, "Ship has title: " + mShip.titleUpgrade());
            spinnerTitle.setSelection(titlesAdapter.getPosition(mShip.titleUpgrade()));
        }

        spinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "onSpinnerItemSelected: position " + position);
                if (position == 0) {
                    mShip.clearTitle();
                } else {
                    Log.d(LOG_TAG, "Setting ship title: " + parent.getItemAtPosition(position));
                    mShip.setTitle((TitleUpgrade) parent.getItemAtPosition(position));
                }
                updateClassDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        updateClassDisplay();

        ImageView imgView = (ImageView) mShipDetailView.findViewById(R.id.img_ship);
        if (imgView.getDrawable() != null) {
            ((BitmapDrawable) imgView.getDrawable()).getBitmap().recycle();
        }
        Picasso.with(getActivity()).load(R.mipmap.cr90_placeholder).into(imgView);

        ListView upgradeView = (ListView) mShipDetailView.findViewById(R.id.list_upgrades);
        upgradeView.setAdapter(new UpgradeSlotsAdapter(getActivity(), mShip.upgradeSlots()));

        upgradeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UpgradeSlot slot = (UpgradeSlot) parent.getItemAtPosition(position);
                mOnUpgradeSlotClickedCallback.onUpgradeSlotClicked(slot);
            }
        });

        return mShipDetailView;
    }
}
