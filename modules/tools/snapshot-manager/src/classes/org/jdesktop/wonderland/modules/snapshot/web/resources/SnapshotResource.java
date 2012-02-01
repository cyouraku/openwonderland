/**
 * Open Wonderland
 *
 * Copyright (c) 2012, Open Wonderland Foundation, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * The Open Wonderland Foundation designates this particular file as
 * subject to the "Classpath" exception as provided by the Open Wonderland
 * Foundation in the License file that accompanied this code.
 */
package org.jdesktop.wonderland.modules.snapshot.web.resources;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import org.jdesktop.wonderland.modules.darkstar.api.weblib.DarkstarRunner;
import org.jdesktop.wonderland.runner.RunManager;
import org.jdesktop.wonderland.runner.Runner.Status;
import org.jdesktop.wonderland.runner.RunnerException;
import org.jdesktop.wonderland.runner.StatusWaiter;
import org.jdesktop.wonderland.tools.wfs.WFS;
import org.jdesktop.wonderland.web.wfs.WFSManager;
import org.jdesktop.wonderland.web.wfs.WFSRoot;
import org.jdesktop.wonderland.web.wfs.WFSSnapshot;

/**
 *
 * Highly adapted from SnapshotManagerServlet.java
 * 
 * 
 * @author JagWire
 */
@Path("/snapshot")
public class SnapshotResource {
 
    private static final Logger LOGGER = Logger.getLogger(SnapshotResource.class.getName());
    private static final CacheControl NO_CACHE = new CacheControl();
    
