/**
 * @header@
 */
package org.pcmm.rcd.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.pcmm.PCMMGlobalConfig;
import org.pcmm.gates.IAMID;
import org.pcmm.gates.IClassifier;
import org.pcmm.gates.IExtendedClassifier;
import org.pcmm.gates.IGateSpec;
import org.pcmm.gates.IGateSpec.DSCPTOS;
import org.pcmm.gates.IGateSpec.Direction;
import org.pcmm.gates.IPCMMGate;
import org.pcmm.gates.ISubscriberID;
import org.pcmm.gates.ITrafficProfile;
import org.pcmm.gates.ITransactionID;
import org.pcmm.gates.impl.AMID;
import org.pcmm.gates.impl.BestEffortService;
import org.pcmm.gates.impl.Classifier;
import org.pcmm.gates.impl.ExtendedClassifier;
import org.pcmm.gates.impl.GateSpec;
import org.pcmm.gates.impl.PCMMGateReq;
import org.pcmm.gates.impl.SubscriberID;
import org.pcmm.gates.impl.TransactionID;
import org.pcmm.messages.IMessage;
import org.pcmm.messages.IMessage.MessageProperties;
import org.pcmm.messages.impl.MessageFactory;
import org.pcmm.objects.MMVersionInfo;
import org.pcmm.rcd.IPCMMPolicyServer;
import org.pcmm.state.IState;
import org.umu.cops.prpdp.COPSPdpConnection;
import org.umu.cops.prpdp.COPSPdpDataProcess;
import org.umu.cops.stack.COPSClientAcceptMsg;
import org.umu.cops.stack.COPSClientCloseMsg;
import org.umu.cops.stack.COPSClientOpenMsg;
import org.umu.cops.stack.COPSClientSI;
import org.umu.cops.stack.COPSData;
import org.umu.cops.stack.COPSDecision;
import org.umu.cops.stack.COPSException;
import org.umu.cops.stack.COPSHandle;
import org.umu.cops.stack.COPSHeader;
import org.umu.cops.stack.COPSMsg;
import org.umu.cops.stack.COPSObjHeader;
import org.umu.cops.stack.COPSReportMsg;
import org.umu.cops.stack.COPSReqMsg;

/**
 * 
 * PCMM policy server
 * 
 * @author rhadjamor@gmail.com
 * 
 */
