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
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.Weapon;
import org.redbat.roguetech.megamek.server.Server;

public class CLBALBX extends Weapon {

    /**
     *
     */
    private static final long serialVersionUID = 2978911783244524588L;

    public CLBALBX() {
        super();

        name = "Battle Armor LB-X AC";
        setInternalName(name);
        addLookupName("CLBALBX");
        addLookupName("Clan BA LBX");
        heat = 0;
        damage = 4;
        rackSize = 4;
        shortRange = 2;
        mediumRange = 5;
        longRange = 8;
        extremeRange = 10;
        tonnage = 0.4;
        criticals = 2;
        toHitModifier = -1;
        ammoType = AmmoType.T_NA;
        bv = 20;
        cost = 70000;
        flags = flags.or(F_NO_FIRES).or(F_BA_WEAPON).andNot(F_MECH_WEAPON).andNot(F_TANK_WEAPON).andNot(F_AERO_WEAPON).andNot(F_PROTO_WEAPON);
        rulesRefs = "207,TM";
        techAdvancement.setTechBase(TECH_BASE_CLAN)
            .setTechRating(RATING_F).setAvailability(RATING_X, RATING_X, RATING_E, RATING_D)
            .setClanAdvancement(3075, 3085).setClanApproximate(false, false)
            .setPrototypeFactions(F_CNC).setProductionFactions(F_CNC)
            .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }

    @Override
    protected AttackHandler getCorrectHandler(ToHitData toHit,
                                              WeaponAttackAction waa, IGame game, Server server) {
        return new BALBXHandler(toHit, waa, game, server);
    }

    /**
     * non-squad size version for AlphaStrike base damage
     */
    @Override
    public double getBattleForceDamage(int range) {
        double damage = 0;
        if (range <= getLongRange()) {
            damage = Compute.calculateClusterHitTableAmount(7, getDamage());
            damage *= 1.05; // -1 to hit
            if (range == BattleForceElement.SHORT_RANGE && getMinimumRange() > 0) {
                damage = adjustBattleForceDamageForMinRange(damage);
            }
        }
        return damage / 10.0;
    }

    @Override
    public double getBattleForceDamage(int range, int baSquadSize) {
        double damage = 0;
        if (range <= getLongRange()) {
            damage = Compute.calculateClusterHitTableAmount(7, getDamage() * baSquadSize);
            damage *= 1.05; // -1 to hit
            if (range == BattleForceElement.SHORT_RANGE && getMinimumRange() > 0) {
                damage = adjustBattleForceDamageForMinRange(damage);
            }
        }
        return damage / 10.0;
    }
}
