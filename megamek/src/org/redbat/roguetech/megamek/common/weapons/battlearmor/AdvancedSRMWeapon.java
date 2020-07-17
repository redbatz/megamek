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
package org.redbat.roguetech.megamek.common.weapons.battlearmor;

import org.redbat.roguetech.megamek.common.*;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.AdvancedSRMHandler;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.srms.SRMWeapon;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Sebastian Brocks
 */
public abstract class AdvancedSRMWeapon extends SRMWeapon {

    /**
     * 
     */
    private static final long serialVersionUID = 8098857067349950771L;

    /**
     * 
     */
    public AdvancedSRMWeapon() {
        super();
        this.ammoType = AmmoType.T_SRM_ADVANCED;
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
        return new AdvancedSRMHandler(toHit, waa, game, server);
    }

    /**
     * non-squad size version for AlphaStrike base damage
     */
    @Override 
    public double getBattleForceDamage(int range) {
        if (range > getLongRange()) {
            return 0;
        }
        double damage = Compute.calculateClusterHitTableAmount(8, getRackSize()) * 2;
        if (range == BattleForceElement.SHORT_RANGE && getMinimumRange() > 0) {
            damage = adjustBattleForceDamageForMinRange(damage);
        }
        return damage / 10.0;
    }

    @Override
    public double getBattleForceDamage(int range, int baSquadSize) {
        if (range > getLongRange()) {
            return 0;
        }
        double damage = Compute.calculateClusterHitTableAmount(8, getRackSize() * baSquadSize);
        if (range == BattleForceElement.SHORT_RANGE && getMinimumRange() > 0) {
            damage = adjustBattleForceDamageForMinRange(damage);
        }
        return damage / 10.0;
    }
    
    @Override
    public int getBattleForceClass() {
        return BFCLASS_STANDARD;
    }
}
