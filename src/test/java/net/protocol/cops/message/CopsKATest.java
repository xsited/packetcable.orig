package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.IllegalCopsObjectException;
import org.junit.Test;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class CopsKATest extends CopsMessageTest {
    public final static int TPS_UNIT = 100 * 10000;

    public final static byte[] BASE_ARRAY = {16, 9, 0, 0, 0, 0, 0, 8};
    public final static byte[] ARRAY = {
            16, 9, 0, 0, 0, 0, 0, 22,
            0, 14, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 75, 65
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {16, 9, 0, 1, 0, 0, 0, 8};
    public final static byte[] ILLEGAL_ARRAY_2 = {16, 10, 0, 0, 0, 0, 0, 8};
    public final static byte[] ILLEGAL_ARRAY_3 = {
            16, 9, 0, 0, 0, 0, 0, 22,
            0, 14, 32, 1, 0, 0, 0, 1, 0, 0, 0, 1, 75, 65
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            16, 9, 0, 0, 0, 0, 0, 16,
            0, 8, 1, 1, 0, 0, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            16, 9, 0, 0, 0, 0, 0, 30,
            0, 14, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 75, 65,
            0, 8, 1, 1, 0, 0, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_6 = {
            16, 9, 0, 0, 0, 0, 0, 22,
            0, 16, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 75, 65
    };
    public final static byte[] ILLEGAL_ARRAY_7 = {
            16, 9, 0, 0, 0, 0, 0, 22,
            0, 14, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 75, 65, 0, 0
    };

    @Override
	public void base() {
        assertEquals(get(), get());
        assertEquals(get().hashCode(), get().hashCode());

        assertEquals(get(2, 2, "KA"), get(2, 2, "KA"));
        assertEquals(get(2, 2, "KA").hashCode(), get(2, 2, "KA").hashCode());

        assertNotEquals(get(), get(2, 2, "KA"));
        assertNotEquals(get().hashCode(), get(2, 2, "KA").hashCode());

        Map<CopsMessage, String> map = new HashMap<CopsMessage, String>();
        map.put(get(), "A");
        map.put(get(), "B");
        assertNotNull(map.get(get()));
        assertEquals(1, map.size());
    }

    @Override
	public void logic() {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException1() throws CopsException {
        new CopsKA(getBuffer(ILLEGAL_ARRAY_1));
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException2() throws CopsException {
        new CopsKA(getBuffer(ILLEGAL_ARRAY_2));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException() throws CopsException {
        new CopsKA(getBuffer(ILLEGAL_ARRAY_3));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException1() throws CopsException {
        new CopsKA(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException2() throws CopsException {
        new CopsKA(getBuffer(ILLEGAL_ARRAY_5));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_6));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        byteMe(get(getBuffer(ILLEGAL_ARRAY_7, ILLEGAL_ARRAY_7.length - 2)));
    }

    @Override
    protected CopsKA get(ByteBuffer buffer) {
        try {
            return new CopsKA(buffer);
        } catch (CopsException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected CopsKA get() {
        return new CopsKA();
    }


    @Override
    protected byte[] getBytes() {
        return ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testEquals() {
        assertEquals(byteMe(get()), getBuffer(BASE_ARRAY));

        assertEquals(byteMe(get(1, 1, "KA")), getBuffer(ARRAY));
    }

    private void testLength() {
        CopsKA e = get();
        System.out.println(e);
        assertEquals(CopsHeader.LENGTH, e.getMessageLength());

        CopsKA f = get(1, 1, "KA");
        System.out.println(f);
        int full_length = CopsHeader.LENGTH + 4 + 4 + 4 + "KA".length();
        assertEquals(full_length, f.getMessageLength());

        f.setIntegrity(null);
        System.out.println(f);
        assertEquals(CopsHeader.LENGTH, e.getMessageLength());
    }

    private CopsKA get(int keyId, int sequenceNumber, String data) {
        return new CopsKA(keyId, sequenceNumber, data.getBytes());
    }
}