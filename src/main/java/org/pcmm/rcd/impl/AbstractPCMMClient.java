/**
 * 
 */
package org.pcmm.rcd.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.pcmm.messages.IMessage;
import org.pcmm.rcd.IPCMMClient;

/**
 * 
 * default implementation for {@link IPCMMClient}
 * 
 * @author rhadjamor@gmail.com 
 * 
 */
public class AbstractPCMMClient implements IPCMMClient {

	private Logger logger = Logger.getLogger(getClass().getName());
	/**
	 * socket used to communicated with server.
	 */
	private Socket socket;
	/**
	 * output stream
	 */
	private ObjectOutputStream outputStream;
	/**
	 * input stream
	 */
	private ObjectInputStream inputStream;

	public AbstractPCMMClient() {
		logger.setLevel(Level.SEVERE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#sendRequest(pcmm.messages.IMessage)
	 */
	public void sendRequest(IMessage requestMessage) {
		Assert.assertNotNull("Output stream is Null", getOutputStream());
		Assert.assertNotNull("Message is Null", requestMessage);
		try {
			getOutputStream().writeObject(requestMessage);
			getOutputStream().flush();
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#readMessage(pcmm.messages.IMessage)
	 */
	public void readMessage(IMessage message) {
		Assert.assertNotNull("Input stream is Null", getInputStream());
		Assert.assertNotNull("Message is Null", message);
		try {
			Object msg = getInputStream().readObject();
			Assert.assertTrue("Can't understand message.",
					(msg instanceof IMessage));
			message.setMessagType(((IMessage) msg).getMessageType());
			message.setContent(((IMessage) msg).getContent());
		} catch (IOException e) {
			logger.severe(e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.severe(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#tryConnect(java.lang.String, int)
	 */
	public boolean tryConnect(String address, int port) {
		try {
			InetAddress addr = InetAddress.getByName(address);
			tryConnect(addr, port);
		} catch (UnknownHostException e) {
			logger.severe(e.getMessage());
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#tryConnect(java.net.InetAddress, int)
	 */
	public boolean tryConnect(InetAddress address, int port) {
		try {
			setSocket(new Socket(address, port));
			setOutputStream(new ObjectOutputStream(socket.getOutputStream()));
			setInputStream(new ObjectInputStream(socket.getInputStream()));
		} catch (IOException e) {
			logger.severe(e.getMessage());
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMClient#disconnect()
	 */
	public boolean disconnect() {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				logger.severe(e.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket
	 *            the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @return the outputStream
	 */
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @param outputStream
	 *            the outputStream to set
	 */
	public void setOutputStream(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * @return the inputStream
	 */
	public ObjectInputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
	}

}
