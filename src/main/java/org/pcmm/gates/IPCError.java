/**
 * 
 */
package org.pcmm.gates;

import org.pcmm.base.IPCMMBaseObject;

/**
 * 
 */
public interface IPCError extends IPCMMBaseObject {
	static final short LENGTH = 8;
	static final byte SNUM = 14;
	static final byte STYPE = 1;

	static enum Description {
		ERROR_01(		1, "Insufficient Resources"),
		ERROR_02(		2,"Unknown GateID"),
		ERROR_06(		6,"Missing Required Object"),
		ERROR_07(		7,"Invalid Object"),
		ERROR_08(		8,"Volume Based Usage Limit Exceeded"),
		ERROR_09(		9,"Time Based Usage Limit Exceeded"),
		ERROR_10(		10,"Session Class Limit Exceeded"),
		ERROR_11(		11,"Undefined Service Class Name"),
		ERROR_12(		12,"Incompatible Envelope"),
		ERROR_13(		13,"Invalid SubscriberID"),
		ERROR_14(		14,"Unauthorized AMID"),
		ERROR_15(		15,"Number of Classifiers Not Supported"),
		ERROR_16(		16,"Policy Exception"),
		ERROR_17(		17,"Invalid Field Value in Object"),
		ERROR_18(		18,"Transport Error"),
		ERROR_19(		19,"Unknown Gate Command"),
		ERROR_20(		20,"DOCSIS 1.0 CM"),
		ERROR_21(		21,"Number of SIDs exceeded in CM"),
		ERROR_22(		22,"Number of SIDs exceeded in CMTS"),
		ERROR_23(		23,"Unauthorized PSID"),
		ERROR_24(		24,"No State for PDP"),
		ERROR_25(		25,"Unsupported Synch Type"),
		ERROR_26(		26,"State Data Incomplete"),
		ERROR_27(		27,"Upstream Drop Unsupported"),
		ERROR_28(		28,"Multicast Gate Error"),
		ERROR_29(		29,"Multicast Volume Limit Unsupported"),
		ERROR_30(		30,"Uncommitted Multicast Not Supported"),
		ERROR_31(		31,"Multicast Gate Modification Not Supported"),
		ERROR_32(		32,"Upstream Multicast Not Supported"),
		ERROR_33(		33,"Multicast GateSpec incompatibility"),
		ERROR_34(		34,"Multicast QoS Error"),
		ERROR_35(		35,"Multicast Downstream Resequencing mismatch"),
		ERROR_127(		127,"Other, Unspecified Error");
		
 		private final int code;
  		private final String description;

  		private Description(int code, String description) {
    		    this.code = code;
    		    this.description = description;
  		}

  		public String getDescription() {
     		    return description;
  		}

  		public int getCode() {
     		    return code;
  		}

  		@Override
  		public String toString() {
    		    return code + ": " + description;
  		}
	}


	void setErrorCode(int ErrorCode);
	int getErrorCode();

	void setErrorSubcode(int ErrorSubcode);
	int getErrorSubcode();

	String getDescription(int ErrorCode, int ErrorSubcode);
}
