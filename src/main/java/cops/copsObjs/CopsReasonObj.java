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
public class CopsReasonObj extends CopsObj {

	private short ReasonC;
	private short ReasonSc;

	public CopsReasonObj(short ReasonC, short ReasonSc) {
		super((byte) 5, (byte) 1);
		this.ReasonC = ReasonC;
		this.ReasonSc = ReasonSc;
	}

	@Override
	public byte[] Packet() {
		ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length + 4);
		super.setLength((short) (super.Packet().length + 4));
		PacoteTemp.put(super.Packet());
		PacoteTemp.putShort(this.ReasonC);
		PacoteTemp.putShort(this.ReasonSc);
		return PacoteTemp.array();
	}
}
