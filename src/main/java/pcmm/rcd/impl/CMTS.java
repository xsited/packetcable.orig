/**
 * 
 */
package pcmm.rcd.impl;

import java.net.InetAddress;
import java.net.Socket;

import pcmm.messages.IMessage;
import pcmm.rcd.ICMTS;

/**
 * @author riadh
 * 
 */
public class CMTS extends AbstractPCMMServer implements ICMTS {

	private Socket socket;

	public CMTS() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#sendRequest(pcmm.messages.IMessage)
	 */
	public void sendRequest(IMessage requestMessage) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#tryConnect(java.lang.String, int)
	 */
	public boolean tryConnect(String address, int port) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#tryConnect(java.net.InetAddress, int)
	 */
	public boolean tryConnect(InetAddress address, int port) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#disconnect()
	 */
	public boolean disconnect() {
		// TODO Auto-generated method stub
		return false;
	}

}
