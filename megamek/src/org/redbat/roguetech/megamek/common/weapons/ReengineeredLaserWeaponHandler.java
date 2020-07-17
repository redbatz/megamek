/**
 * MegaMek -
 * Copyright (C) 2013 Ben Mazur (bmazur@sev.org)
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

import org.redbat.roguetech.megamek.common.HitData;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.server.Server;

public class ReengineeredLaserWeaponHandler extends EnergyWeaponHandler {

    /**
     *
     */
    private static final long serialVersionUID = -7390162086880372388L;


    public ReengineeredLaserWeaponHandler(ToHitData toHit,
            WeaponAttackAction waa, IGame g, Server s) {
        super(toHit, waa, g, s);
        // so that reflective armor doesn't halve the damae
        generalDamageType = HitData.DAMAGE_IGNORES_DMG_REDUCTION;
    }

}
