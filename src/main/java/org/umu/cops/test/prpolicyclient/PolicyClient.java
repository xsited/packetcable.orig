/*
 * Copyright (c) 2004 University of Murcia.  All rights reserved.
 * --------------------------------------------------------------
 * For more information, please see <http://www.umu.euro6ix.org/>.
 */

package org.umu.cops.test.prpolicyclient;

import org.umu.cops.common.COPS_def;
import org.umu.cops.prpep.COPSPepAgent;
import org.umu.cops.prpep.COPSPepConnection;
import org.umu.cops.prpep.COPSPepReqStateMan;
import org.umu.cops.stack.COPSError;

import java.util.Enumeration;

/**
 * XML-IPSec Policy Client
 *
 * @author Félix Jesús García Clemente  (fgarcia@dif.um.es)
 * @version PolicyClient.java, v 3.00 2004
 *
 */
public class PolicyClient {

	static public void main (String args[]) {
		
			if(args.length != 2) {
			System.err.println("PolicyClient: " + "PolicyClient <server> <port>");
			return;
		}
		else
			System.out.println("PolicyClient: " + "Running ...");
		
		String psHost = args[0];
		int psPort = (new Integer(args[1])).intValue();
		
		// COPSPepAgent implements a PEP COPS-PR, when created it must
		// be supplied the Client-Type. The Client-Type for us is C_IPSEC,
        // defined by ourselves.
		COPSPepAgent agent = new COPSPepAgent (COPS_def.C_IPSEC);
		try {
			// Get the connection to the PDP
			if (!agent.connect(psHost,psPort)) {
				System.err.println("PolicyClient: " +  "PDP has closed the connection!!");
                COPSError error = agent.getConnectionError();
                System.err.println("PolicyClient: " + error.getDescription());
                return;
			}
		} catch (Exception e) {
			System.err.println("PolicyClient: " + e);
			return;
		}

		// Add a new Request State named "UMUIPSEC-1" and
        // set PolicyDataProcess as its policy data processor.
		try
		{
			IPsecPolicyDataProcess process = new IPsecPolicyDataProcess();
			agent.addRequestState("UMUIPSEC-1", process);
		}
		catch (Exception e) {
			System.err.println("PolicyClient: " + e);
		}
		
		// The PEP Agent manages the Resquest State internally, and it
        // invokes the PolicyDataProcess for processing data
		while (true)
		{
				// Monitoring ...
				try { Thread.sleep(20000); } catch (Exception ee) {};

                    System.out.println("PolicyClient: " + "-----------------------------------------------------------------");

                    // PEP general information
                    System.out.println("PolicyClient: " + "PEP Identifier: " + agent.getPepID());

                    if(agent.getClientType() == COPS_def.C_IPSEC)
                        System.out.println("PolicyClient: " + "IPSEC - ClientType[" + agent.getClientType() + "]");
                    else
                        System.out.println("PolicyClient: " + "Unknowed - ClientType[" + agent.getClientType() + "]");

                   System.out.println("PolicyClient: " + "connected to PDP " + agent.getPDPName() + ":" + agent.getPDPPort());

                    // Information on the PDP connection
                    COPSPepConnection conn = agent.getConnection();
                    System.out.println("PolicyClient:" + "Connection Parameters: ");
                    System.out.println("PolicyClient:" +  "    Acct Timer: " + conn.getAcctTimer());
                    System.out.println("PolicyClient:" +  "    KAlive Timer: " + conn.getKaTimer());
                    System.out.println("PolicyClient:" +  "    Response Timer: " + conn.getResponseTime());
                    System.out.println("PolicyClient:" +  "    LastMessage: " + conn.getLastmessage());

					// Get the list of handles opened by PEP and show their information
					for (Enumeration ee = agent.getReqStateMans().keys() ; ee.hasMoreElements() ;) {
						String handle = (String) ee.nextElement();
						COPSPepReqStateMan man = (COPSPepReqStateMan) agent.getReqStateMans().get(handle);

						System.out.println("PolicyClient:" +  "      ClientHandle[" + handle + "]");

                        switch (man.getStatus()) {
                            case COPSPepReqStateMan.ST_CREATE:
                                System.out.println("PolicyClient:" +  "      Status: " + "Request State created.");
                            break;
                            case COPSPepReqStateMan.ST_INIT:
                                System.out.println("PolicyClient:" +  "      Status: " + "Request sent.");
                            break;
                            case COPSPepReqStateMan.ST_DECS:
                                System.out.println("PolicyClient:" +  "      Status: " + "Decisions received.");
                            break;
                            case COPSPepReqStateMan.ST_REPORT:
                                System.out.println("PolicyClient:" +  "      Status: " + "Report sent.");
                            break;
                            case COPSPepReqStateMan.ST_FINAL:
                                System.out.println("PolicyClient:" +  "      Status: " + "Request State finalized.");
                            break;
                            case COPSPepReqStateMan.ST_DEL:
                                System.out.println("PolicyClient:" +  "      Status: " + "Delete Request State solicited.");
                            break;
                            case COPSPepReqStateMan.ST_NEW:
                                System.out.println("PolicyClient:" +  "      Status: " + "New Request State solicited.");
                            break;
                            case COPSPepReqStateMan.ST_SYNC:
                                System.out.println("PolicyClient:" +  "      Status: " + "SYNC Request received.");
                            break;
                            case COPSPepReqStateMan.ST_SYNCALL:
                                System.out.println("PolicyClient:" +  "      Status: " + "SYNC Completed.");
                            break;
                            case COPSPepReqStateMan.ST_CCONN:
                                System.out.println("PolicyClient:" +  "      Status: " + "Close Connection received.");
                            break;
                            case COPSPepReqStateMan.ST_NOKA:
                                System.out.println("PolicyClient:" +  "      Status: " + "KAlive Time out.");
                            break;
                            case COPSPepReqStateMan.ST_ACCT:
                                System.out.println("PolicyClient:" +  "      Status: " + "ACCT Time out.");
                            break;
                        }

					}
                    System.out.println("PolicyClient:" +  "-----------------------------------------------------------------");
		}
	}
	
}

