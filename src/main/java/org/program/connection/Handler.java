package org.program.connection;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Handler implements Runnable {
    private Socket sock = null;
    private InputStream sockInput = null;
    private OutputStream sockOutput = null;
    private Thread myThread = null;

    public Handler(Socket sock) throws IOException {
        this.sock = sock;
        sockInput = sock.getInputStream();
        sockOutput = sock.getOutputStream();
        this.myThread = new Thread(this);
        // Note that if we call myThread.start() now, we run the risk
        // of this new thread calling run before we're finished
        // constructing.  We can't count on the fact that we call
        // .start() last - javac or the jvm might have reordered the
        // above lines.  The class constructing us must wait for the
        // constructor to return and then call start() on us.
        System.out.println("Handler: New handler created.");
    }

    public void start() {
        myThread.start();
    }
    
    // All this method does is wait for some bytes from the
    // connection, read them, then write them back again, until the
    // socket is closed from the other side.
    public void run() {
        System.out.println("Handler: Handler run() starting.");
        while(true) {
            byte[] buf=new byte[1024];
            int bytes_read = 0;
            try {
                // This call to read() will wait forever, until the
                // program on the other side either sends some data,
                // or closes the socket.
                bytes_read = sockInput.read(buf, 0, buf.length);
                if(bytes_read < 0) {
                    System.err.println("Handler: Tried to read from socket, read() returned < 0,  Closing socket.");
                    break;
                }
                System.err.println("Handler: Received "+bytes_read
                                   +" bytes, sending them back to client, data="
                                   +(new String(buf, 0, bytes_read)));
                sockOutput.write(buf, 0, bytes_read);
                // This call to flush() is optional - we're saying go
                // ahead and send the data now instead of buffering
                // it.
                sockOutput.flush();
            }
            catch (Exception e){
                e.printStackTrace(System.err);
                break;
            }
        }

        try {
            System.err.println("Handler:Closing socket.");
            sock.close();
        }
        catch (Exception e){
            System.err.println("Handler: Exception while closing socket, e="+e);
            e.printStackTrace(System.err);
        }

    }
}

