package org.pcmm.gates;

public interface IExtendedClassifier extends IClassifier {
	
	static final short LENGTH = 40;
	static final short SNUM = 6;
	static final short STYPE = 2;

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
