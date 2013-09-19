package org.umu.cops.test.ospolicysipclient;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.header.Via;
import org.umu.cops.ospep.COPSPepOSEventListener;
import org.umu.cops.stack.COPSClientSI;
import org.umu.cops.stack.COPSData;
import org.umu.cops.stack.COPSHandle;

import javax.sip.*;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Hashtable;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Vector;

/**
 * This class detects the outsourcing event while acting as
 * as SIP proxy. The event is the reception of a SIP "INVITE"
 * message, meaning that the establishment of a new SIP call
 * is desired. The PEP will use COPS to query the PDP about
 * whether to accept or refuse the call establishment.
 */
public class SIPEventListener extends COPSPepOSEventListener implements SipListener
{
	SipProvider _provider;
	SipStack _stack;
	ListeningPoint _listeningPoint;
	String _localAddress;
	Hashtable _pendingRequests;

    public SIPEventListener() throws Exception
	{
		// For storing incoming SIP requests, until PDP decision has arrived
		_pendingRequests = new Hashtable();

		// Configuration of the SIP stack
		InetAddress localhost = InetAddress.getLocalHost();
		_localAddress = localhost.getHostAddress();
		SipFactory factory = SipFactory.getInstance();
		Properties stackProperties = new Properties();
		stackProperties.setProperty("javax.sip.IP_ADDRESS", _localAddress);
		stackProperties.setProperty("javax.sip.STACK_NAME", localhost.getHostName());
		_stack = factory.createSipStack(stackProperties);
		_listeningPoint = _stack.createListeningPoint(5060, "UDP");
		_provider = _stack.createSipProvider(_listeningPoint);
	}

	/**
	 * Main thread loop. It just registers this object as a SIP listener,
	 * and waits for a keypress. Outsoutcing events will be dispatched
	 * in the meantime by the SIP stack thread.
	 */
	public void run()
	{
		try
		{
			_provider.addSipListener(this);

			System.out.println("Running... press return to exit");
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			console.readLine();

			_provider.removeSipListener(this);
			_stack.deleteListeningPoint(_listeningPoint);
		}
		catch(Exception e)
		{
		}
	}

	/**
	 * Called when the outsourcing event is detected. In this case,
	 * the event is the reception of a SIP request.
	 * @param requestEvent	The received SIP request
	 */
	public synchronized void processRequest(RequestEvent requestEvent)
	{
		Vector clientSIs;

		Request sipRequest = requestEvent.getRequest();
		String method = sipRequest.getMethod();
        System.out.println("Incoming SIP request: " + method);
		System.out.flush();

		// A stateless proxy does not process REGISTER messages
		if(method.startsWith("REGISTER"))
			return;

		// Other non-INVITE requests are just forwarded
		if(! method.startsWith("INVITE"))
		{
			doSipForward(sipRequest);
			return;
		}

		// Builds a clientSI data vector from the event
		clientSIs = new Vector();
		COPSClientSI aClientSI = new COPSClientSI(COPSClientSI.CSI_SIGNALED);
		// TODO: create "eventData" from the actual SIP signaled data
		COPSData eventData = new COPSData("<?xml SIP signaled data>");
		aClientSI.setData(eventData);
		clientSIs.add(aClientSI);

		// Generates a handle for the COPS request
		String strHandle = "OsPepSip-" + sipRequest.getHeader("Call-ID");
		COPSData handleData = new COPSData(strHandle);
		COPSHandle handle = new COPSHandle();
		handle.setId(handleData);

		// Saves the request for forwarding it later
		_pendingRequests.put(strHandle, sipRequest);

		// Wakes up the COPS agent
		_agent.dispatchEvent(handle, clientSIs);
	}

	/**
	 * Called after the decision has been received, to
	 * forward the SIP request that triggered it.
	 * @param handle	The COPS handle for the request
	 */
	public synchronized void forwardPendingRequest(COPSHandle handle)
	{
		Request sipRequest = (Request) _pendingRequests.remove(handle.getId().toString());
		if(sipRequest != null)
			doSipForward(sipRequest);
	}

	/**
	 * Performs actual SIP forwarding as a stateless proxy.
	 * @param sipRequest	SIP request to be forwarded
	 */
	private void doSipForward(Request sipRequest)
	{
		try
		{
			Via myOwnVia = new Via();
			myOwnVia.setBranch(Utils.generateBranchId());
			Host localHost = new Host();
			localHost.setHostAddress(_localAddress);
			HostPort hostPort = new HostPort();
			hostPort.setHost(localHost);
			hostPort.setPort(5060);
			myOwnVia.setSentBy(hostPort);
			sipRequest.addHeader(myOwnVia);
				_provider.sendRequest(sipRequest);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Called by the SIP stack to process incoming reponses.
	 * @param responseEvent	The received SIP response
	 */
	public void processResponse(ResponseEvent responseEvent)
	{
		Response sipResponse = responseEvent.getResponse();
        System.out.println("Incoming SIP response: " + sipResponse.getReasonPhrase());
		System.out.flush();

		// This is not considered an outsourcing event,
		// so the response is promptly forwarded.
		try
		{
			ListIterator viaHeaders = sipResponse.getHeaders("Via");
			Via topmostVia = (Via) viaHeaders.next();
			if(viaHeaders.hasNext())
			{
				String sentBy = topmostVia.getSentBy().getInetAddress().getHostAddress();
				if(_localAddress.equals(sentBy))
				{
					sipResponse.removeHeader("Via");

					// Remaining "Via" headers must be inserted reversely
					while(viaHeaders.hasNext())
						viaHeaders.next();
					while(viaHeaders.previousIndex() > 0)
						sipResponse.addHeader( (Via) viaHeaders.previous() );
					_provider.sendResponse(sipResponse);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Called when the SIP stack detects a timeout.
	 * @param timeoutEvent
	 */
	public void processTimeout(TimeoutEvent timeoutEvent)
	{
		// A stateless proxy does nothing here
	}
}
