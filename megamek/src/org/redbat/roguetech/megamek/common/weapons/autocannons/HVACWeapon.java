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
 * Created on Sep 25, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons.autocannons;

import org.redbat.roguetech.megamek.common.AmmoType;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.Mounted;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.HVACWeaponHandler;
import org.redbat.roguetech.megamek.common.weapons.RapidfireHVACWeaponHandler;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Jason Tighe
 */
public abstract class HVACWeapon extends ACWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 4958849713169213573L;

    public HVACWeapon() {
        super();
        ammoType = AmmoType.T_HYPER_VELOCITY;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * megamek.common.weapons.ACWeapon#getCorrectHandler(megamek.common.ToHitData
     * , megamek.common.actions.WeaponAttackAction, megamek.common.IGame,
     * megamek.server.Server)
     */
    @Override
    protected AttackHandler getCorrectHandler(ToHitData toHit,
                                              WeaponAttackAction waa, IGame game, Server server) {
        Mounted weapon = game.getEntity(waa.getEntityId()).getEquipment(
                waa.getWeaponId());
        if (weapon.curMode().equals("Rapid")) {
            return new RapidfireHVACWeaponHandler(toHit, waa, game, server);
        }
        return new HVACWeaponHandler(toHit, waa, game, server);
    }

}
