package com.travismosley.armadafleetadmiral.fragment.listener;

import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetadmiral.game.component.upgrade.UpgradeSlot;

/**
 * Interface for handling selected upgrades. Requires also knowing about their slot.
 */

public interface OnUpgradeSelectedListener {
    void onComponentSelected(Upgrade upgrade, UpgradeSlot slot);
}
