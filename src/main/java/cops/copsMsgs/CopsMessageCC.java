/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package copsMsgs;

import auxClasses.PacketAssembler;
import cops.*;
import copsObjs.CopsErrorObj;
import copsObjs.CopsPDPRedirAddrObj;
import copsObjs.CopsCommonHeader;
import java.net.InetAddress;

/**
 *
 * @author badaro
 */
public class CopsMessageCC {

	private boolean[] decisions;
	private CopsCommonHeader header;
	private CopsErrorObj error;
	private	CopsPDPRedirAddrObj pdp;

	public CopsMessageCC(short clientType, short ErrorCode, short ErrorSCode, InetAddress IpAddr, short tcpPort, boolean[] decisions)
	{
		this.header = new CopsCommonHeader((byte) 8, clientType);
		this.error =  new CopsErrorObj(ErrorCode, ErrorSCode);
		this.pdp = new CopsPDPRedirAddrObj(IpAddr, tcpPort);
		this.decisions = decisions;
	}

	public byte[] Packet()
	{
		byte[] pacote = null;
		PacketAssembler assembler = new PacketAssembler();

		if(decisions[0] == true)
			pacote = this.pdp.Packet();

		pacote = assembler.Packet(error.Packet(), pacote);
		header.setLength(pacote.length + header.Packet().length);
		pacote = assembler.Packet(header.Packet(), pacote);

		return pacote;
	}
}