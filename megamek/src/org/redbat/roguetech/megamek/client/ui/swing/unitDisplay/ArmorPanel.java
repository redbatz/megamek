package org.redbat.roguetech.megamek.client.ui.swing.unitDisplay;

import org.redbat.roguetech.megamek.client.ui.swing.widget.*;
import org.redbat.roguetech.megamek.common.*;

import java.awt.Rectangle;
import java.util.Enumeration;

/**
 * This panel contains the armor readout display.
 */
class ArmorPanel extends PicMap {
    /**
     *
     */
    private static final long serialVersionUID = -3612396252172441104L;
    private TankMapSet tank;
    private MechMapSet mech;
    private InfantryMapSet infantry;
    private BattleArmorMapSet battleArmor;
    private ProtomechMapSet proto;
    private VTOLMapSet vtol;
    private QuadMapSet quad;
    private TripodMechMapSet tripod;
    private GunEmplacementMapSet gunEmplacement;
    private ArmlessMechMapSet armless;
    private LargeSupportTankMapSet largeSupportTank;
    private SuperHeavyTankMapSet superHeavyTank;
    private AeroMapSet aero;
    private CapitalFighterMapSet capFighter;
    private SquadronMapSet squad;
    private JumpshipMapSet jump;
    private SpheroidMapSet sphere;
    private WarshipMapSet warship;
    private int minTopMargin;
    private int minLeftMargin;
    private int minBottomMargin;
    private int minRightMargin;
    
    private UnitDisplay unitDisplay;

    private static final int minTankTopMargin = 8;
    private static final int minTankLeftMargin = 8;
    private static final int minVTOLTopMargin = 8;
    private static final int minVTOLLeftMargin = 8;
    private static final int minMechTopMargin = 18;
    private static final int minMechLeftMargin = 7;
    private static final int minMechBottomMargin = 0;
    private static final int minMechRightMargin = 0;
    private static final int minInfTopMargin = 8;
    private static final int minInfLeftMargin = 8;
    private static final int minAeroTopMargin = 8;
    private static final int minAeroLeftMargin = 8;

    private IGame game;

    ArmorPanel(IGame g, UnitDisplay unitDisplay) {
        game = g;
        this.unitDisplay = unitDisplay;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        tank = new TankMapSet(this, unitDisplay);
        mech = new MechMapSet(this, unitDisplay);
        infantry = new InfantryMapSet(this);
        battleArmor = new BattleArmorMapSet(this);
        proto = new ProtomechMapSet(this, unitDisplay);
        vtol = new VTOLMapSet(this, unitDisplay);
        quad = new QuadMapSet(this, unitDisplay);
        tripod = new TripodMechMapSet(this, unitDisplay);
        gunEmplacement = new GunEmplacementMapSet(this);
        armless = new ArmlessMechMapSet(this, unitDisplay);
        largeSupportTank = new LargeSupportTankMapSet(this, unitDisplay);
        superHeavyTank = new SuperHeavyTankMapSet(this, unitDisplay);
        aero = new AeroMapSet(this, unitDisplay);
        capFighter = new CapitalFighterMapSet(this);
        sphere = new SpheroidMapSet(this, unitDisplay);
        jump = new JumpshipMapSet(this, unitDisplay);
        warship = new WarshipMapSet(this, unitDisplay);
        squad = new SquadronMapSet(this, game);
    }

    @Override
    public void onResize() {
        Rectangle r = getContentBounds();
        if (r == null) {
            return;
        }
        int w = Math.round(((getSize().width - r.width) / 2));
        int h = Math.round(((getSize().height - r.height) / 2));
        int dx = w < minLeftMargin ? minLeftMargin : w;
        int dy = h < minTopMargin ? minTopMargin : h;
        setContentMargins(dx, dy, minRightMargin, minBottomMargin);
    }

