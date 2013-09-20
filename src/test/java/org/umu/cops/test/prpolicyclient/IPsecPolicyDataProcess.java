/*
 * Copyright (c) 2004 University of Murcia.  All rights reserved.
 * --------------------------------------------------------------
 * For more information, please see <http://www.umu.euro6ix.org/>.
 */

package org.umu.cops.test.prpolicyclient;

import org.umu.cops.prpep.COPSPepDataProcess;
import org.umu.cops.prpep.COPSPepReqStateMan;
import org.umu.cops.stack.COPSError;

import java.util.Enumeration;
import java.util.Hashtable;

public class IPsecPolicyDataProcess extends COPSPepDataProcess
{
	private Hashtable reportClientSI;
    private Hashtable clientSIs;

	public IPsecPolicyDataProcess() {
        clientSIs = new Hashtable(40);
		reportClientSI = new Hashtable(40);
	}

    /**
     * Decisions sent by the PDP after a request
     *
     * @param removeDecs
     * @param installDecs
     * @param errorDecs
     */
	public void setDecisions(COPSPepReqStateMan man, Hashtable removeDecs, Hashtable installDecs, Hashtable errorDecs) {

		System.out.println(getClass().getName() + ": " + "Remove Decisions");
		for (Enumeration e = removeDecs.keys() ; e.hasMoreElements() ;) {
			String strprid = (String) e.nextElement();
			String strepd = (String) removeDecs.get(strprid);
	
			// Check PRID-EPD
			// ....
			System.out.println(getClass().getName() + ": " + "PRID: " + strprid);
			System.out.println(getClass().getName() + ": " + "EPD: " + strepd);
		}

		System.out.println(getClass().getName() + ": " + "Install Decisions");
		for (Enumeration e = installDecs.keys() ; e.hasMoreElements() ;) {
			String strprid = (String) e.nextElement();
			String strepd = (String) installDecs.get(strprid);

			// Check PRID-EPD
			// ....
			System.out.println(getClass().getName() + ": " + "PRID: " + strprid);
			System.out.println(getClass().getName() + ": " + "EPD: " + strepd);
		}
		
		// Configure PEP
		// ....
		
		// Create report
		// ....
	}

    /**
     * Invoked by PEPAgent to determine if an error report
     * is to be sent to the PDP.
     *
     * @return
     */
	public boolean isFailReport(COPSPepReqStateMan man) {
		return false;
	}

    /**
     * Invoked by PEPAgent to retrieve PEP's internal information
     * that will be sent in the initial request to the PDP
     *
     * @return
     */
    public Hashtable getClientData(COPSPepReqStateMan man) {

		// .... get the ClientSIs ...
		String prid = new String("<XPath>");
		String epd = new String("<?xml this is my role>");
		clientSIs.put(prid, epd);

		return clientSIs;
	}

    /**
     * Invoked by PEPAgent to retrieve error/warning/ok information
     * to be sent in a report to the PDP.
     *
     * @return
     */
    public Hashtable getReportData(COPSPepReqStateMan man) {
        reportClientSI = new Hashtable(40);
        // .... get the ClientSIs ...
        String prid = new String("<XPath>");
        String epd = new String("<?xml this is a Report>");
        reportClientSI.put(prid, epd);

        return reportClientSI;
    }

    /**
     * Invoked by PEPAgent to retrieve Accounting and Monitoring information
     * to be sent in a report to the PDP.
     *
     * @return
     */
    public Hashtable getAcctData(COPSPepReqStateMan man) {
        // PEP's Accounting and Monotoring information
        // Unimplemented yet
        reportClientSI = new Hashtable(40);
        // .... get the ClientSIs ...
        String prid = new String("<XPath>");
        String epd = new String("<?xml this is an Acct Report>");
        reportClientSI.put(prid, epd);

        return reportClientSI;
    }

    /**
     * Notifies the PEPAgent that the connection was closed by the PDP.
     *
     * @param error
     */
    public void notifyClosedConnection(COPSPepReqStateMan man, COPSError error) {
        // PDP closed the connection
        // Currently nothing is done, but it'd be nice to
        // try to open a new connection with the PDP

        System.out.println(getClass().getName() + ": " + "Connection was closed by PDP");
    }

    /**
     * Notifies the PEPAgent when a KeepAlive message was not received.
     */
    public void notifyNoKAliveReceived(COPSPepReqStateMan man) {
        // No "I'm alive" message has been received from the PDP.
        // Currently nothing is done, but it'd be nice to
        // initialize a new Request State
    }

    /**
     * Decision sent by the PDP to close the RequestState.
     *
     * @param man
     */
    public void closeRequestState(COPSPepReqStateMan man) {
        // Message from the PDP to close the Request State
        // Currently nothing is done, but it'd be nice to do:
        // agent.deleteRequestState(man)
        // and then create a new request state if needed
    }

    /**
     * Decision from the PDP to create a new RequestState.
     *
     * @param man
     */
    public void newRequestState(COPSPepReqStateMan man) {
        // Message from the PDP to create a new Request State
        // Currently nothing is done, but it'd be nice to do:
        // agent.addRequestState("XXXXXXX", this)
        // and delete any old request state that we no longer need
    }
}

