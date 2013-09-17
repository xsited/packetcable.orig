package net.protocol.common.util;

import net.protocol.common.AbstractTest;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author jinhongw@gmail.com
 */
public class BitsTest extends AbstractTest {
    private final static ByteBuffer dst = ByteBuffer.allocate(8);

    @Override
	public void base() throws Exception {
        assertEquals(Bits.MAX_U32_VALUE, get(buffer(dst, Bits.MAX_U32_VALUE, true)));
        assertEquals(Bits.MAX_U32_VALUE, get(buffer(dst, Bits.MAX_U32_VALUE, false)));

        assertEquals(Long.valueOf(Integer.MAX_VALUE), get(buffer(dst, Integer.MAX_VALUE, true)));
        assertEquals(Long.valueOf(Integer.MAX_VALUE), get(buffer(dst, Integer.MAX_VALUE, false)));

        assertEquals(Long.valueOf(1024), get(buffer(dst, 1024, true)));
        assertEquals(Long.valueOf(1024), get(buffer(dst, 1024, false)));

        assertEquals(Long.valueOf(1), get(buffer(dst, 1, true)));
        assertEquals(Long.valueOf(1), get(buffer(dst, 1, false)));

        assertEquals(Long.valueOf(0), get(buffer(dst, 0, true)));
        assertEquals(Long.valueOf(0), get(buffer(dst, 0, false)));
    }

    @Override
	public void logic() throws Exception {
        byte[] a = { -28, -80, 8, 95 };
        ByteBuffer src = ByteBuffer.allocate(4);
        src.put(a);
        src.flip();
        long u32 = Bits.getU32(src);
        long l32 = 3836741727L;
        assertEquals(l32, u32);

        src.rewind();
        int i = -458225569;
        assertEquals(i, src.getInt());

        assertEquals(i, (int)l32);

        ByteBuffer dst = ByteBuffer.allocate(4);
        Bits.putU32(dst, l32);
        dst.flip();
        src.rewind();
        assertEquals(src, dst);
    }

    @Test(expected = NumberFormatException.class)
    public void testNumberFormatException1() throws NumberFormatException {
        buffer(dst, -1, true);
        dst.clear();
    }

    @Test(expected = NumberFormatException.class)
    public void testNumberFormatException2() throws NumberFormatException {
        buffer(dst, Bits.MAX_U32_VALUE + 1, false);
        dst.clear();
    }

    private long get(final ByteBuffer src) {
        System.out.println(src);
        System.out.println(toString(src));
        src.rewind();
        System.out.println(src);
        long u32 = Bits.getU32(src);
        src.clear();
        return u32;
    }

    private ByteBuffer buffer(ByteBuffer dst, long value, boolean bigEndian) {
        dst.clear();
        if (bigEndian) {
            dst.order(ByteOrder.BIG_ENDIAN);
        } else {
            dst.order(ByteOrder.LITTLE_ENDIAN);
        }
        Bits.putU32(dst, value).flip();
        return dst;
    }

    private static String toString(ByteBuffer buf) {
        StringBuilder builder = new StringBuilder("ByteBuffer {");
        while (buf.hasRemaining()) {
            builder.append(buf.get()).append(", ");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }
}
