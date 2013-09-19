/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cops;

import auxClasses.Cache;
import auxClasses.StringParser;
import copsMsgs.CopsMessageREQ;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class PEPThreadReq extends Thread {

    private String rPacket;
    private Socket client;
    private Cache cache;
    private Integer[][] queue;
    private boolean skip = false;
    private Integer position;

    public PEPThreadReq(String rPacket, Socket client, Cache cache) {
        this.rPacket = rPacket;
        this.client = client;
        this.cache = cache;
    }

    @Override
    public void run() {
        super.run();
        //String coisaDoRouter = "id=1 indev=2 outdev=3 src=64.4.16.37 dst=172.16.1.2 prot=6 src_port=1863 dst_port=51576";


        StringParser parser = new StringParser(this.rPacket);
        parser.parser();



        try {

            if (cache.newCacheLine(parser.getPolicerReq())) {
                this.skip = true;
            }
            cache.changeCacheLineAddElem(parser.getPolicerReq(), Integer.valueOf(parser.getId()));
        } catch (UnknownHostException ex) {
            Logger.getLogger(PEPThreadReq.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean listControl = false;
        boolean[] decision = {true, true, false, true};

        InetAddress pdpAddr = null;
        InetAddress addrIn = null;
        InetAddress addrOut = null;

        if (!this.skip) {
            try {
                addrIn = InetAddress.getByName(parser.getSrc());
                addrOut = InetAddress.getByName(parser.getDst());

            } catch (UnknownHostException ex) {
                Logger.getLogger(PEPThreadReq.class.getName()).log(Level.SEVERE, null, ex);
            }


            CopsMessageREQ request = new CopsMessageREQ((short) 6969, String.valueOf(parser.getId()), (short) 0, (short) 0, addrIn, Integer.getInteger(parser.getInDev()), addrOut, Integer.getInteger(parser.getOutDev()), (byte) 2, parser.getProt() + "," + parser.getSrc_port() + "," + parser.getDst_port(), (byte) 0, "lpdpDecision", (short) 0, (short) 0, decision);


            DataOutputStream out = null;
            try {
                out = new DataOutputStream(this.client.getOutputStream());
                out.write(request.Packet());
            } catch (IOException ex) {
                Logger.getLogger(PEP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
