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
package org.jdesktop.wonderland.modules.swingwhiteboard.client;

import org.jdesktop.wonderland.modules.appbase.client.AppType2D;
import org.jdesktop.wonderland.modules.appbase.common.AppLaunchMethods;
import org.jdesktop.wonderland.modules.swingwhiteboard.common.SwingWhiteboardLaunchMethods;
import org.jdesktop.wonderland.modules.swingwhiteboard.common.SwingWhiteboardTypeName;
import org.jdesktop.wonderland.common.ExperimentalAPI;

/**
 * The AppType for a swing whiteboard
 *
 * @author deronj
 */

@ExperimentalAPI
public class SwingWhiteboardAppType extends AppType2D {

    /** 
     * Return the name of the whiteboard app type.
     */
    public String getName () {
	return SwingWhiteboardTypeName.SWING_WHITEBOARD_APP_TYPE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public AppLaunchMethods getLaunchMethods () {
	return new SwingWhiteboardLaunchMethods();
    }
}