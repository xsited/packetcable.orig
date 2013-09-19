/*
 * Copyright (c) 2004 University of Murcia.  All rights reserved.
 * --------------------------------------------------------------
 * For more information, please see <http://www.umu.euro6ix.org/>.
 */

package org.umu.cops.test.prpolicyserver;

import org.umu.cops.common.COPS_def;
import org.umu.cops.prpdp.COPSPdpAgent;
import org.umu.cops.prpdp.COPSPdpConnection;
import org.umu.cops.prpdp.COPSPdpReqStateMan;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 * Main driver to launch policyServer.
 *
 * @author Félix Jesús García Clemente  (fgarcia@dif.um.es)
 * @version PolicyServer.java, v 3.00 2004
 *
 */
public class PolicyServer {
	
	/**
	 * Method main
	 *
	 */
	public static void main(String[] args) {
		try
		{
			System.out.println("PolicyServer: " + "Ex. START!!");

            IPsecPolicyDataProcess process = new IPsecPolicyDataProcess();
			COPSPdpAgent agent = new COPSPdpAgent(COPS_def.C_IPSEC, process);
            agent.setAcctTimer((short) 60);
			agent.start();
			
			while(true) {

                System.out.println("PolicyServer: " + "-----------------------------------------------------------------");

                // Monitoring ...

                if(agent.getClientType() == COPS_def.C_IPSEC)
                    System.out.println("PolicyServer: " + "IPSEC - ClientType[" + agent.getClientType() + "]");
                else
                    System.out.println("PolicyServer: " + "Unknowed - ClientType[" + agent.getClientType() + "]");

                System.out.println("PolicyServer: " + "    Acct Timer: " + agent.getAcctTimer());
                System.out.println("PolicyServer: " + "    KAlive Timer: " + agent.getKaTimer());

				// Get a list of connected PEP's
				for (Enumeration e = agent.getConnectionMap().keys() ; e.hasMoreElements() ;) {
					
					String pepId = (String) e.nextElement();
					COPSPdpConnection pdpConn = (COPSPdpConnection) agent.getConnectionMap().get(pepId);
									
					System.out.println("PolicyServer: " + "Connection [" + pepId + "], parameters: ");
                    System.out.println("PolicyServer: " + "    Last KAlive: " + pdpConn.getLastKAlive().toString());
                    System.out.println("PolicyServer: " + "    Last Message: " + pdpConn.getLastMessage());
                    System.out.println("PolicyServer: " + "    Acct Timer: " + pdpConn.getAcctTimer());
                    System.out.println("PolicyServer: " + "    KAlive Timer: " + pdpConn.getKaTimer());

					// Get a list of handles opened by PEP and show their information
					for (Enumeration ee = pdpConn.getReqStateMans().keys() ; ee.hasMoreElements() ;) {
						String handle = (String) ee.nextElement();
						COPSPdpReqStateMan man = (COPSPdpReqStateMan) pdpConn.getReqStateMans().get(handle);

						System.out.println("PolicyServer: " + "      ClientHandle[" + handle + "]");

                        switch (man.getStatus()) {
                            case COPSPdpReqStateMan.ST_CREATE:
                                System.out.println("PolicyServer: " + "      Status: " + "Request State created.");
                            break;
                            case COPSPdpReqStateMan.ST_INIT:
                                System.out.println("PolicyServer: " + "      Status: " + "Request sent.");
                            break;
                            case COPSPdpReqStateMan.ST_DECS:
                                System.out.println("PolicyServer: " + "      Status: " + "Decisions received.");
                            break;
                            case COPSPdpReqStateMan.ST_REPORT:
                                System.out.println("PolicyServer: " + "      Status: " + "Report sent.");
                            break;
                            case COPSPdpReqStateMan.ST_FINAL:
                                System.out.println("PolicyServer: " + "      Status: " + "Request State finalized.");
                            break;
                            case COPSPdpReqStateMan.ST_DEL:
                                System.out.println("PolicyServer: " + "      Status: " + "Delete Request State solicited.");
                            break;
                            case COPSPdpReqStateMan.ST_NEW:
                                System.out.println("PolicyServer: " + "      Status: " + "New Request State solicited.");
                            break;
                            case COPSPdpReqStateMan.ST_SYNC:
                                System.out.println("PolicyServer: " + "      Status: " + "SYNC Request received.");
                            break;
                            case COPSPdpReqStateMan.ST_SYNCALL:
                                System.out.println("PolicyServer: " + "      Status: " + "SYNC Completed.");
                            break;
                            case COPSPdpReqStateMan.ST_CCONN:
                                System.out.println("PolicyServer: " + "      Status: " + "Close Connection received.");
                            break;
                            case COPSPdpReqStateMan.ST_NOKA:
                                System.out.println("PolicyServer: " + "      Status: " + "KAlive Time out.");
                            break;
                            case COPSPdpReqStateMan.ST_ACCT:
                                System.out.println("PolicyServer: " + "      Status: " + "ACCT Time out.");
                            break;
                        }

                        // Keyboard input
                        System.out.println("PolicyServer: " + "    Sync? (s/n) ");
                        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                        String inputLine = keyboard.readLine(); // read line
                        if (inputLine.indexOf('s') != -1) {
                            System.out.println("PolicyServer: " + "    Yes!!, Sync ....");
                            agent.sync(pepId);
                        }

					}
				}
                try { Thread.sleep(10000); } catch (Exception eee) {};

			}
		}
		catch (Exception e)
		{
			System.err.println("PolicyServer: " + e);
		}
		
	}
}

