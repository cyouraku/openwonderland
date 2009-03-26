/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath" 
 * exception as provided by Sun in the License file that accompanied 
 * this code.
 */
package org.jdesktop.wonderland.modules.orb.server.cell;


import java.util.logging.Logger;

import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.common.cell.ClientCapabilities;
import org.jdesktop.wonderland.common.cell.state.CellClientState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;

import org.jdesktop.wonderland.server.cell.CellMO;
import org.jdesktop.wonderland.server.cell.MovableComponentMO;

import com.jme.bounding.BoundingBox;

import com.jme.math.Vector3f;

import org.jdesktop.wonderland.server.comms.WonderlandClientID;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;

import org.jdesktop.wonderland.modules.orb.common.OrbCellClientState;
import org.jdesktop.wonderland.modules.orb.common.OrbCellServerState;

/**
 * A server cell that provides Orb functionality
 * @author jprovino
 */
public class OrbCellMO extends CellMO {

    private static final Logger logger =
            Logger.getLogger(OrbCellMO.class.getName());
    private ManagedReference<OrbMessageHandler> orbMessageHandlerRef;
    private String username;
    private String callID;
    private boolean simulateCalls;

    public OrbCellMO() {
        addComponent(new MovableComponentMO(this));
    }

    public OrbCellMO(Vector3f center, float size, String username, String callID, 
	    boolean simulateCalls) {

        super(new BoundingBox(new Vector3f(), size, size, size),
                new CellTransform(null, center));

	this.username = username;
        this.callID = callID;
        this.simulateCalls = simulateCalls;

        addComponent(new MovableComponentMO(this));

        logger.fine("Orb center " + center + " size " + size);
    }

    @Override
    protected void setLive(boolean live) {
        super.setLive(live);

        if (live == false) {
            if (orbMessageHandlerRef != null) {
                OrbMessageHandler orbMessageHandler = orbMessageHandlerRef.get();
                orbMessageHandler.done();
                AppContext.getDataManager().removeObject(orbMessageHandler);
                orbMessageHandlerRef = null;
            }

            return;
        }

        orbMessageHandlerRef = AppContext.getDataManager().createReference(
                new OrbMessageHandler(this, username, callID, simulateCalls));
    }

    @Override
    protected String getClientCellClassName(WonderlandClientID clientID,
            ClientCapabilities capabilities) {

        return "org.jdesktop.wonderland.modules.orb.client.cell.OrbCell";
    }

    @Override
    public CellClientState getClientState(CellClientState cellClientState, WonderlandClientID clientID,
            ClientCapabilities capabilities) {

        if (cellClientState == null) {
            cellClientState = new OrbCellClientState();
        }
        return super.getClientState(cellClientState, clientID, capabilities);
    }

    @Override
    public void setServerState(CellServerState cellServerState) {
        super.setServerState(cellServerState);

        OrbCellServerState orbCellServerState = (OrbCellServerState) cellServerState;
    }

    /**
     * Return a new CellServerState Java bean class that represents the current
     * state of the cell.
     *
     * @return a JavaBean representing the current state
     */
    @Override
    public CellServerState getServerState(CellServerState cellServerState) {
        /* Create a new BasicCellState and populate its members */
        if (cellServerState == null) {
            cellServerState = new OrbCellServerState();
        }
        return super.getServerState(cellServerState);
    }

    public void endCall() {
	orbMessageHandlerRef.get().done();
    }

}