/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copsMsgs;

import auxClasses.PacketAssembler;
import cops.*;
import copsObjs.CopsPEPIDObj;
import copsObjs.CopsLastPDPAddrObj;
import copsObjs.CopsClientSIObj;
import copsObjs.CopsCommonHeader;
import java.net.InetAddress;

/**
 *
 * @author badaro
 */
public class CopsMessageOPN {

	private boolean[] decisions;
	private CopsCommonHeader header;
	private CopsPEPIDObj pepid;
	private CopsClientSIObj client;
	private CopsLastPDPAddrObj last;

	public CopsMessageOPN(short clientType, String Pep, byte Ctype, String ClientSI, InetAddress Ipv4Addr, boolean[] decisions) {
		this.header = new CopsCommonHeader((byte) 6, clientType);
		this.pepid = new CopsPEPIDObj(Pep);
		this.client = new CopsClientSIObj(Ctype, ClientSI);
		this.last = new CopsLastPDPAddrObj(Ipv4Addr);
		this.decisions = decisions;
	}

	public byte[] Packet() {
		byte[] pacote = null;
		PacketAssembler assembler = new PacketAssembler();

		if (decisions[1] == true) {
			pacote = this.last.Packet();
		}

		if (decisions[0] == true) {
			pacote = assembler.Packet(client.Packet(), pacote);
		}

		pacote = assembler.Packet(pepid.Packet(), pacote);
		header.setLength(pacote.length + header.Packet().length);
		pacote = assembler.Packet(header.Packet(), pacote);

		return pacote;
	}
}