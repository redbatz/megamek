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
 * Created on Sep 12, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons.battlearmor;

import org.redbat.roguetech.megamek.common.weapons.lasers.LaserWeapon;

/**
 * @author Jay Lawson
 */
public class ISBALaserSmall extends LaserWeapon {

    /**
     *
     */
    private static final long serialVersionUID = -4033152775138299857L;

    public ISBALaserSmall() {
        super();
        name = "Small Laser";
        setInternalName("ISBASmallLaser");
        addLookupName("ISBASmall Laser");
        heat = 1;
        damage = 3;
        shortRange = 1;
        mediumRange = 2;
        longRange = 3;
        extremeRange = 4;
        waterShortRange = 1;
        waterMediumRange = 2;
        waterExtremeRange = 4;
        tonnage = 0.2;
        criticals = 1;
        flags = flags.or(F_NO_FIRES).or(F_BA_WEAPON).andNot(F_MECH_WEAPON).andNot(F_TANK_WEAPON).andNot(F_AERO_WEAPON).andNot(F_PROTO_WEAPON);
        bv = 9;
        cost = 11250;
        shortAV = 3;
        maxRange = RANGE_SHORT;
        atClass = CLASS_POINT_DEFENSE;
        rulesRefs = "258,TM";
        techAdvancement.setTechBase(TECH_BASE_IS)
    	.setIntroLevel(false)
    	.setUnofficial(false)
        .setTechRating(RATING_E)
        .setAvailability(RATING_X, RATING_X, RATING_B, RATING_B)
        .setISAdvancement(3050, 3050, 3050, DATE_NONE, DATE_NONE)
        .setISApproximate(true, false, false,false, false)
        .setPrototypeFactions(F_FS,F_LC)
        .setProductionFactions(F_FS,F_LC,F_DC);
    }

}