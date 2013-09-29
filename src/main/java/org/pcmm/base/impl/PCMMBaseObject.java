/**
 * 
 */
package org.pcmm.base.impl;

import org.pcmm.base.IPCMMBaseObject;
import org.umu.cops.stack.COPSData;

/**
 * @author riadh
 * 
 */
public class PCMMBaseObject implements IPCMMBaseObject {

	private short sType;
	private short sNum;
	private short len;
	private COPSData copsData;

	public PCMMBaseObject() {

	}

	public PCMMBaseObject(byte[] data) {
		parse(data);
	}

	public PCMMBaseObject(short len, short sType, short sNum) {
		setLength(len);
		setSType(sType);
		setSNum(sNum);
	}

	protected void parse(byte[] data) {
		if (data == null || data.length == 0)
			throw new IllegalArgumentException("data could not be null");
		len = 0;
		len |= ((short) data[0]) << 8;
		len |= ((short) data[1]) & 0xFF;
		sNum = 0;
		sNum |= ((short) data[2]) << 8;
		sNum |= ((short) data[3]) & 0xFF;
		sType = 0;
		sType |= ((short) data[4]) << 8;
		sType |= ((short) data[5]) & 0xFF;
		short offset = (short) 6;
		copsData = new COPSData(data, offset, data.length - offset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#setSType(short)
	 */
	@Override
	public void setSType(short stype) {
		this.sType = stype;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#getSType()
	 */
	@Override
	public short getSType() {
		return sType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#setSNum(short)
	 */
	@Override
	public void setSNum(short snum) {
		this.sNum = snum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#getSNum()
	 */
	@Override
	public short getSNum() {
		return sNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#setLength(short)
	 */
	@Override
	public void setLength(short len) {
		this.len = len;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#getLength()
	 */
	@Override
	public short getLength() {
		return len;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#setData(org.umu.cops.stack.COPSData)
	 */
	@Override
	public void setData(COPSData data) {
		this.copsData = new COPSData(data.getData(), 0, (short) data.length());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#getData()
	 */
	@Override
	public COPSData getData() {
		return copsData;
	}

}
