/**
 * 
 */
package org.pcmm.rcd.impl;

import java.net.Socket;
import java.util.Properties;

import org.pcmm.messages.impl.MessageFactory;
import org.pcmm.rcd.ICMTS;
import org.umu.cops.prpep.COPSPepConnection;
import org.umu.cops.prpep.COPSPepException;
import org.umu.cops.stack.COPSAcctTimer;
import org.umu.cops.stack.COPSClientAcceptMsg;
import org.umu.cops.stack.COPSClientCloseMsg;
import org.umu.cops.stack.COPSHeader;
import org.umu.cops.stack.COPSKATimer;
import org.umu.cops.stack.COPSMsg;

/**
 * @author rhadjamor@gmail.com
 * 
 */
public class CMTS extends AbstractPCMMServer implements ICMTS {

	public CMTS() {
		super();
	}

	@Override
	protected IPCMMClientHandler getPCMMClientHandler(final Socket socket) {
		return new AbstractPCMMClientHandler(socket) {

			public void run() {
				try {
					// send OPN message

					// set the major version info and minor version info to
					// default (5,0)
					logger.info("Send OPN message to the PS");
					sendRequest(MessageFactory.getInstance().create(
							COPSHeader.COPS_OP_OPN, new Properties()));
					// wait for CAT
					COPSMsg recvMsg = readMessage();

					if (recvMsg.getHeader().isAClientClose()) {
						COPSClientCloseMsg cMsg = (COPSClientCloseMsg) recvMsg;
						logger.info("PS requested Client-Close"
								+ cMsg.getError().getDescription());
						// send a CC message and close the socket
						disconnect();
						return;
					}
					if (recvMsg.getHeader().isAClientAccept()) {
						logger.info("received Client-Accept from PS");
						COPSClientAcceptMsg cMsg = (COPSClientAcceptMsg) recvMsg;
						// Support
						if (cMsg.getIntegrity() != null) {
							throw new COPSPepException(
									"Unsupported object (Integrity)");
						}

						// Mandatory KATimer
						COPSKATimer kt = cMsg.getKATimer();
						if (kt == null)
							throw new COPSPepException(
									"Mandatory COPS object missing (KA Timer)");
						short kaTimeVal = kt.getTimerVal();

						// ACTimer
						COPSAcctTimer at = cMsg.getAcctTimer();
						short acctTimer = 0;
						if (at != null)
							acctTimer = at.getTimerVal();

						logger.info("Send a REQ message to the PS");
						{
							Properties prop = new Properties();
							COPSMsg reqMsg = MessageFactory.getInstance()
									.create(COPSHeader.COPS_OP_REQ, prop);
							sendRequest(reqMsg);
						}
						// Create the connection manager
						COPSPepConnection conn = new COPSPepConnection(
								CLIENT_TYPE, socket);
						conn.setKaTimer(kaTimeVal);
						conn.setAcctTimer(acctTimer);
						logger.info(getClass().getName()
								+ " Thread(conn).start");
						new Thread(conn).start();
					} else {
						// messages of other types are not expected
						throw new COPSPepException(
								"Message not expected. Closing connection for "
										+ socket.toString());
					}
				} catch (COPSPepException e) {
					logger.severe(e.getMessage());
				}
			}
		};
	}
}
