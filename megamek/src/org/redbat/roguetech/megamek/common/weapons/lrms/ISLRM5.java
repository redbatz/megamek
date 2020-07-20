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
package org.redbat.roguetech.megamek.common.weapons.lrms;

/**
 * @author Sebastian Brocks
 */
public class ISLRM5 extends LRMWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 1922843634155860893L;

    /**
     *
     */
    public ISLRM5() {
        super();
        name = "LRM 5";
        setInternalName(name);
        addLookupName("IS LRM-5");
        addLookupName("ISLRM5");
        addLookupName("IS LRM 5");
        heat = 2;
        rackSize = 5;
        minimumRange = 6;
        tonnage = 2.0;
        criticals = 1;
        bv = 45;
        cost = 30000;
        shortAV = 3;
        medAV = 3;
        longAV = 3;
        maxRange = RANGE_LONG;
        rulesRefs = "229,TM";
        flags = flags.andNot(F_PROTO_WEAPON);
        techAdvancement.setTechBase(TECH_BASE_ALL)
        	.setIntroLevel(true)
        	.setUnofficial(false)
            .setTechRating(RATING_C)
            .setAvailability(RATING_C, RATING_C, RATING_C, RATING_C)
            .setISAdvancement(2295, 2300, 2400, DATE_NONE, DATE_NONE)
            .setISApproximate(false, false, false,false, false)
            .setClanAdvancement(2295, 2300, 2400, 2830, DATE_NONE)
            .setClanApproximate(false, false, false,false, false)
            .setPrototypeFactions(F_TA)
            .setProductionFactions(F_TA);
    }
}