/**
 * MegaMek - Copyright (C) 2004 Ben Mazur (bmazur@sev.org)
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
 * Created on Sep 29, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons;

import org.redbat.roguetech.megamek.common.AmmoType;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.Report;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.server.Server;

import java.util.Vector;

/**
 * @author Andrew Hunter
 */
public class PrototypeISUltraWeaponHandler extends UltraWeaponHandler {

    /**
     *
     */
    private static final long serialVersionUID = 6441106275439235564L;

    /**
     * @param t
     * @param w
     * @param g
     */
    public PrototypeISUltraWeaponHandler(ToHitData t, WeaponAttackAction w,
                                         IGame g, Server s) {
        super(t, w, g, s);
    }

    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.weapons.UltraWeaponHandler#doChecks(java.util.Vector)
     */
    @Override
    protected boolean doChecks(Vector<Report> vPhaseReport) {
        if (((roll <= 4) && (howManyShots == 2))
                || ((roll == 2) && (howManyShots == 1))) {
            Report r = new Report();
            r.subject = subjectId;
            weapon.setJammed(true);
            if (wtype.getAmmoType() == AmmoType.T_AC_ULTRA) {
                r.messageId = 3160;
                weapon.setHit(true);
            } else {
                r.messageId = 3170;
            }
            vPhaseReport.addElement(r);
            return true;
        }
        return false;
    }
}
