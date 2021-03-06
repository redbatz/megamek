package org.redbat.roguetech.megamek.common.weapons.lasers;

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

/**
 * @author Andrew Hunter
 */
public class CLHeavyLaserLarge extends LaserWeapon {
    /**
     * 
     */
    private static final long serialVersionUID = 4467522144065588079L;

    /**
     * 
     */
    public CLHeavyLaserLarge() {
        super();
        this.name = "Heavy Large Laser";
        this.setInternalName("CLHeavyLargeLaser");
        this.addLookupName("Clan Large Heavy Laser");
        this.heat = 18;
        this.damage = 16;
        this.toHitModifier = 1;
        this.shortRange = 5;
        this.mediumRange = 10;
        this.longRange = 15;
        this.extremeRange = 20;
        this.waterShortRange = 3;
        this.waterMediumRange = 6;
        this.waterLongRange = 9;
        this.waterExtremeRange = 12;
        this.tonnage = 4.0;
        this.criticals = 3;
        this.bv = 244;
        this.cost = 250000;
        this.shortAV = 16;
        this.medAV = 16;
        this.maxRange = RANGE_MED;
        rulesRefs = "226,TM";
        techAdvancement.setTechBase(TECH_BASE_CLAN)
        	.setIntroLevel(false)
        	.setUnofficial(false)
            .setTechRating(RATING_F)
            .setAvailability(RATING_X, RATING_X, RATING_D, RATING_D)
            .setClanAdvancement(3057, 3059, 3064, DATE_NONE, DATE_NONE)
            .setClanApproximate(true, false, false,false, false)
            .setPrototypeFactions(F_CBR,F_CSA)
            .setProductionFactions(F_CSA);
    }
}
