/**
 * MegaMek - Copyright (C) 2004 Ben Mazur (bmazur@sev.org)
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
 * Created on Sep 23, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons.flamers;

import org.redbat.roguetech.megamek.common.AmmoType;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.AmmoWeapon;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.VehicleFlamerCoolHandler;
import org.redbat.roguetech.megamek.common.weapons.VehicleFlamerHandler;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Andrew Hunter
 */
public abstract class VehicleFlamerWeapon extends AmmoWeapon {
    /**
     *
     */
    private static final long serialVersionUID = -8729838198434670197L;

    /**
     *
     */
    public VehicleFlamerWeapon() {
        super();
        flags = flags.or(F_MECH_WEAPON).or(F_TANK_WEAPON).or(F_PROTO_WEAPON)
                .or(F_FLAMER).or(F_ENERGY).or(F_BURST_FIRE);
        ammoType = AmmoType.T_VEHICLE_FLAMER;
        atClass = CLASS_POINT_DEFENSE;
    }

    @Override
    protected AttackHandler getCorrectHandler(ToHitData toHit,
                                              WeaponAttackAction waa, IGame game, Server server) {
        AmmoType atype = (AmmoType) game.getEntity(waa.getEntityId())
                .getEquipment(waa.getWeaponId()).getLinked().getType();
        
        if (atype.getMunitionType() == AmmoType.M_COOLANT) {
            return new VehicleFlamerCoolHandler(toHit, waa, game, server);
        } else {
            return new VehicleFlamerHandler(toHit, waa, game, server);
        }
    }

    @Override
    public int getBattleForceHeatDamage(int range) {
        //Clan ER Flamer does damage at medium
        if (getMediumRange() > range) {
            return getDamage();
        }
        return 0;
    }
}
