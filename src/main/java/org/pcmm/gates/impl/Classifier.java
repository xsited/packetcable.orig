/**
 * 
 */
package org.pcmm.gates.impl;

import java.net.Inet4Address;
import java.net.InetAddress;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.IClassifier;

/**
 * @author rhadjamor@gmail.com
 * 
 */
public class Classifier extends PCMMBaseObject implements IClassifier {

	private Inet4Address destAddress;
	private Inet4Address srcAddress;
	private short dstPort;
	private short srcPort;
	private byte priority;
	private String protocol;

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
		return destAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pcmm.gates.IClassifier#setDestinationIPAddress(java.net.InetAddress)
	 */
	@Override
	public void setDestinationIPAddress(InetAddress address) {
		destAddress = (Inet4Address) address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getDestinationPort()
	 */
	@Override
	public short getDestinationPort() {
		return dstPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setDestinationPort(short)
	 */
	@Override
	public void setDestinationPort(short p) {
		dstPort = p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getSourceIPAddress()
	 */
	@Override
	public InetAddress getSourceIPAddress() {
		return srcAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setSourceIPAddress(java.net.InetAddress)
	 */
	@Override
	public void setSourceIPAddress(InetAddress a) {
		srcAddress = (Inet4Address) a;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#getSourcePort()
	 */
	@Override
	public short getSourcePort() {
		return srcPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setSourcePort(short)
	 */
	@Override
	public void setSourcePort(short p) {
		srcPort = p;

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
		return priority;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IClassifier#setPriority(byte)
	 */
	@Override
	public void setPriority(byte p) {
		priority = p;
	}

}
