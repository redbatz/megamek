package org.redbat.roguetech.megamek.common.verifier;

import org.junit.Test;
import org.redbat.roguetech.megamek.common.MiscType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TestSupportVehicleTest {

    @Test
    public void testChassisModLookup() {
        for (TestSupportVehicle.ChassisModification mod : TestSupportVehicle.ChassisModification.values()) {
            assertNotNull(mod.equipment);
            assertTrue(mod.equipment.hasFlag(MiscType.F_SUPPORT_TANK_EQUIPMENT));
            assertTrue(mod.equipment.hasFlag(MiscType.F_CHASSIS_MODIFICATION));
        }
    }

}