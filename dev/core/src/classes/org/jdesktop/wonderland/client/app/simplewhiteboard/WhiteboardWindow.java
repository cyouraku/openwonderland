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
package org.jdesktop.wonderland.client.simplewhiteboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.app.base.App;
import org.jdesktop.wonderland.client.app.base.DrawingSurface;
import org.jdesktop.wonderland.client.app.base.WindowGraphics2D;
import org.jdesktop.wonderland.client.app.simplewhiteboard.BufferedCompoundMessageSender;
import org.jdesktop.wonderland.client.app.simplewhiteboard.WhiteboardDrawingSurface;
import org.jdesktop.wonderland.common.app.simplewhiteboard.WhiteboardAction;
import org.jdesktop.wonderland.common.app.simplewhiteboard.WhiteboardCellMessage;
import org.jdesktop.wonderland.common.app.simplewhiteboard.WhiteboardTool.Tool;
import com.jme.math.Vector2f;

/**
 *
 * The window for the whiteboard.
 *
 * @author deronj
 */

@ExperimentalAPI
public class WhiteboardWindow extends WindowGraphics2D  {

    /** The logger used by this class */
    private static final Logger logger = Logger.getLogger(WhiteboardWindow.class.getName());

    /** The image which is drawn on */
    private WhiteboardDrawingSurface wbSurface;

    /**
     * Create a new instance of WhiteboardWindow.
     *
     * @param app The whiteboard app which owns the window.
     * @param width The width of the window (in pixels).
     * @param height The height of the window (in pixels).
     * @param topLevel Whether the window is top-level (e.g. is decorated) with a frame.
     * @param pixelScale The size of the window pixels.
     * @param commComponent The communications component for communicating with the server.
     */
    public WhiteboardWindow (final App app, int width, int height, boolean topLevel, Vector2f pixelScale,
			     final WhiteboardComponent commComponent)
        throws InstantiationException
    {
	super(app, width, height, topLevel, pixelScale, new WhiteboardDrawingSurface(width, height));

	// For debug
	setTitle("WHITEBOARD WINDOW");
	
	wbSurface = (WhiteboardDrawingSurface)getSurface();

        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                dragTo(e.getPoint());
                
                // notify other clients
                WhiteboardCellMessage msg = new WhiteboardCellMessage(getClientID(), app.getCell().getCellID(),
                        WhiteboardAction.DRAG_TO,
                        e.getPoint());
                commComponent.send(msg);
            }
            
            public void mouseMoved(MouseEvent e) {
                moveTo(e.getPoint());
                
                // notify other clients
                WhiteboardCellMessage msg = new WhiteboardCellMessage(getClientID(), app.getCell().getCellID(),
                        WhiteboardAction.MOVE_TO,
                        e.getPoint());
                commComponent.send(msg);
            }
            
        });
        
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                selectPen(e.getPoint());
                
                // notify other clients
                WhiteboardCellMessage msg = null;
                
                switch (wbSurface.getActionType()) {
                    case COLOR:
                        logger.info("select color: " + wbSurface.getPenColor());
                        msg = new WhiteboardCellMessage(getClientID(), app.getCell().getCellID(),
                                WhiteboardAction.SET_COLOR,
                                wbSurface.getPenColor());
                        break;
                    case TOOL:
                        logger.info("select tool: " + wbSurface.getTool());
                        msg = new WhiteboardCellMessage(getClientID(), app.getCell().getCellID(),
                                WhiteboardAction.SET_TOOL, 
                                wbSurface.getTool());
                        break;
                    case COMMAND:
                        logger.info("execute command: " + wbSurface.getCommand());
                        msg = new WhiteboardCellMessage(getClientID(), app.getCell().getCellID(),
                                WhiteboardAction.EXECUTE_COMMAND, 
                                wbSurface.getCommand());
                        break;
                }
                if (msg != null) {
                    commComponent.send(msg);
                }
            }
            
            public void mousePressed(MouseEvent e) {
            }
            
            public void mouseReleased(MouseEvent e) {
            }
            
            public void mouseEntered(MouseEvent e) {
            }
            
            public void mouseExited(MouseEvent e) {
            }
            
        });
    }

    /**
     * Move the cursor to the specified position
     *
     * @param position the coordinate to move to
     */
    public void moveTo(Point position) {
        if (wbSurface!=null) {
            logger.finest("moveTo: " + position);
            wbSurface.penMove(position);
        }
    }
    
    /**
     * Drag the mouse to the specified position
     *
     * @param position the coordinate to drag to
     */
    public void dragTo(Point position) {
        if (wbSurface!=null) {
            logger.finest("dragTo: " + position);
            wbSurface.penDrag(position);
        }
    }
    
    /**
     * Select the pen at the specified position
     *
     * @param position the coordinate of the pen
     */
    public void selectPen(Point position) {
        if (wbSurface!=null) {
            logger.fine("selectPen: " + position);
            wbSurface.penSelect(position);
        }
    }
    
    /**
     * Set the pen color
     *
     * @param color the pen color
     */
    public void setPenColor(Color color) {
         if (wbSurface!=null) {
            logger.info("selectColor: " + color);
            wbSurface.setPenColor(color);
        }       
    }

    /**
     * Erase the whiteboard
     */
    public void erase() {
        if (wbSurface!=null) {
            logger.info("erasing whiteboard");
            wbSurface.erase();
            repaint();
        }
    }
    
    /**
     * Set the current tool
     *
     * @param tool the tool
     */
    public void setTool(Tool tool) {
        if (wbSurface!=null) {
            logger.info("selecting tool: " + tool);
            wbSurface.setTool(tool);
        }
    }
}