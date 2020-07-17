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

import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.server.Server;
import org.redbat.roguetech.megamek.server.Server.DamageType;

/**
 * @author Sebastian Brocks
 */
public class SRMAXHandler extends SRMHandler {

    /**
     *
     */
    private static final long serialVersionUID = 8049199984294733124L;

    /**
     * @param t
     * @param w
     * @param g
     * @param s
     */
    public SRMAXHandler(ToHitData t, WeaponAttackAction w, IGame g, Server s) {
        super(t, w, g, s);
        sSalvoType = " acid-head missile(s) ";
        nSalvoBonus = -2;
        damageType = DamageType.ACID;
    }
}
