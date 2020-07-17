/*
 * MegaMek - Copyright (C) 2007-2008 Ben Mazur (bmazur@sev.org)
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation; either version 2 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 */
package org.redbat.roguetech.megamek.server.victory;

import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.IPlayer;

import java.io.Serializable;

/**
 * abstract baseclass for bv-checking victory implementations
 */
public abstract class AbstractBVVictory implements IVictoryConditions, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -689891568905531049L;

    public int getFriendlyBV(IGame game, IPlayer player) {
        int ret = 0;
        for (IPlayer other : game.getPlayersVector()) {
            if (other.isObserver())
                continue;
            if (!other.isEnemyOf(player)) {
                ret += other.getBV();
            }
        }
        return ret;
    }

    public int getEnemyBV(IGame game, IPlayer player) {
        int ret = 0;
        for (IPlayer other : game.getPlayersVector()) {
            if (other.isObserver())
                continue;
            if (other.isEnemyOf(player)) {
                ret += other.getBV();
            }
        }
        return ret;
    }

    public int getEnemyInitialBV(IGame game, IPlayer player) {
        int ret = 0;
        for (IPlayer other : game.getPlayersVector()) {
            if (other.isObserver())
                continue;
            if (other.isEnemyOf(player)) {
                ret += other.getInitialBV();
            }
        }
        return ret;
    }

}