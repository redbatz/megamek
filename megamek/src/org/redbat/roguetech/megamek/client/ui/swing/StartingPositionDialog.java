/*
 * MegaMek - Copyright (C) 2002, 2003 Ben Mazur (bmazur@sev.org)
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
 * StartingPositionDialog.java
 *
 * Created on December 9, 2002, 2:43 PM
 */

package org.redbat.roguetech.megamek.client.ui.swing;

import org.redbat.roguetech.megamek.client.Client;
import org.redbat.roguetech.megamek.client.ui.Messages;
import org.redbat.roguetech.megamek.common.*;
import org.redbat.roguetech.megamek.common.options.GameOptions;
import org.redbat.roguetech.megamek.common.options.OptionsConstants;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * The starting position dialog allows the player to select a starting position.
 *
 * @author Ben
 */
public class StartingPositionDialog extends JDialog implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 7255701351824139329L;
    Client client;
    private ClientGUI clientgui;

    private JPanel panButtons = new JPanel();
    private JButton butOkay = new JButton(Messages.getString("Okay")); //$NON-NLS-1$
    private JButton butCancel = new JButton(Messages.getString("Cancel")); //$NON-NLS-1$

    private JPanel panStartButtons = new JPanel();
    private JButton[] butStartPos = new JButton[11];

    private JList<String> lisStartList = new JList<String>(new DefaultListModel<String>());

    /**
     * Creates a new instance of StartingPositionDialog
     */
    public StartingPositionDialog(ClientGUI clientgui) {
        super(clientgui.frame, Messages
                .getString("StartingPositionDialog.title"), true); //$NON-NLS-1$
        client = clientgui.getClient();
        this.clientgui = clientgui;

        lisStartList.setEnabled(false);

        setupStartGrid();
        setupButtons();

        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        getContentPane().setLayout(gridbag);

        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(4, 4, 4, 4);
        c.gridwidth = 1;
        gridbag.setConstraints(panStartButtons, c);
        getContentPane().add(panStartButtons);

        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        JScrollPane sp = new JScrollPane(lisStartList);
        gridbag.setConstraints(sp, c);
        getContentPane().add(sp);

        c.fill = GridBagConstraints.NONE;
        gridbag.setConstraints(panButtons, c);
        getContentPane().add(panButtons);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

        pack();
        setResizable(false);
        setLocation((clientgui.frame.getLocation().x
                + (clientgui.frame.getSize().width / 2)) - (getSize().width / 2),
                (clientgui.frame.getLocation().y
                        + (clientgui.frame.getSize().height / 2))
                        - (getSize().height / 2));
    }

    private void setupStartGrid() {
        for (int i = 0; i < 11; i++) {
            butStartPos[i] = new JButton(
                    IStartingPositions.START_LOCATION_NAMES[i]);
            butStartPos[i].addActionListener(this);
        }
        panStartButtons.setLayout(new GridLayout(4, 3));
        panStartButtons.add(butStartPos[1]);
        panStartButtons.add(butStartPos[2]);
        panStartButtons.add(butStartPos[3]);
        panStartButtons.add(butStartPos[8]);
        panStartButtons.add(butStartPos[10]);
        panStartButtons.add(butStartPos[4]);
        panStartButtons.add(butStartPos[7]);
        panStartButtons.add(butStartPos[6]);
        panStartButtons.add(butStartPos[5]);
        panStartButtons.add(butStartPos[0]);
        panStartButtons.add(butStartPos[9]);
    }

    private void setupButtons() {
        butOkay.addActionListener(this);
        butCancel.addActionListener(this);

        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panButtons.setLayout(gridbag);

        c.insets = new Insets(5, 5, 0, 0);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.VERTICAL;
        c.ipadx = 20;
        c.ipady = 5;
        c.gridwidth = 1;
        gridbag.setConstraints(butOkay, c);
        panButtons.add(butOkay);

        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(butCancel, c);
        panButtons.add(butCancel);
    }

    public void update() {
        ((DefaultListModel<String>) lisStartList.getModel()).removeAllElements();
        for (Enumeration<IPlayer> i = client.getPlayers(); i.hasMoreElements();) {
            IPlayer player = i.nextElement();
            if (player != null) {
                StringBuffer ssb = new StringBuffer();
                ssb.append(player.getName()).append(" : "); //$NON-NLS-1$
                ssb.append(IStartingPositions.START_LOCATION_NAMES[player
                        .getStartingPos()]);
                ((DefaultListModel<String>) lisStartList.getModel()).addElement(ssb
                        .toString());
            }
        }
    }

    public void actionPerformed(ActionEvent ev) {
        final GameOptions gOpts = client.getGame().getOptions();
        for (int i = 0; i < 11; i++) {
            if (ev.getSource().equals(butStartPos[i])) {
                if (gOpts.booleanOption(OptionsConstants.ADVANCED_DOUBLE_BLIND) //$NON-NLS-1$
                        && gOpts.booleanOption(OptionsConstants.BASE_EXCLUSIVE_DB_DEPLOYMENT)) { //$NON-NLS-1$
                    if (i == 0) {
                        clientgui.doAlertDialog(
                                Messages.getString("ChatLounge.ExclusiveDeploy.title"), //$NON-NLS-1$
                                Messages.getString("ChatLounge.ExclusiveDeploy.msg")); //$NON-NLS-1$
                        return;
                    }
                    for (Enumeration<IPlayer> e = client.getGame().getPlayers(); e
                            .hasMoreElements();) {
                        IPlayer player = e.nextElement();
                        if (player.getStartingPos() == 0) {
                            continue;
                        }
                        // CTR and EDG don't overlap
                        if (((player.getStartingPos() == 9) && (i == 10))
                            || ((player.getStartingPos() == 10) && (i == 9))) {
                            continue;
                        }

                        // check for overlapping starting directions
                        if (((player.getStartingPos() == i)
                                || ((player.getStartingPos() + 1) == i) || ((player
                                .getStartingPos() - 1) == i))
                                && (player.getId() != client.getLocalPlayer()
                                        .getId())) {
                            clientgui.doAlertDialog(
                                    Messages.getString("ChatLounge.OverlapDeploy.title"), //$NON-NLS-1$
                                    Messages.getString("ChatLounge.OverlapDeploy.msg")); //$NON-NLS-1$
                            return;
                        }
                    }
                }
                if (gOpts.booleanOption(OptionsConstants.BASE_DEEP_DEPLOYMENT)
                        && (i > 0) && (i <= 9)) {
                    i += 10;
                }
                client.getLocalPlayer().setStartingPos(i);
                client.sendPlayerInfo();
                // If the gameoption set_arty_player_homeedge is set,
                // set all the player's offboard arty units to be behind the
                // newly
                // selected home edge.
                if (gOpts.booleanOption(OptionsConstants.BASE_SET_ARTY_PLAYER_HOMEEDGE)) { //$NON-NLS-1$
                    OffBoardDirection direction = OffBoardDirection.NONE;
                    switch (i) {
                        case 0:
                            break;
                        case 1:
                        case 2:
                        case 3:
                            direction = OffBoardDirection.NORTH;
                            break;
                        case 4:
                            direction = OffBoardDirection.EAST;
                            break;
                        case 5:
                        case 6:
                        case 7:
                            direction = OffBoardDirection.SOUTH;
                            break;
                        case 8:
                            direction = OffBoardDirection.WEST;
                            break;
                        case 11:
                        case 12:
                        case 13:
                            direction = OffBoardDirection.NORTH;
                            break;
                        case 14:
                            direction = OffBoardDirection.EAST;
                            break;
                        case 15:
                        case 16:
                        case 17:
                            direction = OffBoardDirection.SOUTH;
                            break;
                        case 18:
                            direction = OffBoardDirection.WEST;
                            break;
                        default:
                    }
                    Iterator<Entity> thisPlayerArtyUnits = client.getGame()
                            .getSelectedEntities(new EntitySelector() {
                                public boolean accept(Entity entity) {
                                    if (entity.getOwnerId() == client
                                            .getLocalPlayer().getId()) {
                                        return true;
                                    }
                                    return false;
                                }
                            });
                    while (thisPlayerArtyUnits.hasNext()) {
                        Entity entity = thisPlayerArtyUnits.next();
                        if (entity.getOffBoardDirection() != OffBoardDirection.NONE) {
                            if (direction != OffBoardDirection.NONE) {
                                entity.setOffBoard(entity.getOffBoardDistance(),
                                        direction);
                            }
                        }
                    }
                }
            }
        }
        setVisible(false);
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
