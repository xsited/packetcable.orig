/**
 @header@
 */
package org.pcmm.rcd.impl;

import java.net.Socket;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.pcmm.messages.impl.MessageFactory;
import org.pcmm.rcd.ICMTS;
import org.umu.cops.prpep.COPSPepConnection;
import org.umu.cops.prpep.COPSPepDataProcess;
import org.umu.cops.prpep.COPSPepException;
import org.umu.cops.prpep.COPSPepReqStateMan;
import org.umu.cops.stack.COPSAcctTimer;
import org.umu.cops.stack.COPSClientAcceptMsg;
import org.umu.cops.stack.COPSClientCloseMsg;
import org.umu.cops.stack.COPSError;
import org.umu.cops.stack.COPSException;
import org.umu.cops.stack.COPSHeader;
import org.umu.cops.stack.COPSKATimer;
import org.umu.cops.stack.COPSMsg;
import org.umu.cops.stack.COPSReqMsg;

/**
 *
 */
public class CMTS extends AbstractPCMMServer implements ICMTS {

	public CMTS() {
		super();
	}

	@Override
	protected IPCMMClientHandler getPCMMClientHandler(final Socket socket) {

		return new AbstractPCMMClientHandler(socket) {

			private String handle;

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
							handle = ((COPSReqMsg) reqMsg).getClientHandle()
									.getId().str();
							sendRequest(reqMsg);
						}
						// Create the connection manager
						PCMMCmtsConnection conn = new PCMMCmtsConnection(
								CLIENT_TYPE, socket);
						// pcmm specific processor
						conn.addRequestState(handle,
								new PCMMPolicyDataProcessor());
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
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

			@Override
			public void task(Callable<?> c) {
				// TODO Auto-generated method stub

			}

			@Override
			public void shouldWait(int t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void done() {
				// TODO Auto-generated method stub

			}

		};
	}

	/* public */class PCMMCmtsConnection extends COPSPepConnection {

		public PCMMCmtsConnection(short clientType, Socket sock) {
			super(clientType, sock);
		}

		public COPSPepReqStateMan addRequestState(String clientHandle,
				COPSPepDataProcess process) throws COPSException,
				COPSPepException {
			return super.addRequestState(clientHandle, process);
		}
	}

	@SuppressWarnings("rawtypes")
	class PCMMPolicyDataProcessor extends COPSPepDataProcess {
		Hashtable removeDecs, installDecs, errorDecs;

		@Override
		public void setDecisions(COPSPepReqStateMan man, Hashtable removeDecs,
				Hashtable installDecs, Hashtable errorDecs) {
			this.removeDecs = removeDecs;
			this.installDecs = installDecs;
			this.errorDecs = errorDecs;
		}

		@Override
		public boolean isFailReport(COPSPepReqStateMan man) {
			return (errorDecs != null && errorDecs.size() > 0);
		}

		@Override
		public Hashtable getReportData(COPSPepReqStateMan man) {
			if (isFailReport(man)) {
				return errorDecs;
			} else {
				return removeDecs != null ? removeDecs : installDecs;
			}
		}

		@Override
		public Hashtable getClientData(COPSPepReqStateMan man) {
			return null;
		}

		@Override
		public Hashtable getAcctData(COPSPepReqStateMan man) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void notifyClosedConnection(COPSPepReqStateMan man,
				COPSError error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void notifyNoKAliveReceived(COPSPepReqStateMan man) {
			// TODO Auto-generated method stub

		}

		@Override
		public void closeRequestState(COPSPepReqStateMan man) {
			// TODO Auto-generated method stub

		}

		@Override
		public void newRequestState(COPSPepReqStateMan man) {
			// TODO Auto-generated method stub

		}

	}

}
