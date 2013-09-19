/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copsObjs;

import java.nio.ByteBuffer;

/**
 *
 * @author Pedro Bento
 */
public class CopsObj {
    private short length;
    private byte CNum;
    private byte Ctype;

    public CopsObj(byte CNum, byte Ctype) {
        this.CNum = CNum;
        this.Ctype = Ctype;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public byte[] Packet() {
        ByteBuffer PacoteTemp = ByteBuffer.allocate(4);
        PacoteTemp.putShort(this.length);
        PacoteTemp.put(this.CNum);
        PacoteTemp.put(this.Ctype);
        return PacoteTemp.array();
    }
}