public class PCMMPolicyServer extends AbstractPCMMClient implements
		IPCMMPolicyServer {

	private static final short MAX_PORT_NB = (short) 65535;
	private static final String DEFAULT_IP_MASK = "0.0.0.0";
	private short transactionID;
	private short classifierID;
	private int gateID;

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
				boolean endNegotiation = false;
				while (!endNegotiation) {
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
							MMVersionInfo vInfo = new MMVersionInfo(opn
									.getClientSI().getData().getData());
							setVersionInfo(vInfo);
							logger.info("CMTS sent MMVersion info : major:"
									+ vInfo.getMajorVersionNB() + "  minor:"
									+ vInfo.getMinorVersionNB()); //
							if (getVersionInfo().getMajorVersionNB() == getVersionInfo()
									.getMinorVersionNB()) {
								// send a CC since CMTS has exhausted all
								// protocol selection attempts
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
							COPSReqMsg req = (COPSReqMsg) reqMsg;
							// set the client handle to be used later by the
							// gate-set
							setClientHandle(req.getClientHandle().getId().str());
							COPSPdpDataProcess processor = null;
							COPSPdpConnection copsPdpConnection = new COPSPdpConnection(
									opn.getPepId(), getSocket(), processor);
							copsPdpConnection
									.setKaTimer(((COPSClientAcceptMsg) catMsg)
											.getKATimer().getTimerVal());
							new Thread(copsPdpConnection).start();

							endNegotiation = true;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.rcd.IPCMMPolicyServer#gateSet()
	 */
	@Override
	public boolean gateSet() {
		if (!isConnected())
			throw new IllegalArgumentException("Not connected");
		// XXX check if other values should be provided
		//
		ITrafficProfile trafficProfile = buildTrafficProfile();
		// PCMMGlobalConfig.DefaultBestEffortTrafficRate);
		ITransactionID trID = new TransactionID();
		// set transaction ID to gate set
		trID.setGateCommandType(ITransactionID.GateSet);
		transactionID = (short) (transactionID == 0 ? (short) (Math.random() * hashCode())
				: transactionID);
		trID.setTransactionIdentifier(transactionID);
		// AMID
		IAMID amid = getAMID();
		// GATE SPEC
		IGateSpec gateSpec = getGateSpec();
		ISubscriberID subscriberID = new SubscriberID();
		// Classifier if MM version <4, Extended Classifier else
		IClassifier eclassifier = getClassifier(subscriberID);

		IPCMMGate gate = new PCMMGateReq();
		gate.setTransactionID(trID);
		gate.setAMID(amid);
		gate.setSubscriberID(subscriberID);
		gate.setGateSpec(gateSpec);
		gate.setTrafficProfile(trafficProfile);
		gate.setClassifier(eclassifier);
		byte[] data = gate.getData();

		// configure message properties
		Properties prop = new Properties();
		prop.put(MessageProperties.CLIENT_HANDLE, getClientHandle());
		prop.put(MessageProperties.DECISION_TYPE, COPSDecision.DEC_INSTALL);
		prop.put(MessageProperties.DECISION_FLAG, COPSDecision.F_REQERROR);
		prop.put(MessageProperties.GATE_CONTROL, new COPSData(data, 0,
				data.length));
		COPSMsg decisionMsg = MessageFactory.getInstance().create(
				COPSHeader.COPS_OP_DEC, prop);
		// ** Send the GateSet Decision
		// **
		try {
			decisionMsg.writeData(getSocket());
		} catch (IOException e) {
			System.out.println("Failed to send the decision, reason: "
					+ e.getMessage());
		}

		// TODO check on this ?
		// waits for the gate-set-ack or error
		COPSMsg responseMsg = readMessage();
		if (responseMsg.getHeader().isAReport()) {
			logger.info("processing received report from CMTS");
			COPSReportMsg reportMsg = (COPSReportMsg) responseMsg;
			if (reportMsg.getClientSI().size() == 0) {
				return false;
			}
			COPSClientSI clientSI = (COPSClientSI) reportMsg.getClientSI()
					.elementAt(0);
			IPCMMGate responseGate = new PCMMGateReq(clientSI.getData()
					.getData());
			if (((PCMMGateReq) responseGate).getError() != null) {

			}
			if (responseGate.getTransactionID() != null
					&& responseGate.getTransactionID().getGateCommandType() == ITransactionID.GateSetAck) {
				logger.info("the CMTS has sent a Gate-Set-Ack response");
				// here CMTS responded that he acknowledged the Gate-Set
				// TODO do further check of Gate-Set-Ack GateID etc...
				gateID = responseGate.getGateID().getGateID();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.rcd.IPCMMPolicyServer#gateDelete()
	 */
	@Override
	public boolean gateDelete() {
		IPCMMGate gate = new PCMMGateReq();
		// set transaction ID to gate delete
		ITransactionID trID = new TransactionID();
		trID.setGateCommandType(ITransactionID.GateDelete);
		transactionID = (short) (transactionID == 0 ? (short) (Math.random() * hashCode())
				: transactionID);
		trID.setTransactionIdentifier(transactionID);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.rcd.IPCMMPolicyServer#gateInfo()
	 */
	@Override
	public boolean gateInfo() {
		IPCMMGate gate = new PCMMGateReq();
		// set transaction ID to gate info
		ITransactionID trID = new TransactionID();
		trID.setGateCommandType(ITransactionID.GateInfo);
		transactionID = (short) (transactionID == 0 ? (short) (Math.random() * hashCode())
				: transactionID);
		trID.setTransactionIdentifier(transactionID);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.rcd.IPCMMPolicyServer#synchronize()
	 */
	@Override
	public boolean synchronize() {
		IPCMMGate gate = new PCMMGateReq();
		// set transaction ID to synch request
		ITransactionID trID = new TransactionID();
		trID.setGateCommandType(ITransactionID.SynchRequest);
		transactionID = (short) (transactionID == 0 ? (short) (Math.random() * hashCode())
				: transactionID);
		trID.setTransactionIdentifier(transactionID);
		return false;
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

	private IAMID getAMID() {
		IAMID amid = new AMID();
		amid.setApplicationType((short) 1);
		amid.setApplicationMgrTag((short) 1);
		return amid;
	}

	private IClassifier getClassifier(ISubscriberID subscriberID) {
		IClassifier classifier = null;
		// if the version major is less than 4 we need to use Classifier
		if (getVersionInfo().getMajorVersionNB() >= 4) {
			classifier = new ExtendedClassifier();
			// eclassifier.setProtocol(IClassifier.Protocol.NONE);
			classifier.setProtocol(IClassifier.Protocol.TCP);
			try {
				InetAddress subIP = InetAddress
						.getByName(PCMMGlobalConfig.SubscriberID);
				InetAddress srcIP = InetAddress
						.getByName(PCMMGlobalConfig.srcIP);
				InetAddress dstIP = InetAddress
						.getByName(PCMMGlobalConfig.dstIP);
				InetAddress mask = InetAddress.getByName(DEFAULT_IP_MASK);
				subscriberID.setSourceIPAddress(subIP);
				classifier.setSourceIPAddress(srcIP);
				classifier.setDestinationIPAddress(dstIP);
				((IExtendedClassifier) classifier).setIPDestinationMask(mask);
				((IExtendedClassifier) classifier).setIPSourceMask(mask);
			} catch (UnknownHostException unae) {
				System.out.println("Error getByName" + unae.getMessage());
			}
			((IExtendedClassifier) classifier)
					.setSourcePortStart(PCMMGlobalConfig.srcPort);
			((IExtendedClassifier) classifier)
					.setSourcePortEnd(PCMMGlobalConfig.srcPort);
			((IExtendedClassifier) classifier)
					.setDestinationPortStart(PCMMGlobalConfig.dstPort);
			((IExtendedClassifier) classifier)
					.setDestinationPortEnd(PCMMGlobalConfig.dstPort);
			((IExtendedClassifier) classifier).setActivationState((byte) 0x01);
			/*
			 * check if we have a stored value of classifierID else we just
			 * create one eclassifier.setClassifierID((short) 0x01);
			 */
			((IExtendedClassifier) classifier)
					.setClassifierID((short) (classifierID == 0 ? Math.random()
							* hashCode() : classifierID));
			// XXX - testie
			// eclassifier.setClassifierID((short) 1);
			((IExtendedClassifier) classifier).setAction((byte) 0x00);
			// XXX - temp default until Gate Modify is hacked in
			// eclassifier.setPriority(PCMMGlobalConfig.EClassifierPriority);
			classifier.setPriority((byte) 65);

		} else {
			classifier = new Classifier();
			classifier.setProtocol(IClassifier.Protocol.TCP);
			try {
				InetAddress subIP = InetAddress
						.getByName(PCMMGlobalConfig.SubscriberID);
				InetAddress srcIP = InetAddress
						.getByName(PCMMGlobalConfig.srcIP);
				InetAddress dstIP = InetAddress
						.getByName(PCMMGlobalConfig.dstIP);
				subscriberID.setSourceIPAddress(subIP);
				classifier.setSourceIPAddress(srcIP);
				classifier.setDestinationIPAddress(dstIP);
			} catch (UnknownHostException unae) {
				System.out.println("Error getByName" + unae.getMessage());
			}
			classifier.setSourcePort(PCMMGlobalConfig.srcPort);
			classifier.setDestinationPort(PCMMGlobalConfig.dstPort);
		}
		return classifier;
	}

	private IGateSpec getGateSpec() {
		IGateSpec gateSpec = new GateSpec();
		gateSpec.setDirection(Direction.UPSTREAM);
		gateSpec.setDSCP_TOSOverwrite(DSCPTOS.OVERRIDE);
		gateSpec.setTimerT1(PCMMGlobalConfig.GateT1);
		gateSpec.setTimerT2(PCMMGlobalConfig.GateT2);
		gateSpec.setTimerT3(PCMMGlobalConfig.GateT3);
		gateSpec.setTimerT4(PCMMGlobalConfig.GateT4);
		return gateSpec;
	}

	private ITrafficProfile buildTrafficProfile() {
		ITrafficProfile trafficProfile = new BestEffortService((byte) 7); // BestEffortService.DEFAULT_ENVELOP);
		((BestEffortService) trafficProfile).getAuthorizedEnvelop()
				.setTrafficPriority(BestEffortService.DEFAULT_TRAFFIC_PRIORITY);
		((BestEffortService) trafficProfile).getAuthorizedEnvelop()
				.setMaximumTrafficBurst(
						BestEffortService.DEFAULT_MAX_TRAFFIC_BURST);
		((BestEffortService) trafficProfile).getAuthorizedEnvelop()
				.setRequestTransmissionPolicy(
						PCMMGlobalConfig.BETransmissionPolicy);
		((BestEffortService) trafficProfile).getAuthorizedEnvelop()
				.setMaximumSustainedTrafficRate(
						PCMMGlobalConfig.DefaultLowBestEffortTrafficRate);
		// PCMMGlobalConfig.DefaultBestEffortTrafficRate);

		((BestEffortService) trafficProfile).getReservedEnvelop()
				.setTrafficPriority(BestEffortService.DEFAULT_TRAFFIC_PRIORITY);
		((BestEffortService) trafficProfile).getReservedEnvelop()
				.setMaximumTrafficBurst(
						BestEffortService.DEFAULT_MAX_TRAFFIC_BURST);
		((BestEffortService) trafficProfile).getReservedEnvelop()
				.setRequestTransmissionPolicy(
						PCMMGlobalConfig.BETransmissionPolicy);
		((BestEffortService) trafficProfile).getReservedEnvelop()
				.setMaximumSustainedTrafficRate(
						PCMMGlobalConfig.DefaultLowBestEffortTrafficRate);
		// PCMMGlobalConfig.DefaultBestEffortTrafficRate);

		((BestEffortService) trafficProfile).getCommittedEnvelop()
				.setTrafficPriority(BestEffortService.DEFAULT_TRAFFIC_PRIORITY);
		((BestEffortService) trafficProfile).getCommittedEnvelop()
				.setMaximumTrafficBurst(
						BestEffortService.DEFAULT_MAX_TRAFFIC_BURST);
		((BestEffortService) trafficProfile).getCommittedEnvelop()
				.setRequestTransmissionPolicy(
						PCMMGlobalConfig.BETransmissionPolicy);
		((BestEffortService) trafficProfile).getCommittedEnvelop()
				.setMaximumSustainedTrafficRate(
						PCMMGlobalConfig.DefaultLowBestEffortTrafficRate);
		return trafficProfile;
	}
}
