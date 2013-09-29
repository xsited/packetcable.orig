/**
 * 
 */
package org.pcmm.gates.impl;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.ITransactionID;

/**
 * @author riadh
 * 
 */
public class TransactionID extends PCMMBaseObject implements ITransactionID {

	private short transactionId;
	private short cmdType;

	/**
	 * 
	 */
	public TransactionID() {
		this(LENGTH, STYPE, SNUM);
	}

	/**
	 * @param data
	 */
	public TransactionID(byte[] data) {
		super(data);
	}

	/**
	 * @param len
	 * @param sType
	 * @param sNum
	 */
	public TransactionID(short len, short sType, short sNum) {
		super(len, sType, sNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.ITransactionID#setTransactionIdentifier(short)
	 */
	@Override
	public void setTransactionIdentifier(short id) {
		transactionId = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.ITransactionID#getTransactionIdentifier()
	 */
	@Override
	public short getTransactionIdentifier() {
		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.ITransactionID#setGateCommandType(short)
	 */
	@Override
	public void setGateCommandType(short type) {
		cmdType = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.ITransactionID#getGateCommandType()
	 */
	@Override
	public short getGateCommandType() {
		return cmdType;
	}

}
