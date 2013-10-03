package org.pcmm;

public class PCMMGlobalConfig {
	// System
	public static int Debug = 0;
	public static int LogLevel = 0;
	public static int DefaultBestEffortTrafficRate = 2500000;
	// Service Flow Attributes Defaults
	public static int DefaultBestEffortClassPriority = 69;
	public static int DefaultUnsolicatedGrantSize = 1000;
	public static int DefaultUnsolicatedGrantsPerInterval = 3;
	public static int DefaultUnsolicatedGrantInterval = 8000;
	// Gate Specification Defaults
	public static int DSCPToSMark = 0;
	public static int Priority = 0;
	public static int PreEmption = 0;
	public static int GateFlags = 0;
	public static int GateTOSField = 0;
	public static int GateTOSMask = 0;
	public static int GateClass = 0;
	// Authorization life timer
	public static int GateT1 = 200;
	// Authorization renew timer
	public static int GateT2 = 300;
	// Reservation life timer
	public static int GateT3 = 60;
	// Reservation renew timer
	public static int GateT4 = 30;

	public static int UGSTransmissionPolicy = 0x037F;
	public static int BETransmissionPolicy = 0x0;

	// Temporary Configure Items For Demo or Lacking Design
	public static int DefaultLowBestEffortTrafficRate = 500000;
	public static int DefaultVideoSourcePort = 8081;
	public static int DefaultAlternateSourcePort = 1369;
	// public static String DefaultCMTS = "127.0.0.1"
	public static String DefaultCMTS = "10.32.4.3";
	public static String DefautRadius = "192.168.50.2";
}

/*
 * 
 * // if(Constants.DEBUG.isEnabled()) { } public enum Constants { DEBUG(true),
 * PRINT_VARS(false);
 * 
 * private boolean enabled;
 * 
 * private Constants(boolean enabled) { this.enabled = enabled; }
 * 
 * public boolean isEnabled() { return enabled; } }
 */
