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
 * Sun designates this particular file as subject to the "Classpath" 
 * exception as provided by Sun in the License file that accompanied 
 * this code.
 */
package org.jdesktop.wonderland.modules.jmecolladaloader.server.cell;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.server.WonderlandContext;
import org.jdesktop.wonderland.server.cell.CellMO;
import org.jdesktop.wonderland.server.cell.CellManagerMO;
import org.jdesktop.wonderland.server.cell.MovableComponentMO;
import org.jdesktop.wonderland.server.ServerPlugin;

/**
 *
 * @author paulby
 */
public class TestWorld implements ServerPlugin {

    public TestWorld() {
    }

    public void initialize() {
        try {

            BoundingBox bounds = new BoundingBox(new Vector3f(), 1, 1, 1);

            MovableCellMO c2 = new MovableCellMO(bounds,
                    new CellTransform(null, new Vector3f(10, 5, 10)));
            c2.setName("c2");
            c2.setLocalBounds(bounds);

            MovableCellMO c3 = new MovableCellMO(
                    new BoundingSphere(2, new Vector3f()),
                    new CellTransform(null, new Vector3f(5, 5, 5)));
            c3.setName("c3");

            CellMO c4 = new MovableCellMO(
                    new BoundingSphere(0.5f, new Vector3f()),
                    new CellTransform(null, new Vector3f(1, 0, 0)));
            c4.setName("c4");

            c3.addChild(c4);
            
            float cellSize = 20;
            int xMax = 30;
            int zMax = 3;
            
//            for(int x=0; x<cellSize*xMax; x+=cellSize) {
//                for(int z=0; z<cellSize*zMax; z+=cellSize) {
//                    WonderlandContext.getCellManager().insertCellInWorld(new StaticModelCellMO(new Vector3f(x,0,z), cellSize/2f));
//                }
//            }

            WonderlandContext.getCellManager().insertCellInWorld(c2);
            WonderlandContext.getCellManager().insertCellInWorld(c3);

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(0,0,0), 15,
                            "wla://jmecolladaloader/RoomLow10x15/models/RoomLow10x15.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f})));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(15,0,0), 15,
                            "wla://jmecolladaloader/RoomLow10x15/models/RoomLow10x15.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f})));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(0,0,10), 15,
                            "wla://jmecolladaloader/OutsideFloor10x10/models/OutsideFloor10x10.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f})));
            Task t = new TestTask(c3, c2);
/*
            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(-80,0,-80), 15,
                            "wla://jmecolladaloader/TestW-184k-House/models/TestW.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));
*/
            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(-250,0,-250), 1000,
                            "wla://jmecolladaloader/BigPaving500/models/BigPaving500.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(-25,0,-25), 100,
                            "wla://jmecolladaloader/GooBldg1/models/GooBldg1.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(20,0,20), 15,
                            "wla://jmecolladaloader/Heffernan-meter11/models/Heffernan-meter11.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(45,0,20), 15,
                            "wla://jmecolladaloader/Heffernan-meter9/models/Heffernan-meter9.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(70,0,20), 15,
                            "wla://jmecolladaloader/Heffernan-meter9/models/Heffernan-meter9.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(20,0,60), 15,
                            "wla://jmecolladaloader/Heffernan-meter9/models/Heffernan-meter9.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(45,0,60), 15,
                            "wla://jmecolladaloader/Heffernan-meter9/models/Heffernan-meter9.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));

            WonderlandContext.getCellManager().insertCellInWorld(
                    new JmeColladaCellMO(new Vector3f(70,0,60), 15,
                            "wla://jmecolladaloader/Heffernan-meter9/models/Heffernan-meter9.dae",
                            null,
                            new Quaternion(new float[]{-(float)Math.PI/2, 0f, 0f}),
                            "http://172.16.126.134:8800/test/compiled_models",
                            "Heffernan",
                            "js",
                            "javascript"));


            AppContext.getTaskManager().schedulePeriodicTask(t, 5000, 1000);

        } catch (Exception ex) {
            Logger.getLogger(CellManagerMO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static class TestTask implements Task, Serializable {

        private ManagedReference<MovableCellMO> cellRef;
        private Vector3f pos;
        private Vector3f pos2;
        private int dir = 2;
        private ManagedReference<MovableCellMO> cell2Ref;

        public TestTask(MovableCellMO cell, MovableCellMO c2) {
            this.cellRef = AppContext.getDataManager().createReference(cell);
            this.cell2Ref = AppContext.getDataManager().createReference(c2);
            pos = cell.getLocalTransform(null).getTranslation(null);
            pos2 = cell.getLocalTransform(null).getTranslation(null);
        }

        public void run() throws Exception {
            pos.x += dir;
            pos2.z += dir;
            if (pos.x > 40 || pos.x < 4) {
                dir = -dir;
            }
            cellRef.get().getComponent(MovableComponentMO.class).moveRequest(null, new CellTransform(null, pos));
            cell2Ref.get().getComponent(MovableComponentMO.class).moveRequest(null, new CellTransform(null, pos2));
        }
    }
}
