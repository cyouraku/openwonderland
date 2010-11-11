/**
 * Project Looking Glass
 * 
 * $RCSfile: OrbMuteCallMessage.java,v $
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
 * $Revision: 1.9 $
 * $Date: 2008/06/12 18:48:16 $
 * $State: Exp $ 
 */
package org.jdesktop.wonderland.modules.orb.common.messages;

import org.jdesktop.wonderland.common.cell.CellID;

import org.jdesktop.wonderland.common.cell.messages.CellMessage;

/**
 *
 * @author jprovino
 */
public class OrbMuteCallMessage extends CellMessage {   
    
    private boolean isMuted;

    public OrbMuteCallMessage(CellID cellID, boolean isMuted) {
	super(cellID);

	this.isMuted = isMuted;
    }
    
    public boolean isMuted() {
	return isMuted;
    }

}