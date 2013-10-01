/**
 * 
 */
package org.pcmm.gates.impl;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.ITrafficProfile;

/**
 * @author riadh
 * 
 */
public class DOCSISServiceClassNameTrafficProfile extends PCMMBaseObject
		implements ITrafficProfile {

	public static final short STYPE = 2;
	public static final short LENGTH = 24;

	private byte envelop;
	private String serviceClassName;

	/**
	 * 
	 */
	public DOCSISServiceClassNameTrafficProfile() {
		this(LENGTH, STYPE, SNUM);
	}

	/**
	 * @param data
	 */
	public DOCSISServiceClassNameTrafficProfile(byte[] data) {
		super(data);
	}

	/**
	 * @param len
	 * @param sType
	 * @param sNum
	 */
	public DOCSISServiceClassNameTrafficProfile(short len, short sType,
			short sNum) {
		super(len, sType, sNum);
		setEnvelop((byte) 0x111);
	}

	/**
	 * @return the serviceClassName
	 */
	public String getServiceClassName() {
		return serviceClassName;
	}

	/**
	 * @param serviceClassName
	 *            the serviceClassName to set
	 */
	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.ITrafficProfile#getEnvelop()
	 */
	@Override
	public byte getEnvelop() {
		return envelop;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.ITrafficProfile#setEnvelop(byte)
	 */
	@Override
	public void setEnvelop(byte en) {
		envelop = en;
	}
}
