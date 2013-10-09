/**
 * 
 */
package org.pcmm.gates.impl;

import java.util.Arrays;

import org.pcmm.base.impl.PCMMBaseObject;
import org.pcmm.gates.ITrafficProfile;
import org.umu.cops.stack.COPSData;

/**
 * @author rhadjamor@gmail.com
 * 
 */
public class BestEffortService extends PCMMBaseObject implements
		ITrafficProfile {
	public static final byte STYPE = 3;
	// 60, 112, 164
	public static final short LENGTH = 60;

	public static final byte DEFAULT_TRAFFIC_PRIORITY = 5;
	// Authorized
	public static final byte DEFAULT_ENVELOP = 0x1;

	public static final int DEFAULT_MAX_TRAFFIC_BURST = 1522;

	/**
	 * 
	 * @param e
	 *            envelop
	 */
	public BestEffortService(byte e) {
		super((short) (e == 1 ? LENGTH : (e == 7 ? 164 : 112)), STYPE, SNUM);
		setTrafficPriority(DEFAULT_TRAFFIC_PRIORITY);
		setEnvelop(e);
	}

	public BestEffortService(byte[] bytes) {
		super(bytes);
	}

	@Override
	public void setEnvelop(byte e) {
		setLength((short) (e == 1 ? LENGTH : (e == 7 ? 164 : 112)));
		// reset cops data to fit the new length
		byte[] array = new byte[getLength() - offset];
		Arrays.fill(array, (byte) 0);
		setData(new COPSData(array, 0, array.length));
		setByte(e, (short) 0);
	}

	@Override
	public byte getEnvelop() {
		return getByte((short) 0);
	}

	public void setTrafficPriority(byte p) {
		setByte(p, (short) 4);
	}

	public byte getTrafficPriority() {
		return getByte((short) 4);
	}

	//
	public void setRequestTransmissionPolicy(int p) {
		setInt(p, (short) 8);
	}

	public int getRequestTransmissionPolicy() {
		return getInt((short) 8);
	}

	public int getMaximumSustainedTrafficRate() {
		return getInt((short) 12);
	}

	public void setMaximumSustainedTrafficRate(int p) {
		setInt(p, (short) 12);
	}

	public int getMaximumTrafficBurst() {
		return getInt((short) 16);
	}

	public void setMaximumTrafficBurst(int p) {
		setInt(p, (short) 16);
	}

	public int getMinimumReservedTrafficRate() {
		return getInt((short) 20);
	}

	public void setMinimumReservedTrafficRate(int p) {
		setInt(p, (short) 20);
	}

	public short getAssumedMinimumReservedTrafficRatePacketSize() {
		return getShort((short) 24);
	}

	public void setAssumedMinimumReservedTrafficRatePacketSize(short p) {
		setShort(p, (short) 24);
	}

	public short getMaximumConcatenatedBurst() {
		return getShort((short) 26);
	}

	public void setMaximumConcatenatedBurst(short p) {
		setShort(p, (short) 26);
	}

	public int getUpstreamPeakTrafficRate() {
		return getInt((short) 28);
	}

	public void setUpstreamPeakTrafficRate(int p) {
		setInt(p, (short) 28);
	}

	public int getRequiredAttributeMask() {
		return getInt((short) 32);
	}

	public void setRequiredAttributeMask(int p) {
		setInt(p, (short) 32);
	}

	public int getForbiddenAttributeMask() {
		return getInt((short) 36);
	}

	public void setForbiddenAttributeMask(int p) {
		setInt(p, (short) 36);
	}

	public int getAttributeAggregationRuleMask() {
		return getInt((short) 40);
	}

	public void setAttributeAggregationRuleMask(int p) {
		setInt(p, (short) 40);
	}

	public int getMinimumBuffer() {
		return getInt((short) 44);
	}

	public void setMinimumBuffer(int p) {
		setInt(p, (short) 44);
	}

	public int getTargetBuffer() {
		return getInt((short) 48);
	}

	public void setTargetBuffer(int p) {
		setInt(p, (short) 48);
	}

	public int getMaximumBuffer() {
		return getInt((short) 52);
	}

	public void setMaximumBuffer(int p) {
		setInt(p, (short) 52);
	}

}
