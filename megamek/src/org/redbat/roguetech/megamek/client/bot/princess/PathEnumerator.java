/*
 * MegaMek - Copyright (C) 2000-2011 Ben Mazur (bmazur@sev.org)
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
package org.redbat.roguetech.megamek.client.bot.princess;

import org.redbat.roguetech.megamek.client.bot.princess.BotGeometry.ConvexBoardArea;
import org.redbat.roguetech.megamek.client.bot.princess.BotGeometry.CoordFacingCombo;
import org.redbat.roguetech.megamek.common.*;
import org.redbat.roguetech.megamek.common.logging.LogLevel;
import org.redbat.roguetech.megamek.common.pathfinder.AbstractPathFinder.Filter;
import org.redbat.roguetech.megamek.common.pathfinder.*;
import org.redbat.roguetech.megamek.common.pathfinder.AeroGroundPathFinder.AeroGroundOffBoardFilter;
import org.redbat.roguetech.megamek.common.util.BoardUtilities;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class contains logic that calculates and stores 
 * a) possible paths that units in play can take, and
 * b) their possible locations
 *
 */
public class PathEnumerator {

    private final Princess owner;
    private final IGame game;
    private final Map<Integer, List<MovePath>> unitPaths = new ConcurrentHashMap<>();
    private final Map<Integer, List<BulldozerMovePath>> longRangePaths = new ConcurrentHashMap<>();
    private final Map<Integer, ConvexBoardArea> unitMovableAreas = new ConcurrentHashMap<>();
    private final Map<Integer, Set<CoordFacingCombo>> unitPotentialLocations = new ConcurrentHashMap<>();
    private final Map<Integer, CoordFacingCombo> lastKnownLocations = new ConcurrentHashMap<>();

    private AtomicBoolean mapHasBridges = null;
    private final Object BRIDGE_LOCK = new Object();

    public PathEnumerator(Princess owningPrincess, IGame game) {
        owner = owningPrincess;
        this.game = game;
    }

    private Princess getOwner() {
        return owner;
    }

    void clear() {
        final String METHOD_NAME = "clear()";
        getOwner().methodBegin(getClass(), METHOD_NAME);
        try {
            getUnitPaths().clear();
            getUnitPotentialLocations().clear();
            getLastKnownLocations().clear();
            getLongRangePaths().clear();
        } finally {
            getOwner().methodEnd(getClass(), METHOD_NAME);
        }
    }

    Coords getLastKnownCoords(Integer entityId) {
        final String METHOD_NAME = "getLastKnownCoords(Integer)";
        getOwner().methodBegin(getClass(), METHOD_NAME);
        try {
            CoordFacingCombo ccr = getLastKnownLocations().get(entityId);
            if (ccr == null) {
                return null;
            }
            return ccr.getCoords();
        } finally {
            getOwner().methodEnd(getClass(), METHOD_NAME);
        }
    }

    /**
     * Returns all {@link Entity} objects located at the given {@link Coords}.
     *
     * @param location   The {@link Coords} to be searched for units.
     * @param groundOnly Set TRUE to ignore {@link Aero} units.
     * @return A {@link Set} of {@link Entity} objects at the given {@link Coords}.
     */
    public Set<Integer> getEntitiesWithLocation(Coords location, boolean groundOnly) {
        final String METHOD_NAME = "getEntitiesWithLocation(Coords, boolean)";
        getOwner().methodBegin(getClass(), METHOD_NAME);
        try {
            Set<Integer> returnSet = new TreeSet<>();
            if (location == null) {
                return returnSet;
            }
            for (Integer id : getUnitPotentialLocations().keySet()) {
                if (groundOnly
                        && getGame().getEntity(id) != null
                        && getGame().getEntity(id).isAero()) {
                    continue;
                }

                for (int facing = 0; facing < 5; facing++) {
                    if (getUnitPotentialLocations().get(id).contains(CoordFacingCombo.createCoordFacingCombo
                            (location, facing))) {
                        returnSet.add(id);
                        break;
                    }
                }
            }
            return returnSet;
        } finally {
            getOwner().methodEnd(getClass(), METHOD_NAME);
        }
    }

