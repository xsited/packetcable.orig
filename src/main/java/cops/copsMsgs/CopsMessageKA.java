/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package copsMsgs;

import copsObjs.CopsCommonHeader;
import java.net.InetAddress;

/**
 *
 * @author badaro
 */
public class CopsMessageKA {

	private CopsCommonHeader header;

	public CopsMessageKA(short clientType)
	{
		this.header = new CopsCommonHeader((byte) 9, clientType);
	}

	public byte[] Packet()
	{
		header.setLength(header.Packet().length);
		byte[] pacote = header.Packet();

		return pacote;
	}
}