    /**
     * updates fields for the specified mech
     */
    public void displayMech(Entity en) {
        // Look out for a race condition.
        if (en == null) {
            return;
        }
        DisplayMapSet ams = mech;
        removeAll();
        if (en instanceof QuadMech) {
            ams = quad;
            minLeftMargin = minMechLeftMargin;
            minTopMargin = minMechTopMargin;
            minBottomMargin = minMechBottomMargin;
            minRightMargin = minMechRightMargin;
        } else if (en instanceof TripodMech) {
            ams = tripod;
            minLeftMargin = minMechLeftMargin;
            minTopMargin = minMechTopMargin;
            minBottomMargin = minMechBottomMargin;
            minRightMargin = minMechRightMargin;
        } else if (en instanceof ArmlessMech) {
            ams = armless;
            minLeftMargin = minMechLeftMargin;
            minTopMargin = minMechTopMargin;
            minBottomMargin = minMechBottomMargin;
            minRightMargin = minMechRightMargin;
        } else if (en instanceof Mech) {
            ams = mech;
            minLeftMargin = minMechLeftMargin;
            minTopMargin = minMechTopMargin;
            minBottomMargin = minMechBottomMargin;
            minRightMargin = minMechRightMargin;
        } else if (en instanceof GunEmplacement) {
            ams = gunEmplacement;
            minLeftMargin = minTankLeftMargin;
            minTopMargin = minTankTopMargin;
            minBottomMargin = minTankTopMargin;
            minRightMargin = minTankLeftMargin;
        } else if (en instanceof VTOL) {
            ams = vtol;
            minLeftMargin = minVTOLLeftMargin;
            minTopMargin = minVTOLTopMargin;
            minBottomMargin = minVTOLTopMargin;
            minRightMargin = minVTOLLeftMargin;
        } else if (en instanceof LargeSupportTank) {
            ams = largeSupportTank;
            minLeftMargin = minTankLeftMargin;
            minTopMargin = minTankTopMargin;
            minBottomMargin = minTankTopMargin;
            minRightMargin = minTankLeftMargin;
        } else if (en instanceof SuperHeavyTank) {
            ams = superHeavyTank;
            minLeftMargin = minTankLeftMargin;
            minTopMargin = minTankTopMargin;
            minBottomMargin = minTankTopMargin;
            minRightMargin = minTankLeftMargin;
        } else if (en instanceof Tank) {
            ams = tank;
            minLeftMargin = minTankLeftMargin;
            minTopMargin = minTankTopMargin;
            minBottomMargin = minTankTopMargin;
            minRightMargin = minTankLeftMargin;
        } else if (en instanceof BattleArmor) {
            ams = battleArmor;
            minLeftMargin = minInfLeftMargin;
            minTopMargin = minInfTopMargin;
            minBottomMargin = minInfTopMargin;
            minRightMargin = minInfLeftMargin;
        } else if (en instanceof Infantry) {
            ams = infantry;
            minLeftMargin = minInfLeftMargin;
            minTopMargin = minInfTopMargin;
            minBottomMargin = minInfTopMargin;
            minRightMargin = minInfLeftMargin;
        } else if (en instanceof Protomech) {
            ams = proto;
            minLeftMargin = minTankLeftMargin;
            minTopMargin = minTankTopMargin;
            minBottomMargin = minTankTopMargin;
            minRightMargin = minTankLeftMargin;
        } else if (en instanceof Warship) {
            ams = warship;
            minLeftMargin = minAeroLeftMargin;
            minTopMargin = minAeroTopMargin;
            minBottomMargin = minAeroTopMargin;
            minRightMargin = minAeroLeftMargin;
        } else if (en instanceof Jumpship) {
            ams = jump;
            minLeftMargin = minAeroLeftMargin;
            minTopMargin = minAeroTopMargin;
            minBottomMargin = minAeroTopMargin;
            minRightMargin = minAeroLeftMargin;
        } else if (en instanceof FighterSquadron) {
            ams = squad;
            minLeftMargin = minAeroLeftMargin;
            minTopMargin = minAeroTopMargin;
            minBottomMargin = minAeroTopMargin;
            minRightMargin = minAeroLeftMargin;
        } else if (en instanceof Aero) {
            ams = aero;
            if (en instanceof SmallCraft) {
                SmallCraft sc = (SmallCraft) en;
                if (sc.isSpheroid()) {
                    ams = sphere;
                }
            }
            if (en.isCapitalFighter()) {
                ams = capFighter;
            }
            minLeftMargin = minAeroLeftMargin;
            minTopMargin = minAeroTopMargin;
            minBottomMargin = minAeroTopMargin;
            minRightMargin = minAeroLeftMargin;
        }
        if (ams == null) {
            System.err.println("The armor panel is null."); //$NON-NLS-1$
            return;
        }
        ams.setEntity(en);
        addElement(ams.getContentGroup());
        Enumeration<BackGroundDrawer> iter = ams.getBackgroundDrawers()
                                                .elements();
        while (iter.hasMoreElements()) {
            addBgDrawer(iter.nextElement());
        }
        onResize();
        update();
    }
}