    /**
     * From a list of potential moves, make a potential ending location chart
     */
    void updateUnitLocations(Entity entity, List<MovePath> paths) {
        final String METHOD_NAME = "updateUnitLocations(Entity, ArrayList<MovePath>)";
        getOwner().methodBegin(getClass(), METHOD_NAME);
        try {
            // clear previous locations for this entity
            getUnitPotentialLocations().remove(entity.getId());
            //
            Set<CoordFacingCombo> toAdd = new HashSet<>();
            for (MovePath path : paths) {
                toAdd.add(CoordFacingCombo.createCoordFacingCombo(path));
            }
            getUnitPotentialLocations().put(entity.getId(), toAdd);
        } finally {
            getOwner().methodEnd(getClass(), METHOD_NAME);
        }
    }

    /**
     * calculates all moves for a given unit, keeping the shortest path to each hex/facing pair
     */
    public void recalculateMovesFor(final Entity mover) {
        final String METHOD_NAME = "recalculateMovesFor(IGame, Entity)";
        getOwner().methodBegin(getClass(), METHOD_NAME);
        try {

            // Record it's current position.
            getLastKnownLocations().put(
                    mover.getId(),
                    CoordFacingCombo.createCoordFacingCombo(
                            mover.getPosition(), mover.getFacing()));

            // Clear out any already calculated paths.
            getUnitPaths().remove(mover.getId());
            getLongRangePaths().remove(mover.getId());

            // Start constructing the new list of paths.
            List<MovePath> paths = new ArrayList<>();
            
            // Aero movement on atmospheric ground maps
            // currently only applies to a) conventional aircraft, b) aerotech units, c) lams in air mode
            if(mover.isAirborneAeroOnGroundMap() && !((IAero) mover).isSpheroid()) {
                AeroGroundPathFinder apf = AeroGroundPathFinder.getInstance(getGame());
                MovePath startPath = new MovePath(getGame(), mover);
                apf.run(startPath);
                paths.addAll(apf.getAllComputedPathsUncategorized());
                
                // Remove illegal paths.
                Filter<MovePath> filter = new Filter<MovePath>() {
                    @Override
                    public boolean shouldStay(MovePath movePath) {
                        return isLegalAeroMove(movePath);
                    }
                };
                
                this.owner.log(this.getClass(), METHOD_NAME, LogLevel.DEBUG, "Unfiltered paths: " + paths.size());
                paths = new ArrayList<>(filter.doFilter(paths));
                this.owner.log(this.getClass(), METHOD_NAME, LogLevel.DEBUG, "Filtered out illegal paths: " + paths.size());
                AeroGroundOffBoardFilter offBoardFilter = new AeroGroundOffBoardFilter();
                paths = new ArrayList<>(offBoardFilter.doFilter(paths));
                
                MovePath offBoardPath = offBoardFilter.getShortestPath();
                if(offBoardPath != null) {
                    paths.add(offBoardFilter.getShortestPath());
                }
                
                this.owner.log(this.getClass(), METHOD_NAME, LogLevel.DEBUG, "Filtered out offboard paths: " + paths.size());
                
                // This is code useful for debugging, but puts out a lot of log entries, which slows things down. 
                HashMap<Integer, Integer> pathLengths = new HashMap<Integer, Integer>();
                for(MovePath path : paths) {
                    if(!pathLengths.containsKey(path.length())) {
                        pathLengths.put(path.length(), 0);
                    }
                    Integer lengthCount = pathLengths.get(path.length());
                    pathLengths.put(path.length(), lengthCount + 1);
                    
                    this.owner.log(this.getClass(), "Path ", LogLevel.DEBUG, path.toString());
                }
                
                for(Integer length : pathLengths.keySet()) {
                    this.owner.log(this.getClass(), METHOD_NAME, LogLevel.DEBUG, "Paths of length " + length + ": " + pathLengths.get(length));
                }
            // this handles the case of the mover being an aerospace unit and "advances space flight" rules being on
            } else if(mover.isAero() && game.useVectorMove()) {
                NewtonianAerospacePathFinder npf = NewtonianAerospacePathFinder.getInstance(getGame());
                npf.run(new MovePath(game, mover));
                paths.addAll(npf.getAllComputedPathsUncategorized());
            // this handles the case of the mover being an aerospace unit on a space map
            } else if(mover.isAero() && game.getBoard().inSpace()) {
                AeroSpacePathFinder apf = AeroSpacePathFinder.getInstance(getGame());
                apf.run(new MovePath(game, mover));
                paths.addAll(apf.getAllComputedPathsUncategorized());
            // this handles the case of the mover being a winged aerospace unit on a low-atmo map
            } else if(mover.isAero() && game.getBoard().inAtmosphere() && !Compute.useSpheroidAtmosphere(game, mover)) {
                AeroLowAltitudePathFinder apf = AeroLowAltitudePathFinder.getInstance(getGame());
                apf.run(new MovePath(game, mover));
                paths.addAll(apf.getAllComputedPathsUncategorized());
            // this handles the case of the mover acting like a spheroid aerospace unit in an atmosphere
            } else if(Compute.useSpheroidAtmosphere(game, mover)) {
                SpheroidPathFinder spf = SpheroidPathFinder.getInstance(game);
                spf.run(new MovePath(game, mover));
                paths.addAll(spf.getAllComputedPathsUncategorized());
            // this handles the case of the mover being an infantry unit of some kind, that's not airborne.
            } else if (mover.hasETypeFlag(Entity.ETYPE_INFANTRY) && !mover.isAirborne()) {
                InfantryPathFinder ipf = InfantryPathFinder.getInstance(getGame());
                ipf.run(new MovePath(game, mover));
                paths.addAll(ipf.getAllComputedPathsUncategorized());
                
                // generate long-range paths appropriate to the bot's current state
                updateLongRangePaths(mover);
            // this handles situations where a unit is high up in the air, but is not an aircraft
            // such as an ejected pilot or a unit hot dropping from a dropship, as these cannot move
            } else if (!mover.isAero() && mover.isAirborne()) {
                paths.add(new MovePath(game, mover));
            } else { // Non-Aero movement
                // TODO: Will this cause Princess to never use MASC?
                LongestPathFinder lpf = LongestPathFinder
                        .newInstanceOfLongestPath(mover.getRunMPwithoutMASC(),
                                MovePath.MoveStepType.FORWARDS, getGame());
                lpf.run(new MovePath(game, mover));
                paths.addAll(lpf.getLongestComputedPaths());

                //add walking moves
                lpf = LongestPathFinder.newInstanceOfLongestPath(
                        mover.getWalkMP(), MovePath.MoveStepType.BACKWARDS, getGame());
                lpf.run(new MovePath(getGame(), mover));
                paths.addAll(lpf.getLongestComputedPaths());

                //add jumping moves
                if (mover.getJumpMP() > 0) {
                    ShortestPathFinder spf = ShortestPathFinder
                            .newInstanceOfOneToAll(mover.getJumpMP(),
                                    MovePath.MoveStepType.FORWARDS, getGame());
                    spf.run((new MovePath(game, mover))
                            .addStep(MovePath.MoveStepType.START_JUMP));
                    paths.addAll(spf.getAllComputedPathsUncategorized());
                }

                for(MovePath path : paths) {
                    this.owner.log(this.getClass(), "Path ", LogLevel.DEBUG, path.toString());
                }
                
                // Try climbing over obstacles and onto bridges
                adjustPathsForBridges(paths);

                //filter those paths that end in illegal state
                Filter<MovePath> filter = new Filter<MovePath>() {
                    @Override
                    public boolean shouldStay(MovePath movePath) {
                        boolean isLegal = movePath.isMoveLegal();
                        return isLegal
                                && (Compute.stackingViolation(getGame(),
                                        mover.getId(),
                                        movePath.getFinalCoords()) == null);
                    }
                };
                paths = new ArrayList<>(filter.doFilter(paths));
                
                // generate long-range paths appropriate to the bot's current state
                updateLongRangePaths(mover);
            }

            // Update our locations and add the computed paths.
            updateUnitLocations(mover, paths);
            getUnitPaths().put(mover.getId(), paths);

            // calculate bounding area for move
            ConvexBoardArea myArea = new ConvexBoardArea(owner);
            myArea.addCoordFacingCombos(getUnitPotentialLocations().get(
                    mover.getId()).iterator());
            getUnitMovableAreas().put(mover.getId(), myArea);
        } finally {
            getOwner().methodEnd(getClass(), METHOD_NAME);
        }
    }
    
