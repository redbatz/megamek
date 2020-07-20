/* MegaMek - Copyright (C) 2004,2005 Ben Mazur (bmazur@sev.org)
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
package org.redbat.roguetech.megamek.common.weapons.bayweapons;

import org.redbat.roguetech.megamek.common.EquipmentTypeLookup;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.ACBayHandler;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Jay Lawson
 */
public class ACBayWeapon extends AmmoBayWeapon {
    /**
     * 
     */
    private static final long serialVersionUID = 8756042527483383101L;

    /**
     * 
     */
    public ACBayWeapon() {
        super();
        // tech levels are a little tricky
        this.name = "AC Bay";
        this.setInternalName(EquipmentTypeLookup.AC_BAY);
        this.heat = 0;
        this.damage = DAMAGE_VARIABLE;
        this.shortRange = 6;
        this.mediumRange = 12;
        this.longRange = 20;
        this.extremeRange = 25;
        this.tonnage = 0.0;
        this.bv = 0;
        this.cost = 0;
        this.atClass = CLASS_AC;
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
        return new ACBayHandler(toHit, waa, game, server);
    }
    
    @Override
    public int getBattleForceClass() {
        return BFCLASS_AC;
    }
}