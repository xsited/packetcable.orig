/**
 * 
 */
package org.pcmm.gates.impl;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.IClassifier;

/**
 * @author rhadjamor@gmail.com
 * 
 */
public class Classifier extends PCMMBaseObject implements IClassifier {

	// TODO change this
	private String protocol = "TCP";

	/**
	 * 
	 */
	public Classifier() {
		this(LENGTH, STYPE, SNUM);
	}

	/**
	 * @param data
	 */
	public Classifier(byte[] data) {
		super(data);
	}

	/**
	 * @param len
	 * @param sType
	 * @param sNum
	 */
	public Classifier(short len, short sType, short sNum) {
		super(len, sType, sNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getDestinationIPAddress()
	 */
	@Override
	public InetAddress getDestinationIPAddress() {
		try {
			return Inet4Address.getByAddress(getBytes((short) 6, (short) 4));
		} catch (UnknownHostException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pcmm.gates.IClassifier#setDestinationIPAddress(java.net.InetAddress)
	 */
	@Override
	public void setDestinationIPAddress(InetAddress address) {
		setBytes(address.getAddress(), (short) 6);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getDestinationPort()
	 */
	@Override
	public short getDestinationPort() {
		return getShort((short) 12);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setDestinationPort(short)
	 */
	@Override
	public void setDestinationPort(short p) {
		setShort(p, (short) 12);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getSourceIPAddress()
	 */
	@Override
	public InetAddress getSourceIPAddress() {
		try {
			return Inet4Address.getByAddress(getBytes((short) 2, (short) 4));
		} catch (UnknownHostException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setSourceIPAddress(java.net.InetAddress)
	 */
	@Override
	public void setSourceIPAddress(InetAddress a) {
		setBytes(a.getAddress(), (short) 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getSourcePort()
	 */
	@Override
	public short getSourcePort() {
		return getShort((short) 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setSourcePort(short)
	 */
	@Override
	public void setSourcePort(short p) {
		setShort(p, (short) 10);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getProtocol()
	 */
	@Override
	public String getProtocol() {
		return protocol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setProtocol(java.lang.String)
	 */
	@Override
	public void setProtocol(String p) {
		protocol = p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getPriority()
	 */
	@Override
	public byte getPriority() {
		return getBytes((short) 14, (short) 1)[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setPriority(byte)
	 */
	@Override
	public void setPriority(byte p) {
		setBytes(new byte[] { p }, (short) 14);
	}

	@Override
	public byte getDSCPTOS() {
		return getBytes((short) 0, (short) 1)[0];
	}

	@Override
	public void setDSCPTOS(byte v) {
		setBytes(new byte[] { v }, (short) 0);

	}

	@Override
	public byte getDSCPTOSMask() {
		return getBytes((short) 1, (short) 1)[0];
	}

	@Override
	public void setDSCPTOSMask(byte v) {
		setBytes(new byte[] { v }, (short) 1);

	}

}
