/*
 */

/** XXX -tek-This pacakge name is needed to resolve references to protected classes */
package org.pcmm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import org.umu.cops.common.COPSDebug;
import org.umu.cops.ospep.COPSPepException;
import org.umu.cops.prpdp.COPSPdpAgent;
import org.umu.cops.prpdp.COPSPdpConnection;
import org.umu.cops.prpdp.COPSPdpDataProcess;
import org.umu.cops.prpdp.COPSPdpException;
import org.umu.cops.stack.COPSAcctTimer;
import org.umu.cops.stack.COPSClientAcceptMsg;
import org.umu.cops.stack.COPSClientCloseMsg;
import org.umu.cops.stack.COPSClientOpenMsg;
import org.umu.cops.stack.COPSError;
import org.umu.cops.stack.COPSException;
import org.umu.cops.stack.COPSHeader;
import org.umu.cops.stack.COPSKATimer;
import org.umu.cops.stack.COPSMsg;
import org.umu.cops.stack.COPSPepId;
import org.umu.cops.stack.COPSTransceiver;

/**
 * Core PDP agent for provisioning
 */
public class PCMMPdpAgent extends COPSPdpAgent {
	/** Well-known port for PCMM */
	public static final int WELL_KNOWN_PDP_PORT = 3918;

	/**
	 * PEP host name
	 */
	private String psHost;

	/**
	 * PEP port
	 */
	private int psPort;

	private Socket socket;

	/**
	 * Policy data processing object
	 */
	private COPSPdpDataProcess _process;

	/**
	 * Creates a PDP Agent
	 * 
	 * @param clientType
	 *            COPS Client-type
	 * @param process
	 *            Object to perform policy data processing
	 */
	public PCMMPdpAgent(short clientType, COPSPdpDataProcess process) {
		this(clientType, null, WELL_KNOWN_PDP_PORT, process);
	}

	/**
	 * Creates a PDP Agent
	 * 
	 * @param clientType
	 *            COPS Client-type
	 * @param psHost
	 *            Host to connect to
	 * @param psPort
	 *            Port to connect to
	 * @param process
	 *            Object to perform policy data processing
	 */
	public PCMMPdpAgent(short clientType, String psHost, int psPort,
			COPSPdpDataProcess process) {
		super(psPort, clientType, process);
		this._process = process;
		this.psHost = psHost;
	}

	/**
	 * XXX -tek- This is the retooled connect. Not sure if the while forever
	 * loop is needed. Socket accept --> handleClientOpenMsg --> pdpConn.run()
	 * 
	 * Below is new Thread(pdpConn).start(); Does that do it?
	 * 
	 */
	/**
	 * Connects to a PDP
	 * 
	 * @param psHost
	 *            CMTS host name
	 * @param psPort
	 *            CMTS port
	 * @return <tt>true</tt> if PDP accepts the connection; <tt>false</tt>
	 *         otherwise
	 * @throws java.net.UnknownHostException
	 * @throws java.io.IOException
	 * @throws COPSException
	 * @throws COPSPepException
	 */
	public boolean connect(String psHost, int psPort)
			throws UnknownHostException, IOException, COPSException,
			COPSPdpException {

		this.psHost = psHost;
		this.psPort = psPort;
		// Create Socket and send OPN
		InetAddress addr = InetAddress.getByName(psHost);
		try {
			socket = new Socket(addr, psPort);
		} catch (IOException e) {
			COPSDebug.err(getClass().getName(), COPSDebug.ERROR_SOCKET, e);
			return (false);
		}
		COPSDebug.err(getClass().getName(), "PDP Socket Opened");
		// Loop through for Incoming messages

		// server infinite loop
		// while(true)
		{

			// We're waiting for an message
			try {
				COPSDebug.err(getClass().getName(),
						"PDP  COPSTransceiver.receiveMsg ");
				COPSMsg msg = COPSTransceiver.receiveMsg(socket);
				if (msg.getHeader().isAClientOpen()) {
					COPSDebug.err(getClass().getName(),
							"PDP msg.getHeader().isAClientOpen");
					handleClientOpenMsg(socket, msg);
				} else {
					// COPSDebug.err(getClass().getName(),
					// COPSDebug.ERROR_NOEXPECTEDMSG);
					try {
						socket.close();
					} catch (Exception ex) {
					}
					;
				}
			} catch (Exception e) // COPSException, IOException
			{
				// COPSDebug.err(getClass().getName(),
				// COPSDebug.ERROR_EXCEPTION,
				// "(" + socket.getInetAddress() + ":" + socket.getPort() + ")",
				// e);
				try {
					socket.close();
				} catch (Exception ex) {
				}
				;
				return true;
			}
		}
		return false;
	}

