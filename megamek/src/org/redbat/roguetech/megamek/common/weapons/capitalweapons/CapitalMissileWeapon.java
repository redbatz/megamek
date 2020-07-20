/**
 * MegaMek - Copyright (C) 2004,2005 Ben Mazur (bmazur@sev.org)
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
/*
 * Created on Sep 2, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons.capitalweapons;

import org.redbat.roguetech.megamek.common.*;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.*;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Jay Lawson
 */
public abstract class CapitalMissileWeapon extends AmmoWeapon {
    /**
     * 
     */
    private static final long serialVersionUID = 9186993166684654767L;

    public CapitalMissileWeapon() {
        super();
        this.atClass = CLASS_CAPITAL_MISSILE;
        this.capital = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * megamek.common.weapons.Weapon#getCorrectHandler(megamek.common.ToHitData,
     * megamek.common.actions.WeaponAttackAction, megamek.common.IGame)
     */
    @Override
    protected AttackHandler getCorrectHandler(ToHitData toHit,
                                              WeaponAttackAction waa, IGame game, Server server) {
        Mounted weapon = game.getEntity(waa.getEntityId())
                .getEquipment(waa.getWeaponId());
        Entity attacker = game.getEntity(waa.getEntityId());
        int rangeToTarget = attacker.getPosition().distance(waa.getTarget(game).getPosition());
        // Capital missiles work like artillery for surface to surface fire
        if (Compute.isGroundToGround(attacker, waa.getTarget(game))) {
            return new ArtilleryWeaponIndirectFireHandler(toHit, waa, game, server);
        }
        if (weapon.isInBearingsOnlyMode()
                && rangeToTarget >= RangeType.RANGE_BEARINGS_ONLY_MINIMUM) {
            return new CapitalMissileBearingsOnlyHandler(toHit, waa, game, server);
        }
        return new CapitalMissileHandler(toHit, waa, game, server);
    }
    
    @Override
    public int getBattleForceClass() {
        return BFCLASS_CAPITAL_MISSILE;
    }
}