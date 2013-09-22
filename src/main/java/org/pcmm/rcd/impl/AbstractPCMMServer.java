/**
 * 
 */
package org.pcmm.rcd.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.pcmm.messages.IMessage;
import org.pcmm.rcd.IPCMMServer;
import org.pcmm.state.IState;

/*
 * (non-Javadoc)
 * 
 * @see pcmm.rcd.IPCMMServer
 */
public class AbstractPCMMServer implements IPCMMServer {
	private Logger logger;
	/*
	 * A ServerSocket to accept messages ( OPN requests)
	 */
	private ServerSocket serverSocket;

	private boolean keepAlive;
	/*
	 * 
	 */
	private int port;

	private Map<Socket, IPCMMClientHandler> handlersPool = new HashMap<Socket, IPCMMServer.IPCMMClientHandler>();

	protected AbstractPCMMServer() {
		this(DEFAULT_LISTENING_PORT);
	}

	protected AbstractPCMMServer(int port) {
		Assert.assertTrue(port >= 0 && port <= 65535);
		this.port = port;
		keepAlive = true;
		logger = Logger.getLogger(getClass().getName());
		logger.setLevel(Level.SEVERE);
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMServer#startServer()
	 */
	public void startServer() {
		new Thread(new Runnable() {
			public void run() {
				while (keepAlive) {
					try {
						Socket socket = serverSocket.accept();
						handlersPool.put(socket,
								getPCMMClientHandler(socket, null, null));
					} catch (IOException e) {
						logger.severe(e.getMessage());
					}
				}
			}
		}).start();
	}

	protected IPCMMClientHandler getPCMMClientHandler(Socket socket,
			Thread senderThread, Thread receiverThread) {
		return new AbstractPCMMClientHandler(socket, senderThread,
				receiverThread);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMServer#stopServer()
	 */
	public void stopServer() {
		keepAlive = false;
		// XXX handle force stop server
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.state.IStateful#recordState()
	 */
	public void recordState() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.state.IStateful#getRecoredState()
	 */
	public IState getRecoredState() {
		return null;
	}

	/**
	 * @return the serverSocket
	 */
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	/**
	 * @param serverSocket
	 *            the serverSocket to set
	 */
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMServer.IPCMMClientHandler
	 */
	public static class AbstractPCMMClientHandler extends Thread implements
			IPCMMClientHandler {
		private Logger clientLogger = Logger
				.getLogger(AbstractPCMMClientHandler.class.getName());
		/**
		 * communication socket
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

		private boolean sendCCMessage;

		/**
		 * Thread which handles sending messages to the client.
		 */
		private Thread sender;
		/**
		 * thread which handles receiving from client.
		 */
		private Thread receiver;

		public AbstractPCMMClientHandler(Socket socket, Runnable sender,
				Runnable receiver) {
			this.socket = socket;
			this.sender = new Thread(sender);
			this.receiver = new Thread(receiver);
		}

		@Override
		public void run() {
			receiver.start();
			sender.start();
		}

		public void close() {
			// XXX send CC message
			sendCCMessage = true;
			IMessage message = null;
			sendResponse(message);
			if (socket != null && !socket.isClosed())
				try {
					socket.close();
				} catch (IOException e) {
					clientLogger.severe(e.getMessage());
				}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * pcmm.rcd.IPCMMServer.IPCMMClientHandler#sendResponse(pcmm.messages
		 * .IMessage)
		 */
		public void sendResponse(IMessage responseMessage) {
			Assert.assertNotNull("message is null", responseMessage);
			Assert.assertNotNull("Output stream is NULL, check connection",
					outputStream);
			try {
				synchronized (outputStream) {
					outputStream.writeObject(responseMessage);
					outputStream.flush();
				}
			} catch (IOException e) {
				clientLogger.severe(e.getMessage());
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * pcmm.rcd.IPCMMServer.IPCMMClientHandler#receiveMessage(pcmm.messages
		 * .IMessage)
		 */
		public void receiveMessage(IMessage inMessage) {
			Assert.assertNotNull("You should provide a message container",
					inMessage);
			Assert.assertNotNull("Input stream is NULL, check connection",
					inputStream);
			try {
				synchronized (inputStream) {
					Object obj = inputStream.readObject();
					Assert.assertTrue("Unhandled message type",
							obj instanceof IMessage);
					inMessage.setContent(((IMessage) obj).getContent());
					inMessage.setMessagType(((IMessage) obj).getMessageType());
				}
			} catch (ClassNotFoundException e) {
				clientLogger.severe(e.getMessage());
			} catch (IOException e) {
				clientLogger.severe(e.getMessage());
			}

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

}
