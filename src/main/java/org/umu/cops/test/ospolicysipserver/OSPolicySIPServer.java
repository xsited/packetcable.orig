package org.umu.cops.test.ospolicysipserver;

import org.umu.cops.common.COPS_def;
import org.umu.cops.ospdp.COPSPdpOSAgent;
import org.umu.cops.ospdp.COPSPdpOSConnection;
import org.umu.cops.ospdp.COPSPdpOSReqStateMan;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 * Sample outsourcing server for SIP.
 */
public class OSPolicySIPServer
{
	public static void main(String[] args)
	{
		try
		{
			System.out.println("PolicyServer: " + "Ex. START!!");

            SIPPolicyDataProcess process = new SIPPolicyDataProcess();
			COPSPdpOSAgent agent = new COPSPdpOSAgent(COPS_def.C_SIP, process);
            agent.setAcctTimer((short) 60);

			// Agent ready for accepting connections
			agent.start();

			while(true) // Monitoring loop
			{

                System.out.println("PolicyServer: " + "-----------------------------------------------------------------");

                if(agent.getClientType() == COPS_def.C_SIP)
                    System.out.println("PolicyServer: " + "SIP - ClientType[" + agent.getClientType() + "]");
                else
                    System.out.println("PolicyServer: " + "Unknown - ClientType[" + agent.getClientType() + "]");

                System.out.println("PolicyServer: " + "    Acct Timer: " + agent.getAcctTimer());
                System.out.println("PolicyServer: " + "    KAlive Timer: " + agent.getKaTimer());

				// Gets a list of all the connected PEPs
				for (Enumeration e = agent.getConnectionMap().keys() ; e.hasMoreElements() ;)
				{
					String pepId = (String) e.nextElement();
					COPSPdpOSConnection pdpConn = (COPSPdpOSConnection) agent.getConnectionMap().get(pepId);

					System.out.println("PolicyServer: " + "Connection [" + pepId + "], parameters: ");
                    System.out.println("PolicyServer: " + "    Last KAlive: " + pdpConn.getLastKAlive().toString());
                    System.out.println("PolicyServer: " + "    Last Message: " + pdpConn.getLastMessage());
                    System.out.println("PolicyServer: " + "    Acct Timer: " + pdpConn.getAcctTimer());
                    System.out.println("PolicyServer: " + "    KAlive Timer: " + pdpConn.getKaTimer());

					// Info about each COPS handle active
					for (Enumeration ee = pdpConn.getReqStateMans().keys() ; ee.hasMoreElements() ;)
					{
						String handle = (String) ee.nextElement();
						COPSPdpOSReqStateMan man = (COPSPdpOSReqStateMan) pdpConn.getReqStateMans().get(handle);

						System.out.println("PolicyServer: " + "      ClientHandle[" + handle + "]");

                        switch (man.getStatus()) {
                            case COPSPdpOSReqStateMan.ST_CREATE:
                                System.out.println("PolicyServer: " + "      Status: " + "Request State created.");
                            break;
                            case COPSPdpOSReqStateMan.ST_INIT:
                                System.out.println("PolicyServer: " + "      Status: " + "Request sent.");
                            break;
                            case COPSPdpOSReqStateMan.ST_DECS:
                                System.out.println("PolicyServer: " + "      Status: " + "Decisions received.");
                            break;
                            case COPSPdpOSReqStateMan.ST_REPORT:
                                System.out.println("PolicyServer: " + "      Status: " + "Report sent.");
                            break;
                            case COPSPdpOSReqStateMan.ST_FINAL:
                                System.out.println("PolicyServer: " + "      Status: " + "Request State finalized.");
                            break;
                            case COPSPdpOSReqStateMan.ST_DEL:
                                System.out.println("PolicyServer: " + "      Status: " + "Delete Request State solicited.");
                            break;
                            case COPSPdpOSReqStateMan.ST_NEW:
                                System.out.println("PolicyServer: " + "      Status: " + "New Request State solicited.");
                            break;
                            case COPSPdpOSReqStateMan.ST_SYNC:
                                System.out.println("PolicyServer: " + "      Status: " + "SYNC Request received.");
                            break;
                            case COPSPdpOSReqStateMan.ST_SYNCALL:
                                System.out.println("PolicyServer: " + "      Status: " + "SYNC Completed.");
                            break;
                            case COPSPdpOSReqStateMan.ST_CCONN:
                                System.out.println("PolicyServer: " + "      Status: " + "Close Connection received.");
                            break;
                            case COPSPdpOSReqStateMan.ST_NOKA:
                                System.out.println("PolicyServer: " + "      Status: " + "KAlive Time out.");
                            break;
                            case COPSPdpOSReqStateMan.ST_ACCT:
                                System.out.println("PolicyServer: " + "      Status: " + "ACCT Time out.");
                            break;
                        }

                        // Keyboard input
                        System.out.println("PolicyServer: " + "    Sync? (y/n) ");
                        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                        String inputLine = keyboard.readLine(); // read a line
                        if (inputLine.indexOf('y') != -1) {
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
