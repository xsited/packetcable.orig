package net.protocol.cops.object;

import org.junit.Test;

import java.net.InetAddress;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public class INIntTest extends IntTest {

    public final static byte[] IPV4_ARRAY = {
            0, 12, 3, 1,
            10, 60, 3, 78,
            0, 0, 31, -112
    };
    public final static byte[] IPV6_ARRAY = {
            0, 24, 3, 2,
            32, 1, 13, -88, -112, 0, -78, 85, 2, 0, -24, -1, -2, -80, 92, 94,
            0, 0, 31, -112
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 12, 0, 1,
            10, 60, 3, 78,
            0, 0, 31, -112
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 12, 5, 1,
            10, 60, 3, 78,
            0, 0, 31, -112
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 12, 3, 3,
            10, 60, 3, 78,
            0, 0, 31, -112
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 12, 3, 1,
            10, 60, 3, 78,
            31, -112
    };

    @Override
	public void logic() {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException1() throws IllegalCopsObjectException {
        new Int((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new Int((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new Int((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        Int e = get(getBuffer(IPV4_ARRAY));
        e.byteMe(ByteBuffer.allocate(IPV4_ARRAY.length - 1));
    }

    @Override
    protected Int get() {
        return new INInt(getIpv4(), 8080);
    }

    @Override
    protected byte[] getBytes() {
        return IPV4_ARRAY;
    }

    private void testEquals() {
        assertEquals(byteMe(get(getBuffer(IPV4_ARRAY))), getBuffer(IPV4_ARRAY));
        assertEquals(byteMe(get()), getBuffer(IPV4_ARRAY));

        assertEquals(byteMe(get(getBuffer(IPV6_ARRAY))), getBuffer(IPV6_ARRAY));
        assertEquals(byteMe(get(getIpv6())), getBuffer(IPV6_ARRAY));
    }

    private void testLength() {
        Int ipv4 = get();
        System.out.println(ipv4);
        assertEquals(Int.LENGTH_FOR_IPV4, ipv4.getLength());

        Int ipv6 = get(getIpv6());
        System.out.println(ipv6);
        assertEquals(Int.LENGTH_FOR_IPV6, ipv6.getLength());
    }

    private Int get(InetAddress address) {
        return new INInt(address, 8080);
    }
}
