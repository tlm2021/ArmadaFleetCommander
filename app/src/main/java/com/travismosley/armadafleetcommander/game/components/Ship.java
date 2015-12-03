package com.travismosley.armadafleetcommander.game.components;

import java.util.List;

/**
 * Simple container class for Ship properties
 */
public class Ship {
    public int mInstanceId;
    public int mShipId;
    public String mTitle;
    public boolean mUnique;
    public int mHull;
    public int mSpeed;
    public int mPointCost;

    public List<String> mUpgrades;
}