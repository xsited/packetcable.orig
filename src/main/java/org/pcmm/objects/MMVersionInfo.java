/**
 * 
 */
package org.pcmm.objects;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.umu.cops.stack.COPSData;
import org.umu.cops.stack.COPSObjBase;
import org.umu.cops.stack.COPSObjHeader;

/**
 * 
 * PCMM MM version info Object
 *@author rhadjamor@gmail.com  
 */
public class MMVersionInfo extends COPSObjBase {

	private COPSObjHeader objHeader;
	private COPSData copsData;
	private short majorVersionNB;
	private short minorVersionNB;
	public static final short DEFAULT_MAJOR_VERSION_INFO = (short) 5;
	public static final short DEFAULT_MINOR_VERSION_INFO = (short) 0;

	public MMVersionInfo() {
		this(DEFAULT_MAJOR_VERSION_INFO, DEFAULT_MINOR_VERSION_INFO);
	}

	public MMVersionInfo(short majorVersionNB, short minorVersionNB) {
		objHeader = new COPSObjHeader();
		objHeader.setCNum((byte) 16);// len =16
		objHeader.setCType((byte) 1);
		objHeader.setDataLength((byte) 4);// total of 8
		this.majorVersionNB = majorVersionNB;
		this.minorVersionNB = minorVersionNB;
		byte[] buff = new byte[4];
		buff[0] = (byte) (this.majorVersionNB >> 8);
		buff[1] = (byte) (this.majorVersionNB);
		buff[2] = (byte) (this.minorVersionNB >> 8);
		buff[3] = (byte) (this.minorVersionNB);
		copsData = new COPSData(buff, 0, 4);
	}

	/**
	 * Parse data and create COPSHandle object
	 */
	protected MMVersionInfo(byte[] dataPtr) {
		objHeader = new COPSObjHeader();
		objHeader.parse(dataPtr);
		majorVersionNB |= ((short) dataPtr[4]) << 8;
		majorVersionNB |= ((short) dataPtr[5]) & 0xFF;
		minorVersionNB |= ((short) dataPtr[6]) << 8;
		minorVersionNB |= ((short) dataPtr[7]) & 0xFF;
		// Get the length of data following the obj header
		int dLen = objHeader.getDataLength() - 8;
		COPSData d = new COPSData(dataPtr, 4, dLen);
		setId(d);
	}

	/**
	 * Set handle value
	 * 
	 * @param id
	 *            a COPSData
	 * 
	 */
	public void setId(COPSData id) {
		copsData = id;
		majorVersionNB = 0;
		minorVersionNB = 0;
		byte[] dataPtr = id.getData();
		majorVersionNB |= ((short) dataPtr[0]) << 8;
		majorVersionNB |= ((short) dataPtr[1]) & 0xFF;
		minorVersionNB |= ((short) dataPtr[2]) << 8;
		minorVersionNB |= ((short) dataPtr[3]) & 0xFF;
		objHeader.setDataLength((short) copsData.length());
	}

	/**
	 * Returns size in number of octects, including header
	 * 
	 * @return a short
	 * 
	 */
	public short getDataLength() {
		// Add the size of the header also
		return (short) objHeader.getDataLength();
	}

	/**
	 * Get handle value
	 * 
	 * @return a COPSData
	 * 
	 */
	public COPSData getCopsData() {
		return copsData;
	}

	/**
	 * Write data in network byte order on a given network socket
	 * 
	 * @param id
	 *            a Socket
	 * 
	 * @throws IOException
	 * 
	 */
	public void writeData(Socket id) throws IOException {
		throw new RuntimeException(
				"MMVersionInfo.writeData() is not implemented");
	}

	/**
	 * Write an object textual description in the output stream
	 * 
	 * @param os
	 *            an OutputStream
	 * 
	 * @throws IOException
	 * 
	 */
	public void dump(OutputStream os) throws IOException {
		throw new RuntimeException("MMVersionInfo.dump() is not implemented");
	}

	/**
	 * @return the majorVersionNB
	 */
	public short getMajorVersionNB() {
		return majorVersionNB;
	}

	/**
	 * @param majorVersionNB
	 *            the majorVersionNB to set
	 */
	public void setMajorVersionNB(short majorVersionNB) {
		this.majorVersionNB = majorVersionNB;
	}

	/**
	 * @return the minorVersionNB
	 */
	public short getMinorVersionNB() {
		return minorVersionNB;
	}

	/**
	 * @param minorVersionNB
	 *            the minorVersionNB to set
	 */
	public void setMinorVersionNB(short minorVersionNB) {
		this.minorVersionNB = minorVersionNB;
	}

}
