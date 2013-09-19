package org.umu.cops.test.ospolicysipclient;

import org.umu.cops.ospep.COPSPepOSDataProcess;
import org.umu.cops.ospep.COPSPepOSReqStateMan;
import org.umu.cops.stack.*;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Sample DataProcess class for an outsourcing PEP for SIP.
 */
public class SIPPolicyDataProcess extends COPSPepOSDataProcess
{
	SIPEventListener _listener;
	Hashtable _reports;

	public SIPPolicyDataProcess(SIPEventListener aListener)
	{
		_listener = aListener;
		_reports = new Hashtable();
	}

	/**
	 * Applies the decisions received from PDP. Returns <tt>true</tt>
	 * if reports indicate failure, <tt>false</tt> otherwise. 
	 * @param man	The request state manager
	 * @param dMsg	The decision message
	 */
	public boolean setDecisions(COPSPepOSReqStateMan man, COPSDecisionMsg dMsg)
	{
		Hashtable decisionsPerContext = dMsg.getDecisions();
		Enumeration contexts = decisionsPerContext.keys();

        // Loop on contexts
		while(contexts.hasMoreElements())
		{
			COPSContext context = (COPSContext) contexts.nextElement();
			Vector decs = (Vector) decisionsPerContext.get(context);
			Enumeration decsEnum = decs.elements();
			while(decsEnum.hasMoreElements())
			{
				// Get next decision in this contexts
				COPSDecision dec = (COPSDecision) decsEnum.nextElement();

				// TODO:  apply decision

				// Create report
				Vector reportData = new Vector();
				COPSClientSI csi = new COPSClientSI(COPSClientSI.CSI_SIGNALED);
				// TODO: create real report data
				csi.setData(new COPSData("<?xml report data>"));
				reportData.add(csi);
				_reports.put(man, reportData);
			}
		}

		// If decisions were solicited, wake up the SIP proxy for
		// forwarding the original SIP request.
		if((dMsg.getHeader().getFlags() | COPSHeader.COPS_FLAG_SOLICITED) != 0)
			_listener.forwardPendingRequest(dMsg.getClientHandle());

		// TODO: return "false" only if reports are successful; return "true" otherwise
		return false;
	}

    /**
     * Returns Report Data
	 * @param man	The associated Request State Manager
	 */
    public Vector getReportData(COPSPepOSReqStateMan man)
	{
		return (Vector) _reports.get(man);
	}

    /**
     * Returns Client Data
	 * @param man	The associated Request State Manager
     */
    public Vector getClientData(COPSPepOSReqStateMan man)
	{
		return null;// Currently usused
	}

    /**
     * Returns Accouting Data
	 * @param man	The associated Request State Manager
     */
    public Vector getAcctData(COPSPepOSReqStateMan man)
	{
		Vector acctData;

		acctData = new Vector();
		COPSClientSI csi = new COPSClientSI(COPSClientSI.CSI_SIGNALED);
		// TODO: return real accounting data
		csi.setData(new COPSData("<?xml accounting>"));
		acctData.add(csi);
		return acctData;
	}

    /**
     * Called when the connection is closed
	 * @param man	The associated Request State Manager
	 * @param error	Reason
     */
    public void notifyClosedConnection (COPSPepOSReqStateMan man, COPSError error)
	{
        // Currently unused
	}

    /**
     * Called when the keep-alive message is not received
	 * @param man	The associated Request State Manager
     */
    public void notifyNoKAliveReceived (COPSPepOSReqStateMan man)
	{
        // Currently unused
	}

    /**
     * Process a PDP request to close a Request State
	 * @param man	The associated Request State Manager
     */
	public void closeRequestState(COPSPepOSReqStateMan man)
	{
        // Currently unused
	}
}
