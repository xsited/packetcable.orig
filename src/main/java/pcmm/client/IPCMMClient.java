package pcmm.client;

/**
 * <p>
 * This is a Client Type 1, which represents existing "legacy" endpoints (e.g.,
 * PC applications, gaming consoles) that lack specific QoS awareness or
 * signaling capabilities. This client has no awareness of DOCSIS, CableHome, or
 * PacketCable messaging, and hence no related requirements can be placed upon
 * it. Client Type 1 communicates with an Application Manager to request
 * service, and does not (cannot) request QoS resources directly from the MSO
 * access network.
 * </p>
 * 
 * @author riadh
 * 
 */
public interface IPCMMClient {

}
