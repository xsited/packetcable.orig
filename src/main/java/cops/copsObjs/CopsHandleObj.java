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
public class CopsHandleObj extends CopsObj {

	private String ClientHandle;

	public CopsHandleObj(String ClientHandle) {
		super((byte) 1, (byte) 1);
		this.ClientHandle = ClientHandle;
	}

	@Override
	public byte[] Packet() {
		ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length + this.ClientHandle.getBytes().length);
		int i = this.ClientHandle.getBytes().length % 4;

									// ISTO É PARA TRATAR!
		if(i == 0)
			super.setLength((short) (super.Packet().length + this.ClientHandle.getBytes().length));
		else
			super.setLength((short) (super.Packet().length + this.ClientHandle.getBytes().length + 4 - 1));
		PacoteTemp.put(super.Packet());
		PacoteTemp.put(this.ClientHandle.getBytes());
		return PacoteTemp.array();
	}
}
