package com.travismosley.armadafleetcommander.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.adaptor.UpgradeSlotsAdapter;
import com.travismosley.armadafleetcommander.game.component.Ship;

public class ShipDetailFragment extends Fragment {

    private Ship mShip;

    public ShipDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShip = getArguments().getParcelable(getString(R.string.key_ship));
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

        return shipDetailFrag;
    }
}
