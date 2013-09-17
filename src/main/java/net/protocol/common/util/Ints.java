package net.protocol.common.util;

/**
 * @author jinhongw@gmail.com
 */
public class Ints {

	/**
	 * Returns a multiple of four-octets in length.
	 * 
	 * @param length
	 * @return A multiple of four-octets in length
	 */
	public static int multiple4(int length) {
		if ((length % 4) == 0)
			return length;
		return ((length / 4) + 1) * 4;
	}

	/**
	 * Returns a padding size to align on a 32-bit(four-octets) boundary for
	 * this size.
	 * 
	 * @param size
	 * @return The length of the padding
	 */
	public static int paddingSize(int size) {
		int residue = size - ((size >> 2) << 2);
		if (residue == 0)
			return 0;
		return 4 - residue;
	}
}
