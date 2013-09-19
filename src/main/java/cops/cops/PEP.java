/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cops;

/**
 *
 * @author Pedro Bento
 */
import auxClasses.Cache;
import copsMsgs.CopsMessageOPN;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PEP {

    public static void main(String[] args) throws UnknownHostException, IOException {

        ByteBuffer bb = null;
        InetAddress dummy = null;
        Socket sSocket = null;
        Socket client = null;
        byte[] rPacket = null;
        DataInputStream inS = null;
        DataOutput outS = null;
        Cache cache = null;
        PEPThreadReq thread = null;
        PEPThreadCacheUpdate thread2 = null;
        PEPThreadReplyer thread3 = null;
        DataInputStream inC = null;
        DataOutputStream outC = null;

        boolean[] decisions = {false, false};


        CopsMessageOPN cOpen = new CopsMessageOPN((short) 69696, "Fofi", (byte) 0, "", dummy, decisions);


        try {
            sSocket = new Socket("172.16.1.1", 1234);
            inS = new DataInputStream(sSocket.getInputStream());
            outS = new DataOutputStream(sSocket.getOutputStream());
            client = new Socket("172.16.1.2", 3288);
            inC = new DataInputStream(client.getInputStream());
            outC = new DataOutputStream(client.getOutputStream());

        } catch (IOException ex) {
            Logger.getLogger(PEP.class.getName()).log(Level.SEVERE, null, ex);
        }

        outC.write(cOpen.Packet());
        inC.read(rPacket);


        bb = ByteBuffer.wrap(rPacket);

        // Check if its our ClientType
        if (bb.getShort(2) == (short) 6969) {// Convert array to ByteBuffer


            switch ((int) bb.get(1)) {
                // Its a CopsMessageCAT
                case 7:

                    System.out.println("PEP accepted");
                    break;

                case 8:
                    System.out.println("PEP not allowed on Destination PDP.");
                    System.exit(-1);
            }
        }


        while (true) {
            try {
                inS.read(rPacket);
            } catch (IOException ex) {
                Logger.getLogger(PEP.class.getName()).log(Level.SEVERE, null, ex);
            }

            thread = new PEPThreadReq(rPacket.toString(), client, cache);
            thread.start();

            thread2 = new PEPThreadCacheUpdate(client, cache);
            thread2.start();

            thread3 = new PEPThreadReplyer(sSocket, cache);
            thread3.start();


        }
    }
}