	/**
	 * Handles a COPS client-open message
	 * 
	 * @param conn
	 *            Socket to the PEP
	 * @param msg
	 *            <tt>COPSMsg</tt> holding the client-open message
	 * @throws COPSException
	 * @throws IOException
	 */
	private void handleClientOpenMsg(Socket conn, COPSMsg msg)
			throws COPSException, IOException {
		COPSClientOpenMsg cMsg = (COPSClientOpenMsg) msg;
		COPSPepId pepId = cMsg.getPepId();

		// Validate Client Type
		if (msg.getHeader().getClientType() != getClientType()) {
			// Unsupported client type
			COPSHeader cHdr = new COPSHeader(COPSHeader.COPS_OP_CC, msg
					.getHeader().getClientType());
			COPSError err = new COPSError(
					COPSError.COPS_ERR_UNSUPPORTED_CLIENT_TYPE, (short) 0);
			COPSClientCloseMsg closeMsg = new COPSClientCloseMsg();
			closeMsg.add(cHdr);
			closeMsg.add(err);
			try {
				closeMsg.writeData(conn);
			} catch (IOException unae) {
			}

			throw new COPSException("Unsupported client type");
		}

		// PEPId is mandatory
		if (pepId == null) {
			// Mandatory COPS object missing
			COPSHeader cHdr = new COPSHeader(COPSHeader.COPS_OP_CC, msg
					.getHeader().getClientType());
			COPSError err = new COPSError(
					COPSError.COPS_ERR_MANDATORY_OBJECT_MISSING, (short) 0);
			COPSClientCloseMsg closeMsg = new COPSClientCloseMsg();
			closeMsg.add(cHdr);
			closeMsg.add(err);
			try {
				closeMsg.writeData(conn);
			} catch (IOException unae) {
			}

			throw new COPSException("Mandatory COPS object missing (PEPId)");
		}

		// Support
		if ((cMsg.getClientSI() != null) ) {
		    System.out.println("ClientSI Version");
		    // cMsg.getClientSI().dump(System.out);
		    // XXX - tek Make object
		    byte[] arr = cMsg.getClientSI().getData().getData();
		    ByteBuffer bb = ByteBuffer.wrap(arr); // Wrapper around underlying byte[].
		    ShortBuffer sb = bb.asShortBuffer(); // Wrapper around ByteBuffer.

		    // Now traverse ShortBuffer to obtain each short.
		    short s1 = sb.get();
		    short s2 = sb.get(); // etc.
		    short s3 = sb.get(); // etc.
		    short s4 = sb.get(); // etc.

		    System.out.println("Length=  " + s1 + " arr[1]= " + " s-num=  " + arr[2] + " s-type= " + arr[3]);
		    System.out.println("Version Major : " + s3 + " Minor : " + s4);
			
		} else {
		// || (cMsg.getPdpAddress() != null)
		// if ((cMsg.getPdpAddress() != null) || (cMsg.getIntegrity() != null)) {

			// Unsupported objects
			COPSHeader cHdr = new COPSHeader(COPSHeader.COPS_OP_CC, msg
					.getHeader().getClientType());
			COPSError err = new COPSError(COPSError.COPS_ERR_UNKNOWN_OBJECT,
					(short) 0);
			COPSClientCloseMsg closeMsg = new COPSClientCloseMsg();
			closeMsg.add(cHdr);
			closeMsg.add(err);
			try {
				closeMsg.writeData(conn);
			} catch (IOException unae) {
			}

			throw new COPSException("Unsupported objects (PdpAddress, Integrity)");
		}
		/*
		*/

		// Connection accepted
		COPSHeader ahdr = new COPSHeader(COPSHeader.COPS_OP_CAT, msg
				.getHeader().getClientType());
		COPSKATimer katimer = new COPSKATimer(getKaTimer());
		COPSAcctTimer acctTimer = new COPSAcctTimer(getAcctTimer());
		COPSClientAcceptMsg acceptMsg = new COPSClientAcceptMsg();
		acceptMsg.add(ahdr);
		acceptMsg.add(katimer);
		if (getAcctTimer() != 0)
			acceptMsg.add(acctTimer);
		acceptMsg.writeData(conn);

		COPSPdpConnection pdpConn = new COPSPdpConnection(pepId, conn, _process);
		pdpConn.setKaTimer(getKaTimer());
		if (getAcctTimer() != 0)
			pdpConn.setAccTimer(getAcctTimer());
		COPSDebug.err(getClass().getName(), "PDP Thread(pdpConn).start");
		new Thread(pdpConn).start();
		getConnectionMap().put(pepId.getData().str(), pdpConn);
	}

	/**
	 * @return the _psHost
	 */
	public String getPsHost() {
		return psHost;
	}

	/**
	 * @param _psHost
	 *            the _psHost to set
	 */
	public void setPsHost(String _psHost) {
		this.psHost = _psHost;
	}

	/**
	 * @return the _psPort
	 */
	public int getPsPort() {
		return psPort;
	}

	/**
	 * @param _psPort
	 *            the _psPort to set
	 */
	public void setPsPort(int _psPort) {
		this.psPort = _psPort;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket
	 *            the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @return the _process
	 */
	public COPSPdpDataProcess getProcess() {
		return _process;
	}

	/**
	 * @param _process
	 *            the _process to set
	 */
	public void setProcess(COPSPdpDataProcess _process) {
		this._process = _process;
	}

}
