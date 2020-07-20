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
 * Created on Sep 24, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons.other;

import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.FireExtinguisherHandler;
import org.redbat.roguetech.megamek.common.weapons.Weapon;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Sebastian Brocks
 */
public class ISFireExtinguisher extends Weapon {
    /**
     *
     */
    private static final long serialVersionUID = -5387366609676650747L;

    /**
     *
     */
    public ISFireExtinguisher() {
        super();
        name = "Fire Extinguisher";
        addLookupName("IS Fire Extinguisher");
        setInternalName(name);
        heat = 0;
        damage = 0;
        shortRange = 1;
        mediumRange = 1;
        longRange = 1;
        extremeRange = 1;
        tonnage = 0.0;
        criticals = 0;
        flags = flags.or(F_NO_FIRES).or(F_SOLO_ATTACK);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * megamek.common.weapons.Weapon#getCorrectHandler(megamek.common.ToHitData,
     * megamek.common.actions.WeaponAttackAction, megamek.common.IGame)
     */
    @Override
    protected AttackHandler getCorrectHandler(ToHitData toHit,
                                              WeaponAttackAction waa, IGame game, Server server) {
        return new FireExtinguisherHandler(toHit, waa, game, server);
    }
}