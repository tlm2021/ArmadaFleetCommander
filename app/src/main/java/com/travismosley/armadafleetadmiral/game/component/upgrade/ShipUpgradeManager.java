package com.travismosley.armadafleetadmiral.game.component.upgrade;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipUpgradeManager implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ShipUpgradeManager> CREATOR = new Parcelable.Creator<ShipUpgradeManager>() {
        @Override
        public ShipUpgradeManager createFromParcel(Parcel in) {
            return new ShipUpgradeManager(in);
        }

        @Override
        public ShipUpgradeManager[] newArray(int size) {
            return new ShipUpgradeManager[size];
        }
    };
    private final static String LOG_TAG = ShipUpgradeManager.class.getSimpleName();
    private Map<Integer, UpgradeSlot> mUpgradeSlots = new HashMap<>();
    private Map<Integer, List<Integer>> mTypeMap = new HashMap<>();
    private int mNextId = 0;

    public ShipUpgradeManager(List<UpgradeSlot> upgradeSlots) {
        setUpgradeSlots(upgradeSlots);
    }

    // Parcel support
    protected ShipUpgradeManager(Parcel in) {

        ArrayList<UpgradeSlot> slots = new ArrayList<>();
        if (in.readByte() == 0x01) {
            in.readList(slots, UpgradeSlot.class.getClassLoader());
        }
        setUpgradeSlots(slots);
    }

    public List<Upgrade> getEquippedUpgrades() {
        ArrayList<Upgrade> upgrades = new ArrayList<>();

        for (UpgradeSlot upgradeSlot : getUpgradeSlots()) {
            if (upgradeSlot.isEquipped()) {
                upgrades.add(upgradeSlot.getEquipped());
            }
        }

        return upgrades;
    }

    public int getEquippedUpgradePoints() {

        int pointTotal = 0;
        List<Upgrade> equippedUpgrades = getEquippedUpgrades();

        for (int i = 0; i < equippedUpgrades.size(); i++) {
            pointTotal += equippedUpgrades.get(i).pointCost();
        }
        return pointTotal;
    }

    private UpgradeSlot getEmptySlotForType(int typeId) {
        List<UpgradeSlot> slotCandidates = getUpgradeSlotsByType(typeId);

        if (slotCandidates == null) {
            return null;
        }

        for (int i = 0; i < slotCandidates.size(); i++) {
            UpgradeSlot slot = slotCandidates.get(i);
            if (!slot.isEquipped()) {
                return slot;
            }
        }

        return null;
    }

    public boolean hasUpgrade(Upgrade upgrade) {
        List<UpgradeSlot> slotCandidates = getUpgradeSlotsByType(upgrade.upgradeTypeId());
        if (slotCandidates == null) {
            return false;
        }

        for (int i = 0; i < slotCandidates.size(); i++) {
            if (slotCandidates.get(i).isEquipped()) {
                if (slotCandidates.get(i).getEquipped().id() == upgrade.id()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addUpgrade(Upgrade upgrade) {
        Log.d(LOG_TAG, "added upgrade " + upgrade);

        UpgradeSlot slot = getEmptySlotForType(upgrade.upgradeTypeId());
        Log.d(LOG_TAG, "addUpgrade isNull " + String.valueOf(slot == null));

        if (slot == null) {
            throw new RuntimeException("Unable to add upgrade " + upgrade + ". No empty slots of type: " + upgrade.upgradeTypeId());
        }

        slot.equip(upgrade);

    }

    public List<UpgradeSlot> getUpgradeSlots() {
        return new ArrayList<>(mUpgradeSlots.values());
    }

    public void setUpgradeSlots(List<UpgradeSlot> upgradeSlots) {

        for (int i = 0; i < upgradeSlots.size(); i++) {
            UpgradeSlot slot = upgradeSlots.get(i);
            mUpgradeSlots.put(mNextId, slot);

            if (!mTypeMap.containsKey(slot.typeId())) {
                mTypeMap.put(slot.typeId(), new ArrayList<Integer>());
            }

            mTypeMap.get(slot.typeId()).add(mNextId);

            mNextId++;
        }
    }

    public ArrayList<UpgradeSlot> getUpgradeSlotsByType(int typeId) {
        if (!hasSlotsOfType(typeId)) {
            return null;
        }

        ArrayList<UpgradeSlot> slots = new ArrayList<>();
        List<Integer> slotIds = mTypeMap.get(typeId);

        for (int i = 0; i < slotIds.size(); i++) {
            slots.add(mUpgradeSlots.get(slotIds.get(i)));
        }

        return slots;
    }

    public boolean hasSlotsOfType(int typeId) {
        return mTypeMap.containsKey(typeId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        List<UpgradeSlot> upgradeSlots = getUpgradeSlots();

        if (upgradeSlots.isEmpty()) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(getUpgradeSlots());
        }
    }
}
