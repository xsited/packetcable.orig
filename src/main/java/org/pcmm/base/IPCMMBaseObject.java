package org.pcmm.base;

import org.umu.cops.stack.COPSData;

public interface IPCMMBaseObject {

	/**
	 * sets the S-Type
	 * 
	 * @param stype
	 */
	void setSType(short stype);

	/**
	 * 
	 * @return S-Type
	 */
	short getSType();

	/**
	 * sets the S-Num
	 * 
	 * @param snum
	 *            S-Num
	 */
	void setSNum(short snum);

	/**
	 * gets the S-Num
	 * 
	 * @return S-Num
	 */
	short getSNum();

	/**
	 * sets the length;
	 * 
	 * @param len
	 */
	void setLength(short len);

	/**
	 * gets the length;
	 * 
	 * @return length
	 */
	short getLength();

	/**
	 * sets the COPS data
	 * 
	 * @param data
	 *            COPS data
	 */
	void setData(COPSData data);

	/**
	 * gets the COPS data
	 * 
	 * @return COPS data
	 */
	COPSData getData();

	
	byte[] getAsBinaryArray();

}
