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
package org.redbat.roguetech.megamek.common.weapons.unofficial;

import org.redbat.roguetech.megamek.common.TechAdvancement;
import org.redbat.roguetech.megamek.common.weapons.missiles.MRMWeapon;

/**
 * @author Sebastian Brocks
 */
public class ISMRM5OS extends MRMWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 3581161640370371727L;

    /**
     *
     */
    public ISMRM5OS() {
        super();
        name = "MRM 5 (OS)";
        setInternalName(name);
        addLookupName("ISMRM5OS");
        rackSize = 5;
        shortRange = 3;
        mediumRange = 8;
        longRange = 15;
        extremeRange = 16;
        bv = 6;
        flags = flags.or(F_ONESHOT);
        techAdvancement.setTechBase(TechAdvancement.TECH_BASE_IS);
        techAdvancement.setISAdvancement(DATE_NONE, DATE_NONE, 3057);
        techAdvancement.setTechRating(RATING_E);
        techAdvancement.setAvailability( new int[] { RATING_X, RATING_X, RATING_E, RATING_X });
    }
}