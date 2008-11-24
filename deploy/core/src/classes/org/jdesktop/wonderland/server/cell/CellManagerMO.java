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

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.NameNotBoundException;
import com.sun.sgs.app.util.ScalableHashSet;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.common.ExperimentalAPI;
import org.jdesktop.wonderland.common.InternalAPI;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.MultipleParentException;
import org.jdesktop.wonderland.server.WonderlandContext;
import org.jdesktop.wonderland.server.comms.CommsManager;
import org.jdesktop.wonderland.server.spatial.UniverseManager;
import org.jdesktop.wonderland.wfs.loader.CellLoader;

/**
 *
 * @author paulby
 */
@ExperimentalAPI
public class CellManagerMO implements ManagedObject, Serializable {

    // Used to generate unique cell ids
    private long cellCounter=CellID.getFirstCellID();
    
    private static final String BINDING_NAME=CellManagerMO.class.getName();
    private static final Logger logger = Logger.getLogger(CellManagerMO.class.getName());

    private ManagedReference<ScalableHashSet<CellID>> rootCellsRef = null;

    /**
     * Creates a new instance of CellManagerMO
     */
    CellManagerMO() {
        AppContext.getDataManager().setBinding(BINDING_NAME, this);
    }
    
    /**
     * Initialize the master cell cache. This is an implementation detail and
     * should not be called by users of this class.
     */
    @InternalAPI
    public static void initialize() {
        logger.info("Initializing");

        CellManagerMO manager = new CellManagerMO();

        // load all existing root cells.  Be sure to only do this in the
        // initial (untimed) transaction
        manager.reloadCells();

        // register the cell channel message listener
        CommsManager cm = WonderlandContext.getCommsManager();
        cm.registerClientHandler(new CellChannelConnectionHandler());
        
        // Register the cell cache message handler
        cm.registerClientHandler(new CellCacheConnectionHandler());
        
        // Register the cell hierarchy edit message handler
        cm.registerClientHandler(new CellEditConnectionHandler());
    }
    
    /**
     * Return singleton master cell cache
     * @return the master cell cache
     */
    public static CellManagerMO getCellManager() {
        return (CellManagerMO) AppContext.getDataManager().getBinding(BINDING_NAME);                
    }
    
    /**
     * Return the cell with the given ID, or null if the id is invalid
     * 
     * @param cellID the cell ID to getTranslation
     * @return the cell with the given ID
     */
    public static CellMO getCell(CellID cellID) {
        try {
            return (CellMO) AppContext.getDataManager().getBinding("CELL_"+cellID.toString()); 
        } catch(NameNotBoundException e) {
            return null;
        }
    }
    
    /**
     * Insert the cell into the world. 
     */
    public void insertCellInWorld(CellMO cell) throws MultipleParentException {
        doInsert(cell);
        rootCellsRef.getForUpdate().add(cell.cellID);
    }

    /**
     * Internal insert that doesn't update the root cells map
     * @param cell the cell to insert
     * @throws MultipleParentException if there is an error inserting
     */
    protected void doInsert(CellMO cell) throws MultipleParentException {
        cell.setLive(true);
        UniverseManager.getUniverseManager().addRootToUniverse(cell);
    }

    public void removeCellFromWorld(CellMO cell) {
        UniverseManager.getUniverseManager().removeRootFromUniverse(cell);
        cell.setLive(false);
        rootCellsRef.getForUpdate().remove(cell.getCellID());
    }

    /**
     * Reload all root cells into the universe, based on the set of root
     * cells we can find. Be sure to only do this once, during the initial
     * (untimed) Darkstar transaction
     */
    protected void reloadCells() {
        ScalableHashSet<CellID> rootCells;

        // create the root cells set if it doesn't exist
        if (rootCellsRef == null) {
            rootCells = new ScalableHashSet();
            rootCellsRef = AppContext.getDataManager().createReference(rootCells);
        } else {
            rootCells = rootCellsRef.get();
        }

        int addedCount = 0;
        int errorCount = 0;

        for (CellID rootCellID : rootCells) {
            CellMO cell = getCell(rootCellID);
            if (cell == null) {
                logger.warning("Removing non-existant cell " + rootCellID);
                AppContext.getDataManager().markForUpdate(rootCells);
                rootCells.remove(cell);
                errorCount++;
                continue;
            }

            try {
                doInsert(cell);
                addedCount++;
            } catch (MultipleParentException mpe) {
                logger.log(Level.WARNING, "Error re-inserting cell " +
                           rootCellID, mpe);
                errorCount++;
            }
        }

        logger.info("Added " + addedCount + " cells. " +
                    errorCount + " errors.");
    }


    /**
     * For testing.....
     */
    public void loadWorld() {
//        buildWFSWorld();
//
//        test();
    }
    
    /**
     * Builds a world defined by a wonderland file system (e.g. on disk). The
     * world's root directory must be setTranslation in the system property 
     * wonderland.wfs.root
     */
    private void buildWFSWorld() {
        new CellLoader().load();
    }
    
    /**
     * Returns a unique cell id and registers the cell with the system
     * @return
     */
    CellID createCellID(CellMO cell) {
        CellID cellID = new CellID(cellCounter++);
        
        AppContext.getDataManager().setBinding("CELL_"+cellID.toString(), cell);
        
        return cellID;
    }
    
}
