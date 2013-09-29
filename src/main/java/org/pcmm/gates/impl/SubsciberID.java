/**
 * 
 */
package org.pcmm.gates.impl;

import java.net.InetAddress;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.ISubscriberID;

/**
 * @author riadh
 * 
 */
public class SubsciberID extends PCMMBaseObject implements ISubscriberID {

	private InetAddress address;

	/**
	 * 
	 */
	public SubsciberID() {
		this(LENGTH, STYPE, SNUM);
	}

	/**
	 * @param data
	 */
	public SubsciberID(byte[] data) {
		super(data);
	}

	/**
	 * @param len
	 * @param sType
	 * @param sNum
	 */
	public SubsciberID(short len, short sType, short sNum) {
		super(len, sType, sNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.ISubscriberID#getSourceIPAddress()
	 */
	@Override
	public InetAddress getSourceIPAddress() {
		return address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pcmm.gates.ISubscriberID#setSourceIPAddress(java.net.InetAddress)
	 */
	@Override
	public void setSourceIPAddress(InetAddress address) {
		this.address = address;
	}

}
