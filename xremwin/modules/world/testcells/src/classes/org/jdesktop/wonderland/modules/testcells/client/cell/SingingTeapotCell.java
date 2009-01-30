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
package org.jdesktop.wonderland.modules.testcells.client.cell;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.sun.scenario.animation.Clip;
import com.sun.scenario.animation.Interpolators;
import com.sun.scenario.animation.Timeline;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.ProcessorCollectionComponent;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.wonderland.client.cell.*;
import org.jdesktop.wonderland.client.jme.cellrenderer.BasicRenderer;
import org.jdesktop.wonderland.client.jme.cellrenderer.CellRendererJME;
import org.jdesktop.wonderland.client.jme.input.test.MouseEvent3DLogger;
import org.jdesktop.wonderland.client.jme.input.test.SpinObjectEventListener;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.modules.testcells.client.jme.cellrenderer.ShapeRenderer;
import org.jdesktop.wonderland.modules.testcells.client.timingframework.RotationAnimationProcessor;
import org.jdesktop.wonderland.modules.testcells.client.timingframework.TranslationAnimationProcessor;
import org.jdesktop.wonderland.modules.testcells.client.timingframework.util.Mouse3DTrigger;
import org.jdesktop.wonderland.modules.testcells.client.timingframework.util.Mouse3DTriggerEvent;
import org.jdesktop.wonderland.modules.testcells.common.cell.state.SimpleShapeCellClientState.Shape;

/**
 * Test for mouse over spin
 * 
 * @deprecated
 * @author paulby
 */
public class SingingTeapotCell extends SimpleShapeCell {
    
    public SingingTeapotCell(CellID cellID, CellCache cellCache) {
        super(cellID, cellCache);
        shape = Shape.TEAPOT;
    }
    
    @Override
    protected CellRenderer createCellRenderer(RendererType rendererType) {
        CellRenderer ret = super.createCellRenderer(rendererType);

        Entity entity = ((CellRendererJME)ret).getEntity();

        Node node = ((BasicRenderer)ret).getSceneRoot();
        Vector3f currentLoc = node.getLocalTranslation();
        Vector3f dest = new Vector3f(currentLoc);
        dest.y+=0.3;



        // There is a bug in scenario so endClips do not function correctly
//        RotationAnimationProcessor rocking = new RotationAnimationProcessor(entity, node, 0, 0, new Vector3f(1f,0f,0f));
//        Clip rockClip = Clip.create(2000, 1, rocking);
//        rockClip.setEndBehavior(Clip.EndBehavior.RESET);

        RotationAnimationProcessor handleSpout = new RotationAnimationProcessor(entity, node, -10, 10, new Vector3f(0f,0f,1f));
        Clip spoutClip = Clip.create(300, 20,  handleSpout);
//        spoutClip.setInterpolator(Interpolators.getEasingInstance(0.4f, 0.4f));
        spoutClip.setEndBehavior(Clip.EndBehavior.RESET);

//        Clip endClip = Clip.create(500, 4, rocking);

//        rockClip.addEndAnimation(spoutClip);
//        spoutClip.addEndAnimation(endClip);

        Mouse3DTrigger.addTrigger(entity, spoutClip, Mouse3DTriggerEvent.PRESS);

        return ret;
    }
    

}
