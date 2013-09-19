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
public class CopsClientSIObj extends CopsObj {

    private String ClientSI;

    public CopsClientSIObj(byte Ctype, String ClientSI) {
        super((byte) 9, (byte) Ctype);
        this.ClientSI = ClientSI;
    }

    @Override
    public byte[] Packet() {
        ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length+this.ClientSI.getBytes().length);
        super.setLength((short)(super.Packet().length+this.ClientSI.getBytes().length));
        PacoteTemp.put(super.Packet());
        PacoteTemp.put(this.ClientSI.getBytes());
        return PacoteTemp.array();
    }
}