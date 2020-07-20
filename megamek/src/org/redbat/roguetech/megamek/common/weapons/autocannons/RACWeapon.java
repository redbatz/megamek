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
 * Created on Oct 19, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons.autocannons;

import org.redbat.roguetech.megamek.common.*;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.weapons.AttackHandler;
import org.redbat.roguetech.megamek.common.weapons.RACHandler;
import org.redbat.roguetech.megamek.common.weapons.UltraWeaponHandler;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author Andrew Hunter TODO: is this the right hierarchy location?
 */
public abstract class RACWeapon extends UACWeapon {

    private static final long serialVersionUID = 659000035767322660L;

    /**
     *
     */
    public RACWeapon() {
        super();
        ammoType = AmmoType.T_AC_ROTARY;
        String[] modeStrings = {MODE_AC_SINGLE, MODE_RAC_TWO_SHOT, MODE_RAC_THREE_SHOT,
                MODE_RAC_FOUR_SHOT, MODE_RAC_FIVE_SHOT, MODE_RAC_SIX_SHOT};
        setModes(modeStrings);
        // explosive when jammed
        explosive = true;
        explosionDamage = damage;
        atClass = CLASS_AC;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * megamek.common.weapons.Weapon#getCorrectHandler(megamek.common.ToHitData,
     * megamek.common.actions.WeaponAttackAction, megamek.common.Game,
     * megamek.server.Server)
     */
    @Override
    protected AttackHandler getCorrectHandler(ToHitData toHit,
                                              WeaponAttackAction waa, IGame game, Server server) {
        Mounted weapon = game.getEntity(waa.getEntityId()).getEquipment(
                waa.getWeaponId());
        if (weapon.curMode().equals(MODE_RAC_SIX_SHOT)
                || weapon.curMode().equals(MODE_RAC_FIVE_SHOT)
                || weapon.curMode().equals(MODE_RAC_FOUR_SHOT)
                || weapon.curMode().equals(MODE_RAC_THREE_SHOT)) {
            return new RACHandler(toHit, waa, game, server);
        } else {
            return new UltraWeaponHandler(toHit, waa, game, server);
        }
    }

    @Override
    public double getBattleForceDamage(int range) {
        double damage = 0;
        if (range <= getLongRange()) {
            damage = getRackSize() * 6;
            if (range == BattleForceElement.SHORT_RANGE && getMinimumRange() > 0) {
                damage = adjustBattleForceDamageForMinRange(damage);
            }
        }
        return damage / 10.0;
    }
}