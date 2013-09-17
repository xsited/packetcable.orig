package net.protocol.common.util;

import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public class Bits {
    // unsigned 32 bit number
    public static final long MIN_U32_VALUE = 0x00000000L;
    public static final long MAX_U32_VALUE = 0xFFFFFFFFL;
    
    /**
     * Extract a 4-element byte array from source buffer
     * then use this array make unsigned 32 bit number(long).
     * <p>
     * For example: {0, 0, 1, 0} -> {0, 0, 0, 0, 0, 0, 1, 0} -> 256
     *
     * @param src The buffer from which bytes are to be retrieved[big-endian]
     * @return Unsigned32[unsigned 32 bit number - 0000****]
     */
    public static long getU32(final ByteBuffer src) {
        final byte[] dst = new byte[4];
        src.get(dst);
        final long u32 = makeU32(dst);
        if (u32 < MIN_U32_VALUE || u32 > MAX_U32_VALUE)
            throw new NumberFormatException("Invalid 32 bit unsigned value: " + u32);
        return u32;
    }
    
    /**
     * Extract a 4-element byte array from source buffer
     * then use this array make 32 bit number(long - ****0000).
     * <p>
     * For example: {0, 0, 1, 0} -> {0, 0, 1, 0, 0, 0, 0, 0} -> 0x10000000000
     *
     * @param src The buffer from which bytes are to be retrieved[big-endian]
     * @return long - ****0000[32 bit number]
     */
    public static long get32(final ByteBuffer src) {
        final byte[] dst = new byte[4];
        src.get(dst);
        return make32(dst);
    }

    /**
     * Transfers Unsigned32(long) to a 4-element byte array
     * then put this array into the given destination ByteBuffer.
     * <p>
     * For example: 256{0, 0, 0, 0, 0, 0, 1, 0} -> {0, 0, 1, 0}
     *
     * @param dst The buffer into which bytes are to be transferred[big-endian]
     * @param u32 Unsigned32[unsigned 32 bit number - 0000****]
     * @return This buffer
     */
    public static ByteBuffer putU32(final ByteBuffer dst, final long u32) {
        if (u32 < MIN_U32_VALUE || u32 > MAX_U32_VALUE)
            throw new NumberFormatException("Invalid 32 bit unsigned value: " + u32);
        dst.put(toByteArrayU32(u32));
        return dst;
    }
    
    /**
     * Transfers long[****0000] to a 4-element byte array
     * then put this array into the given destination ByteBuffer.
     * <p>
     * Long.MAX_VALUE{127, -1, -1, -1, -1, -1, -1, -1} -> {127, -1, -1, -1}
     *
     * @param dst The buffer into which bytes are to be transferred[big-endian]
     * @param l32 long - ****0000[32 bit number]
     * @return This buffer
     */
    public static ByteBuffer put32(final ByteBuffer dst, final long l32) {
        dst.put(toByteArray32(l32));
        return dst;
    }
    
	/**
	 * Returns a big-endian representation of {@code v} in a 4-element byte array.
	 * <p>
	 * For example: (int)256 -> {0, 0, 1, 0}
	 * 
	 * @param v the given int value
	 * @return a 4-element byte array
	 */
	public static byte[] toByteArray(int v) {
		return new byte[] {
			(byte) (v >> 24),
		    (byte) (v >> 16),
		    (byte) (v >> 8),
		    (byte) v};
	}
	
	/**
	 * Returns a big-endian representation of {@code v} in an 8-element byte array.
	 * <p>
	 * For example: (long)256 -> {0, 0, 1, 0}
	 * 
	 * @param v the given long value
	 * @return an 8-element byte array
	 */
	public static byte[] toByteArray(long v) {
	    return new byte[] {
	        (byte) (v >> 56),
	        (byte) (v >> 48),
	        (byte) (v >> 40),
	        (byte) (v >> 32),
	        (byte) (v >> 24),
	        (byte) (v >> 16),
	        (byte) (v >> 8),
	        (byte) v};
	}
	
	/**
	 * Returns a big-endian representation of {@code float} in a 4-element byte array.
	 * <p>
	 * For example: (float)8.0 -> {65, 0, 0, 0}
	 * 
	 * @param v the given long value
	 * @return a 4-element byte array
	 */
	public static byte[] toByteArray(float v) {
		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.putFloat(v);
		buf.flip();
		return buf.array();
	}
	
	/**
	 * Returns a big-endian representation of {@code double} in an 8-element byte array.
	 * <p>
	 * For example: (double)8.0 -> {64, 32, 0, 0, 0, 0, 0, 0}
	 * 
	 * @param v the given long value
	 * @return an 8-element byte array
	 */
	public static byte[] toByteArray(double v) {
		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putDouble(v);
		buf.flip();
		return buf.array();
	}
	
    /**
     * Returns a big-endian representation of 32 bit unsigned value in a 4-element byte array.
     * <p>
     * For example: 256{0, 0, 0, 0, 0, 0, 1, 0} -> {0, 0, 1, 0}
     * 
     * @param u32 32 bit unsigned value
     * @return a 4-element byte array
     */
    public static byte[] toByteArrayU32(long u32) {
        final byte[] buf = new byte[4];
        buf[0] = (byte) ((u32 & 0xFF000000L) >> 24);
        buf[1] = (byte) ((u32 & 0x00FF0000L) >> 16);
        buf[2] = (byte) ((u32 & 0x0000FF00L) >> 8);
        buf[3] = (byte) (u32 & 0x000000FFL);
        return buf;
    }
    
    /**
     * Returns a big-endian representation of {@code long} in a 4-element byte array.
     * <p>
     * For example: Long.MAX_VALUE{127, -1, -1, -1, -1, -1, -1, -1} -> {127, -1, -1, -1}
     * 
     * @param v 32 bit unsigned value
     * @return a 4-element byte array
     */
    public static byte[] toByteArray32(long v) {
        final byte[] buf = new byte[4];
        buf[0] = (byte) ((v >> 56) & 0xff);
        buf[1] = (byte) ((v >> 48) & 0xff);
        buf[2] = (byte) ((v >> 40) & 0xff);
        buf[3] = (byte) ((v >> 32) & 0xff);
        return buf;
    }

    /**
     * Returns 32 bit unsigned value.
     * <p>
     * For example: {0, 0, 1, 0} -> {0, 0, 0, 0, 0, 0, 1, 0} -> 256
     * 
     * @param a the given a 4-element byte array[big-endian]
     * @return Unsigned32,long - 0000****[32 bit number]
     */
    public static long makeU32(final byte[] a) {
        return fromBytes(a);
    }
    
    /**
     * Returns the {@code long} value.
     * <p>
     * For example: {0, 0, 1, 0} -> {0, 0, 1, 0, 0, 0, 0, 0} -> 0x10000000000
     * 
     * @param a the given a 4-element byte array[big-endian]
     * @return long - ****0000[32 bit number]
     */
    public static long make32(final byte[] a) {
        return fromBytes(a[0], a[1], a[2], a[3], (byte) 0, (byte) 0, (byte) 0, (byte) 0);
    }

    /**
     * Returns the {@code int} value whose byte representation is the given 4
     * bytes, in big-endian order
     *
     * @param a a 4-element byte array[big-endian]
     * @return The int value representing this byte array
     */
    public static int fromByteArray(byte[] a) {
        return fromBytes(a[0], a[1], a[2], a[3]);
    }

    /**
     * Returns the {@code long} value whose byte representation is the given 8
     * bytes, in big-endian order
     *
     * <pre>
     * byte array form: {b0, b1, b2, b3, b4, b5, b6, b7}
     * For example:
     * 		{127, -1, -1, -1, -1, -1, -1, -1} -> Long.MAX_VALUE
     * 		{0, 0, 0, 0, 0, 0, 1, 0} -> 256
     * </pre>
     *
     * @param a a 8-element byte array
     * @return The {@code long} value representing this byte array
     */
    public static long fromBytes(final byte[] a) {
        return fromBytes((byte) 0, (byte) 0, (byte) 0, (byte) 0, a[0], a[1], a[2], a[3]);
    }

    private static int fromBytes(byte b0, byte b1, byte b2, byte b3) {
        return b0 << 24 | (b1 & 0xFF) << 16 | (b2 & 0xFF) << 8 | (b3 & 0xFF);
    }

    private static long fromBytes(byte b0, byte b1, byte b2, byte b3,
                                  byte b4, byte b5, byte b6, byte b7) {
        // (x && 0xFFL) -> {0, 0, 0, 0, 0, 0, 0, x}
        return (b0 & 0xFFL) << 56    // {b0, 0, 0, 0, 0, 0, 0, 0}
                | (b1 & 0xFFL) << 48 // {0, b1, 0, 0, 0, 0, 0, 0}
                | (b2 & 0xFFL) << 40 // {0, 0, b2, 0, 0, 0, 0, 0}
                | (b3 & 0xFFL) << 32 // {0, 0, 0, b3, 0, 0, 0, 0}
                | (b4 & 0xFFL) << 24 // {0, 0, 0, 0, b4, 0, 0, 0}
                | (b5 & 0xFFL) << 16 // {0, 0, 0, 0, 0, b5, 0, 0}
                | (b6 & 0xFFL) << 8  // {0, 0, 0, 0, 0, 0, b6, 0}
                | (b7 & 0xFFL);      // {0, 0, 0, 0, 0, 0, 0, b7}
    }
}

