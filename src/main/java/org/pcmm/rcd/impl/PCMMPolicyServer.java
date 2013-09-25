/**
 * 
 */
package org.pcmm.rcd.impl;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.pcmm.messages.impl.MessageFactory;
import org.pcmm.obj.MMVersionInfo;
import org.pcmm.rcd.IPCMMPolicyServer;
import org.pcmm.state.IState;
import org.umu.cops.prpdp.COPSPdpConnection;
import org.umu.cops.prpdp.COPSPdpDataProcess;
import org.umu.cops.stack.COPSClientAcceptMsg;
import org.umu.cops.stack.COPSClientCloseMsg;
import org.umu.cops.stack.COPSClientOpenMsg;
import org.umu.cops.stack.COPSException;
import org.umu.cops.stack.COPSHeader;
import org.umu.cops.stack.COPSMsg;

/**
 * 
 * PCMM policy server
 * 
 * @author rhadjamor@gmail.com
 * 
 */
public class PCMMPolicyServer extends AbstractPCMMClient implements
		IPCMMPolicyServer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pcmm.rcd.IPCMMPolicyServer#requestCMTSConnection(java.lang.String)
	 */
	public Socket requestCMTSConnection(String host) {
		try {
			InetAddress address = InetAddress.getByName(host);
			return requestCMTSConnection(address);
		} catch (UnknownHostException e) {
			logger.severe(e.getMessage());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pcmm.rcd.IPCMMPolicyServer#requestCMTSConnection(java.net.InetAddress
	 * )
	 */
	public Socket requestCMTSConnection(InetAddress host) {
		try {
			if (tryConnect(host, IANA_PORT)) {
				boolean endNegociation = false;
				while (!endNegociation) {
					logger.info("waiting for OPN message from CMTS");
					COPSMsg opnMessage = readMessage();
					// Client-Close
					if (opnMessage.getHeader().isAClientClose()) {
						logger.info(((COPSClientCloseMsg) opnMessage)
								.getError().getDescription());
						// close the socket
						disconnect();
						throw new COPSException("CMTS requetsed Client-Close");
					} else // Client-Open
					if (opnMessage.getHeader().isAClientOpen()) {
						logger.info("OPN message received from CMTS");
						COPSClientOpenMsg opn = (COPSClientOpenMsg) opnMessage;
						if (opn.getClientSI() == null)
							throw new COPSException(
									"CMTS shoud have sent MM version info in Client-Open message");
						else {
							// set the version info
							MMVersionInfo vInfo = new MMVersionInfo();
							vInfo.setId(opn.getClientSI().getData());
							setVersionInfo(vInfo);
							logger.info("CMTS sent MMVersion info : major:"
									+ vInfo.getMajorVersionNB() + "  minor:"
									+ vInfo.getMinorVersionNB()); //
							if (getVersionInfo().getMajorVersionNB() == getVersionInfo()
									.getMinorVersionNB()) {
								// send a CC since CMTS has exhausted
								throw new COPSException(
										"CMTS exhausted all protocol selection attempts");
							}
						}
						// send CAT response
						Properties prop = new Properties();
						logger.info("send CAT to the CMTS ");
						COPSMsg catMsg = MessageFactory.getInstance().create(
								COPSHeader.COPS_OP_CAT, prop);
						sendRequest(catMsg);
						// wait for REQ msg
						COPSMsg reqMsg = readMessage();
						// Client-Close
						if (reqMsg.getHeader().isAClientClose()) {
							logger.info(((COPSClientCloseMsg) opnMessage)
									.getError().getDescription());
							// close the socket
							throw new COPSException(
									"CMTS requetsed Client-Close");
						} else // Request
						if (reqMsg.getHeader().isARequest()) {
							logger.info("Received REQ message form CMTS");
							// end connection attempts
							COPSPdpDataProcess processor = null;
							COPSPdpConnection copsPdpConnection = new COPSPdpConnection(
									opn.getPepId(), getSocket(), processor);
							copsPdpConnection
									.setKaTimer(((COPSClientAcceptMsg) catMsg)
											.getKATimer().getTimerVal());
							new Thread(copsPdpConnection).start();
							endNegociation = true;
						} else
							throw new COPSException("Can't understand request");
					} else {
						throw new COPSException("Can't understand request");
					}
				}
			}
			// else raise exception.
		} catch (Exception e) {
			logger.severe(e.getMessage());
			// no need to keep connection.
			disconnect();
			return null;
		}
		return getSocket();
	}

	public PCMMPolicyServer() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.rcd.IPCMMServer#startServer()
	 */
	public void startServer() {
		// TODO not needed yet

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.rcd.IPCMMServer#stopServer()
	 */
	public void stopServer() {
		// TODO not needed yet

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.state.IStateful#recordState()
	 */
	public void recordState() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.state.IStateful#getRecoredState()
	 */
	public IState getRecoredState() {
		return null;
	}

}
