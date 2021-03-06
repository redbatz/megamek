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
package org.redbat.roguetech.megamek.common.weapons.battlearmor;

import org.redbat.roguetech.megamek.common.TechAdvancement;
import org.redbat.roguetech.megamek.common.WeaponType;

/**
 * @author Sebastian Brocks
 */
public class ISBAMG extends BAMGWeapon {

    /**
     *
     */
    private static final long serialVersionUID = -4420620461776813639L;

    /**
     *
     */
    public ISBAMG() {
        super();
        name = "Machine Gun (Medium)";
        setInternalName("ISBAMG");
        addLookupName("IS BA Machine Gun");
        addLookupName("ISBAMachine Gun");
        addLookupName("ISBAMachineGun");
        heat = 0;
        damage = 2;
        infDamageClass = WeaponType.WEAPON_BURST_1D6;
        rackSize = 2;
        shortRange = 1;
        mediumRange = 2;
        longRange = 3;
        extremeRange = 4;
        tonnage = 0.1;
        criticals = 1;
        bv = 5;
        cost = 5000;
		rulesRefs = "258,TM";
        techAdvancement.setTechBase(TechAdvancement.TECH_BASE_IS);
        techAdvancement.setISAdvancement(DATE_NONE, 1950, 3050);
        techAdvancement.setTechRating(RATING_C);
        techAdvancement.setAvailability( new int[] { RATING_X, RATING_D, RATING_B, RATING_B });
    }

}


//Commented out in Weapontype. Clan version is same stats as IS one. And Clan versions captures Tech progression for both.