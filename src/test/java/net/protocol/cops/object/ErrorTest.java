package net.protocol.cops.object;

import org.junit.Test;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class ErrorTest extends CopsObjectTest {
    public final static int TPS_UNIT = 300 * 10000;

    public final static byte[] ARRAY = {
            0, 8, 8, 1,
            0, 1,
            0, 1
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 8, 18, 1,
            0, 1,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 8, 9, 1,
            0, 1,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 8, 8, 0,
            0, 1,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 8, 8, 1,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            0, 4, 8, 1,
            0, 1,
            0, 1
    };

    @Override
	public void base() {
        assertEquals(get(30), get(30));
        assertEquals(get(30).hashCode(), get(30).hashCode());

        assertNotEquals(get(75), get(30));
        assertNotEquals(get(75).hashCode(), get(30).hashCode());

        Map<CopsObject, String> map = new HashMap<CopsObject, String>();
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

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException1() throws IllegalCopsObjectException {
        new Error((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new Error((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new Error((getBuffer(ILLEGAL_ARRAY_3)));
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
    protected Error get(ByteBuffer buffer) {
        try {
            return new Error(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Error get() {
        return new Error(Error.Code.BAD_HANDLE, 1);
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
        assertEquals(byteMe(get(getBuffer(ARRAY))), getBuffer(ARRAY));

        assertEquals(byteMe(get()), getBuffer(ARRAY));
    }

    private void testLength() {
        Error e = get();
        System.out.println(e);
        assertEquals(Error.LENGTH, e.getLength());
    }

    private Error get(int subCode) {
        return new Error(Error.Code.BAD_HANDLE, subCode);
    }
}
