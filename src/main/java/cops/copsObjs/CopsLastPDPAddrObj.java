/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package copsObjs;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 *
 * @author pedro
 */
public class CopsLastPDPAddrObj extends CopsObj{
    private InetAddress Ipv4Addr;

    public CopsLastPDPAddrObj(InetAddress Ipv4Addr) {
        super((byte)14,(byte)1);
        this.Ipv4Addr = Ipv4Addr;
    }

    @Override
    public byte[] Packet(){
        ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length+8);
		super.setLength((short) (super.Packet().length+8));
		PacoteTemp.put(super.Packet());
        PacoteTemp.put(this.Ipv4Addr.getAddress());
        return PacoteTemp.array();
    }

}
