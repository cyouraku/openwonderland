/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2008, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * $State$
 */

package org.jdesktop.wonderland.server.cell;

import com.sun.sgs.app.ClientSession;
import java.io.Serializable;
import org.jdesktop.wonderland.common.cell.CellClientType;
import org.jdesktop.wonderland.common.cell.messages.CellMessage;
import org.jdesktop.wonderland.common.comms.ClientType;
import org.jdesktop.wonderland.common.messages.Message;
import org.jdesktop.wonderland.server.WonderlandContext;
import org.jdesktop.wonderland.server.comms.ClientHandler;
import org.jdesktop.wonderland.server.comms.ClientSender;

/**
 * Handles CellMessages sent by the Wonderland client
 * @author jkaplan
 */
class CellClientHandler implements ClientHandler, Serializable {
    
    public ClientType getClientType() {
        return CellClientType.CELL_CLIENT_TYPE;
    }

    public void clientAttached(ClientSender sender) {
        // ignore
    }

    public void clientDetached(ClientSession session) {
        // ignore
    }
    
    public void messageReceived(ClientSender sender, Message message) {
        if (message instanceof CellMessage) {
            messageReceived(sender, (CellMessage) message);
        } else {
            sender.sendError(message.getMessageID(), 
                             "Unexpected message type: " + message.getClass());
        }
    }
  
    /**
     * When a cell message is received, dispatch it to the appropriate cell.
     * If the cell does not exist, send back an error message.
     * @param message the cell message
     * @param sender the message sender to send responses to
     */
    public void messageReceived(ClientSender sender, CellMessage message) {
        // get the MasterCellCache
        MasterCellCache mcc = WonderlandContext.getMasterCellCache();
        
        // find the appropriate cell
        CellMO cell = mcc.getCell(message.getCellID());
        
        // if there was no cell, handle the error
        if (cell == null) {
            sender.sendError(message.getMessageID(), "Unknown cell id: " + 
                             message.getCellID());
            return;
        }
        
        // dispatch the message
        cell.messageReceived(sender, message);
    }
}
