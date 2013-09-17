package net.protocol.cops.object;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public class PDPRedirAddrTest extends PDPAddrTest {
    public final static byte[] IPV4_ARRAY = {
            0, 12, 13, 1,
            10, 60, 3, 78,
            0, 0, 31, -112
    };
    public final static byte[] IPV6_ARRAY = {
            0, 24, 13, 2,
            32, 1, 13, -88, -112, 0, -78, 85, 2, 0, -24, -1, -2, -80, 92, 94,
            0, 0, 31, -112
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 12, 0, 1,
            10, 60, 3, 78,
            0, 0, 31, -112
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 12, 1, 1,
            10, 60, 3, 78,
            0, 0, 31, -112
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 12, 13, 0,
            10, 60, 3, 78,
            0, 0, 31, -112
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 12, 13, 1,
            10, 60, 3, 78
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            0, 8, 13, 1,
            10, 60, 3, 78,
            0, 0, 31, -112
    };

    @Override
	public void logic() {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException1() throws IllegalCopsObjectException {
        new PDPRedirAddr((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new PDPRedirAddr((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new PDPRedirAddr((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        byteMe(get(getBuffer(ILLEGAL_ARRAY_5)));
    }

    @Override
    protected PDPAddr get(ByteBuffer buffer) {
        try {
            return new PDPRedirAddr(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected PDPAddr get() {
        try {
            return new PDPRedirAddr(InetAddress.getByName("10.60.3.78"), 8080);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected byte[] getBytes() {
        return IPV4_ARRAY;
    }

    private void testEquals() {
        assertEquals(byteMe(get(getBuffer(IPV4_ARRAY))), getBuffer(IPV4_ARRAY));
        assertEquals(byteMe(get()), getBuffer(IPV4_ARRAY));

        assertEquals(byteMe(get(getBuffer(IPV6_ARRAY))), getBuffer(IPV6_ARRAY));
        assertEquals(byteMe(get(getIpv6(), 8080)), getBuffer(IPV6_ARRAY));
    }

    private void testLength() {
        PDPAddr ipv4 = get();
        System.out.println(ipv4);
        assertEquals(PDPRedirAddr.LENGTH_FOR_IPV4, ipv4.getLength());

        PDPAddr ipv6 = get(getIpv6(), 8080);
        System.out.println(ipv6);
        assertEquals(PDPRedirAddr.LENGTH_FOR_IPV6, ipv6.getLength());
    }

    private PDPAddr get(InetAddress address, int port) {
        return new PDPRedirAddr(address, port);
    }
}
