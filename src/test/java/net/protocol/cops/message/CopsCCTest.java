package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.Error;
import net.protocol.cops.object.IllegalCopsObjectException;
import net.protocol.cops.object.Integrity;
import net.protocol.cops.object.PDPRedirAddr;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class CopsCCTest extends CopsMessageTest {
    public final static int TPS_UNIT = 100 * 10000;

    public final static byte[] ARRAY = {
            16, 8, 127, -11, 0, 0, 0, 16,
            0, 8, 8, 1, 0, 8, 0, 1
    };
    public final static byte[] FULL_ARRAY = {
            16, 8, 127, -11, 0, 0, 0, 41,
            0, 8, 8, 1, 0, 8, 0, 1,
            0, 12, 13, 1, 127, 0, 0, 1, 0, 0, 31, -112,
            0, 13, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            16, 0, 127, -11, 0, 0, 0, 16,
            0, 8, 8, 1, 0, 8, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            16, 7, 127, -11, 0, 0, 0, 16,
            0, 8, 8, 1, 0, 8, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            16, 8, 127, -11, 0, 0, 0, 44,
            0, 8, 8, 1, 0, 8, 0, 1,
            0, 12, 20, 1, 127, 0, 0, 1, 0, 0, 31, -112,
            0, 16, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 0, 0, 0
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            16, 8, 127, -11, 0, 0, 0, 8
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            16, 8, 127, -11, 0, 0, 0, 16,
            0, 8, 1, 1, 0, 0, 0, 10
    };
    public final static byte[] ILLEGAL_ARRAY_6 = {
            16, 8, 127, -11, 0, 0, 0, 44,
            0, 8, 8, 1, 0, 8, 0, 1,
            0, 8, 14, 1, 10, 60, 3, 78,
            0, 16, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 0, 0, 0
    };
    public final static byte[] ILLEGAL_ARRAY_7 = {
            16, 8, 127, -11, 0, 0, 0, 44,
            0, 8, 8, 1, 0, 8, 0, 1,
            0, 12, 13, 1, 127, 0, 0, 1, 0, 0, 31, -112,
            0, 16, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73
    };
    public final static byte[] ILLEGAL_ARRAY_8 = {
            16, 8, 127, -11, 0, 0, 0, 41,
            0, 8, 8, 1, 0, 8, 0, 1,
            0, 12, 13, 1, 127, 0, 0, 1, 0, 0, 31, -112,
            0, 16, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 0, 0, 0
    };

    @Override
	public void base() {
        assertEquals(get(), get());
        assertEquals(get().hashCode(), get().hashCode());

        assertNotEquals(get(1), get(2));
        assertNotEquals(get(1).hashCode(), get(2).hashCode());

        Map<CopsMessage, String> map = new HashMap<CopsMessage, String>();
        map.put(get(1), "A");
        map.put(get(1), "B");
        assertNotNull(map.get(get(1)));

        map.put(get(2), "C");
        map.put(get(2), "D");
        assertNotNull(map.get(get(2)));

        assertEquals(2, map.size());
    }

    @Override
	public void logic() throws CopsException {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException1() throws CopsException {
        new CopsCC(getBuffer(ILLEGAL_ARRAY_1));
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException2() throws CopsException {
        new CopsCC(getBuffer(ILLEGAL_ARRAY_2));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException() throws CopsException {
        new CopsCC(getBuffer(ILLEGAL_ARRAY_3));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException1() throws CopsException {
        new CopsCC(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException2() throws CopsException {
        new CopsCC(getBuffer(ILLEGAL_ARRAY_5));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException3() throws CopsException {
        new CopsCC(getBuffer(ILLEGAL_ARRAY_6));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_7));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        byteMe(get(getBuffer(ILLEGAL_ARRAY_8, ILLEGAL_ARRAY_8.length - 3)));
    }

    @Override
    protected CopsCC get(ByteBuffer buffer) {
        try {
            return new CopsCC(buffer);
        } catch (CopsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected CopsCC get() {
        return new CopsCC(32757, new Error(Error.Code.CLIENT_FAILURE, 1));
    }

    @Override
    protected byte[] getBytes() {
        return ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testEquals() throws CopsException {
        assertEquals(byteMe(get()), getBuffer(ARRAY));
        assertEquals(byteMe(new CopsCC(getBuffer(ARRAY))), getBuffer(ARRAY));

        assertEquals(byteMe(fill(get())), getBuffer(FULL_ARRAY));
    }

    private void testLength() {
        CopsCC e = get();
        System.out.println(e);
        int length = CopsHeader.LENGTH + Error.LENGTH;
        assertEquals(length, e.getMessageLength());

        fill(e);
        int full_length = length + PDPRedirAddr.LENGTH_FOR_IPV4
                + (4 + 4 + 4 + 1);
        assertEquals(full_length, e.getMessageLength());

        empty(e);
        assertEquals(length, e.getMessageLength());
    }

    private CopsCC get(int subCode) {
        return new CopsCC(32757, new Error(Error.Code.CLIENT_FAILURE, subCode));
    }

    private CopsCC fill(CopsCC e) {
        e.setPdpRedirAddr((PDPRedirAddr) getP());
        e.setIntegrity(getI());
        return e;
    }

    private CopsCC empty(CopsCC e) {
        e.setPdpRedirAddr(null);
        e.setIntegrity(null);
        return e;
    }

    private PDPRedirAddr getP() {
        try {
            return new PDPRedirAddr(InetAddress.getByName("127.0.0.1"), 8080);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integrity getI() {
        return new Integrity(1, 1, "I".getBytes());
    }
}
