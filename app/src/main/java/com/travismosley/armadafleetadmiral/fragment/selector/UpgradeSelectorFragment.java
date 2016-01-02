package com.travismosley.armadafleetadmiral.fragment.selector;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.travismosley.armadafleetadmiral.adaptor.list.selector.UpgradesSelectorAdapter;
import com.travismosley.armadafleetadmiral.fragment.listener.OnUpgradeSelectedListener;
import com.travismosley.armadafleetadmiral.game.Fleet;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetadmiral.game.component.upgrade.UpgradeSlot;

/**
 * A fragment for selecting upgrades
 */
public class UpgradeSelectorFragment extends ComponentSelectorFragment<Upgrade> {

    private static final String LOG_TAG = UpgradeSelectorFragment.class.getSimpleName();
    private static final String KEY_UPGRADE_SLOT = "KEY_UPGRADE_SLOT";

    private UpgradeSlot mUpgradeSlot;
    OnUpgradeSelectedListener mListener;

    // Public constructor
    public UpgradeSelectorFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUpgradeSlot = getArguments().getParcelable(KEY_UPGRADE_SLOT);
    }

    public static UpgradeSelectorFragment newInstance(UpgradeSlot slot, Fleet fleet){
        UpgradeSelectorFragment shipSelector = new UpgradeSelectorFragment();
        shipSelector.setArguments(getBundleForNewInstance(slot, fleet));
        return shipSelector;
    }

    private static Bundle getBundleForNewInstance(UpgradeSlot slot, Fleet fleet){
        Bundle args = getBundleForNewInstance(fleet);
        args.putParcelable(KEY_UPGRADE_SLOT, slot);
        return args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View upgradeSelectorView = super.onCreateView(inflater, container, savedInstanceState);

        setListAdapter(new UpgradesSelectorAdapter(
                getActivity(),
                mArmadaDb.getUpgradesForTypeAndFaction(mUpgradeSlot.typeId(), mFleet.mFactionId),
                mFleet));

        return upgradeSelectorView;
    }

    @Override
    public void onListItemClick(ListView view, View v, int position, long id){
        super.onListItemClick(view, v, position, id);
        Log.i(LOG_TAG, "Called onListItemClick");

        if (mListener != null){
            Upgrade upgrade = (Upgrade) this.getListAdapter().getItem(position);
            mListener.onComponentSelected(upgrade, mUpgradeSlot);
        }
    }

    public void setSelectionListener(OnUpgradeSelectedListener listener){
        mListener = listener;
    }
}
