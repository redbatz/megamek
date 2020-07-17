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
package org.redbat.roguetech.megamek.common.weapons;

import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.Infantry;
import org.redbat.roguetech.megamek.common.SimpleTechLevel;
import org.redbat.roguetech.megamek.common.ToHitData;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Sebastian Brocks
 */
public class StopSwarmAttack extends InfantryAttack {

    /**
     * 
     */
    private static final long serialVersionUID = -5682796365154321224L;

    public StopSwarmAttack() {
        super();
        this.name = "Stop Swarm Attack";
        this.setInternalName(Infantry.STOP_SWARM);
        techAdvancement.setTechBase(TECH_BASE_ALL).setAdvancement(2456, 2460, 2500)
            .setStaticTechLevel(SimpleTechLevel.STANDARD)
            .setApproximate(true, false, false).setTechBase(RATING_D)
            .setPrototypeFactions(F_LC).setProductionFactions(F_LC)
            .setAvailability(RATING_D, RATING_D, RATING_D, RATING_D);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * megamek.common.weapons.Weapon#getCorrectHandler(megamek.common.ToHitData,
     * megamek.common.actions.WeaponAttackAction, megamek.common.Game)
     */
    @Override
    protected AttackHandler getCorrectHandler(ToHitData toHit,
            WeaponAttackAction waa, IGame game, Server server) {
        return new StopSwarmAttackHandler(toHit, waa, game, server);
    }
}
