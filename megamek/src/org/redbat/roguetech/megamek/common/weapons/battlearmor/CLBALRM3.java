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

import org.redbat.roguetech.megamek.common.weapons.lrms.LRMWeapon;

/**
 * @author Sebastian Brocks
 */
public class CLBALRM3 extends LRMWeapon {

    /**
     *
     */
    private static final long serialVersionUID = -4086505975056019860L;

    /**
     *
     */
    public CLBALRM3() {
        super();
        name = "LRM 3";
        setInternalName("CLBALRM3");
        heat = 0;
        rackSize = 3;
        minimumRange = WEAPON_NA;
        tonnage = 0.105;
        criticals = 3;
        bv = 35;
        cost = 18000;
        flags = flags.or(F_NO_FIRES).or(F_BA_WEAPON).andNot(F_MECH_WEAPON).andNot(F_TANK_WEAPON).andNot(F_AERO_WEAPON).andNot(F_PROTO_WEAPON);
		rulesRefs = "261,TM";
		techAdvancement.setTechBase(TECH_BASE_CLAN)
		.setIntroLevel(false)
		.setUnofficial(false)
	    .setTechRating(RATING_F)
	    .setAvailability(RATING_X, RATING_X, RATING_F, RATING_D)
	    .setClanAdvancement(3058, 3060, 3062, DATE_NONE, DATE_NONE)
	    .setClanApproximate(true, false, false, false, false)
	    .setPrototypeFactions(F_CGS)
	    .setProductionFactions(F_CGS);
    }
}
