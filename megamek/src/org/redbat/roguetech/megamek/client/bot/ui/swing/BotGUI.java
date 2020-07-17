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
package org.redbat.roguetech.megamek.client.bot.ui.swing;

import org.redbat.roguetech.megamek.client.bot.BotClient;
import org.redbat.roguetech.megamek.client.bot.Messages;
import org.redbat.roguetech.megamek.client.ui.swing.CommonHelpDialog;
import org.redbat.roguetech.megamek.client.ui.swing.ConfirmDialog;
import org.redbat.roguetech.megamek.client.ui.swing.GUIPreferences;
import org.redbat.roguetech.megamek.common.IGame;
import org.redbat.roguetech.megamek.common.event.*;

import javax.swing.*;
import java.io.File;

public class BotGUI implements GameListener {

    private BotClient bot;
    private static boolean WarningShown;

    public BotGUI(BotClient bot) {
        this.bot = bot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.GameListener#gamePhaseChange(megamek.common.GamePhaseChangeEvent)
     */
    public void gamePhaseChange(GamePhaseChangeEvent e) {
        if (bot.getGame().getPhase() == IGame.Phase.PHASE_LOUNGE
                || bot.getGame().getPhase() == IGame.Phase.PHASE_STARTING_SCENARIO) {
            notifyOfBot();
        }
    }

    public void notifyOfBot() {
        if (GUIPreferences.getInstance().getNagForBotReadme() && !WarningShown) {
            WarningShown = true;
            
            JFrame frame = new JFrame();
            String title = Messages.getString("BotGUI.notifyOfBot.title"); //$NON-NLS-1$
            String body = Messages.getString("BotGUI.notifyOfBot.message"); //$NON-NLS-1$
            frame.pack();
            frame.setLocationRelativeTo(null);
            ConfirmDialog confirm = new ConfirmDialog(frame, title, body, true);
            confirm.setVisible(true);

            if (!confirm.getShowAgain()) {
                GUIPreferences.getInstance().setNagForBotReadme(false);
            }

            if (confirm.getAnswer()) {
                File helpfile = new File("docs/Bot Stuff/ai-readme.txt"); //$NON-NLS-1$
                new CommonHelpDialog(frame, helpfile).setVisible(true);
            }
            frame.dispose();
        }
    }

    public void gamePlayerConnected(GamePlayerConnectedEvent e) {
    }

    public void gamePlayerDisconnected(GamePlayerDisconnectedEvent e) {
    }

    public void gamePlayerChange(GamePlayerChangeEvent e) {
    }

    public void gamePlayerChat(GamePlayerChatEvent e) {
    }

    public void gameTurnChange(GameTurnChangeEvent e) {
    }

    public void gameReport(GameReportEvent e) {
    }

    public void gameEnd(GameEndEvent e) {
    }

    public void gameBoardNew(GameBoardNewEvent e) {
    }

    public void gameBoardChanged(GameBoardChangeEvent e) {
    }

    public void gameSettingsChange(GameSettingsChangeEvent e) {
    }

    public void gameMapQuery(GameMapQueryEvent e) {
    }

    public void gameEntityNew(GameEntityNewEvent e) {
    }

    public void gameEntityNewOffboard(GameEntityNewOffboardEvent e) {
    }

    public void gameEntityChange(GameEntityChangeEvent e) {
    }

    public void gameNewAction(GameNewActionEvent e) {
    }

    public void gameEntityRemove(GameEntityRemoveEvent e) {
    }
    
    @Override
    public void gameClientFeedbackRequest(GameCFREvent evt) {
    }

    @Override
    public void gameVictory(GameVictoryEvent e) {       
    }

}
