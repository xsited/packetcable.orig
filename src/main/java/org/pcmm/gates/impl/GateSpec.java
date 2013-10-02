/**
 * 
 */
package org.pcmm.gates.impl;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.IGateSpec;
import org.pcmm.gates.ISessionClassID;

/**
 * @author rhadjamor@gmail.com
 * 
 */
public class GateSpec extends PCMMBaseObject implements IGateSpec {

	private ISessionClassID sessionClassID;

	private Direction direction;

	private short authorizedTimer;

	private short committedRecoveryTimer;

	private short committedTimer;

	private short reservedTimer;

	private byte dscpTOSMask;

	private DSCPTOS dscpTOSOverride;

	public GateSpec() {
		super(LENGTH, STYPE, SNUM);
		authorizedTimer = 0;
		committedRecoveryTimer = 0;
		committedTimer = 0;
		reservedTimer = 0;
		dscpTOSMask = (byte) 0;
	}

	public GateSpec(byte[] data) {
		super(data);
	}

	@Override
	public ISessionClassID getSessionClassID() {
		return sessionClassID;
	}

	@Override
	public void setSessionClassID(ISessionClassID id) {
		this.sessionClassID = id;
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public short getAuthorizedTimer() {
		return authorizedTimer;
	}

	@Override
	public void setAuthorizedTimer(short authTimer) {
		this.authorizedTimer = authTimer;
	}

	@Override
	public short getReservedTimer() {
		return reservedTimer;
	}

	@Override
	public void setReservedTimer(short timer) {
		this.reservedTimer = timer;
	}

	@Override
	public short getCommittedTimer() {
		return committedTimer;
	}

	@Override
	public void setCommittedTimer(short t) {
		this.committedTimer = t;
	}

	@Override
	public short getCommittedRecoveryTimer() {
		return this.committedRecoveryTimer;
	}

	@Override
	public void setCommittedRecoveryTimer(short t) {
		this.committedRecoveryTimer = t;
	}

	@Override
	public void setDSCP_TOSOverwrite(DSCPTOS dscpTos) {
		this.dscpTOSOverride = dscpTos;
	}

	@Override
	public DSCPTOS getDSCP_TOSOverwrite() {
		return dscpTOSOverride;
	}

	@Override
	public byte getDSCP_TOSMask() {
		return dscpTOSMask;
	}

	@Override
	public void setDSCP_TOSMask(byte dscp_tos_mask) {
		this.dscpTOSMask = dscp_tos_mask;
	}

}
