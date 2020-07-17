/**
 * 
 */
package org.redbat.roguetech.megamek.server.commands;

import org.redbat.roguetech.megamek.common.Entity;
import org.redbat.roguetech.megamek.common.options.OptionsConstants;
import org.redbat.roguetech.megamek.server.Server;

/**
 * @author dirk This command exists to print entity information to the chat
 *         window, it's primarily intended for vissually impaired users.
 */

public class ShowEntityCommand extends ServerCommand {

    public ShowEntityCommand(Server server) {
        super(
                server,
                "entity",
                "print the information about a entity into the chat window. Ussage: /entity # whih would show the details for the entity numbered #.");
        // to be extended by adding /entity unit# loc# to list details on
        // locations.
    }

    /**
     * Run this command with the arguments supplied
     * 
     * @see ServerCommand#run(int, java.lang.String[])
     */
    @Override
    public void run(int connId, String[] args) {
        if (server.getGame().getOptions().booleanOption(OptionsConstants.ADVANCED_DOUBLE_BLIND)) {
            server.sendServerChat(connId,
                    "Sorry, this command is disabled during double blind.");
            return;
        }
        try {
            int id = Integer.parseInt(args[1]);
            Entity ent = server.getGame().getEntity(id);

            if (ent != null) {
                server.sendServerChat(connId, ent.statusToString());
            } else {
                server.sendServerChat(connId, "No such entity.");
            }
        } catch (NumberFormatException nfe) {
        } catch (NullPointerException npe) {
        } catch (IndexOutOfBoundsException ioobe) {
        }
    }

}
