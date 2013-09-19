/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valter Vicente - 39360 <valtervicente@ua.pt>
 */
public class PolicyRepository {

	private Policy policy[] = new Policy[10];
	private int nPolicies = 0;

	public PolicyRepository() {
	}

	public void newPolicy(String allowOrDeny, String srcIP, String dstIP, int protocol, int srcPort, int dstPort) throws UnknownHostException {

		// DO THIS
		// Check if this rule is not already present
//		for(int i = 0; i < policy.length; i++)
//		{
//		}

		// Do this if the Policy Repository is full
		if (nPolicies == policy.length) {
			Policy aux[] = new Policy[policy.length + 10];
			System.arraycopy(policy, 0, aux, 0, policy.length);
			policy = aux;
		}

		policy[nPolicies] = new Policy(allowOrDeny, srcIP, dstIP, protocol, srcPort, dstPort);	// Creates the Policy in the Policy Repository

		// Saves the Policy in the file
		try {
			PrintWriter filePolicies = new PrintWriter(new FileWriter("policies.txt", true));

			filePolicies.print(allowOrDeny + " ");
			filePolicies.print(srcIP + " ");
			filePolicies.print(dstIP + " ");
			filePolicies.print(protocol + " ");
			filePolicies.print(srcPort + " ");
			filePolicies.println(dstPort);

			filePolicies.close();
		} catch (IOException ex) {
			Logger.getLogger(PolicyRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
		nPolicies++;
	}

	public boolean deletePolicy(String allowOrDeny, String srcIP, String dstIP, int protocol, int srcPort, int dstPort) throws UnknownHostException{
		boolean deleted = false;
		int ad = 0;
		InetAddress sIP;
		InetAddress dIP;

		if(allowOrDeny.equalsIgnoreCase("allow"))
			ad = 1;
		sIP = InetAddress.getByName(srcIP);
		dIP = InetAddress.getByName(dstIP);

//		String s = allowOrDeny + " " + srcIP + " " + dstIP + " " + protocol + " " + srcPort + " " + dstPort;

		for (int i = 0; i < this.nPolicies; i++) {

			System.out.println(sIP.getHostAddress());
			System.out.println(dIP.getHostAddress());

			if(ad == policy[i].getAllowOrDeny())
				System.out.println("1");
			if(sIP == policy[i].getSrcIP())
				System.out.println("2");
			if(dIP == policy[i].getDstIP())
				System.out.println("3");
			if(protocol == policy[i].getProtocol())
				System.out.println("4");
			if(srcPort == policy[i].getSrcPort())
				System.out.println("5");
			if(dstPort == policy[i].getDstPort())
				System.out.println("6");



System.out.println("PENISES");
//			if((ad == policy[i].getAllowOrDeny()) && (sIP == policy[i].getSrcIP()) && (dIP == policy[i].getDstIP()) &&
//				(protocol == policy[i].getProtocol()) && (srcPort == policy[i].getSrcPort()) && (dstPort == policy[i].getDstPort()))
//			{
//				System.arraycopy(policy, i+1, policy, i, policy.length - i - 1);
//				deleted = true;
//				break;
//			}
		}

		return deleted;
	}
}
