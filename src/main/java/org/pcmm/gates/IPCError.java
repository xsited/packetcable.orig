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
		ERROR_01(		(short)1, "Insufficient Resources"),
		ERROR_02(		(short)2,"Unknown GateID"),
		ERROR_06(		(short)6,"Missing Required Object"),
		ERROR_07(		(short)7,"Invalid Object"),
		ERROR_08(		(short)8,"Volume Based Usage Limit Exceeded"),
		ERROR_09(		(short)9,"Time Based Usage Limit Exceeded"),
		ERROR_10(		(short)10,"Session Class Limit Exceeded"),
		ERROR_11(		(short)11,"Undefined Service Class Name"),
		ERROR_12(		(short)12,"Incompatible Envelope"),
		ERROR_13(		(short)13,"Invalid SubscriberID"),
		ERROR_14(		(short)14,"Unauthorized AMID"),
		ERROR_15(		(short)15,"Number of Classifiers Not Supported"),
		ERROR_16(		(short)16,"Policy Exception"),
		ERROR_17(		(short)17,"Invalid Field Value in Object"),
		ERROR_18(		(short)18,"Transport Error"),
		ERROR_19(		(short)19,"Unknown Gate Command"),
		ERROR_20(		(short)20,"DOCSIS 1.0 CM"),
		ERROR_21(		(short)21,"Number of SIDs exceeded in CM"),
		ERROR_22(		(short)22,"Number of SIDs exceeded in CMTS"),
		ERROR_23(		(short)23,"Unauthorized PSID"),
		ERROR_24(		(short)24,"No State for PDP"),
		ERROR_25(		(short)25,"Unsupported Synch Type"),
		ERROR_26(		(short)26,"State Data Incomplete"),
		ERROR_27(		(short)27,"Upstream Drop Unsupported"),
		ERROR_28(		(short)28,"Multicast Gate Error"),
		ERROR_29(		(short)29,"Multicast Volume Limit Unsupported"),
		ERROR_30(		(short)30,"Uncommitted Multicast Not Supported"),
		ERROR_31(		(short)31,"Multicast Gate Modification Not Supported"),
		ERROR_32(		(short)32,"Upstream Multicast Not Supported"),
		ERROR_33(		(short)33,"Multicast GateSpec incompatibility"),
		ERROR_34(		(short)34,"Multicast QoS Error"),
		ERROR_35(		(short)35,"Multicast Downstream Resequencing mismatch"),
		ERROR_127(		(short)127,"Other, Unspecified Error");
		
 		private final short code;
  		private final String description;

  		private Description(short code, String description) {
    		    this.code = code;
    		    this.description = description;
  		}

  		public String getDescription() {
     		    return description;
  		}

  		public short getCode() {
     		    return code;
  		}

  		@Override
  		public String toString() {
    		    return code + ": " + description;
  		}
	}


	void setErrorCode(short ErrorCode);
	short getErrorCode();

	void setErrorSubcode(short ErrorSubcode);
	short getErrorSubcode();

	String getDescription(short ErrorCode, short ErrorSubcode);
}
