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
package org.redbat.roguetech.megamek.common.weapons;

import org.redbat.roguetech.megamek.common.*;
import org.redbat.roguetech.megamek.common.weapons.capitalweapons.CapitalMissileWeapon;

/**
 * @author Jay Lawson
 */
public class AlamoMissileWeapon extends CapitalMissileWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 3672430739887768960L;

    public AlamoMissileWeapon() {
        super();
        name = "Alamo Missile";
        setInternalName(BombType.getBombWeaponName(BombType.B_ALAMO));
        flags = flags.or(WeaponType.F_BOMB_WEAPON).or(WeaponType.F_MISSILE);
        heat = 0;
        damage = 10;
        rackSize = 1;
        shortRange = 6;
        mediumRange = 12;
        longRange = 24;
        extremeRange = 40;
        tonnage = 0;
        criticals = 0;
        hittable = false;
        bv = 0;
        cost = 0;
        shortAV = 10;
        medAV = 10;
        missileArmor = 20;
        maxRange = WeaponType.RANGE_MED;
        ammoType = AmmoType.T_ALAMO;
        capital = true;
        techAdvancement.setTechBase(TechAdvancement.TECH_BASE_IS);
        techAdvancement.setISAdvancement(3071, ITechnology.DATE_NONE, ITechnology.DATE_NONE);
        techAdvancement.setTechRating(ITechnology.RATING_C);
        techAdvancement.setAvailability( new int[] { ITechnology.RATING_E, ITechnology.RATING_E, ITechnology.RATING_E, ITechnology.RATING_E });
    }
}
