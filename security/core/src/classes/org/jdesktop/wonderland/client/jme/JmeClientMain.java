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
package org.jdesktop.wonderland.client.jme;

import org.jdesktop.wonderland.client.jme.login.JmeLoginUI;
import imi.loaders.repository.Repository;
import imi.scene.processors.JSceneAWTEventProcessor;
import imi.scene.processors.JSceneEventProcessor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.mtgame.AWTInputComponent;
import org.jdesktop.mtgame.CameraComponent;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.FrameRateListener;
import org.jdesktop.mtgame.ProcessorComponent;
import org.jdesktop.mtgame.WorldManager;
import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.comms.WonderlandServerInfo;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.client.input.Event;
import org.jdesktop.wonderland.client.input.EventClassFocusListener;
import org.jdesktop.wonderland.client.input.InputManager;
import org.jdesktop.wonderland.client.jme.MainFrame.ServerURLListener;
import org.jdesktop.wonderland.client.jme.input.InputManager3D;
import org.jdesktop.wonderland.client.jme.input.KeyEvent3D;
import org.jdesktop.wonderland.client.jme.input.MouseEvent3D;
import org.jdesktop.wonderland.client.login.LoginManager;

/**
 *
 */
public class JmeClientMain {
    private static final Logger logger =
            Logger.getLogger(JmeClientMain.class.getName());

    /** The frame of the Wonderland client window. */
    private static MainFrame frame;

    // properties
    private Properties props;
    
    // standard properties
    private static final String SERVER_URL_PROP = "sgs.server";
    private static final String USER_NAME_PROP   = "cellboundsviewer.username";
    
    // default values
    private static final String SERVER_URL_DEFAULT = "http://localhost:8080";
    private static final String USER_NAME_DEFAULT   = "jmetest";
   
    /**
     * The desired frame rate
     */
    private int desiredFrameRate = 30;
    
    /**
     * The width and height of our 3D window
     */
    private int width = 800;
    private int height = 600;
    
    // the current Wonderland login and session
    private JmeLoginUI login;
    private JmeClientSession curSession;

    public JmeClientMain(String[] args) {
        props = loadProperties("run-client.properties");
   
        String serverURL = props.getProperty(SERVER_URL_PROP,
                                              SERVER_URL_DEFAULT);
        String userName   = props.getProperty(USER_NAME_PROP,
                                              USER_NAME_DEFAULT);
        
        
        processArgs(args);

        WorldManager worldManager = ClientContextJME.getWorldManager();

        worldManager.getRenderManager().setDesiredFrameRate(desiredFrameRate);
        
        createUI(worldManager);

        // Register our loginUI for login requests
        login = new JmeLoginUI(frame);
        LoginManager.setLoginUI(login);

        // add a listener that will be notified when the user selects a new
        // server
        frame.addServerURLListener(new ServerURLListener() {
            public void serverURLChanged(final String serverURL) {
                // run in a new thread so we don't block the AWT thread
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            loadServer(serverURL);
                        } catch (IOException ioe) {
                            logger.log(Level.WARNING, "Error connecting to " +
                                       serverURL, ioe);
                        }
                    }
                }).start();
            }
        });

        // connect to the default server
        try {
            loadServer(serverURL);
        } catch (IOException ioe) {
            logger.log(Level.WARNING, "Error connecting to default server " +
                       serverURL, ioe);
        }
        
        // Low level Federation testing
//        ClientManager clientManager2 = new ClientManager(serverName, Integer.parseInt(serverPort), userName+"2");
        
    }

    protected void loadServer(String serverURL) throws IOException {
        // disconnect from the current session
        if (curSession != null) {
            curSession.getCellCache().detachRootEntities();
            curSession.logout();
        }

        curSession = login.doLogin(serverURL);
        if (curSession == null) {
            logger.log(Level.WARNING, "Unable to connect to session");
        } else {
            frame.setServerURL(serverURL);
        }

        ClientContext.getWonderlandSessionManager().setPrimaryServer(
                                                    curSession.getServerInfo());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (Webstart.isWebstart()) {
            Webstart.webstartSetup();
        }
        
        JmeClientMain worldTest = new JmeClientMain(args);
        
    }
    
    /**
     * Process any command line args
     */
    private void processArgs(String[] args) {
        for (int i=0; i<args.length;i++) {
            if (args[i].equals("-fps")) {
                desiredFrameRate = Integer.parseInt(args[i+1]);
                System.out.println("DesiredFrameRate: " + desiredFrameRate);
                i++;
            }
        }
    }
    
    /**
     * Create all of the Swing windows - and the 3D window
     */
    private void createUI(WorldManager wm) {
        ViewManager.initialize(width, height); // Initialize an onscreen view
        
        frame = new MainFrame(wm, width, height);
        // center the frame
        frame.setLocationRelativeTo(null);

        // show frame
        frame.setVisible(true);

        ViewManager.getViewManager().attachViewCanvas(frame.getCanvas3DPanel());



	// Initialize the input manager.
	// Note: this also creates the view manager.
	// TODO: low bug: we would like to initialize the input manager BEFORE frame.setVisible.
	// But if we create the camera before frame.setVisible the client window never appears.
	CameraComponent cameraComp = ViewManager.getViewManager().getCameraComponent();
	InputManager inputManager = ClientContext.getInputManager();
	inputManager.initialize(frame.getCanvas(), cameraComp);

	// Default Policy: Enable global key and mouse focus everywhere 
	// Note: the app base will impose its own (different) policy later
	inputManager.addKeyMouseFocus(inputManager.getGlobalFocusEntity());

	//TODO: temporary: example global key event listener for Paul */
	InputManager3D.getInputManager().addGlobalEventListener(
	    new EventClassFocusListener () {
		private final Logger logger = Logger.getLogger("My Logger");
		public Class[] eventClassesToConsume () {
		    return new Class[] { KeyEvent3D.class, MouseEvent3D.class };
		}
		public void commitEvent (Event event) {
		    // NOTE: to test, change the two logger.fine calls below to logger.warning
		    if (event instanceof KeyEvent3D) {
			if (((KeyEvent3D)event).isPressed()) {
			    logger.fine("Global listener: received key event, event = " + event );
			}
		    } else {
			logger.fine("Global listener: received mouse event, event = " + event);
		    }
		}
    	    });
    }

    /**
     * Load system properties and properties from the named file
     */
    /**
     * Returns the frame of the Wonderland client window.
     */
    public static MainFrame getFrame () {
        return frame;
    }

    private static Properties loadProperties(String fileName) {
        // start with the system properties
        Properties props = new Properties(System.getProperties());
    
        // load the given file
        if (fileName != null) {
            try {
                props.load(new FileInputStream(fileName));
            } catch (IOException ioe) {
                Logger.getLogger(JmeClientMain.class.getName()).log(Level.WARNING, "Error reading properties from " +
                           fileName, ioe);
            }
        }
        
        return props;
    }    
}
