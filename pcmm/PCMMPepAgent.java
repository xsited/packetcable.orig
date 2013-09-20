/*
 */

package org.umu.cops.prpep;

import org.umu.cops.stack.*;
import org.umu.cops.common.COPSDebug;
import org.umu.cops.prpep.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

/**
 * This is a provisioning COPS PEP. Responsible for making
 * connection to the PDP and maintaining it
 */
public class PCMMPepAgent {	
	/** Well-known port for COPS */
	public static final int WELL_KNOWN_PDP_PORT = 3918;
	/** Default keep-alive timer value (secs) */
	public static final short KA_TIMER_VALUE = 30;
	/** Default accounting timer value (secs) */
	public static final short ACCT_TIMER_VALUE = 0;



	/**
		PEP's Identifier
	 */
	private String _pepID;
	

	/**
	 	PEP's client-type
	 */
	private short _clientType;	
	/**
		PDP host IP
	 */
	private ServerSocket _serverSocket;
	
	/**
		PDP host port
	 */
	private int _serverPort;



	/**
	 	PDP host name
	 */
	private String _psHost;

	/**
	 	PDP port
	 */
	private int _psPort;
	
	/**
	 	PEP-PDP connection manager
	 */
	private COPSPepConnection _conn;
	
	/**
	 	COPS error returned by PDP
	 */
	private COPSError _error;
	
	/**
	 * Creates a PEP agent
	 * @param    pepID              PEP-ID
	 * @param    clientType         Client-type
	 */
	public PCMMPepAgent(String pepID, short clientType) {
		_pepID = pepID;
		_clientType = clientType;
	}

	/**
	 * Creates a PEP agent with a PEP-ID equal to "noname"
	 * @param    clientType         Client-type
	 */
	public PCMMPepAgent(short clientType) {
	
		// PEPId
		try {
			_pepID = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			_pepID = "noname";
		}
			
		_clientType = clientType;
	}
	
	/**
	 * Gets the identifier of the PEP
	 * @return	PEP-ID
	 */
	public String getPepID() {
		return _pepID;
	}

	/**
	 * Gets the COPS client-type
	 * @return	PEP's client-type
	 */
	public short getClientType() {
		return _clientType;
	}
	
    /**
	 * Gets PDP host name
	 * @return	PDP host name
	 */
	public String getPDPName() {
		return _psHost;
	}
	
	/**
	 * Gets the port of the PDP
	 * @return	PDP port
	 */
	public int getPDPPort() {
		return _psPort;
	}


	/**
	 * Gets the connection manager
	 * @return	PEP-PDP connection manager object
	 */
	public COPSPepConnection getConnection () {
		return (_conn);
	}
	
	/**
	 * Gets the COPS error returned by the PDP
	 * @return   <tt>COPSError</tt> returned by PDP
	 */
	public COPSError getConnectionError()	{
		return _error;
	}
	
	/**
	 * Disconnects from the PDP
	 * @param error	Reason
	 * @throws COPSException
	 * @throws IOException
	 */
	public void disconnect(COPSError error)
		throws COPSException, IOException {

		COPSHeader cHdr = new COPSHeader(COPSHeader.COPS_OP_CC, _clientType);
		COPSClientCloseMsg closeMsg = new COPSClientCloseMsg();
		closeMsg.add(cHdr);
		if (error != null)
			closeMsg.add(error);
			
		closeMsg.writeData(_conn.getSocket());
		_conn.close();
		_conn = null;
	}
	
	/**
	 * Adds a request state to the connection manager.
	 * @return	The newly created connection manager
	 * @throws COPSPepException
	 * @throws COPSException
	 */
	public COPSPepReqStateMan addRequestState (String handle, COPSPepDataProcess process)
		throws COPSPepException, COPSException {
		if (_conn != null) {
			return _conn.addRequestState(handle, process);
		}
		return null;
	}

	
	/**
	 * Queries the connection manager to delete a request state
	 * @param man	Request state manager
	 * @throws COPSPepException
	 * @throws COPSException
	 */
	public void deleteRequestState (COPSPepReqStateMan man)
		throws COPSPepException, COPSException {
		if (_conn != null)
			_conn.deleteRequestState(man);
	}
	
