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
public class CopsCommonHeader {

    public final static byte REQ = 1;
    public final static byte DEC = 2;
    public final static byte RPT = 3;
    public final static byte DRQ = 4;
    public final static byte SSQ = 5;
    public final static byte OPN = 6;
    public final static byte CAT = 7;
    public final static byte CC = 8;
    public final static byte KA = 9;
    public final static byte SSC = 10;
    private byte VersionFlags;
    private byte OpCode;
    private short ClientType;
    private int Length;

    public CopsCommonHeader() {
        this.VersionFlags = 0x10;
        this.OpCode = 0;
        this.ClientType = 0;
        this.Length = 0;
    }

    public CopsCommonHeader(byte opCode, short clientType) {
        this.VersionFlags = 0x10;
        this.OpCode = opCode;
        this.ClientType = clientType;
        this.Length = 0;
    }

    public CopsCommonHeader(byte opCode) {
        this.VersionFlags = 0x10;
        this.OpCode = opCode;
        this.ClientType = 0;
        this.Length = 0;
    }

    public void setLength(int Length) {
        this.Length = Length;
    }


    public byte[] Packet() {
        ByteBuffer PacoteTemp = ByteBuffer.allocate(8);
        PacoteTemp.put(0, this.VersionFlags);
        PacoteTemp.put(1, this.OpCode);
        PacoteTemp.putShort(2, this.ClientType);
        PacoteTemp.putInt(4, this.Length);
        return PacoteTemp.array();
    }
}