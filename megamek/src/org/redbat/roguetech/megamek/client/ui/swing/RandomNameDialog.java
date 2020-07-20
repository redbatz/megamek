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
package org.redbat.roguetech.megamek.client.ui.swing;

import org.redbat.roguetech.megamek.client.Client;
import org.redbat.roguetech.megamek.client.generator.RandomGenderGenerator;
import org.redbat.roguetech.megamek.client.generator.RandomNameGenerator;
import org.redbat.roguetech.megamek.client.ui.Messages;
import org.redbat.roguetech.megamek.common.Entity;
import org.redbat.roguetech.megamek.common.enums.Gender;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * The random names dialog allows the player to randomly assign names to pilots based on faction and gender.
 */
public class RandomNameDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = -2459992981678758743L;
    private Client client;
    private ClientGUI clientgui;
    private List<Entity> units;

    private JLabel lblFaction;
    private JLabel lblGender;
    private JComboBox<String> comboFaction;
    private JSlider sldGender;

    private JButton butOkay;
    private JButton butSave;
    private JButton butCancel;

    private JPanel panButtons;
    private JPanel panMain;

    private JComboBox<String> chPlayer;

    public RandomNameDialog(ClientGUI clientgui) {
        super(clientgui.frame, Messages.getString("RandomNameDialog.title"), true); //$NON-NLS-1$
        this.clientgui = clientgui;
        init();
    }

    private void init() {
        initComponents();

        client = clientgui.getClient();

        updateFactions();

        updatePlayerChoice();

        butOkay.addActionListener(this);
        butSave.addActionListener(this);
        butCancel.addActionListener(this);
        chPlayer.addActionListener(this);
        setLocationRelativeTo(clientgui.frame);
    }

    private void updateFactions() {
        //Fill the combobox with choices
        Set<String> factions = RandomNameGenerator.getInstance().getFactions();
        if (null == factions) {
            return;
        }
        comboFaction.removeAllItems();
        for (String faction : factions) {
            comboFaction.addItem(faction);
        }
        comboFaction.setSelectedItem(RandomNameGenerator.getInstance().getChosenFaction());

    }

    private void updatePlayerChoice() {
        String lastChoice = (String) chPlayer.getSelectedItem();
        String clientName = clientgui.getClient().getName();
        chPlayer.removeAllItems();
        chPlayer.setEnabled(true);
        chPlayer.addItem(clientName);

        for (Iterator<Client> i = clientgui.getBots().values().iterator(); i.hasNext();) {
            chPlayer.addItem(i.next().getName());
        }
        if (chPlayer.getItemCount() == 1) {
            chPlayer.setEnabled(false);
        }

        chPlayer.setSelectedItem(lastChoice);
        if (chPlayer.getSelectedIndex() < 0) {
            chPlayer.setSelectedIndex(0);
        }

        comboFaction.setSelectedItem(RandomNameGenerator.getInstance().getChosenFaction());
        sldGender.setValue(RandomGenderGenerator.getPercentFemale());
    }

    private void saveSettings() {
        RandomNameGenerator.getInstance().setChosenFaction((String) comboFaction.getSelectedItem());
        RandomGenderGenerator.setPercentFemale(sldGender.getValue());
    }

    @Override
    public void setVisible(boolean show) {
        if (show) {
            updateFactions();
            updatePlayerChoice();
        }
        super.setVisible(show);
    }

    public void showDialog(List<Entity> units) {
        this.units = units;
        setVisible(true);
    }

    public void showDialog(Entity unit) {
         Vector<Entity> units = new Vector<>();
         units.add(unit);
         showDialog(units);
    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == butOkay) {
            Client c = null;
            if (chPlayer.getSelectedIndex() > 0) {
                String name = (String) chPlayer.getSelectedItem();
                c = clientgui.getBots().get(name);
            }
            if (c == null) {
                c = client;
            }
            saveSettings();
            // go through all of the units provided for this player and assign random names
            for (Entity ent : units) {
                if (ent.getOwnerId() == c.getLocalPlayer().getId()) {
                    for (int i = 0; i < ent.getCrew().getSlotCount(); i++) {
                        Gender gender = RandomGenderGenerator.generate();
                        ent.getCrew().setGender(gender, i);
                        ent.getCrew().setName(RandomNameGenerator.getInstance().generate(gender), i);
                    }
                    c.sendUpdateEntity(ent);
                }
            }
            clientgui.chatlounge.refreshEntities();
            // need to notify about customization not updating entities in server
            setVisible(false);
        } else if (ev.getSource() == butSave) {
            saveSettings();
            setVisible(false);
        } else if (ev.getSource() == butCancel) {
            setVisible(false);
        } else if (ev.getSource() == chPlayer) {
            updatePlayerChoice();
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void initComponents() {
        panButtons = new JPanel();
        butOkay = new JButton(Messages.getString("RandomSkillDialog.Okay"));
        butSave = new JButton(Messages.getString("RandomSkillDialog.Save"));
        butCancel = new JButton(Messages.getString("Cancel"));
        panMain = new JPanel();
        lblFaction = new JLabel(Messages.getString("RandomNameDialog.lblFaction"));
        lblGender = new JLabel(Messages.getString("RandomNameDialog.lblGender"));
        comboFaction = new JComboBox<>();
        sldGender = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 50);
        sldGender.setMajorTickSpacing(25);
        sldGender.setPaintTicks(true);
        sldGender.setPaintLabels(true);
        chPlayer = new JComboBox<>();
        chPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panButtons.add(butOkay);
        panButtons.add(butSave);
        panButtons.add(butCancel);
        panButtons.add(chPlayer);

        getContentPane().add(panButtons, java.awt.BorderLayout.PAGE_END);

        panMain.setLayout(new GridBagLayout());

        GridBagConstraints c;
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panMain.add(lblFaction, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panMain.add(comboFaction, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panMain.add(lblGender, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panMain.add(sldGender, c);

        getContentPane().add(panMain, java.awt.BorderLayout.PAGE_START);

        pack();
    }
}