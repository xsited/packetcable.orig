/**
 * 
 */
package org.pcmm.gates.impl;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.IGateID;

/**
 * @author riadh
 * 
 */
public class GateID extends PCMMBaseObject implements IGateID {

	private int gateID;

	/**
	 * 
	 */
	public GateID() {
		this(LENGTH, STYPE, SNUM);
	}

	/**
	 * @param data
	 */
	public GateID(byte[] data) {
		super(data);
	}

	/**
	 * @param len
	 * @param sType
	 * @param sNum
	 */
	public GateID(short len, short sType, short sNum) {
		super(len, sType, sNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IGateID#setGateID(int)
	 */
	@Override
	public void setGateID(int gateID) {
		this.gateID = gateID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IGateID#getGateID()
	 */
	@Override
	public int getGateID() {
		return gateID;
	}

}
