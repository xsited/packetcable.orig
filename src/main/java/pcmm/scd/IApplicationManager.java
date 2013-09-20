package pcmm.scd;

/**
 * <p>
 * As noted in the preceding summary, the Application Manager is a network
 * entity that defines SCD policies, coordinates subscriber-initiated requests
 * for application sessions with access to the resources needed to meet those
 * requests, and maintains application-level state.
 * </p>
 * <p>
 * The AM may reside on the MSO's network or it may reside outside this domain
 * and interact with the MSO network via a particular trust relationship
 * (typically defined by and enforced on the basis of a Service Level
 * Agreement). Similarly, the AM may be under the direct control of the operator
 * or it may be controlled by a third party. Any given Application Manager may
 * communicate with one or more Policy Servers on the operator's network;
 * likewise, one or more Application Managers may communicate with any given
 * Policy Server on the operator's network (so long as an appropriate trust
 * relationship exists).
 * </p>
 * <p>
 * The Application Manager may communicate with the Application Server using a
 * signaling protocol as defined in [20]. For Application Server requests the AM
 * authenticates and authorizes the Application Server requests based upon
 * Service Control Domain policies.
 * </p>
 * <p>
 * The Application Manager may communicate with a client via a signaling
 * protocol that is beyond the scope of this specification. Using this
 * unspecified protocol, the AM authenticates and authorizes client requests
 * based on Service Control Domain policies.
 * </p>
 * <p>
 * For client and Application Server requests that pass these checks, the AM
 * determines the particular QoS parameters necessary to deliver the service to
 * the client, based on its knowledge of the requested service. It then sends a
 * request for these resources to the appropriate Policy Server, which may deny
 * the request based on network or RCD policy or may pass the request on to the
 * CMTS for admission control and enforcement.
 * </p>
 * 
 * @author riadh
 * 
 */
public interface IApplicationManager {

}