    @GET
    @Path("/take/snapshot")
    public Response takeSnapshot() {
        DarkstarRunner runner = getRunner();
        if(runner == null || runner.getStatus() != Status.NOT_RUNNING) {
            return Response.status(Response.Status.BAD_REQUEST).cacheControl(NO_CACHE).build();
        }
        String snapshotName = "";
        
        // use a default name based on the current data
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SS");
        snapshotName = df.format(new Date());
               
        try {
            runner.createSnapshot(snapshotName);            
        } catch (RunnerException re) {
            LOGGER.warning("Error creating snapshot!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).cacheControl(NO_CACHE).build();
        }
        
        return Response.ok().cacheControl(NO_CACHE).build();
    }
    
    @GET
    @Path("/restore/{snapshotID}")
    public Response restoreSnapshot(@PathParam("snapshotID") String snapshotID) {
        snapshotID = snapshotID.replace("&", "/");
        DarkstarRunner runner = getRunner();
        if(runner == null || runner.getStatus() != Status.NOT_RUNNING) {
            return Response.status(Response.Status.BAD_REQUEST).cacheControl(NO_CACHE).build();
                    
        }
        WFSManager m = WFSManager.getWFSManager();
        WFSRoot root = getRoot(snapshotID);
        runner.setWFSName(root.getRootPath());
        
        return Response.ok().cacheControl(NO_CACHE).build();
    }
    
    @GET
    @Path("/make/current/{snapshotID}")
    public Response makeSnapshotCurrent(@PathParam("snapshotID") String snapshotID) {
        snapshotID = snapshotID.replace("&", "/");
        DarkstarRunner runner = getRunner();
        LOGGER.warning("received: " +snapshotID);
        if(runner == null || runner.getStatus() != Status.NOT_RUNNING) {
            return Response.status(Response.Status.BAD_REQUEST).cacheControl(NO_CACHE).build();
        
        }
        WFSManager m = WFSManager.getWFSManager();
        WFSRoot root = getRoot(snapshotID);
        WFSSnapshot snapshot = null;

        
        runner.setWFSName(root.getRootPath());
        return Response.ok().cacheControl(NO_CACHE).build();
    }
    
    @GET
    @Path("/stop")
    public Response stopServer() {
        DarkstarRunner runner = getRunner();
        if(runner == null) {
            LOGGER.warning("No Darkstar servers available!");
            return Response.status(Response.Status.BAD_REQUEST).cacheControl(NO_CACHE).build();
        }
        
        if(runner.getStatus() != Status.NOT_RUNNING) {
            try {
                StatusWaiter waiter = RunManager.getInstance().stop(runner, true);
                waiter.waitFor(Status.NOT_RUNNING);
            } catch (InterruptedException ex) {
                LOGGER.warning("Wait for NOT_RUNNING interrupted!\n" +ex.getLocalizedMessage());
            } catch (RunnerException ex) {
                LOGGER.warning("Error stopping server!\n" +ex);
            }            
        }
        return Response.ok().cacheControl(NO_CACHE).build();
        
    }
    
    @GET
    @Path("/start")
    public Response startServer() {
        DarkstarRunner runner = getRunner();
        if(runner == null) {
            LOGGER.warning("No Darkstar servers available!");
            return Response.status(Response.Status.BAD_REQUEST).cacheControl(NO_CACHE).build();
        }
        
        if(runner.getStatus() == Status.NOT_RUNNING) {
            try {
                
                StatusWaiter waiter = RunManager.getInstance().start(runner, true);
                waiter.waitFor(Status.RUNNING);
            } catch (InterruptedException ex) {
                LOGGER.warning("Wait for RUNNING interrupted!\n"+ex);
            } catch( RunnerException ex) {
                LOGGER.warning("Error starting server!\n"+ex);                
            }
        }
        
        return Response.ok().cacheControl(NO_CACHE).build();
        
    }
    
    
    
    
        /**
     * Get a Darkstar runner.  For now, this returns the first valid
     * runner.
     * XXX TODO: Deal with multiple runners XXX
     * @return the runner, or null if no Darkstar runner exists
     */
    protected DarkstarRunner getRunner() {
        Collection<DarkstarRunner> runners =
                RunManager.getInstance().getAll(DarkstarRunner.class);
        if (runners.isEmpty()) {
            return null;
        }

        return runners.iterator().next();
    }
    
    SnapshotResult requestRestart(
                                  DarkstarRunner runner,
                                  String action)
        throws IOException, ServletException
    {
        // make sure the runner exists
        if (runner == null) {
            return new SnapshotResult("No Darkstar servers available", null);
        }

        // find out whether we can stop the server
        boolean restart = true;
//        String restartStr = request.getParameter("restart");
//        if (restartStr != null) {
//            restart = Boolean.parseBoolean(restartStr);
//        }

        if (runner.getStatus() != Status.NOT_RUNNING) {
            if (restart) {
                try {
                    // stop the runner, and wait for it to stop
                    StatusWaiter sw = RunManager.getInstance().stop(runner, true);
                    sw.waitFor();
                } catch (RunnerException re) {
                    LOGGER.log(Level.WARNING, "Error stopping " + runner, re);
                    return new SnapshotResult("Error stopping runner", null);
                } catch (InterruptedException ie) {
                    // just ignore?
                    LOGGER.log(Level.WARNING, "Status wait interrupted", ie);
                }
            } else {
                //no problems
                return new SnapshotResult(null, "/confirm.jsp");
            }
        }

        // everything is OK
        return null;
    }
            /**
     * Complete a previous restart request
     * @param request the request
     * @param runner the runner to restart
     */
    protected void completeRestart(HttpServletRequest request,
                                   DarkstarRunner runner)
        throws IOException, ServletException
    {
        // make sure the runner exists
        if (runner == null) {
            return;
        }

        // find out whether we can stop the server
        boolean restart = false;
        String restartStr = request.getParameter("restart");
        if (restartStr != null) {
            restart = Boolean.parseBoolean(restartStr);
        }

        // start back up
        if (runner.getStatus() == Status.NOT_RUNNING) {
            if (restart) {
                // start the runner, don't wait for it though
                try {
                    RunManager.getInstance().start(runner, false);
                } catch (RunnerException re) {
                    throw new ServletException(re);
                }
            }
        }
    }
    
    protected WFSRoot getRoot(String id) {
        WFSRoot root = null;
        
                // decide if it is a world or a snapshot
        if (id.startsWith(WFSRoot.WORLDS_DIR)) {
            String worldName = id.substring(WFSRoot.WORLDS_DIR.length() + 1);
            if (worldName.equals(new EmptyWorld().getName())) {
                return new EmptyWorld();
            }
            
            root = WFSManager.getWFSManager().getWFSRoot(worldName);
        } else if (id.startsWith(WFSSnapshot.SNAPSHOTS_DIR)) {
            String snapshotName = id.substring(WFSSnapshot.SNAPSHOTS_DIR.length() + 1);
            root = WFSManager.getWFSManager().getWFSSnapshot(snapshotName);
        }

        if (root == null) {
            LOGGER.warning("Unable to find root: " + id);
        }

        return root;
    }
    
    class SnapshotResult {

        private String error;
        private String redirect;

        SnapshotResult(String error, String redirect) {
            this.error = error;
            this.redirect = redirect;
        }

        boolean hasError() {
            return error != null;
        }

        String getError() {
            return error;
        }

        boolean hasRedirect() {
            return redirect != null;
        }

        String getRedirect() {
            return redirect;
        }
    }
    
    static class EmptyWorld extends WFSRoot {
        @Override
        public String getName() {
            return "Empty World";
        }

        @Override
        public String getRootPath() {
            return "none";
        }

        @Override
        public WFS getWfs() {
            return null;
        }
    }
    
}