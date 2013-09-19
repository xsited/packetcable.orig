/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copsObjs;

import java.nio.ByteBuffer;

/**
 *
 * @author pedro
 */
public class CopsAcctTimerObj extends CopsObj {

	private short ACT;
	private final short reserved = 0;

	public CopsAcctTimerObj(short ACT) {
		super((byte) 15, (byte) 1);
		this.ACT = ACT;
	}

	@Override
	public byte[] Packet() {
		ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length + 4);
		super.setLength((short) (super.Packet().length + 4));
		PacoteTemp.put(super.Packet());
		PacoteTemp.putShort(this.reserved);
		PacoteTemp.putShort(ACT);

		return PacoteTemp.array();
	}
}
