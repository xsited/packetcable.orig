/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package copsMsgs;

import auxClasses.PacketAssembler;
import cops.*;
import copsObjs.CopsAcctTimerObj;
import copsObjs.CopsKATimerObj;
import copsObjs.CopsCommonHeader;

/**
 *
 * @author badaro
 */
public class CopsMessageCAT {

	private boolean[] decisions;
	private CopsCommonHeader header;
	private CopsKATimerObj ka;
	private	CopsAcctTimerObj acct;

	public CopsMessageCAT(short clientType, short timerValue, short act, boolean[] decisions)
	{
		this.header = new CopsCommonHeader((byte) 7, clientType);
		this.ka =  new CopsKATimerObj(timerValue);
		this.acct = new CopsAcctTimerObj(act);
		this.decisions = decisions;
	}

	public byte[] Packet()
	{
		byte[] pacote = null;
		PacketAssembler assembler = new PacketAssembler();

		if(decisions[0] == true)
			pacote = this.acct.Packet();

		pacote = assembler.Packet(ka.Packet(), pacote);
		header.setLength(pacote.length + header.Packet().length);
		pacote = assembler.Packet(header.Packet(), pacote);

		return pacote;
	}
}