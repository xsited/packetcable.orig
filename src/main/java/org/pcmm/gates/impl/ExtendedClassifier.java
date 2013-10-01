/**
 * 
 */
package org.pcmm.gates.impl;

import java.net.InetAddress;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.IExtendedClassifier;

/**
 * @author RH030971
 *
 */
public class ExtendedClassifier extends PCMMBaseObject implements
		IExtendedClassifier {

	/**
	 * @param data
	 */
	public ExtendedClassifier(byte[] data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param len
	 * @param sType
	 * @param sNum
	 */
	public ExtendedClassifier(short len, short sType, short sNum) {
		super(len, sType, sNum);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#getDestinationIPAddress()
	 */
	@Override
	public InetAddress getDestinationIPAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#setDestinationIPAddress(java.net.InetAddress)
	 */
	@Override
	public void setDestinationIPAddress(InetAddress address) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#getDestinationPort()
	 */
	@Override
	public short getDestinationPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#setDestinationPort(short)
	 */
	@Override
	public void setDestinationPort(short p) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#getSourceIPAddress()
	 */
	@Override
	public InetAddress getSourceIPAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#setSourceIPAddress(java.net.InetAddress)
	 */
	@Override
	public void setSourceIPAddress(InetAddress a) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#getSourcePort()
	 */
	@Override
	public short getSourcePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#setSourcePort(short)
	 */
	@Override
	public void setSourcePort(short p) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#getProtocol()
	 */
	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#setProtocol(java.lang.String)
	 */
	@Override
	public void setProtocol(String p) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#getPriority()
	 */
	@Override
	public byte getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IClassifier#setPriority(byte)
	 */
	@Override
	public void setPriority(byte p) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getIPSourceMask()
	 */
	@Override
	public int getIPSourceMask() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getIPDestinationMask()
	 */
	@Override
	public int getIPDestinationMask() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getSourcePortStart()
	 */
	@Override
	public int getSourcePortStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getSourcePortEnd()
	 */
	@Override
	public int getSourcePortEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getDestinationPortStart()
	 */
	@Override
	public int getDestinationPortStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getDestinationPortEnd()
	 */
	@Override
	public int getDestinationPortEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getClassifierID()
	 */
	@Override
	public int getClassifierID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getActivationState()
	 */
	@Override
	public int getActivationState() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pcmm.gates.IExtendedClassifier#getAction()
	 */
	@Override
	public int getAction() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getDSCPTOS() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDSCPTOS(byte v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte getDSCPTOSMask() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDSCPTOSMask(byte v) {
		// TODO Auto-generated method stub
		
	}

}
