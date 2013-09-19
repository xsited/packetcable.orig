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
public class CopsPDPRedirAddrObj extends CopsObj {

    private InetAddress Ipv4Addr;
    private short TcpPort;
    private final short reserved = 0;
    public CopsPDPRedirAddrObj(InetAddress IpAddr,short tcpPort) {
        super((byte)13,(byte)1);
        this.Ipv4Addr=IpAddr;
        this.TcpPort=tcpPort;
    }

    @Override
    public byte[] Packet(){
        ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length+8);
		super.setLength((short) (super.Packet().length+8));
		PacoteTemp.put(super.Packet());
        PacoteTemp.put(this.Ipv4Addr.getAddress());
        PacoteTemp.putShort(this.reserved);
        PacoteTemp.putShort(this.TcpPort);
        return PacoteTemp.array();
    }

}