    /**
     * Worker function that updates the long-range path collection for a particular entity
     */
    private void updateLongRangePaths(final Entity mover) {
        // don't bother doing this if the entity can't move anyway
        // or if it's not one of mine
        // or if I've already moved it
        if((mover.getWalkMP() == 0) ||
                ((getOwner().getLocalPlayer() != null) && (mover.getOwnerId() != getOwner().getLocalPlayer().getId())) || 
                !mover.isSelectableThisTurn()) {
            return;
        }
        
        DestructionAwareDestinationPathfinder dpf = new DestructionAwareDestinationPathfinder();
        
        // where are we going?
        Set<Coords> destinations = new HashSet<Coords>();
        // if we're going to an edge or can't see anyone, generate long-range paths to the opposite edge
        switch(getOwner().getUnitBehaviorTracker().getBehaviorType(mover, getOwner())) {
            case ForcedWithdrawal:
            case MoveToDestination:
                destinations = getOwner().getClusterTracker().getDestinationCoords(mover, getOwner().getHomeEdge(mover), true);
                break;
            case MoveToContact:
                CardinalEdge oppositeEdge = CardinalEdge.getOppositeEdge(BoardUtilities.determineOppositeEdge(mover));
                destinations = getOwner().getClusterTracker().getDestinationCoords(mover, oppositeEdge, true);
                break;
            default:
                for(Targetable target : FireControl.getAllTargetableEnemyEntities(getOwner().getLocalPlayer(), getGame(), getOwner().getFireControlState())) {
                    // don't consider crippled units as valid long-range pathfinding targets 
                    if((target.getTargetType() == Targetable.TYPE_ENTITY) && ((Entity) target).isCrippled()) {
                        continue;
                    }
                    
                    destinations.add(target.getPosition());
                    // we can easily shoot at an entity from right next to it as well
                    destinations.addAll(target.getPosition().allAdjacent());
                }
                break;
        }
        
        if(!getLongRangePaths().containsKey(mover.getId())) {
            getLongRangePaths().put(mover.getId(), new ArrayList<>());
        }
        
        // calculate a ground-bound long range path
        BulldozerMovePath bmp = dpf.findPathToCoords(mover, destinations, owner.getClusterTracker());
        
        if(bmp != null) {
            getLongRangePaths().get(mover.getId()).add(bmp);
        }
        
        // calculate a jumping long range path
        BulldozerMovePath jmp = dpf.findPathToCoords(mover, destinations, owner.getClusterTracker()); 
        if(jmp != null) {
            getLongRangePaths().get(mover.getId()).add(jmp);
        }
    }
    
