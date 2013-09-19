/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package auxClasses;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Valter Vicente - 39360 <valtervicente@ua.pt>
 */
public class Policy {

	private int allowOrDeny;
	private InetAddress srcIP;
	private InetAddress dstIP;
	private int protocol;
	private int srcPort;
	private int dstPort;

	public Policy() {
	}

	public Policy(String allowOrDeny, String srcIP, String dstIP, int protocol, int srcPort, int dstPort) throws UnknownHostException {
		 
		if(allowOrDeny.equalsIgnoreCase("allow"))
			this.allowOrDeny = 1;
		else if(allowOrDeny.equalsIgnoreCase("deny"))
			this.allowOrDeny = 0;
		this.srcIP = InetAddress.getByName(srcIP);
		this.dstIP = InetAddress.getByName(dstIP);
		this.protocol = protocol;
		this.srcPort = srcPort;
		this.dstPort = dstPort;
	}

	public int getAllowOrDeny() {
		return allowOrDeny;
	}

	public InetAddress getSrcIP() {
		return srcIP;
	}

	public InetAddress getDstIP() {
		return dstIP;
	}

	public int getProtocol() {
		return protocol;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public int getDstPort() {
		return dstPort;
	}

}
