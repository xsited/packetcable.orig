/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cops;

import auxClasses.PolicyRepository;
import copsMsgs.CopsMessageCAT;
import copsMsgs.CopsMessageCC;
import copsMsgs.CopsMessageDEC;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valter Vicente - 39360 <valtervicente@ua.pt>
 */
public class PDPThread extends Thread {

    private Socket socket;
    private PolicyRepository pr;
    private DataInputStream in;
    private DataOutputStream out;
    private byte[] array;
    private ByteBuffer bb;
    private int checkOC;
    private byte handleHelp;
    private String handle = "";

    public PDPThread() {
    }

    public PDPThread(Socket socket, PolicyRepository pr) {
        this.socket = socket;
        this.pr = pr;
    }

    @Override
    public void run() {
        try {
            super.run();

            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.in.read(array);

            bb = ByteBuffer.wrap(array);	// Convert array to ByteBuffer
            checkOC = bb.get(1);

            // Check if its our ClientType
            if (bb.getShort() == (short) 6969) {
                bb.getInt();
                bb.getInt();

                switch (checkOC) {
                    // Its a CopsMessageREQ
                    case 1:

                        // Get ClientHandle
                        while (true) {
                            if ((this.handleHelp = bb.get()) == 0) {
                                break;
                            }
                            this.handle = this.handle + String.valueOf(this.handleHelp - '0');
                        }

                        // Context
                        if (bb.getShort() == (short) 1 && bb.getShort() == (short) 1) {
                            // In-Interface
                            byte ipSrc[] = {bb.get(), bb.get(), bb.get(), bb.get()};
                            InetAddress ipS = InetAddress.getByAddress(ipSrc);
                            int ifIndexIn = bb.getInt();

                            // Out-Interface
                            byte ipDst[] = {bb.get(), bb.get(), bb.get(), bb.get()};
                            InetAddress ipD = InetAddress.getByAddress(ipDst);
                            int ifIndexOut = bb.getInt();

                            // ClientSI
                            int clientCtype = bb.get();
                            byte ClientSIHelp;
                            String ClientSI = "";
                            int protocol = 0;
                            int srcPort = 0;
                            int dstPort = 0;

                            while (true) {
                                if ((ClientSIHelp = bb.get()) == 0) {
                                    break;
                                }
                                ClientSI = ClientSI + String.valueOf(ClientSIHelp - '0');

                                String[] tokens = ClientSI.split(",");
                                protocol = Integer.valueOf(tokens[0]);
                                srcPort = Integer.valueOf(tokens[1]);
                                dstPort = Integer.valueOf(tokens[2]);
                            }

                            String policyString = "allow " + ipS.toString() + " " + ipD.toString() + " " + protocol + " " + srcPort + " " + dstPort;

                            String line = null;
                            short commandCode = 2;
                            short flags = 0;

                            BufferedReader br = new BufferedReader(new FileReader("policies.txt"));

                            while ((line = br.readLine()) != null) {

                                if (line.trim().equalsIgnoreCase(policyString)) {
                                    commandCode = 1;
                                    break;
                                }
                            }

                            br.close();

                            boolean[] decisions = {true};
                            CopsMessageDEC dec = new CopsMessageDEC((short) 6969, this.handle, (short) 0, (short) 0, (byte) 1, null, commandCode, flags, decisions);
                            out.write(dec.Packet());
                        }

                        break;
                    // Its a CopsMessageRPT
                    case 3:
                        break;
                    // Its a CopsMessageDRQ
                    case 4:
                        break;
                    // Its a CopsMessageOPN
                    case 6:
                        boolean[] decisions = {true};
                        CopsMessageCAT cat = new CopsMessageCAT((short) 6969, (short) 32767, (short) 32767, decisions);
                        out.write(cat.Packet());
                        break;
                    // Its a CopsMessageCC
                    case 8:
                        break;
                    // Its a CopsMessageKA
                    case 9:
                        break;
                    // Its a CopsMessageSSC
                    case 10:
                        break;
                    default:
                        System.out.println("Operation not expected by PDP.");
                        break;
                }
            } else {
                // Sends Client-Close
                boolean[] decisions = {false, false};
                CopsMessageCC cc = new CopsMessageCC((short) 6969, (short) 6, (short) 0, null, (short) 0, decisions);
                out.write(cc.Packet());
            }
        } catch (IOException ex) {
            Logger.getLogger(PDPThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
