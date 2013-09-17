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
public class IntegrityTest extends CopsObjectTest {
    public final static int TPS_UNIT = 250 * 10000;

    public final static byte[] ARRAY = {
            0, 21, 16, 1,
            0, 0, 0, 1,
            0, 0, 0, 1,
            73, 110, 116, 101, 103, 114, 105, 116, 121
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 21, 17, 1,
            0, 0, 0, 1,
            0, 0, 0, 1,
            73, 110, 116, 101, 103, 114, 105, 116, 121
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 21, 1, 1,
            0, 0, 0, 1,
            0, 0, 0, 1,
            73, 110, 116, 101, 103, 114, 105, 116, 121
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 21, 16, 16,
            0, 0, 0, 1,
            0, 0, 0, 1,
            73, 110, 116, 101, 103, 114, 105, 116, 121
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 24, 16, 1,
            0, 0, 0, 1,
            0, 0, 0, 1,
            73, 110, 116, 101, 103, 114, 105, 116, 121
    };

    @Override
	public void base() {
        assertEquals(get("Integrity"), get("Integrity"));
        assertEquals(get("Integrity").hashCode(), get("Integrity").hashCode());

        assertNotEquals(get("Integrity"), get("I"));
        assertNotEquals(get("Integrity").hashCode(), get("I").hashCode());

        Map<CopsObject, String> map = new HashMap<CopsObject, String>();
        map.put(get(), "A");
        map.put(get(), "B");
        assertNotNull(map.get(get()));
        assertEquals(1, map.size());
    }

    @Override
	public void logic() throws Exception {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException1() throws IllegalCopsObjectException {
        new Integrity((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new Integrity((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new Integrity((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        Integrity e = get(getBuffer(ARRAY));
        e.byteMe(ByteBuffer.allocate(ARRAY.length -1));
    }

    @Override
    protected Integrity get(ByteBuffer buffer) {
        try {
            return new Integrity(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Integrity get() {
        return new Integrity(1, 1, "Integrity".getBytes());
    }

    @Override
    protected byte[] getBytes() {
        return ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testEquals() throws IllegalCopsObjectException {
        assertEquals(new Integrity(byteMe(get("Int"))), get("Int"));

        assertEquals(byteMe(get(getBuffer(ARRAY))), getBuffer(ARRAY));

        assertEquals(byteMe(get()), getBuffer(ARRAY));
    }

    private void testLength() {
        String data = "Integrity";
        Integrity e = get(data);
        System.out.println(e);
        assertEquals(4 + 4 + 4 + data.length(), e.getLength());
    }

    private Integrity get(String data) {
        return new Integrity(1, 1, data.getBytes());
    }
}
