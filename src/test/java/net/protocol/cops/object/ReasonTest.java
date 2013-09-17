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
public class ReasonTest extends CopsObjectTest {
    public final static int TPS_UNIT = 400 * 10000;

    public final static byte[] ARRAY = {
            0, 8, 5, 1,
            0, 13,
            0, 1
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 8, 25, 1,
            0, 13,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 8, 6, 1,
            0, 13,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 8, 5, 2,
            0, 13,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 8, 5, 1,
            0, 13
    };

    @Override
	public void base() {
        assertEquals(get(45), get(45));
        assertEquals(get(45).hashCode(), get(45).hashCode());

        assertNotEquals(get(90), get(45));
        assertNotEquals(get(90).hashCode(), get(45).hashCode());

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
        new Reason((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new Reason((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new Reason((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        Reason e = get(getBuffer(ARRAY));
        e.byteMe(ByteBuffer.allocate(ARRAY.length -1));
    }

    @Override
    protected Reason get(ByteBuffer buffer) {
        try {
            return new Reason(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Reason get() {
        return new Reason(Reason.Code.UNKNOWN_COPS_OBJECT, 1);
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
        Reason e = get();
        System.out.println(e);
        assertEquals(Reason.LENGTH, e.getLength());
    }

    private Reason get(int subCode) {
        return new Reason(Reason.Code.UNKNOWN_COPS_OBJECT, subCode);
    }
}
