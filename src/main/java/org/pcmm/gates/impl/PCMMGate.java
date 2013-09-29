/**
 * 
 */
package org.pcmm.gates.impl;

import org.pcmm.gates.IAMID;
import org.pcmm.gates.IClassifier;
import org.pcmm.gates.IGateID;
import org.pcmm.gates.IGateSpec;
import org.pcmm.gates.IPCMMGate;
import org.pcmm.gates.ISubscriberID;
import org.pcmm.gates.ITrafficProfile;
import org.pcmm.gates.ITransactionID;
import org.umu.cops.stack.COPSData;
import org.umu.cops.stack.COPSHeader;
import org.umu.cops.stack.COPSObjHeader;

/**
 * @author riadh
 * 
 */
public class PCMMGate implements IPCMMGate {

	private boolean multicast;
	private IGateID gateID;
	private IAMID iamid;
	private ISubscriberID subscriberID;
	private ITransactionID transactionID;
	private IGateSpec gateSpec;
	private ITrafficProfile trafficProfile;
	private IClassifier classifier;
	private COPSHeader header;

	public PCMMGate() {
		header = new COPSHeader(COPSObjHeader.COPS_DEC);
	}

	public PCMMGate(byte[] data) {
		header = new COPSHeader(COPSObjHeader.COPS_DEC);
	}

	@Override
	public COPSHeader getCopsHeader() {
		return header;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#isMulticast()
	 */
	@Override
	public boolean isMulticast() {
		// TODO Auto-generated method stub
		return multicast;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#setGateID(short)
	 */
	@Override
	public void setGateID(IGateID gateid) {
		this.gateID = gateid;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#setAMID(org.pcmm.gates.IAMID)
	 */
	@Override
	public void setAMID(IAMID iamid) {
		this.iamid = iamid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pcmm.gates.IPCMMGate#getSubscriberID(org.pcmm.gates.ISubscriberID)
	 */
	@Override
	public void setSubscriberID(ISubscriberID subscriberID) {
		this.subscriberID = subscriberID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#getGateSpec(org.pcmm.gates.IGateSpec)
	 */
	@Override
	public void setGateSpec(IGateSpec gateSpec) {
		this.gateSpec = gateSpec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#getClassifier(org.pcmm.gates.IClassifier)
	 */
	@Override
	public void setClassifier(IClassifier classifier) {
		this.classifier = classifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pcmm.gates.IPCMMGate#getTrafficProfile(org.pcmm.gates.ITrafficProfile
	 * )
	 */
	@Override
	public void setTrafficProfile(ITrafficProfile profile) {
		this.trafficProfile = profile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#getGateID()
	 */
	@Override
	public IGateID getGateID() {
		return gateID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#getAMID()
	 */
	@Override
	public IAMID getAMID() {
		return iamid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#getSubscriberID()
	 */
	@Override
	public ISubscriberID getSubscriberID() {
		return subscriberID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#getGateSpec()
	 */
	@Override
	public IGateSpec getGateSpec() {
		return gateSpec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#getClassifier()
	 */
	@Override
	public IClassifier getClassifier() {
		return classifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.gates.IPCMMGate#getTrafficProfile()
	 */
	@Override
	public ITrafficProfile getTrafficProfile() {
		return trafficProfile;
	}

	@Override
	public void setTransactionID(ITransactionID transactionID) {
		this.transactionID = transactionID;
	}

	@Override
	public ITransactionID getTransactionID() {
		return transactionID;
	}

	@Override
	public COPSData getCopsData() {
		// TODO Auto-generated method stub
		return null;
	}
}
