package com.travismosley.armadafleetcommander.fragment.selector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travismosley.armadafleetcommander.adaptor.selector.UpgradesSelectorAdapter;
import com.travismosley.armadafleetcommander.game.Fleet;
import com.travismosley.armadafleetcommander.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetcommander.game.component.upgrade.UpgradeSlot;

/**
 * A fragment for selecting upgrades
 */
public class UpgradeSelectorFragment extends ComponentSelectorFragment<Upgrade> {

    private static final String LOG_TAG = UpgradeSelectorFragment.class.getSimpleName();
    private static final String KEY_UPGRADE_SLOT = "KEY_UPGRADE_SLOT";
    private UpgradeSlot mUpgradeSlot;

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
}
