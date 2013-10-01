/**
 * 
 */
package org.pcmm.gates.impl;

import java.util.Arrays;

import org.pcmm.base.IPCMMBaseObject;
import org.pcmm.gates.IAMID;
import org.pcmm.gates.IClassifier;
import org.pcmm.gates.IGateID;
import org.pcmm.gates.IGateSpec;
import org.pcmm.gates.IPCMMGate;
import org.pcmm.gates.ISubscriberID;
import org.pcmm.gates.ITrafficProfile;
import org.pcmm.gates.ITransactionID;

/**
 * <p>
 * <Gate-set>=<Decision Header><TransactionID><AMID> <SubscriberID> [<GateI>]
 * <GateSpec> <Traffic Profile> <classifier>
 * </p>
 * 
 * @author rhadjamor@gmail.com
 * 
 */
public class PCMMGateReq implements IPCMMGate {

	private boolean multicast;
	private IGateID gateID;
	private IAMID iamid;
	private ISubscriberID subscriberID;
	private ITransactionID transactionID;
	private IGateSpec gateSpec;
	private ITrafficProfile trafficProfile;
	private IClassifier classifier;

	public PCMMGateReq() {
	}

	public PCMMGateReq(byte[] data) {
		short len, sNum, sType, offset;
		len = sNum = sType = offset = 0;
		while (offset + 5 < data.length) {
			len = 0;
			len |= ((short) data[offset]) << 8;
			len |= ((short) data[offset + 1]) & 0xFF;
			sNum = 0;
			sNum |= ((short) data[offset + 2]) << 8;
			sNum |= ((short) data[offset + 3]) & 0xFF;
			sType = 0;
			sType |= ((short) data[offset + 4]) << 8;
			sType |= ((short) data[offset + 5]) & 0xFF;
			switch (sNum) {
			case IGateID.SNUM:
				setGateID(new GateID(Arrays.copyOfRange(data, offset, len)));
				break;
			case IAMID.SNUM:
				setAMID(new AMID(Arrays.copyOfRange(data, offset, len)));
				break;
			case ISubscriberID.SNUM:
				setSubscriberID(new SubscriberID(Arrays.copyOfRange(data,
						offset, len)));
				break;
			case ITransactionID.SNUM:
				setTransactionID(new TransactionID(Arrays.copyOfRange(data,
						offset, len)));
				break;
			case IGateSpec.SNUM:
				setGateSpec(new GateSpec(Arrays.copyOfRange(data, offset, len)));
				break;
			case ITrafficProfile.SNUM:
				setTrafficProfile(new DOCSISServiceClassNameTrafficProfile(
						Arrays.copyOfRange(data, offset, len)));
				break;
			case IClassifier.SNUM:
				setClassifier(new Classifier(Arrays.copyOfRange(data, offset,
						len)));
				break;
			default:
				throw new IllegalArgumentException("unhandled Object");
			}
			offset += len;
		}
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
	public byte[] getData() {
		byte[] array = new byte[0];
		if (getTransactionID() != null) {
			array = fill(array, getTransactionID());
		}
		if (getGateID() != null) {
			array = fill(array, getGateID());
		}
		if (getAMID() != null) {
			array = fill(array, getAMID());

		}
		if (getSubscriberID() != null) {
			array = fill(array, getSubscriberID());
		}
		if (getGateSpec() != null) {
			array = fill(array, getGateSpec());
		}
		if (getClassifier() != null) {
			array = fill(array, getClassifier());
		}
		if (getTrafficProfile() != null) {
			array = fill(array, getTrafficProfile());
		}
		return array;
	}

	private byte[] fill(byte[] array, IPCMMBaseObject obj) {
		byte[] a = obj.getAsBinaryArray();
		array = Arrays.copyOf(array, array.length + a.length);
		System.arraycopy(a, 0, array, 0, a.length);
		return array;
	}
}
