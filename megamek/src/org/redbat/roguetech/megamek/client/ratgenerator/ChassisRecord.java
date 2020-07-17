/*
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

package org.redbat.roguetech.megamek.client.ratgenerator;

import org.redbat.roguetech.megamek.common.logging.DefaultMmLogger;
import org.redbat.roguetech.megamek.common.logging.LogLevel;

import java.util.HashSet;

/**
 * The ChassisRecord tracks all available variants and determines how much total weight
 * is to be distributed among the various models.
 * 
 * @author Neoancient
 * 
 */
public class ChassisRecord extends AbstractUnitRecord {

	protected HashSet<ModelRecord> models;
	
	public ChassisRecord(String chassis) {
		super(chassis);
		models = new HashSet<ModelRecord>();
	}
	
	public void addModel(ModelRecord model) {
		models.add(model);
		if (introYear == 0 || model.getIntroYear() < getIntroYear()) {
			introYear = model.getIntroYear();
		}
	}
	
	public HashSet<ModelRecord> getModels() {
		return models;
	}
	
	public int totalModelWeight(int era, String fKey) {
		FactionRecord fRec = RATGenerator.getInstance().getFaction(fKey);
		if (fRec == null) {
            DefaultMmLogger.getInstance().log(getClass(), "totalModelWeight(int, String)", LogLevel.WARNING,
                    "Attempt to find totalModelWeight for non-existent faction " + fKey);
			return 0;
		}
		return totalModelWeight(era, fRec);
	}
	
	public int totalModelWeight(int era, FactionRecord fRec) {
		int retVal = 0;
		RATGenerator rg = RATGenerator.getInstance();
		
		for (ModelRecord mr : models) {
			AvailabilityRating ar = rg.findModelAvailabilityRecord(era,
					mr.getKey(), fRec);
			if (ar != null) {
				retVal += AvailabilityRating.calcWeight(ar.getAvailability());
			}
		}
		
		return retVal;
	}
}
