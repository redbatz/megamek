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
package org.redbat.roguetech.megamek.common.weapons.other;

import org.redbat.roguetech.megamek.common.AmmoType;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.NarcExplosiveHandler;
import org.redbat.roguetech.megamek.common.weapons.NarcHandler;
import org.redbat.roguetech.megamek.common.weapons.missiles.MissileWeapon;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Sebastian Brocks
 */
public abstract class NarcWeapon extends MissileWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 1651402906360520759L;

    /**
     *
     */
    public NarcWeapon() {
        super();
        flags = flags.or(F_NO_FIRES).or(F_PROTO_WEAPON);
        ammoType = AmmoType.T_NARC;
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
        AmmoType atype = (AmmoType) game.getEntity(waa.getEntityId())
                .getEquipment(waa.getWeaponId()).getLinked().getType();
        if ((atype.getMunitionType() == AmmoType.M_NARC_EX)
                || (atype.getMunitionType() == AmmoType.M_EXPLOSIVE)) {
            return new NarcExplosiveHandler(toHit, waa, game, server);
        }
        return new NarcHandler(toHit, waa, game, server);
    }
}