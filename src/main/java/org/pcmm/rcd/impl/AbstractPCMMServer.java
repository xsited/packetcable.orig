/**
 @header@
 */
package org.pcmm.rcd.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

import org.pcmm.PCMMConstants;
import org.pcmm.PCMMProperties;
import org.pcmm.concurrent.IWorkerPool;
import org.pcmm.concurrent.impl.WorkerPool;
// import org.junit.Assert;
import org.pcmm.messages.impl.MessageFactory;
import org.pcmm.rcd.IPCMMServer;
import org.pcmm.state.IState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.umu.cops.stack.COPSHeader;
import org.umu.cops.stack.COPSMsg;

/*
 * (non-Javadoc)
 *
 * @see pcmm.rcd.IPCMMServer
 */
public abstract class AbstractPCMMServer implements IPCMMServer {
	protected Logger logger;
	/*
	 * A ServerSocket to accept messages ( OPN requests)
	 */
	private ServerSocket serverSocket;

	private Socket stopSocket;

	private volatile boolean keepAlive;
	/*
     *
     */
	private int port;

	IWorkerPool pool;

	protected AbstractPCMMServer() {
		this(Integer.valueOf(PCMMProperties.get(PCMMConstants.PCMM_PORT)));
	}

	protected AbstractPCMMServer(int port) {
		// XXX - Assert.assertTrue(port >= 0 && port <= 65535);
		this.port = port;
		keepAlive = true;
		logger = LoggerFactory.getLogger(getClass().getName());
		int poolSize = Integer.valueOf(PCMMProperties.get(PCMMConstants.PS_POOL_SIZE));
		pool = new WorkerPool(poolSize);
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMServer#startServer()
	 */
	public void startServer() {
		// execute this in a single thread executor
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			public void run() {
				while (keepAlive) {
					try {
						Socket socket = serverSocket.accept();
						if (keepAlive)
							pool.schedule(getPCMMClientHandler(socket));
						else
							socket.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
				try {
					if (stopSocket != null && stopSocket.isConnected())
						stopSocket.close();
					if (serverSocket != null && serverSocket.isBound())
						serverSocket.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		});
	}

	/**
	 * This client is used to handle requests from within the Application
	 * Manager
	 * 
	 * @param socket 
	 * @return client handler
	 */
	protected abstract IPCMMClientHandler getPCMMClientHandler(Socket socket);

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.rcd.IPCMMServer#stopServer()
	 */
	public void stopServer() {
		// set to stop
		keepAlive = false;
		try {
			if (serverSocket != null)
				stopSocket = new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
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
	public abstract class AbstractPCMMClientHandler extends AbstractPCMMClient
			implements IPCMMClientHandler {

		protected boolean sendCCMessage = false;

		public AbstractPCMMClientHandler(Socket socket) {
			super();
			setSocket(socket);
		}

		@Override
		public boolean disconnect() {
			// XXX send CC message
			sendCCMessage = true;
			/*
			 * is this really needed ?
			 */
			// if (getSocket() != null)
			// handlersPool.remove(getSocket());
			COPSMsg message = MessageFactory.getInstance().create(COPSHeader.COPS_OP_CC);
			sendRequest(message);
			return super.disconnect();
		}

	}

}
