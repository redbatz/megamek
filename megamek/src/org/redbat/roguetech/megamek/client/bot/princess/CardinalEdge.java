/*
 * MegaMek - Copyright (C) 2003 Ben Mazur (bmazur@sev.org)
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

import org.redbat.roguetech.megamek.common.OffBoardDirection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Deric "Netzilla" Page (deric dot page at usa dot net
 * @version $Id$
 * @since : 8/17/13 10:37 PM
 */
public enum CardinalEdge {
    NORTH(0),
    SOUTH(1),
    WEST(2),
    EAST(3),
    // this signals special logic. In the context of a retreat edge, it's the nearest edge, so a bot unit will look for the nearest edge
    NEAREST_OR_NONE(4);  

    private int index;

    CardinalEdge(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static CardinalEdge getCardinalEdge(int index) {
        for (CardinalEdge he : values()) {
            if (he.getIndex() == index) {
                return he;
            }
        }
        return null;
    }
    
    public static CardinalEdge getCardinalEdge(OffBoardDirection direction) {
        switch (direction) {

        case NORTH:
            return NORTH;
        case SOUTH:
            return SOUTH;
        case EAST:
            return EAST;
        case WEST:
            return WEST;
        default:
            return NEAREST_OR_NONE;
        }
    }
    
    public static CardinalEdge getOppositeEdge(CardinalEdge edge) {
        switch (edge) {

        case NORTH:
            return SOUTH;
        case SOUTH:
            return NORTH;
        case EAST:
            return WEST;
        case WEST:
            return EAST;
        default:
            return NEAREST_OR_NONE;
        }
            
    }
}
