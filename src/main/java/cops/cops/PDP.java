/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cops;

import auxClasses.PolicyRepository;
import copsMsgs.CopsMessageCAT;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDP {

	public static void main(String[] args) {

		// Creation of the Policy Repository of this PDP
		PolicyRepository pr = new PolicyRepository();

		try {
			// Adding a rule to the Policy Repository
			pr.newPolicy("deny", "64.4.16.37", "172.16.1.2", 5, 7, 7);
			pr.newPolicy("deny", "64.4.16.38", "172.16.2.2", 7, 5, 5);
			pr.newPolicy("allow", "64.4.16.37", "65.65.65.65", 6, -1, -1);
			pr.deletePolicy("allow", "64.4.16.37", "65.65.65.65", 6, -1, -1);
		} catch (UnknownHostException ex) {
			Logger.getLogger(PDP.class.getName()).log(Level.SEVERE, null, ex);
		}

		ServerSocket servidor = null;
		try {
			servidor = new ServerSocket(3288, 10, InetAddress.getLocalHost());
		} catch (IOException ex) {
			Logger.getLogger(PDP.class.getName()).log(Level.SEVERE, null, ex);
		}

		Socket incoming = null;

		while (true) {
			try {
				incoming = servidor.accept();

				PDPThread pthread = new PDPThread(incoming, pr);

				pthread.start();


			} catch (Exception ex) {
				Logger.getLogger(PDP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
