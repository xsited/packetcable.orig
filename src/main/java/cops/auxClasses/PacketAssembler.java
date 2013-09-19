/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

import java.nio.ByteBuffer;

/**
 *
 * @author Pedro Bento
 */
public class PacketAssembler {

	private byte[] Object1;
	private byte[] Object2;

	public PacketAssembler() {
	}

	public byte[] Packet(byte[] Object1, byte[] Object2)
	{

		if (Object2 == null) {
			return Object1;
		}

		if (Object1 == null) {
			return Object2;
		}

		this.Object1 = Object1;
		this.Object2 = Object2;

		byte[] pad1 = Padder(this.Object1);
		byte[] pad2 = Padder(this.Object2);

		ByteBuffer temp = ByteBuffer.allocate(pad1.length + pad2.length);

		temp.put(pad1);
		temp.put(pad2);

		return temp.array();
	}

	public byte[] Padder(byte[] Object)
	{
		int length = Object.length;
		int padValue = 0;
		ByteBuffer padding = null;

		if (length % 4 != 0) {
			padValue = 4 - (length % 4);
			padding = ByteBuffer.allocate(length + padValue);
			padding.put(Object);
			for (int i = length; i < (length + padValue); i++) {
				padding.put(i, (byte) 0);
			}
		}
		else
		{
			padding = ByteBuffer.allocate(Object.length);
			padding.put(Object);
		}

		return padding.array();
	}
}