package pcmm.gates;

/**
 * <p>
 * A PacketCable Multimedia Gate is a logical representation of a policy
 * decision that has been installed on the CMTS. A Gate is used to control
 * access by a single IP flow to enhanced QoS Services provided by a DOCSIS
 * cable network. Gates are unidirectional; a single Gate controls access to a
 * flow in either the upstream or the downstream direction, but not both. For a
 * bi-directional IP session, two Gates are required, one for upstream and one
 * for downstream, each identified by a unique GateID. It is important to
 * recognize that this is a fundamental difference from PacketCable 1.x, in
 * which a single GateID may reference both an upstream and a downstream Gate.
 * </p>
 * <p>
 * In PacketCable Multimedia, each Gate has a separate GateID. The Gate defines
 * the authorization, reservation and committed envelopes to be used by the CMTS
 * to perform authorization, reservation and commit operations.
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author riadh
 * 
 */
public interface IPCMMGate {

	/**
	 * 
	 * @return whether this gate is multicast or unicast.
	 */
	boolean isMulticast();

	/**
	 * GateID is the handle for the Gate.
	 * 
	 * @return the GateID.
	 */
	int getGateID();

	/**
	 * AMID is the handle that identifies the Application Manager and
	 * Application Type
	 * 
	 * @return AMID handle.
	 */
	IAMID getAMID();

	/**
	 * SubscriberID uniquely identifies the Client for which the policy is being
	 * set.
	 * 
	 * @return unique subscriber ID.
	 */
	ISubscriberID getSubscriberID();

	/**
	 * GateSpec describes specific authorization parameters defining a Gate
	 * (i.e., QoS limits, timers, etc.).
	 * 
	 * @return gateSpec object.
	 */
	IGateSpec getGateSpec();

	/**
	 * Classifier describes the IP flow(s) that will be mapped to the DOCSIS
	 * Service Flow.
	 * 
	 * @return Classifier object.
	 */
	IClassifier getClassifier();

	/**
	 * Traffic Profile describes the QoS attributes of the Service Flow used to
	 * support the IP flow.
	 */
	ITrafficProfile getTrafficProfile();

	// Event Generation Info (optional)
	// Time-Based Usage Limit (optional)
	// Volume-Based Usage Limit (optional)
	// Opaque Data (optional)
	// UserID (optional)
	// SharedResourceID (optional)
}
