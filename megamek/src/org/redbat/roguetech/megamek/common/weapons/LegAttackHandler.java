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
/*
 * Created on Sep 23, 2004
 *
 */
package org.redbat.roguetech.megamek.common.weapons;

import org.redbat.roguetech.megamek.common.*;
import org.redbat.roguetech.megamek.common.actions.WeaponAttackAction;
import org.redbat.roguetech.megamek.common.options.OptionsConstants;
import org.redbat.roguetech.megamek.server.Server;

import java.util.Vector;

/**
 * @author Sebastian Brocks
 */
public class LegAttackHandler extends WeaponHandler {
    /**
     *
     */
    private static final long serialVersionUID = 4429993211361286138L;

    /**
     * @param toHit
     * @param waa
     * @param g
     */
    public LegAttackHandler(ToHitData toHit, WeaponAttackAction waa, IGame g,
            Server s) {
        super(toHit, waa, g, s);
    }

    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.weapons.WeaponHandler#calcHits(java.util.Vector)
     */
    @Override
    protected int calcHits(Vector<Report> vPhaseReport) {
        return 1;
    }

    @Override
    protected void handleEntityDamage(Entity entityTarget,
            Vector<Report> vPhaseReport, Building bldg, int hits, int nCluster,
            int bldgAbsorbs) {
        HitData hit = entityTarget.rollHitLocation(toHit.getHitTable(),
                toHit.getSideTable(), waa.getAimedLocation(),
                waa.getAimingMode(), toHit.getCover());
        hit.setAttackerId(getAttackerId());
        // If a leg attacks hit a leg that isn't
        // there, then hit the other leg.
        if (entityTarget.getInternal(hit) <= 0) {
            if (hit.getLocation() == Mech.LOC_RLEG) {
                hit = new HitData(Mech.LOC_LLEG);
            } else {
                hit = new HitData(Mech.LOC_RLEG);
            }
        }
        hit.setGeneralDamageType(generalDamageType);

        Report r = new Report(3405);
        r.subject = subjectId;
        r.add(toHit.getTableDesc());
        r.add(entityTarget.getLocationAbbr(hit));
        vPhaseReport.addElement(r);

        int damage = 4;
        if (ae instanceof BattleArmor) {
            damage += ((BattleArmor) ae).getVibroClaws();
            if (((BattleArmor) ae).hasMyomerBooster()) {
                damage += ((BattleArmor) ae).getTroopers() * 2;
            }
        }

        // ASSUMPTION: buildings CAN'T absorb *this* damage.
        vPhaseReport.addAll(server.damageEntity(entityTarget, hit, damage,
                false, damageType, false, false, throughFront, underWater));
        Report.addNewline(vPhaseReport);
        // Do criticals.
        int critMod = 0;
        if (entityTarget.getArmorType(hit.getLocation()) == EquipmentType.T_ARMOR_HARDENED) {
            critMod -= 2;
        }
        if (ae.hasAbility(OptionsConstants.MISC_HUMAN_TRO,Crew.HUMANTRO_MECH)) {
            critMod += 1;
        }
        vPhaseReport.addAll(server.criticalEntity(entityTarget, hit.getLocation(), hit.isRear(), critMod, damage));
    }
}