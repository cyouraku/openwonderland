/**
 * Project Wonderland
 *
 * $RCSfile: LogControl.java,v $
 *
 * Copyright (c) 2004-2007, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.3 $
 * $Date: 2007/10/23 18:27:41 $
 * $State: Exp $
 */
package org.jdesktop.wonderland.client.tools;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.*;
import org.jdesktop.wonderland.ExperimentalAPI;
import org.jdesktop.wonderland.client.avatar.LocalAvatar;
import org.jdesktop.wonderland.client.cell.CellCacheClient;
import org.jdesktop.wonderland.client.cell.CellCacheClient.CellCacheMessageListener;
import org.jdesktop.wonderland.client.comms.AttachFailureException;
import org.jdesktop.wonderland.client.comms.LoginFailureException;
import org.jdesktop.wonderland.client.comms.LoginParameters;
import org.jdesktop.wonderland.client.comms.WonderlandServerInfo;
import org.jdesktop.wonderland.client.comms.WonderlandSessionImpl;
import org.jdesktop.wonderland.common.cell.messages.AvatarCreateResponseMessage;
import org.jdesktop.wonderland.common.cell.messages.AvatarMessage;
import org.jdesktop.wonderland.common.cell.messages.CellHierarchyMessage;

/**
 * An extension of WonderlandSession that attaches all the relevant
 * clients needed for the standard 3D client.
 * @author jkaplan
 */
@ExperimentalAPI
public class BoundsTestClientSession extends WonderlandSessionImpl {
    
    /** the cell client */
    private CellCacheClient cellCacheClient;
    private AvatarClient avatarClient;
    private LocalAvatar localAvatar;
    
    public BoundsTestClientSession(WonderlandServerInfo serverInfo, 
            CellCacheMessageListener messageListener) {
        super (serverInfo);
        
        
        avatarClient = new AvatarClient();
        localAvatar = new LocalAvatar(avatarClient);
        avatarClient.addListener(localAvatar);

        cellCacheClient = new CellCacheClient();
        cellCacheClient.addListener(messageListener);
        
    }
    
    /**
     * Return the local avatar for this session
     * @return
     */
    public LocalAvatar getLocalAvatar() {
        return localAvatar;
    }
    
    /**
     * Override the login message to attach clients after the login
     * succeeds.  If a client fails to attach, the login will be aborted and
     * a LoginFailureException will be thrown
     * @param loginParameters the parameters to login with
     * @throws LoginFailureException if the login fails or any of the clients
     * fail to attach
     */
    @Override
    public void login(LoginParameters loginParams) 
            throws LoginFailureException 
    {
        // this will wait for login to succeed
        super.login(loginParams);
        
        String AVATAR_ID = "DEFAULT";
        
        // if login succeeds, attach the various clients
        try {
            avatarClient.attach(this);
            AvatarCreateResponseMessage response = (AvatarCreateResponseMessage) 
                    avatarClient.sendAndWait(AvatarMessage.newCreateMessage(AVATAR_ID));
            localAvatar.setAvatarCellID(response.getAvatarCellID());
            System.out.println("CREATED AVATAR "+response.getAvatarCellID());
            
            // Now attach to the cellCache and inform it what avatar to associate with
            cellCacheClient.attach(this);
            cellCacheClient.send(CellHierarchyMessage.newSetAvatarMessage(AVATAR_ID));
        } catch (InterruptedException ex) {
            Logger.getLogger(BoundsTestClientSession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AttachFailureException afe) {
            // a client failed to attach -- logout
            disconnect();
            
            // throw a login exception
            throw new LoginFailureException("Failed to attach client" , afe);
        }
    }
}
