/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cops;

import auxClasses.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class PEPThreadReplyer extends Thread {

    private Socket client;
    private Cache cache;
    private byte[] rPacket;
    private boolean skip = true;
    private Integer position;
    private Integer handleInt;
    private short decision;
    private byte handleHelp;
    private String handle = "";
    private int line;
    ByteBuffer bb = null;
    private Integer queue[];

    public PEPThreadReplyer() {
    }

    public PEPThreadReplyer(Socket client, Cache cache) {
        this.client = client;
        this.cache = cache;
    }

    @Override
    public void run() {
        super.run();

        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(PEPThreadReplyer.class.getName()).log(Level.SEVERE, null, ex);
        }


        for (int i = 0; i <= cache.getnSessions(); i++) {
            if (cache.getLineDecision(i) == 1) {

                this.queue = cache.getQueueFromLine(i);
                cache.setQueueNull(i);

                for (int j = 0; j <= this.queue.length; j++) {
                    String rep = String.valueOf(this.queue) + " " + "a";
                    try {
                        out.write(rep.getBytes());
                    } catch (IOException ex) {
                        Logger.getLogger(PEPThreadReplyer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


            }
            if (cache.getLineDecision(i) == 2) {
                for (int j = 0; j <= this.queue.length; j++) {
                    String rep = String.valueOf(this.queue) + " " + "d";
                    try {
                        out.write(rep.getBytes());
                    } catch (IOException ex) {
                        Logger.getLogger(PEPThreadReplyer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }



    }
}
