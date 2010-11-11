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

package org.jdesktop.wonderland.web.wfs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jdesktop.wonderland.tools.wfs.WFS;
import org.jdesktop.wonderland.tools.wfs.WFSFactory;

/**
 * @author Jordan Slott <jslott@dev.java.net>
 * @author kaplanj
 */
@XmlRootElement(name="wfs-snapshot")
public class WFSSnapshot {
    /**
     * The name of the WFS snapshot description file relative to the
     * snapshot root directory.
     */
    private static final String SNAPSHOT_DESC = "snapshot.xml";

    /**
     * The name of the WFS directory relative to the snapshot root directory
     */
    public static final String SNAPSHOT_WFS = "world-wfs";

    /* The XML marshaller and unmarshaller for later use */
    private static Marshaller marshaller = null;
    private static Unmarshaller unmarshaller = null;

    /** The top-level directory for this snapshot */
    private File directory;

    /** The timestamp for this snapshot */
    private Date timestamp;

    /** The description of this snapshot */
    private String description;

    /** The decoded WFS associated with this snapshot */
    private WFS wfs;

    /* Create the XML marshaller and unmarshaller once for all ModuleInfos */
    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(WFSSnapshot.class);
            WFSSnapshot.unmarshaller = jc.createUnmarshaller();
            WFSSnapshot.marshaller = jc.createMarshaller();
            WFSSnapshot.marshaller.setProperty("jaxb.formatted.output", true);
        } catch (javax.xml.bind.JAXBException excp) {
            Logger.getLogger(WFSSnapshot.class.getName()).log(Level.WARNING,
                    "[WFS] Unable to create JAXBContext", excp);
        }
    }

    /**
     * No-arg constructor.  Use getInstance() instead.
     */
    public WFSSnapshot() {
    }

    /**
     * Get the name of this snapshot
     * @return name the name of this snapshot
     */
    @XmlTransient
    public String getName() {
        return getDirectory().getName();
    }

    /**
     * Set the name of this snapshot.  This will rename the directory, so
     * be careful
     * @param name the new name for this snapshot
     * @throws IOException if there is an error renaming
     */
    public void setName(String newName) throws IOException {
        // get the current name
        String oldName = getName();

        File parent = getDirectory().getParentFile();
        File newDir = new File(parent, newName);

        if (newDir.exists()) {
            throw new IOException("New directory " + newDir + " exists");
        }

        if (!getDirectory().renameTo(newDir)) {
            throw new IOException("Unable to rename " + getDirectory() +
                                  " to " + newDir);
        }

        // update our internal state
        setDirectory(newDir);
        setWfs(newDir);

        // notify the manager
        WFSManager.getWFSManager().renameSnapshot(oldName, this);
    }

    /**
     * Get the description of this snapshot
     * @return the user-provided description of this snapshot, or null if
     * no description was provided
     */
    @XmlTransient
    public String getDescription() {
        return getDescriptionInternal();
    }

    /**
     * Set the description of this snapshot
     * @param description the description to set
     * @throws IOException if there is an error saving the updated description
     */
    public void setDescription(String description) throws IOException {
        setDescriptionInternal(description);
        save();
    }

    /**
     * Get the description if this snapshot.  Used internally by jaxb.
     */
    @XmlElement(name="description")
    protected String getDescriptionInternal() {
        return description;
    }

    /**
     * Set the description of this snapshot but don't rewrite the value.  Used
     * internally by jaxb
     * @param description the description to set
     */
    protected void setDescriptionInternal(String description) {
        this.description = description;
    }


    /**
     * Get the top-level directory for this snapshot
     * @return the snapshot directory
     */
    @XmlTransient
    public File getDirectory() {
        return directory;
    }

    /**
     * Set the top-level directory for this snapshot. Only done when restoring.
     * @param directory the directory of this snapshot
     */
    protected void setDirectory(File directory) {
        this.directory = directory;
    }

    /**
     * Get the path to the WFS relative to the root of this directory
     * @return the path
     */
    @XmlTransient
    public String getPath() {
        return SNAPSHOT_WFS;
    }

    /**
     * Get the timestamp for this snapshot
     * @return the timestamp
     */
    @XmlTransient
    public Date getTimestamp() {
        return getTimestampInternal();
    }

    /**
     * Set the timestamp for this snapshot
     * @param timestamp the timestamp to set
     * @throws IOException if there is an error setting the timestamp
     */
    public void setTimestamp(Date timestamp) throws IOException {
        setTimestampInternal(timestamp);
        save();
    }

     /**
     * Get the timestamp for this snapshot.  Used internally by jaxb
     * @return the timestamp
     */
    @XmlElement(name="timestamp")
    protected Date getTimestampInternal() {
        return timestamp;
    }

    /**
     * Set the timestamp for this snapshot.  Used internally by jaxb.
     * @param timestamp the timestamp to set
     */
    public void setTimestampInternal(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get the WFS associated with this snapshot
     * @return the WFS object associated with this snapshot
     */
    @XmlTransient
    public WFS getWfs() {
        return wfs;
    }

    /**
     * Set the WFS associated with this snapshot.  Only done at restore time.
     * @param dir the base directory to read the WFS from
     */
    protected void setWfs(File dir) throws IOException {
        // decode the WFS if it exists
        File wfsDir = new File(dir, SNAPSHOT_WFS);
        try {
            if (wfsDir.exists()) {
                wfs = WFSFactory.open(wfsDir.toURI().toURL());
            } else {
                wfs = WFSFactory.create(wfsDir.toURI().toURL());
            }
        } catch (Exception ex) {
            IOException ioe = new IOException("Error reading WFS from " +
                                              wfsDir);
            ioe.initCause(ex);
            throw ioe;
        }
    }

    /**
     * Gets an instance of WFSSnapshot for the given directory.  If there
     * is no WFS snapshot in the directory, it will be created.
     *
     * @param dir the directory to get a snapshot for
     * @throws IOException if there is an error reading the directory
     */
    public static WFSSnapshot getInstance(File dir) throws IOException {
        WFSSnapshot snapshot;

        // read the description file if it exists
        File snapshotDesc = new File(dir, SNAPSHOT_DESC);
        if (snapshotDesc.exists()) {
            // if the snapshot description file exists, read it in
            FileReader r = new FileReader(snapshotDesc);
            try {
                snapshot = (WFSSnapshot) WFSSnapshot.unmarshaller.unmarshal(r);
            } catch (JAXBException je) {
                IOException ioe = new IOException("Error reading snapshot " +
                        " description from " + snapshotDesc);
                ioe.initCause(je);
                throw ioe;
            }
        } else {
            // otherwise, just start with an empty one
            snapshot = new WFSSnapshot();
        }

        // fill in the root directory & wfs
        snapshot.setDirectory(dir);
        snapshot.setWfs(dir);

        // all set
        return snapshot;
    }

    /**
     * Save the XML description
     * @throw IOException Upon error writing the XML file
     */
    public void save() throws IOException {
        File snapshotDesc = new File(getDirectory(), SNAPSHOT_DESC);
        FileWriter fw = new FileWriter(snapshotDesc);

        try {
            WFSSnapshot.marshaller.marshal(this, fw);
        } catch (JAXBException je) {
            IOException ioe = new IOException("Error writing snapshot to " +
                                              snapshotDesc);
            ioe.initCause(je);
            throw ioe;
        }
    }
}