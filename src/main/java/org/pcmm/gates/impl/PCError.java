/**
 * 
 */
package org.pcmm.gates.impl;

import java.lang.Integer;
import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.IPCError;

/**
 * 
 */
public class PCError extends PCMMBaseObject implements IPCError {
	/**
	 * 
	 */
	public PCError() {
		this(LENGTH, STYPE, SNUM);
	}

	/**
	 * @param data
	 */
	public PCError(byte[] data) {
		super(data);
	}

	/**
	 * @param len
	 * @param sType
	 * @param sNum
	 */
	public PCError(short len, byte sType, byte sNum) {
		super(len, sType, sNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCError#setErrorCode(int)
	 */
	@Override
	public void setErrorCode(int ErrorCode) {
		setInt(ErrorCode, (short) 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCError#getErrorCode()
	 */
	@Override
	public int getErrorCode() {
		return getInt((short) 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCError#setErrorSubcode(int)
	 */
	@Override
	public void setErrorSubcode(int ErrorSubcode) {
		setInt(ErrorSubcode, (short) 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCError#getErrorCode()
	 */
	@Override
	public int getErrorSubcode() {
		return getInt((short) 2);
	}
	// toString ????
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCError#getDescription()
	 */
	@Override
	public String getDescription(int ErrorCode, int ErrorSubcode) {
		String hex = Integer.toHexString(ErrorSubcode & 0xFFFF);
		return "Error Code: " + ErrorCode + " Error Subcode : " + hex; // + Description;

	}
}
