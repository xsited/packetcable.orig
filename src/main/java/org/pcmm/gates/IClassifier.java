package org.pcmm.gates;

import java.net.InetAddress;

/**
 * 
 * @author rhadjamor@gmail.com
 * 
 * 
 */
public interface IClassifier {

	/**
	 * IP Destination Address or IPv6 Destination Address is the termination
	 * point for the IP flow
	 * 
	 * @return destination IP address.
	 */
	InetAddress getDestinationIPAddress();

	int getDestinationPort();

	/**
	 * Source IP, IP Source Address, or IPv6 Source Address (in the case of
	 * Extended Classifier or IPv6 Classifier) is the IP address (as seen at the
	 * CMTS) of the originator of the IP flow.
	 * 
	 * @return source IP address.
	 */
	InetAddress getSourceIPAddress();

	int getSourcePort();

	/**
	 * Protocol field, in a legacy Classifier or Extended Classifier, identifies
	 * the type of protocol (e.g., IP, ICMP, etc.). The Next Header Type field
	 * serves a similar function in the IPv6 Classifier.
	 * 
	 * @return the protocol.
	 */
	String getProtocol();

	/**
	 * Priority may be used to distinguish between multiple classifiers that
	 * match a particular packet. This is typically set to a default value since
	 * classifiers are generally intended to be unique.
	 * 
	 * @return priority.
	 */
	int getPriority();

	// DSCP/TOS Field
	// 
	// DSCP/TOS Mask

}
