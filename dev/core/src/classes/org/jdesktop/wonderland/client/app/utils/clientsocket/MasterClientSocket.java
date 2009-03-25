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
package org.jdesktop.wonderland.client.app.utils.clientSocket;

public class MasterClientSocket extends ClientSocket {

    public MasterClientSocket (BigInteger masterClientID, Socket s, ClientSocketListener listener) {
	super(masterClientId, s, listener);
	master = true;
	enable = true;

	if (ENABLE_STATS) {
	    statReporter = new StatisticsReporter(10, /* secs */
						  new WriteStatistics(), 
						  new WriteStatistics(),
						  new WriteStatistics());
	    statReporter.start();
	}
    }

    public void setEnableSlave (BigInteger slave, boolean enable) {
	SlaveClientSocket scs = clientSocketMap.get(slave);
	if (scs != null) {
	    scs.setEnable(enable);
	}
    }

    public void close () {
	super.close();
	if (ENABLE_STATS) {
	    statReporter.stop();
	}
    }
}