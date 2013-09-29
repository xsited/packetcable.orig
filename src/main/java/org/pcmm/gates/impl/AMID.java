/**
 * 
 */
package org.pcmm.gates.impl;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.IAMID;

/**
 * @author riadh
 * 
 */
public class AMID extends PCMMBaseObject implements IAMID {

	private short applicationType;

	private short applicationMgrTag;

	/**
	 * 
	 */
	public AMID() {
		this(LENGTH, STYPE, SNUM);
	}

	/**
	 * @param data
	 */
	public AMID(byte[] data) {
		super(data);
	}

	/**
	 * @param len
	 * @param sType
	 * @param sNum
	 */
	public AMID(short len, short sType, short sNum) {
		super(len, sType, sNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IAMID#setApplicationType(short)
	 */
	@Override
	public void setApplicationType(short type) {
		this.applicationType = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IAMID#getApplicationType()
	 */
	@Override
	public short getApplicationType() {
		return applicationType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IAMID#setApplicationMgrTag(short)
	 */
	@Override
	public void setApplicationMgrTag(short type) {
		applicationMgrTag = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IAMID#getApplicationMgrTag()
	 */
	@Override
	public short getApplicationMgrTag() {
		return applicationMgrTag;
	}

}
