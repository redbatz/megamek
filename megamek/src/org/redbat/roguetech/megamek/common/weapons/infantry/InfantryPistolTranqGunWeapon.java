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
 * Created on Sep 7, 2005
 *
 */
package org.redbat.roguetech.megamek.common.weapons.infantry;

import org.redbat.roguetech.megamek.common.AmmoType;

/**
 * @author Ben Grills
 */
public class InfantryPistolTranqGunWeapon extends InfantryWeapon {

	/**
	 *
	 */
	private static final long serialVersionUID = -3164871600230559641L;

	public InfantryPistolTranqGunWeapon() {
		super();

		name = "Tranq Gun";
		setInternalName(name);
		addLookupName("InfantryTranquilizerGun");
		ammoType = AmmoType.T_NA;
		cost = 30;
		bv = 0.13;
		tonnage = .0015;
		flags = flags.or(F_NO_FIRES).or(F_INF_NONPENETRATING).or(F_DIRECT_FIRE).or(F_BALLISTIC);
		infantryDamage = 0.14;
		infantryRange = 0;
		rulesRefs = " 273,TM";
		techAdvancement.setTechBase(TECH_BASE_ALL).setISAdvancement(1950, 1950, 1950, DATE_NONE, DATE_NONE)
		        .setISApproximate(false, false, false, false, false)
		        .setClanAdvancement(1950, 1950, 1950, DATE_NONE, DATE_NONE)
		        .setClanApproximate(false, false, false, false, false).setTechRating(RATING_A)
		        .setAvailability(RATING_A, RATING_A, RATING_A, RATING_A);

	}
}