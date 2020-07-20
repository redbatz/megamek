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
 * Created on Sep 24, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons.battlearmor;

import org.redbat.roguetech.megamek.common.AmmoType;
import org.redbat.roguetech.megamek.common.WeaponType;
import org.redbat.roguetech.megamek.common.weapons.Weapon;

/**
 * @author Andrew Hunter
 */
public class CLBARecoillessRifleMedium extends Weapon {
    /**
     *
     */
    private static final long serialVersionUID = -8244299318168866609L;

    /**
     *
     */
    public CLBARecoillessRifleMedium() {
        super();
        name = "Recoilless Rifle (Medium)";
        setInternalName("CLBAMedium Recoilless Rifle");
        addLookupName("CLBAMedium Recoilless Rifle");
        addLookupName("CLBAMediumRecoillessRifle");
        addLookupName("ISBAMediumRecoillessRifle");
        addLookupName("IS BA Medium Recoilless Rifle");
        addLookupName("ISBAMedium Recoilless Rifle");
        addLookupName("ISBAMediumRecoillessRifle");
        heat = 0;
        damage = 3;
        infDamageClass = WeaponType.WEAPON_BURST_2D6;
        ammoType = AmmoType.T_NA;
        shortRange = 2;
        mediumRange = 4;
        longRange = 6;
        extremeRange = 8;
        tonnage = 0.25;
        criticals = 2;
        bv = 19;
        flags = flags.or(F_NO_FIRES).or(F_DIRECT_FIRE).or(F_BALLISTIC).or(F_BA_WEAPON).or(F_BURST_FIRE).andNot(F_MECH_WEAPON).andNot(F_TANK_WEAPON).andNot(F_AERO_WEAPON).andNot(F_PROTO_WEAPON);
        cost = 3000;
        rulesRefs = "268,TM";
        techAdvancement.setTechBase(TECH_BASE_ALL)
    	.setIntroLevel(false)
    	.setUnofficial(false)
        .setTechRating(RATING_C)
        .setAvailability(RATING_X, RATING_X, RATING_D, RATING_D)
        .setISAdvancement(3052, 3054, 3056, DATE_NONE, DATE_NONE)
        .setISApproximate(true, false, false,false, false)
        .setClanAdvancement(DATE_NONE, DATE_NONE, 3062, DATE_NONE, DATE_NONE)
        .setClanApproximate(false, false, true, false, false)
        .setPrototypeFactions(F_FS,F_LC)
        .setProductionFactions(F_FS,F_LC);
    }

}