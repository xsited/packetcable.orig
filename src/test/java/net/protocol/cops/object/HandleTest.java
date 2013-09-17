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
public class HandleTest extends CopsObjectTest {
    public final static int TPS_UNIT = 300 * 10000;

    public final static byte[] ARRAY = {
            0, 8, 1, 1,
            0, 0, 0, 1
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 8, 20, 1,
            0, 0, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 8, 2, 1,
            0, 0, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 8, 1, 4,
            0, 0, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 8, 1, 1,
            0, 0, 0
    };

    @Override
	public void base() {
        assertEquals(get(10), get(10));
        assertEquals(get(10).hashCode(), get(10).hashCode());

        assertNotEquals(get(15), get(10));
        assertNotEquals(get(15).hashCode(), get(10).hashCode());

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
        new Handle((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new Handle((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new Handle((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        Handle e = get(getBuffer(ARRAY));
        e.byteMe(ByteBuffer.allocate(ARRAY.length -1));
    }

    @Override
    protected Handle get(ByteBuffer buffer) {
        try {
            return new Handle(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Handle get() {
        return new Handle(1);
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
        Handle e = get();
        System.out.println(e);
        assertEquals(Handle.LENGTH, e.getLength());
    }

    private Handle get(int handle) {
        return new Handle(handle);
    }
}
