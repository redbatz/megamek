/*
 * MegaMek -
 * Copyright (C) 2007 Ben Mazur (bmazur@sev.org)
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
package org.redbat.roguetech.megamek.client.ui;

import org.redbat.roguetech.megamek.client.commands.ClientCommand;

import java.util.Enumeration;

/**
 * @author dirk
 */
public interface IClientCommandHandler {
    public ClientCommand getCommand(String name);

    public Enumeration<String> getAllCommandNames();

    public void registerCommand(ClientCommand command);

}
