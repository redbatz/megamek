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
 * Created on Sep 25, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons.autocannons;

import org.redbat.roguetech.megamek.common.AmmoType;

public abstract class ProtoMechACWeapon extends ACWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 5955226813134596666L;

    public ProtoMechACWeapon() {
        super();
        ammoType = AmmoType.T_PAC;
        explosive = false;
        flags = flags.or(F_PROTO_WEAPON);
    }
}