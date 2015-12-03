package com.travismosley.armadafleetcommander.game;

import com.travismosley.armadafleetcommander.game.components.Ship;
import com.travismosley.armadafleetcommander.game.components.Squadron;

import java.util.List;

/**
 * Simple container class for Fleet properties
 */
public class Fleet {

    public int mPointLimit;
    public List<Squadron> mSquadrons;
    public List<Ship> mShips;

}
