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
public class CopsOutInterfaceObj extends CopsObj {

    private InetAddress Ipv4Addr;
	private int Ifindex;

	public CopsOutInterfaceObj(InetAddress Ipv4Addr, int Ifindex) {
		super((byte) 4, (byte) 1);
        this.Ipv4Addr=Ipv4Addr;
		this.Ifindex = Ifindex;
	}

	@Override
	public byte[] Packet() {
		ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length+8);
		super.setLength((short) (super.Packet().length+8));
		PacoteTemp.put(super.Packet());
        PacoteTemp.put(Ipv4Addr.getAddress());
		PacoteTemp.putInt(Ifindex);
		return PacoteTemp.array();
	}
}
