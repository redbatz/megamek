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
public class InfantryArchaicNeuralWhipWeapon extends InfantryWeapon {

	/**
	 *
	 */
	private static final long serialVersionUID = -3164871600230559641L;

	public InfantryArchaicNeuralWhipWeapon() {
		super();

		name = "Whip (Neural Whip)";
		setInternalName(name);
		addLookupName("InfantryNeuralWhip");
		addLookupName("Neural Whip");
		ammoType = AmmoType.T_NA;
		cost = 500;
		bv = 0.08;
        tonnage = .0003; 
		flags = flags.or(F_NO_FIRES).or(F_INF_NONPENETRATING).or(F_INF_POINT_BLANK).or(F_INF_ARCHAIC);
		infantryDamage = 0.09;
		infantryRange = 0;
		rulesRefs = "272,TM";
		techAdvancement.setTechBase(TECH_BASE_ALL).setISAdvancement(2315, 2325, 2500, DATE_NONE, DATE_NONE)
				.setISApproximate(true, true, false, false, false).setClanAdvancement(2315, 2325, 2500, 2786, DATE_NONE)
				.setClanApproximate(true, true, false, false, false).setPrototypeFactions(F_DC)
				.setProductionFactions(F_DC).setTechRating(RATING_D)
				.setAvailability(RATING_F, RATING_F, RATING_E, RATING_F);

	}
}