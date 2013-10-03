package org.pcmm.gates;

public interface IExtendedClassifier extends IClassifier {
	
	static final short LENGTH = 40;
	static final byte SNUM = 6;
	static final byte STYPE = 2;

	int getIPSourceMask();

	int getIPDestinationMask();

	int getSourcePortStart();

	int getSourcePortEnd();

	int getDestinationPortStart();

	int getDestinationPortEnd();

	int getClassifierID();

	int getActivationState();

	int getAction();
}
