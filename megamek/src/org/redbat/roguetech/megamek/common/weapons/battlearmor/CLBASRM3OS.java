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

import org.redbat.roguetech.megamek.common.weapons.srms.SRMWeapon;


/**
 * @author Sebastian Brocks
 */
public class CLBASRM3OS extends SRMWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 1661723137877595056L;

    /**
     *
     */
    public CLBASRM3OS() {
        super();
        name = "SRM 3 (OS)";
        setInternalName("CLBASRM3 (OS)");
        addLookupName("Clan BA SRM 3 (OS)");
        addLookupName("Clan BA OS SRM-3");
        addLookupName("CLBASRM3OS");
        rackSize = 3;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        bv = 6;
        flags = flags.or(F_NO_FIRES).or(F_BA_WEAPON).or(F_ONESHOT).andNot(F_MECH_WEAPON).andNot(F_TANK_WEAPON).andNot(F_AERO_WEAPON).andNot(F_PROTO_WEAPON);
        cost = 7500;
        tonnage = .06;
        criticals = 2;
        shortAV = 2;
        maxRange = RANGE_SHORT;
		rulesRefs = "261,TM";
		techAdvancement.setTechBase(TECH_BASE_CLAN)
		.setIntroLevel(false)
		.setUnofficial(false)
	    .setTechRating(RATING_F)
	    .setAvailability(RATING_X, RATING_D, RATING_C, RATING_B)
	    .setClanAdvancement(2865, 2868, 2870, DATE_NONE, DATE_NONE)
	    .setClanApproximate(true, false, false, false, false)
	    .setPrototypeFactions(F_CWF)
	    .setProductionFactions(F_CWF);
    }
    
}