	/**
	 * Gets all the request state managers
	 * @return	A <tt>Hashtable</tt> holding all active request state managers
	 */
	public Hashtable getReqStateMans() {
		if (_conn != null)
			return _conn.getReqStateMans();
		return null;
	}	
	/**
	 * Runs the PEP process
	 * XXX - not sure of the exception throwing
	 */
	public void run() 
		throws UnknownHostException, COPSPepException, COPSException {
	    try {
		_serverSocket = new ServerSocket (_serverPort);
                //Loop through for Incoming messages

                // server infinite loop
            while(true) {

                // Wait for an incoming connection from a PEP
                Socket socket = _serverSocket.accept();

                COPSDebug.err(getClass().getName(),"New connection accepted " +
                          socket.getInetAddress() +
                          ":" + socket.getPort());

		processConnection(socket);
	 	/** XXX - processConnection handles the open request from PEP 
                 *	And a thread is created for conn = new COPSPepConnection(_clientType, socket);
		 *      the main processing loop for PEP
		 */
		

            }
            }
            catch (IOException e) {
                COPSDebug.err(getClass().getName(), COPSDebug.ERROR_SOCKET, e);
                return;
            }
	}


	
	/**
	 * Establish connection to PDP's IP address
	 *
	 * <Client-Open> ::= <Common Header>
	 *					<PEPID>
	 *					[<ClientSI>]
	 *					[<LastPDPAddr>]
	 *					[<Integrity>]
	 *
	 * Not support [<ClientSI>], [<LastPDPAddr>], [<Integrity>]
	 *
	 * <Client-Accept> ::= <Common Header>
	 *						<KA Timer>
	 *						[<ACCT Timer>]
	 *						[<Integrity>]
	 *
	 * Not send [<Integrity>]
	 *
	 * <Client-Close> ::= <Common Header>
	 *						<Error>
	 *						[<PDPRedirAddr>]
	 *						[<Integrity>]
	 *
	 * Not send [<PDPRedirAddr>], [<Integrity>]
	 *
	 * @throws   UnknownHostException
	 * @throws   IOException
	 * @throws   COPSException
	 * @throws   COPSPepException
	 *
	 */
	private COPSPepConnection processConnection(Socket socket)
		throws UnknownHostException, IOException, COPSException, COPSPepException
	{
		// Build OPN
		COPSHeader hdr = new COPSHeader(COPSHeader.COPS_OP_OPN, _clientType);
		
		COPSPepId pepId = new COPSPepId();
		COPSData d = new COPSData(_pepID);
		pepId.setData(d);
	
		COPSClientOpenMsg msg = new COPSClientOpenMsg();
		msg.add(hdr);
		msg.add(pepId);
	
		// Create Socket and send OPN
		/*
		InetAddress addr = InetAddress.getByName(psHost);
		Socket socket = new Socket(addr,psPort);
		*/
		msg.writeData(socket);
					
		// Receive the response
		COPSMsg recvmsg = COPSTransceiver.receiveMsg(socket);

		if(recvmsg.getHeader().isAClientAccept())
		{
			COPSClientAcceptMsg cMsg = (COPSClientAcceptMsg) recvmsg;
			
			// Support
			if (cMsg.getIntegrity() != null) {
				throw new COPSPepException("Unsupported object (Integrity)");
			}
			
			// Mandatory KATimer
			COPSKATimer kt = cMsg.getKATimer();
			if (kt == null)
				throw new COPSPepException ("Mandatory COPS object missing (KA Timer)");
			short _kaTimeVal = kt.getTimerVal();
			
			// ACTimer
			COPSAcctTimer at = cMsg.getAcctTimer();
			short _acctTimer = 0;
			if (at != null)
				_acctTimer = at.getTimerVal();
			
			// Create the connection manager
			COPSPepConnection conn = new COPSPepConnection(_clientType, socket);
			conn.setKaTimer(_kaTimeVal);
			conn.setAcctTimer(_acctTimer);
			new Thread(conn).start();

			return conn;
		}
		else if(recvmsg.getHeader().isAClientClose())
		{
			COPSClientCloseMsg cMsg = (COPSClientCloseMsg) recvmsg;
			_error = cMsg.getError();
			socket.close();
			return null;
		}
		else // messages of other types are not expected
		{
			throw new COPSPepException("Message not expected. Closing connection for " + socket.toString());
		}
	}
}



