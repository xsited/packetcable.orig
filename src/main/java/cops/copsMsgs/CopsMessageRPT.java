/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package copsMsgs;

import auxClasses.PacketAssembler;
import cops.*;
import copsObjs.CopsHandleObj;
import copsObjs.CopsReportTypeObj;
import copsObjs.CopsClientSIObj;
import copsObjs.CopsCommonHeader;

/**
 *
 * @author badaro
 */
public class CopsMessageRPT {

	private boolean[] decisions;
	private CopsCommonHeader header;
	private CopsHandleObj handle;
	private	CopsReportTypeObj report;
	private	CopsClientSIObj client;

	public CopsMessageRPT(short clientType, String ClientHandle, short reportType, byte Ctype, String ClientSI, boolean[] decisions)
	{
		this.header = new CopsCommonHeader((byte) 3, clientType);
		this.handle =  new CopsHandleObj(ClientHandle);
		this.report = new CopsReportTypeObj(reportType);
		this.client = new CopsClientSIObj(Ctype, ClientSI);
		this.decisions = decisions;
	}

	public byte[] Packet()
	{
		byte[] pacote = null;
		PacketAssembler assembler = new PacketAssembler();

		if(decisions[0] == true)
			pacote = this.client.Packet();

		pacote = assembler.Packet(handle.Packet(), assembler.Packet(report.Packet(), pacote));
		header.setLength(pacote.length + header.Packet().length);
		pacote = assembler.Packet(header.Packet(), pacote);

		return pacote;
	}
}