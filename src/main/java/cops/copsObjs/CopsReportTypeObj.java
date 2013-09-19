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
public class CopsReportTypeObj extends CopsObj {

    private short ReportType;
    private final short Reserved = 0;

    public CopsReportTypeObj(short reportType) {
        super((byte) 12, (byte) 1);
        this.ReportType = reportType;
    }

    @Override
    public byte[] Packet() {
        ByteBuffer PacoteTemp = ByteBuffer.allocate(super.Packet().length+4);
		super.setLength((short) (super.Packet().length+4));
		PacoteTemp.put(super.Packet());
        PacoteTemp.putShort(this.ReportType);
        PacoteTemp.putShort(this.Reserved);
        return PacoteTemp.array();
    }
}
