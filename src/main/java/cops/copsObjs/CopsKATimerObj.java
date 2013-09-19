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
public class CopsKATimerObj extends CopsObj {

    private short TimerValue;
    private final short Reserved = 0;

    public CopsKATimerObj(short TimerValue) {
        super((byte) 10, (byte) 1);
        this.TimerValue = TimerValue;
    }

    @Override
    public byte[] Packet() {
        ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length+4);
        super.setLength((short)(super.Packet().length+4));
        PacoteTemp.put(super.Packet());
        PacoteTemp.putShort(this.Reserved);
        PacoteTemp.putShort(this.TimerValue);
        return PacoteTemp.array();
    }
}
