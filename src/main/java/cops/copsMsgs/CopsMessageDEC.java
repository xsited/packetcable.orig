/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copsMsgs;

import auxClasses.PacketAssembler;
import cops.*;
import copsObjs.CopsDecisionObj;
import copsObjs.CopsErrorObj;
import copsObjs.CopsHandleObj;
import copsObjs.CopsCommonHeader;

/**
 *
 * @author badaro
 */
public class CopsMessageDEC {

	private boolean[] decisions;
	private CopsCommonHeader header;
	private CopsHandleObj handle;
	private CopsDecisionObj decision;
	private CopsErrorObj error;

	public CopsMessageDEC(short clientType, String ClientHandle, short ErrorCode, short ErrorSCode, byte Ctype, String dDecision,
			short CommandCode, short Flags, boolean[] decisions) {
		this.header = new CopsCommonHeader((byte) 2, clientType);
		this.handle = new CopsHandleObj(ClientHandle);
		this.error =  new CopsErrorObj(ErrorCode, ErrorSCode);

		if (Ctype == 1) {
			this.decision = new CopsDecisionObj(Ctype, CommandCode, Flags);
		} else {
			this.decision = new CopsDecisionObj(Ctype, dDecision);
		}

		this.decisions = decisions;
	}

	public byte[] Packet() {
		byte[] pacote = null;
		PacketAssembler assembler = new PacketAssembler();

		if (decisions[0] == true)
			pacote = this.decision.Packet();
		else
			pacote = this.error.Packet();

		pacote = assembler.Packet(handle.Packet(), pacote);
		header.setLength(pacote.length + header.Packet().length);
		pacote = assembler.Packet(header.Packet(), pacote);

		return pacote;
	}
}