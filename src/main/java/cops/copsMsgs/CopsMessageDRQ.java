/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package copsMsgs;

import auxClasses.PacketAssembler;
import cops.*;
import copsObjs.CopsHandleObj;
import copsObjs.CopsReasonObj;
import copsObjs.CopsCommonHeader;

/**
 *
 * @author badaro
 */
public class CopsMessageDRQ {

	private CopsCommonHeader header;
	private CopsHandleObj handle;
	private	CopsReasonObj reason;

	public CopsMessageDRQ(short clientType, String ClientHandle, short ReasonC, short ReasonSc)
	{
		this.header = new CopsCommonHeader((byte) 4, clientType);
		this.handle =  new CopsHandleObj(ClientHandle);
		this.reason = new CopsReasonObj(ReasonC, ReasonSc);
	}

	public byte[] Packet()
	{
		byte[] pacote = null;
		PacketAssembler assembler = new PacketAssembler();

		pacote = assembler.Packet(handle.Packet(), reason.Packet());
		header.setLength(pacote.length + header.Packet().length);
		pacote = assembler.Packet(header.Packet(), pacote);

		return pacote;
	}
}