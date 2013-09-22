package org.umu.cops.test.ospolicysipserver;

import org.umu.cops.ospdp.COPSPdpOSDataProcess;
import org.umu.cops.ospdp.COPSPdpOSReqStateMan;
import org.umu.cops.stack.COPSClientSI;
import org.umu.cops.stack.COPSData;
import org.umu.cops.stack.COPSDecision;
import org.umu.cops.stack.COPSError;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Sample DataProcess class for an outsourcing PDP for SIP.
 */
public class SIPPolicyDataProcess extends COPSPdpOSDataProcess
{
	private Vector installPolicy;
	private Vector removePolicy;

	public SIPPolicyDataProcess()
	{
		removePolicy = new Vector();
		installPolicy = new Vector();
	}

    /**
     * Returns the policies to be uninstalled.
     * @param man	The associated Request State Manager
     */
	public Vector getRemovePolicy(COPSPdpOSReqStateMan man)
	{
		return removePolicy;
	}

    /**
     * Returns the policies to be installed.
     * @param man	The associated Request State Manager
	 */
	public Vector getInstallPolicy(COPSPdpOSReqStateMan man)
	{
		return installPolicy;
	}

    /**
     * Retrieves the needed policies, based on the received client data.
     * @param man	The associated Request State Manager
     * @param reqSIs	The client data sent by the PEP
     */
    public void setClientData(COPSPdpOSReqStateMan man, Vector reqSIs)
	{
        System.out.println(getClass().getName() + ": " + "Request Info:");
		for(int i = 0; i < reqSIs.size(); i++)
			System.out.println(((COPSClientSI) reqSIs.elementAt(i)).getData().toString());

		// TODO: perform actual policy lookup and decision making process

		COPSDecision inst = new COPSDecision(COPSDecision.DEC_STATELESS);
		inst.setData(new COPSData("<?xml install decisions>"));
		installPolicy.add(inst);

		COPSDecision rem = new COPSDecision(COPSDecision.DEC_STATELESS);
		rem.setData(new COPSData("<?xml remove decisions>"));
		removePolicy.add(rem);
    }

    /**
     * Called upon the reception of a failure report
     * @param man	The associated Request State Manager
     * @param reportSIs	Report data sent by PEP
     */
    public void failReport(COPSPdpOSReqStateMan man, Vector reportSIs)
	{
        System.out.println(getClass().getName()+ ": " + "Fail Report notified.");

        Enumeration reportSIsEnum = reportSIs.elements();
		while(reportSIsEnum.hasMoreElements())
			System.out.println(((COPSClientSI) reportSIsEnum.nextElement()).toString());
    }

    /**
     * Called upon the reception of a success report
     * @param man	The associated Request State Manager
     * @param reportSIs	Report data sent by PEP
     */
    public void successReport(COPSPdpOSReqStateMan man, Vector reportSIs)
	{
        System.out.println(getClass().getName()+ ": " + "Success Report notified.");

        Enumeration reportSIsEnum = reportSIs.elements();
		while(reportSIsEnum.hasMoreElements())
			System.out.println(((COPSClientSI) reportSIsEnum.nextElement()).toString());
    }

    /**
     * Called upon the reception of an accounting report
     * @param man	The associated Request State Manager
     * @param reportSIs	Report data sent by PEP
     */
    public void acctReport(COPSPdpOSReqStateMan man, Vector reportSIs)
	{
        System.out.println(getClass().getName()+ ": " + "Acct Report notified.");

        Enumeration reportSIsEnum = reportSIs.elements();
		while(reportSIsEnum.hasMoreElements())
			System.out.println(((COPSClientSI) reportSIsEnum.nextElement()).toString());
    }

    /**
     * Called when no accounting report has been received.
     * @param man	The associated Request State Manager
     */
    public void notifyNoAcctReport(COPSPdpOSReqStateMan man)
	{
        // Currently unused
    }

    /**
     * Called when no KA has been received within the
	 * negotiated time interval.
     * @param man	The associated Request State Manager
     */
    public void notifyNoKAliveReceived(COPSPdpOSReqStateMan man)
	{
        // Currently unused
    }

    /**
     * Called when PEP requests that the connection be closed.
     * @param man	The associated Request State Manager
     * @param error	COPSError object
     */
    public void notifyClosedConnection(COPSPdpOSReqStateMan man, COPSError error)
	{
        System.out.println(getClass().getName() + ": " + "Connection closed!!!");
    }

    /**
     * Called when PEP requests that a request state be deleted.
     * @param man	The associated Request State Manager
     */
    public void notifyDeleteRequestState(COPSPdpOSReqStateMan man)
	{
        // Currently unused
    }

    /**
     * Closes a request state
     * @param man	The associated Request State Manager
     */
    public void closeRequestState(COPSPdpOSReqStateMan man)
	{
        // Currently unused
    }
}
