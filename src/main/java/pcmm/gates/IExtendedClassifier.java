package pcmm.gates;

public interface IExtendedClassifier extends IClassifier {

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
