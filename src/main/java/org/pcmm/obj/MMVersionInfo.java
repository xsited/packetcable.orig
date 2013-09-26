package org.pcmm.obj;
import org.umu.cops.stack.*;


public class MMVersionInfo {
	/** Well-known port for PCMM */
	public static final int DEFAULT_MAJOR_VERSION_INFO=4;
	public static final int DEFAULT_MINOR_VERSION_INFO=0;
	
	public MMVersionInfo() {
	}

	public MMVersionInfo(short majorVersion, short minorVersion) {
		byte[] test = new byte[] {
            		0x0, 0x0,
            		0x0, 0x0,
		};
		COPSData data = new COPSData();
	//	return data;
	}
	public void setId(COPSData d) {
	}
	public short getMinorVersionNB() {
		short x = 0;
		return x;
	}
	public short getMajorVersionNB() {
		short x = 0;
		return x;
	}
 	public COPSData getCopsData() {
		COPSData data = new COPSData();
		return data;
		
	}


}