    private void adjustPathsForBridges(List<MovePath> paths) {
        if (!worryAboutBridges()) {
            return;
        }

        for (MovePath path : paths) {
            adjustPathForBridge(path);
        }
    }

    private void adjustPathForBridge(MovePath path) {
        boolean needsAdjust = false;
        for (Coords c : path.getCoordsSet()) {
            IHex hex = getGame().getBoard().getHex(c);
            if ((hex != null) && hex.containsTerrain(Terrains.BRIDGE)) {
                if (getGame().getBoard().getBuildingAt(c).getCurrentCF(c) >=
                    path.getEntity().getWeight()) {
                    needsAdjust = true;
                    break;
                } else {
                    needsAdjust = false;
                    break;
                }
            }
        }
        if (!needsAdjust) {
            return;
        }
        MovePath adjusted = new MovePath(getGame(), path.getEntity());
        adjusted.addStep(MovePath.MoveStepType.CLIMB_MODE_ON);
        adjusted.addSteps(path.getStepVector(), true);
        adjusted.addStep(MovePath.MoveStepType.CLIMB_MODE_OFF);
        path.replaceSteps(adjusted.getStepVector());
    }

//    public void debugPrintContents() {
//        final String METHOD_NAME = "debugPrintContents()";
//        getOwner().methodBegin(getClass(), METHOD_NAME);
//        try {
//            for (Integer id : getUnitPaths().keySet()) {
//                Entity entity = getGame().getEntity(id);
//                List<MovePath> paths = getUnitPaths().get(id);
//                int pathsSize = paths.size();
//                String msg = "Unit " + entity.getDisplayName() + " has " + pathsSize + " paths and " +
//                             getUnitPotentialLocations().get(id).size() + " ending locations.";
//                getOwner().log(getClass(), METHOD_NAME, msg);
//            }
//        } finally {
//            getOwner().methodEnd(getClass(), METHOD_NAME);
//        }
//    }

