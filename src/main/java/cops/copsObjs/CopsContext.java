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
public class CopsContext extends CopsObj {

    private Short Rtype;
    private Short Mtype;

    public CopsContext(Short rType,Short mType) {
        super((byte) 2, (byte) 1);
        this.Rtype = rType;
        this.Mtype = mType;
    }

    @Override
    public byte[] Packet(){
        ByteBuffer PacoteTemp = ByteBuffer.allocate (super.Packet().length+4);
        super.setLength((short)(super.Packet().length+4));
        PacoteTemp.put(super.Packet());
        PacoteTemp.putShort(this.Rtype);
        PacoteTemp.putShort(this.Mtype);
       return PacoteTemp.array();
    }
}