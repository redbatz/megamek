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

import org.redbat.roguetech.megamek.common.Compute;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.Report;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.server.Server;

import java.util.Vector;

public class InsulatedLaserWeaponHandler extends EnergyWeaponHandler {

    /**
     *
     */
    private static final long serialVersionUID = -7951442134048385366L;

    /**
     * @param toHit
     * @param waa
     * @param g
     */
    public InsulatedLaserWeaponHandler(ToHitData toHit, WeaponAttackAction waa,
                                       IGame g, Server s) {
        super(toHit, waa, g, s);
    }

    @Override
    protected boolean doChecks(Vector<Report> vPhaseReport) {
        if (roll == 2) {
            int damageRoll = Compute.d6(2);
            if (damageRoll >= 8) {
                Report r = new Report();
                r.subject = subjectId;
                r.messageId = 3172;
                vPhaseReport.addElement(r);
                weapon.setHit(true);
                return false;
            }
        }
        return false;
    }
}
