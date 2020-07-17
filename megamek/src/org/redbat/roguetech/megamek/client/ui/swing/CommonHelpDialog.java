/*
 * MegaMek - Copyright (C) 2003 Ben Mazur (bmazur@sev.org)
 * MegaMek - Copyright (C) 2020 - The MegaMek Team  
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

import org.redbat.roguetech.megamek.client.ui.Messages;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;

/**
 * Every about dialog in MegaMek should have an identical look-and-feel.
 */
public class CommonHelpDialog extends JDialog {

    private static final long serialVersionUID = 5189627839475444823L;

    /**
     * Create a help dialog for the given <code>parentFrame</code> by reading
     * from the indicated <code>File</code>.
     */
    public CommonHelpDialog(JFrame parentFrame, File helpfile) {
        super(parentFrame);

        setLayout(new BorderLayout());
        JEditorPane helpPane = new JEditorPane();
        helpPane.setEditable(false);

        // Get the help content file if possible
        try {
            helpPane.setPage(helpfile.toURI().toURL());
            setTitle(Messages.getString("CommonHelpDialog.helpFile") + helpfile.getName()); //$NON-NLS-1$
        } catch (Exception exc) {
            helpPane.setText(Messages.getString("CommonHelpDialog.errorReading") //$NON-NLS-1$
                    + exc.getMessage());
            setTitle(Messages.getString("CommonHelpDialog.noHelp.title")); //$NON-NLS-1$
            exc.printStackTrace();
        }

        // Close Button
        JButton butClose = new ButtonEsc(new CloseAction(this));

        // Add all to the dialog
        getContentPane().add(new JScrollPane(helpPane), BorderLayout.CENTER);
        getContentPane().add(butClose, BorderLayout.SOUTH);
        
        // Make the window half the screensize and center on screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Dimension windowSize = new Dimension(gd.getDisplayMode().getWidth() / 2,
                gd.getDisplayMode().getHeight() / 2);
        pack();
        setSize(windowSize);
        setLocationRelativeTo(null);
    }
}