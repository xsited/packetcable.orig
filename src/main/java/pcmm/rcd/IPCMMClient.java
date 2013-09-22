package pcmm.rcd;

import java.net.InetAddress;

import pcmm.messages.IMessage;

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

	/**
	 * default port used by server to listen to clients
	 */
	static final int DEFAULT_SERVER_PORT = 3918;

	/**
	 * sends a message to the server.
	 * 
	 * @param requestMessage
	 *            request message.
	 */
	void sendRequest(IMessage requestMessage);

	/**
	 * Reads message from server
	 * 
	 * @param message
	 *            received message
	 */
	void readMessage(IMessage message);

	/**
	 * tries to connect to the server.
	 * 
	 * @param address
	 *            server address
	 * @param port
	 *            server port
	 * @return connection state
	 */
	boolean tryConnect(String address, int port);

	/**
	 * tries to connect to the server.
	 * 
	 * @param address
	 *            server address
	 * @param port
	 *            server port
	 * @return connection state
	 */
	boolean tryConnect(InetAddress address, int port);

	/**
	 * disconnects from server.
	 * 
	 * @return disconnection status.
	 */
	boolean disconnect();

}
