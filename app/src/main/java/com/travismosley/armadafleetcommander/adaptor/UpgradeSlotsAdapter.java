package com.travismosley.armadafleetcommander.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetcommander.game.component.upgrade.UpgradeSlot;

import java.util.List;

/**
 * An ArrayAdapter for upgrade slots
 */
public class UpgradeSlotsAdapter extends ArrayAdapter<UpgradeSlot> {

    public static final String LOG_TAG = UpgradeSlotsAdapter.class.getSimpleName();

    private List<UpgradeSlot> mUpgradeSlots;

    public UpgradeSlotsAdapter(Context context, List<UpgradeSlot> componentList){
        super(context, -1, componentList);

        Log.d(LOG_TAG, "Initialize for " + componentList);
        mUpgradeSlots = componentList;
    }

    public UpgradeSlot getItem(int position){
        return mUpgradeSlots.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View upgradeSlotView;

        // Recycle the view if possible
        if (convertView != null) {
            upgradeSlotView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            upgradeSlotView = inflater.inflate(R.layout.list_item_upgrade_slot, parent, false);
        }

        UpgradeSlot upgradeSlot = mUpgradeSlots.get(position);

        TextView upgradeClassView = (TextView) upgradeSlotView.findViewById(R.id.txt_upgrade_type);
        upgradeClassView.setText(upgradeSlot.typeName());

        TextView upgradeTitle = (TextView) upgradeSlotView.findViewById(R.id.txt_upgrade_title);
        TextView upgradeCost = (TextView) upgradeSlotView.findViewById(R.id.txt_point_cost);
        Button btnClear = (Button) upgradeSlotView.findViewById(R.id.btn_clear);

        if (!upgradeSlot.isEquipped()){
            upgradeTitle.setText(getContext().getString(R.string.upgrade_empty));
            upgradeCost.setText("");
            btnClear.setVisibility(View.INVISIBLE);
        } else{
            Upgrade upgrade = upgradeSlot.getEquipped();
            upgradeTitle.setText(upgrade.title());
            upgradeCost.setText(String.valueOf(upgrade.pointCost()));
            btnClear.setVisibility(View.INVISIBLE);
        }

        return upgradeSlotView;
    }

}
