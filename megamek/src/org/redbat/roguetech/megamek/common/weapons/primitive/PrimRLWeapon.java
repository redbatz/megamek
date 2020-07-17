/**
 * MegaMek - Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
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
package org.redbat.roguetech.megamek.common.weapons.primitive;

import org.redbat.roguetech.megamek.common.AmmoType;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.RLHandler;
import org.redbat.roguetech.megamek.common.weapons.missiles.RLWeapon;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author David Nawton
 */
public abstract class PrimRLWeapon extends RLWeapon {

    /**
     *
     */
    private static final long serialVersionUID = -1718301014226423896L;

    /**
     *
     */
    public PrimRLWeapon() {
        super();
        ammoType = AmmoType.T_ROCKET_LAUNCHER;
        flags = flags.or(F_ONESHOT);
        toHitModifier = 1;
        atClass = CLASS_ROCKET_LAUNCHER;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * megamek.common.weapons.Weapon#getCorrectHandler(megamek.common.ToHitData,
     * megamek.common.actions.WeaponAttackAction, megamek.common.Game,
     * megamek.server.Server)
     */
    @Override
    protected AttackHandler getCorrectHandler(ToHitData toHit,
                                              WeaponAttackAction waa, IGame game, Server server) {
        return new RLHandler(toHit, waa, game, server);
    }
}

