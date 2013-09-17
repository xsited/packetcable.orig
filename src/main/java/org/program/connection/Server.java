package org.program.connection;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Server {
    private int serverPort = 0;
    private ServerSocket serverSock = null;
    private Socket sock = null;

    public Server(int serverPort) throws IOException {
        this.serverPort = serverPort;

        serverSock = new ServerSocket(serverPort);
    }
    
    public void waitForConnections() {
        while (true) {
            try {
                sock = serverSock.accept();
                System.err.println("Server:Accepted new socket, creating new handler for it.");
                Handler handler = new Handler(sock);
                handler.start();
                System.err.println("Server:Finished with socket, waiting for next connection.");
            }
            catch (IOException e){
                e.printStackTrace(System.err);
            }
        }
    }

    public static void stop() {
	// server.close();

    }

    public static void start() {
        int port = 3918;

        Server server = null;
        try {
            server = new Server(port);
        }
        catch (IOException e){
            e.printStackTrace(System.err);
        }
        server.waitForConnections();
    }
}

