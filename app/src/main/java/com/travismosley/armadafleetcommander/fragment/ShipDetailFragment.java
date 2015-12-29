package com.travismosley.armadafleetcommander.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.UpgradeSlotsAdapter;
import com.travismosley.armadafleetcommander.game.component.Ship;
import com.travismosley.armadafleetcommander.game.component.upgrade.UpgradeSlot;


public class ShipDetailFragment extends Fragment {

    private Ship mShip;
    private static final String KEY_SHIP = "KEY_SHIP";

    OnUpgradeSlotClickedListener mOnUpgradeSlotClickedCallback;

    // onAddSquadron callback
    public interface OnUpgradeSlotClickedListener{
        void onUpgradeSlotClicked(UpgradeSlot slot);
    }

    public ShipDetailFragment() {
        // Required empty public constructor
    }

    public static ShipDetailFragment newInstance(Ship ship){

        ShipDetailFragment shipDetailFragment = new ShipDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_SHIP, ship);
        shipDetailFragment.setArguments(args);
        return shipDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShip = getArguments().getParcelable(KEY_SHIP);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View shipDetailFrag =  inflater.inflate(R.layout.fragment_ship_detail, container, false);

        TextView nameView = (TextView) shipDetailFrag.findViewById(R.id.txt_ship_title);
        nameView.setText(mShip.title());

        TextView classView = (TextView) shipDetailFrag.findViewById(R.id.txt_ship_class);
        classView.setText(mShip.vehicleClass());

        ListView upgradeView = (ListView) shipDetailFrag.findViewById(R.id.list_upgrades);
        upgradeView.setAdapter(new UpgradeSlotsAdapter(getActivity(), mShip.upgradeSlots()));

        upgradeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UpgradeSlot slot = (UpgradeSlot) parent.getItemAtPosition(position);
                mOnUpgradeSlotClickedCallback.onUpgradeSlotClicked(slot);
            }
        });

        return shipDetailFrag;
    }
}
