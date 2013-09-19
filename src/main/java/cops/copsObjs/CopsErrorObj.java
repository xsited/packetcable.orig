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
public class CopsErrorObj extends CopsObj {

	private short ErrorCode;
	private short ErrorSCode;

	public CopsErrorObj(short ErrorCode, short ErrorSCode) {
		super((byte) 8, (byte) 1);
		this.ErrorCode = ErrorCode;
		this.ErrorSCode = ErrorSCode;
	}

	@Override
	public byte[] Packet() {
		ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length + 4);
		super.setLength((short) (super.Packet().length + 4));
		PacoteTemp.put(super.Packet());
		PacoteTemp.putShort(this.ErrorCode);
		PacoteTemp.putShort(this.ErrorSCode);
		return PacoteTemp.array();
	}
}
