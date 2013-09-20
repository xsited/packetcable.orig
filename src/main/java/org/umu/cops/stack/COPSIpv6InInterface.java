/*
 * Copyright (c) 2003 University of Murcia.  All rights reserved.
 * --------------------------------------------------------------
 * For more information, please see <http://www.umu.euro6ix.org/>.
 */

package org.umu.cops.stack;

import java.io.IOException;
import java.net.Socket;

/**
 * COPS IPv6 Input Interface
 *
 * @author F�lix Jes�s Garc�a Clemente  (fgarcia@dif.um.es)
 * @version COPSIpv6InInterface.java, v 1.00 2003
 *
 */
public class COPSIpv6InInterface extends COPSIpv6Interface {
    public COPSIpv6InInterface()
	{
		_objHdr = new COPSObjHeader();
		_objHdr.setCNum(COPSObjHeader.COPS_ININTF);
	}

	public COPSIpv6InInterface(byte[] dataPtr) {
		super(dataPtr);
	}
	
	/**
	 * Method className
	 *
	 * @return   a String
	 *
	 */
	public String className()
	{
		return "COPSIpv6InInterface";
	}

	/**
	 * Method isInInterface
	 *
	 * @return   a boolean
	 *
	 */
	public boolean isInInterface()
	{
		return true;
	}

	/**
	 * Writes data to given socket
	 *
	 * @param    id                  a  Socket
	 *
	 * @throws   IOException
	 *
	 */
	public void writeData(Socket id) throws IOException {
	}
}

