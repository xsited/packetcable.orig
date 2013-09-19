/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cops;

import auxClasses.Cache;
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
public class PEPThreadCacheUpdate extends Thread {

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

    public PEPThreadCacheUpdate(Socket client, Cache cache) {
        this.client = client;
        this.cache = cache;
    }

    @Override
    public void run() {
        super.run();

        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            in = new DataInputStream(this.client.getInputStream());
            out = new DataOutputStream(this.client.getOutputStream());
            in.read(rPacket);
        } catch (IOException ex) {
            Logger.getLogger(PEPThreadCacheUpdate.class.getName()).log(Level.SEVERE, null, ex);

        }

        bb = ByteBuffer.wrap(rPacket);	// Convert array to ByteBuffer

        // Check if its our ClientType
        if (bb.getShort(2) == (short) 6969) {
            bb.getInt();
            bb.getShort();

            switch ((int) bb.get()) {
                // Its a CopsMessageCAT
                case 1:
                    bb.get();

                    while (skip) {
                        if ((this.handleHelp = bb.get()) == 0) {
                            break;
                        }
                        this.handle = this.handle + String.valueOf(this.handleHelp - '0');

                    }

                    this.handleInt = Integer.valueOf(this.handle);

                    if (bb.get() == 1) {
                        decision = bb.getShort();
                    }


                    line = this.cache.CacheLineDiscover(handleInt);
                    cache.changeCacheLineDecision(line, decision);

                    break;

                case 8:
                    System.out.println("PEP not allowed on Destination PDP.");
                    System.exit(-1);
            }
        }

    }
}
