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
package org.jdesktop.wonderland.modules.webdav.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.apache.webdav.lib.WebdavResource;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentResource;

/**
 * Webdav implementation of ContentResource
 * @author jkaplan
 */
public class WebdavContentResource extends WebdavContentNode 
        implements ContentResource
{
    WebdavContentResource(WebdavResource resource, WebdavContentCollection parent) {
        super (resource, parent);
    }

    public long getSize() {
        return getResource().getGetContentLength();
    }

    public Date getLastModified() {
        return new Date(getResource().getGetLastModified());
    }

    public InputStream getInputStream() throws ContentRepositoryException {
        try {
            return getResource().getMethodData();
        } catch (IOException ioe) {
            throw new ContentRepositoryException(ioe);
        }
    }

    public void get(File file) throws ContentRepositoryException, IOException {
        getResource().getMethod(file);
    }

    public void put(byte[] data) throws ContentRepositoryException {
        try {
            getResource().putMethod(data);
        } catch (IOException ioe) {
            throw new ContentRepositoryException(ioe);
        }
    }

    public void put(File file) throws ContentRepositoryException, IOException {
        getResource().putMethod(file);
    }
}
