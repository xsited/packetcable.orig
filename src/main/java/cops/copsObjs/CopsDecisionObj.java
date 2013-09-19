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
public class CopsDecisionObj extends CopsObj {

    private short CommandCode;
    private short Flags;
    private String Decision;

    public CopsDecisionObj(byte ctype, String decision) {
        super((byte) 6, (byte) ctype);
        this.Decision = decision;
    }

    public CopsDecisionObj(byte ctype, short CommandCode, short Flags) {
        super((byte) 6, (byte) ctype);
        if (ctype == 1) {
            this.CommandCode = CommandCode;
            this.Flags = Flags;
        }
    }

    @Override
    public byte[] Packet() {
        ByteBuffer PacoteTemp;
        if (this.CommandCode >= 0) {
            PacoteTemp = ByteBuffer.allocate(super.Packet().length+4);
            super.setLength((short)(super.Packet().length+4));
            PacoteTemp.put(super.Packet());
            PacoteTemp.putShort(this.CommandCode);
            PacoteTemp.putShort(this.Flags);
        } else {
            PacoteTemp = ByteBuffer.allocate(super.Packet().length+this.Decision.getBytes().length);
            super.setLength((short)(super.Packet().length+this.Decision.getBytes().length));
            PacoteTemp.put(super.Packet());
            PacoteTemp.put(this.Decision.getBytes());
        }
        return PacoteTemp.array();
    }
}