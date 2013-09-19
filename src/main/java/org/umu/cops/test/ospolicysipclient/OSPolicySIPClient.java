package org.umu.cops.test.ospolicysipclient;

/**
 * Sample outsourcing PEP for SIP
 */

import org.umu.cops.common.COPS_def;
import org.umu.cops.ospep.COPSPepOSAgent;
import org.umu.cops.ospep.COPSPepOSConnection;
import org.umu.cops.ospep.COPSPepOSReqStateMan;
import org.umu.cops.stack.COPSError;

import java.util.Enumeration;

public class OSPolicySIPClient
{
	public static void main(String args[])
	{
		if(args.length != 2)
		{
			System.err.println("OSPolicySIPClient: " + "OSPolicySIPClient <server> <port>");
			return;
		}
		else
			System.out.println("OSPolicySIPClient: " + "Running ...");

		String psHost = args[0];
		int psPort = (new Integer(args[1])).intValue();

		COPSPepOSAgent agent = new COPSPepOSAgent (COPS_def.C_SIP);
		try
		{
			System.out.println("Connecting to " + psHost + "...");
			// Connect to PDP
			if (!agent.connect(psHost,psPort))
			{
				System.err.println("OSPolicySIPClient: " +  "PDP has closed the connection!!");
                COPSError error = agent.getConnectionError();
                System.err.println("OSPolicySIPClient: " + error.getDescription());
                return;
			}
		}
		catch (Exception e)
		{
			System.err.println("OSPolicySIPClient: " + e);
			return;
		}

		System.out.println("Connected to PDP");

		try
		{
			SIPEventListener listener = new SIPEventListener();
			agent.setDataProcess(new SIPPolicyDataProcess(listener));
			listener.setAgent(agent);
			listener.start();
		}
		catch (Exception e)
		{
			System.err.println("OSPolicySIPClient: " + e);
		}

		while (true) // Monitoring loop
		{
			try { Thread.sleep(20000); } catch (Exception ee) {};

            System.out.println("OSPolicySIPClient: " + "-----------------------------------------------------------------");

            // General info about the PEP
            System.out.println("OSPolicySIPClient: " + "PEP Identifier: " + agent.getPepID());

            if(agent.getClientType() == COPS_def.C_SIP)
            	System.out.println("OSPolicySIPClient: " + "SIP - ClientType[" + agent.getClientType() + "]");
                    else
                System.out.println("OSPolicySIPClient: " + "Unknown - ClientType[" + agent.getClientType() + "]");

            System.out.println("OSPolicySIPClient: " + "connected to PDP " + agent.getPDPName() + ":" + agent.getPDPPort());

                    // Info about the connection to the PDP
                    COPSPepOSConnection conn = agent.getConnection();
                    System.out.println("OSPolicySIPClient:" + "Connection Parameters: ");
                    System.out.println("OSPolicySIPClient:" +  "    Acct Timer: " + conn.getAcctTimer());
                    System.out.println("OSPolicySIPClient:" +  "    KAlive Timer: " + conn.getKaTimer());
                    System.out.println("OSPolicySIPClient:" +  "    Response Timer: " + conn.getResponseTime());
                    System.out.println("OSPolicySIPClient:" +  "    LastMessage: " + conn.getLastmessage());

					// Info about each COPS handle active
					for (Enumeration ee = agent.getReqStateMans().keys() ; ee.hasMoreElements() ;) {
						String handle = (String) ee.nextElement();
						COPSPepOSReqStateMan man = (COPSPepOSReqStateMan) agent.getReqStateMans().get(handle);

						System.out.println("OSPolicySIPClient:" +  "      ClientHandle[" + handle + "]");

                        switch (man.getStatus()) {
                            case COPSPepOSReqStateMan.ST_CREATE:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "Request State created.");
                            break;
                            case COPSPepOSReqStateMan.ST_INIT:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "Request sent.");
                            break;
                            case COPSPepOSReqStateMan.ST_DECS:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "Decisions received.");
                            break;
                            case COPSPepOSReqStateMan.ST_REPORT:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "Report sent.");
                            break;
                            case COPSPepOSReqStateMan.ST_FINAL:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "Request State finalized.");
                            break;
                            case COPSPepOSReqStateMan.ST_DEL:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "Delete Request State solicited.");
                            break;
                            case COPSPepOSReqStateMan.ST_NEW:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "New Request State solicited.");
                            break;
                            case COPSPepOSReqStateMan.ST_SYNC:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "SYNC Request received.");
                            break;
                            case COPSPepOSReqStateMan.ST_SYNCALL:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "SYNC Completed.");
                            break;
                            case COPSPepOSReqStateMan.ST_CCONN:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "Close Connection received.");
                            break;
                            case COPSPepOSReqStateMan.ST_NOKA:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "KAlive Time out.");
                            break;
                            case COPSPepOSReqStateMan.ST_ACCT:
                                System.out.println("OSPolicySIPClient:" +  "      Status: " + "ACCT Time out.");
                            break;
                        }

					}
                    System.out.println("OSPolicySIPClient:" +  "-----------------------------------------------------------------");
		}
	}
}