    /**
     * Returns whether a {@link MovePath} is legit for an {@link Aero} unit isMoveLegal() seems  to disagree with me
     * on some aero moves, but I can't exactly figure out why, and who is right. So, I'm just going to put a list of
     * exceptions here instead of possibly screwing up {@link MovePath#isMoveLegal()} for everyone.  I think it has
     * to do with flyoff or return at the end of a move.  This also affects cliptopossible
     *
     * @param path The path to be examined.
     * @return TRUE if the path is legal.
     */
    public boolean isLegalAeroMove(MovePath path) {
        final String METHOD_NAME = "isLegalAeroMove(MovePath)";
        getOwner().methodBegin(getClass(), METHOD_NAME);
        try {
            // no non-aeros allowed
            if (!path.getEntity().isAero()) {
                return true;
            }

            if (!path.isMoveLegal()) {
                if (path.getLastStep() == null) {
                	LogAeroMoveLegalityEvaluation("illegal move with null last step", path);
                    return false;
                }
                if ((path.getLastStep().getType() != MovePath.MoveStepType.RETURN) &&
                    (path.getLastStep().getType() != MovePath.MoveStepType.OFF)) {
                	LogAeroMoveLegalityEvaluation("illegal move without return/off at the end", path);
                    return false;
                }
            }

            // we have to have used all velocity by the last step
            if ((path.getLastStep() != null) && (path.getLastStep().getVelocityLeft() != 0)) {
                if ((path.getLastStep().getType() != MovePath.MoveStepType.RETURN) &&
                    (path.getLastStep().getType() != MovePath.MoveStepType.OFF)) {
                	LogAeroMoveLegalityEvaluation("not all velocity used without return/off at the end", path);
                    return false;
                }
            }
            return true;
        } finally {
            getOwner().methodEnd(getClass(), METHOD_NAME);
        }
    }
    
    private void LogAeroMoveLegalityEvaluation(String whyNot, MovePath path) {
    	this.getOwner().log(this.getClass(), "isLegalAeroMove", LogLevel.DEBUG, 
    			path.length() + ":" + 
    			path.toString() + ":" + whyNot);
    }

    protected Map<Integer, List<BulldozerMovePath>> getLongRangePaths() {
        return longRangePaths;
    }
    
    protected Map<Integer, List<MovePath>> getUnitPaths() {
        return unitPaths;
    }

    public Map<Integer, ConvexBoardArea> getUnitMovableAreas() {
        return unitMovableAreas;
    }

    protected Map<Integer, Set<CoordFacingCombo>> getUnitPotentialLocations() {
        return unitPotentialLocations;
    }

    protected Map<Integer, CoordFacingCombo> getLastKnownLocations() {
        return lastKnownLocations;
    }

    protected IGame getGame() {
        return game;
    }

    private boolean worryAboutBridges() {
        if (mapHasBridges != null) {
            return mapHasBridges.get();
        }

        synchronized (BRIDGE_LOCK) {
            if (mapHasBridges != null) {
                return mapHasBridges.get();
            }

            mapHasBridges = new AtomicBoolean(getGame().getBoard()
                                                       .containsBridges());
        }

        return mapHasBridges.get();
    }
}
