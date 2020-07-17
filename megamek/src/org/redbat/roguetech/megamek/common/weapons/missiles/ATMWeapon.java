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
package org.redbat.roguetech.megamek.common.weapons.missiles;

import org.redbat.roguetech.megamek.common.AmmoType;
import org.redbat.roguetech.megamek.common.BattleForceElement;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.ATMHandler;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Sebastian Brocks
 */
public abstract class ATMWeapon extends MissileWeapon {

    /**
     *
     */
    private static final long serialVersionUID = -1735365348213073649L;

    public ATMWeapon() {
        super();
        ammoType = AmmoType.T_ATM;
        atClass = CLASS_ATM;
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
        return new ATMHandler(toHit, waa, game, server);
    }
    
    @Override
    public double getBattleForceDamage(int range) {
        double damage = super.getBattleForceDamage(range);
        if (range < BattleForceElement.MEDIUM_RANGE) {
            damage *= 3;
        } else if (range < BattleForceElement.LONG_RANGE) {
            damage *= 2;
        }
        return damage;
    }
}
