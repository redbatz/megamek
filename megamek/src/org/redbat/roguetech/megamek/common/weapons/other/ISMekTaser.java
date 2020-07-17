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
package org.redbat.roguetech.megamek.common.weapons.other;

import org.redbat.roguetech.megamek.common.AmmoType;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.SimpleTechLevel;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.AmmoWeapon;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.MechTaserHandler;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Jason Tighe
 */
public class ISMekTaser extends AmmoWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 4393086562754363816L;

    /**
     *
     */
    public ISMekTaser() {
        super();
        name = "BattleMech Taser";
        setInternalName("Mek Taser");
        addLookupName("ISMekTaser");
        addLookupName("ISBattleMechTaser");
        heat = 6;
        rackSize = 1;
        damage = 1;
        ammoType = AmmoType.T_TASER;
        shortRange = 1;
        mediumRange = 2;
        longRange = 4;
        extremeRange = 4;
        bv = 40;
        toHitModifier = 1;
        cost = 200000;
        tonnage = 4;
        criticals = 3;
        explosionDamage = 6;
        explosive = true;
        flags = flags.or(F_MECH_WEAPON).or(F_BALLISTIC).or(F_DIRECT_FIRE)
                .or(F_TASER).or(F_TANK_WEAPON);
        rulesRefs = "346,TO";
        techAdvancement.setTechBase(TECH_BASE_IS).setTechRating(RATING_E)
            .setAvailability(RATING_X, RATING_X, RATING_F, RATING_E)
            .setISAdvancement(3065, 3084).setPrototypeFactions(F_FS)
            .setProductionFactions(F_FS).setStaticTechLevel(SimpleTechLevel.EXPERIMENTAL);
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
        return new MechTaserHandler(toHit, waa, game, server);
    }
}
