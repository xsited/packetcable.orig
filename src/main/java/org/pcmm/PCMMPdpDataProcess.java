/*
 * Copyright (c) 2004 University of Murcia.  All rights reserved.
 * --------------------------------------------------------------
 * For more information, please see <http://www.umu.euro6ix.org/>.
 */

package org.pcmm;

import org.umu.cops.prpdp.COPSPdpDataProcess;
import org.umu.cops.prpdp.COPSPdpReqStateMan;
import org.umu.cops.stack.COPSError;

import java.util.Enumeration;
import java.util.Hashtable;

public class PCMMPdpDataProcess extends COPSPdpDataProcess
{
	private Hashtable installPolicy;
	private Hashtable removePolicy;

	public PCMMPdpDataProcess() {
	}

    /**
     * PDPAgent gets the policies to delete from PEP
     *
     * @param man
     * @return
     */
	public Hashtable getRemovePolicy(COPSPdpReqStateMan man) {
		return removePolicy;
	}

    /**
     * PDPAgent gets the policies to be installed in PEP
     *
     * @param man
     * @return
     */
	public Hashtable getInstallPolicy(COPSPdpReqStateMan man) {
		return installPolicy;
	}

    /**
     * PEP configuration items for sending inside the request
     *
     * @param man
     * @param reqSIs
     */
    public void setClientData(COPSPdpReqStateMan man, Hashtable reqSIs) {

        System.out.println(getClass().getName() + ": " + "Request Info");
        for (Enumeration e = reqSIs.keys() ; e.hasMoreElements() ;) {
            String strprid = (String) e.nextElement();
            String strepd = (String) reqSIs.get(strprid);

            // Check PRID-EPD
            // ....
            System.out.println(getClass().getName() + ": " + "PRID: " + strprid);
            System.out.println(getClass().getName() + ": " + "EPD: " + strepd);
        }

        // Create policies to be deleted
        // ....

        // Create policies to be installed
        String prid = new String("<XPath>");
        String epd = new String("<?xml this is an XML policy>");
        installPolicy.put(prid, epd);
    }

    /**
     * Fail report received
     *
     * @param man
     * @param reportSIs
     */
    public void failReport(COPSPdpReqStateMan man, Hashtable reportSIs) {

        System.out.println(getClass().getName()+ ": " + "Fail Report notified.");

        System.out.println(getClass().getName() + ": " + "Report Info");
        for (Enumeration e = reportSIs.keys() ; e.hasMoreElements() ;) {
            String strprid = (String) e.nextElement();
            String strepd = (String) reportSIs.get(strprid);

            // Check PRID-EPD
            // ....
            System.out.println(getClass().getName()+ ": " + "PRID: " + strprid);
            System.out.println(getClass().getName()+ ": " + "EPD: " + strepd);
        }
    }

    /**
     * Positive report received
     *
     * @param man
     * @param reportSIs
     */
    public void successReport(COPSPdpReqStateMan man, Hashtable reportSIs) {
        System.out.println(getClass().getName()+ ": " + "Success Report notified.");

        System.out.println(getClass().getName()+ ": " + "Report Info");
        for (Enumeration e = reportSIs.keys() ; e.hasMoreElements() ;) {
            String strprid = (String) e.nextElement();
            String strepd = (String) reportSIs.get(strprid);

            // Check PRID-EPD
            // ....
            System.out.println(getClass().getName()+ ": " + "PRID: " + strprid);
            System.out.println(getClass().getName()+ ": " + "EPD: " + strepd);
        }

    }

    /**
     * Accounting report received
     *
     * @param man
     * @param reportSIs
     */
    public void acctReport(COPSPdpReqStateMan man, Hashtable reportSIs) {
        System.out.println(getClass().getName()+ ": " + "Acct Report notified.");

        System.out.println(getClass().getName()+ ": " + "Report Info");
        for (Enumeration e = reportSIs.keys() ; e.hasMoreElements() ;) {
            String strprid = (String) e.nextElement();
            String strepd = (String) reportSIs.get(strprid);

            // Check PRID-EPD
            // ....
            System.out.println(getClass().getName()+ ": " + "PRID: " + strprid);
            System.out.println(getClass().getName()+ ": " + "EPD: " + strepd);
        }
    }

    /**
     * Notifies that an Accounting report is missing
     *
     * @param man
     */
    public void notifyNoAcctReport(COPSPdpReqStateMan man) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Notifies that a KeepAlive message is missing
     *
     * @param man
     */
    public void notifyNoKAliveReceived(COPSPdpReqStateMan man) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * PEP closed the connection
     *
     * @param man
     * @param error
     */
    public void notifyClosedConnection(COPSPdpReqStateMan man, COPSError error) {
        System.out.println(getClass().getName() + ": " + "Connection was closed by PEP");
    }

    /**
     * Delete request state received
     *
     * @param man
     */
    public void notifyDeleteRequestState(COPSPdpReqStateMan man) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Closes request state
     *
     * @param man
     */
    public void closeRequestState(COPSPdpReqStateMan man) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
