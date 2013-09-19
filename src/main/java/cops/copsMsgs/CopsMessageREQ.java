/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copsMsgs;

import auxClasses.PacketAssembler;
import cops.*;
import copsObjs.CopsLPDPDecisionObj;
import copsObjs.CopsInInterfaceObj;
import copsObjs.CopsHandleObj;
import copsObjs.CopsOutInterfaceObj;
import copsObjs.CopsClientSIObj;
import copsObjs.CopsContext;
import copsObjs.CopsCommonHeader;
import java.net.InetAddress;

/**
 *
 * @author badaro
 */
public class CopsMessageREQ {

	private boolean[] decisions;
	private CopsCommonHeader header;
	private CopsHandleObj handle;
	private CopsContext context;
	private CopsInInterfaceObj in;
	private CopsOutInterfaceObj out;
	private CopsClientSIObj client;
	private CopsLPDPDecisionObj lpdp;

	public CopsMessageREQ(short clientType, String ClientHandle, short rType, short mType, InetAddress inIpv4Addr,
			int inIfindex, InetAddress outIpv4Addr, int outIfindex, byte clientCtype, String ClientSI, byte lpdpCtype, String lpdpDecision,
			short CommandCode, short Flags, boolean[] decisions) {
		this.header = new CopsCommonHeader((byte) 1, clientType);
		this.handle = new CopsHandleObj(ClientHandle);
		this.context = new CopsContext(rType, mType);
		this.in = new CopsInInterfaceObj(inIpv4Addr, inIfindex);
		this.out = new CopsOutInterfaceObj(outIpv4Addr, outIfindex);
		this.client = new CopsClientSIObj(clientCtype, ClientSI);

		if (lpdpCtype == 1) {
			this.lpdp = new CopsLPDPDecisionObj(lpdpCtype, CommandCode, Flags);
		} else {
			this.lpdp = new CopsLPDPDecisionObj(lpdpCtype, lpdpDecision);
		}

		this.decisions = decisions;
	}

	public byte[] Packet() {
		byte[] pacote = null;
		PacketAssembler assembler = new PacketAssembler();

		if (decisions[3] == true) {
			pacote = this.lpdp.Packet();
		}

		if (decisions[2] == true) {
			pacote = assembler.Packet(client.Packet(), pacote);
		}

		if (decisions[1] == true) {
			pacote = assembler.Packet(out.Packet(), pacote);
		}

		if (decisions[0] == true) {
			pacote = assembler.Packet(in.Packet(), pacote);
		}

		pacote = assembler.Packet(handle.Packet(), assembler.Packet(context.Packet(), pacote));
		header.setLength(pacote.length + header.Packet().length);
		pacote = assembler.Packet(header.Packet(), pacote);

		return pacote;
	}
}