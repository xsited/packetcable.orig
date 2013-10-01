/**
 * 
 */
package org.pcmm.base.impl;

import java.util.Arrays;

import org.pcmm.base.IPCMMBaseObject;
import org.umu.cops.stack.COPSData;

/**
 * @author riadh
 * 
 */
public class PCMMBaseObject /* extends COPSPrObjBase */implements
		IPCMMBaseObject {

	private short sType;
	private short sNum;
	private short len;
	private COPSData copsData;

	public PCMMBaseObject(byte[] data) {
		parse(data);
	}

	public PCMMBaseObject(short len, short sType, short sNum) {
		setLength(len);
		setSType(sType);
		setSNum(sNum);
		copsData = new COPSData(new byte[len], 0, len);
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
		/*
		 * sType = 0; sType |= ((short) data[4]) << 8; sType |= ((short)
		 * data[5]) & 0xFF;
		 */
		short offset = (short) 4;
		copsData = new COPSData(data, offset, data.length - offset);
	}

	protected void setShort(short value, short startPos) {
		byte[] data = getData().getData();
		data[startPos] = (byte) (value >> 8);
		data[startPos + 1] = (byte) value;
		setData(new COPSData(data, 0, data.length));
	}

	protected short getShort(short startPos) {
		byte[] data = getData().getData();
		short retVal = 0;
		retVal |= ((short) data[startPos]) << 8;
		retVal |= ((short) data[startPos + 1]) & 0xFF;
		return retVal;
	}

	protected void setInt(int value, short startPos) {
		byte[] data = getData().getData();
		data[startPos] = (byte) (value >> 24);
		data[startPos + 1] = (byte) (value >> 16);
		data[startPos + 2] = (byte) (value >> 8);
		data[startPos + 3] = (byte) value;
		setData(new COPSData(data, 0, data.length));
	}

	protected int getInt(short startPos) {
		byte[] data = getData().getData();
		int retVal = 0;
		retVal |= ((short) data[startPos]) << 24;
		retVal |= ((short) data[startPos + 1]) << 16;
		retVal |= ((short) data[startPos + 2]) << 8;
		retVal |= ((short) data[startPos + 3]) & 0xFF;
		return retVal;
	}

	protected void setBytes(byte[] value, short startPos) {
		byte[] data = getData().getData();
		for (byte b : value)
			data[startPos++] = b;
		setData(new COPSData(data, 0, data.length));
	}

	protected byte[] getBytes(short startPos, short size) {
		return Arrays.copyOfRange(getData().getData(), startPos, startPos
				+ size);
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
		byte[] d = Arrays.copyOf(data.getData(), len);
		if (data.getData().length < len)
			Arrays.fill(d, data.getData().length, len, (byte) 0);
		this.copsData = new COPSData(d, 0, len);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.base.IPCMMBaseObject#getAsBinaryArray()
	 */
	@Override
	public byte[] getAsBinaryArray() {
		byte[] array = new byte[getLength()];
		array[0] = (byte) (len >> 8);
		array[1] = (byte) len;
		array[2] = (byte) (sNum >> 8);
		array[3] = (byte) sNum;
		/*
		 * array[2] = (byte) (sType >> 8); array[3] = (byte) sType;
		 */
		System.arraycopy(getData().getData(), 0, array, 4,
				getData().length() - 4);
		return array;
	}
}
