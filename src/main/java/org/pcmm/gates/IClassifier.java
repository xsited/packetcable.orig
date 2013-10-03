package org.pcmm.gates;

import java.net.InetAddress;

import org.pcmm.base.IPCMMBaseObject;

/**
 * 
 * @author rhadjamor@gmail.com
 * 
 * 
 */
public interface IClassifier extends IPCMMBaseObject {

	static final short LENGTH = 24;
	static final byte SNUM = 6;
	static final byte STYPE = 1;

	/**
	 * IP Destination Address or IPv6 Destination Address is the termination
	 * point for the IP flow
	 * 
	 * @return destination IP address.
	 */
	InetAddress getDestinationIPAddress();

	void setDestinationIPAddress(InetAddress address);

	short getDestinationPort();

	void setDestinationPort(short p);

	/**
	 * Source IP, IP Source Address, or IPv6 Source Address (in the case of
	 * Extended Classifier or IPv6 Classifier) is the IP address (as seen at the
	 * CMTS) of the originator of the IP flow.
	 * 
	 * @return source IP address.
	 */
	InetAddress getSourceIPAddress();

	void setSourceIPAddress(InetAddress a);

	short getSourcePort();
	
	void setSourcePort(short p);

	/**
	 * Protocol field, in a legacy Classifier or Extended Classifier, identifies
	 * the type of protocol (e.g., IP, ICMP, etc.). The Next Header Type field
	 * serves a similar function in the IPv6 Classifier.
	 * 
	 * @return the protocol.
	 */
	String getProtocol();
	
	void setProtocol(String p);

	/**
	 * Priority may be used to distinguish between multiple classifiers that
	 * match a particular packet. This is typically set to a default value since
	 * classifiers are generally intended to be unique.
	 * 
	 * @return priority.
	 */
	byte getPriority();

	/**
	 * sets the priority;
	 * 
	 * @param p
	 *            priority
	 */
	void setPriority(byte p);
	
	
	byte getDSCPTOS();
	
	void setDSCPTOS(byte v);
	
	byte getDSCPTOSMask();
	
	void setDSCPTOSMask(byte v);

	// DSCP/TOS Field
	// 
	// DSCP/TOS Mask

}
