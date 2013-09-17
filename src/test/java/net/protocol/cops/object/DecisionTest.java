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
public class DecisionTest extends CopsObjectTest {
    public final static int TPS_UNIT = 300 * 10000;

    public final static byte[] FLAG_ARRAY = {
            0, 8, 6, 1,
            0, 1,
            0, 1
    };
    public final static byte[] STATELESS_ARRAY = {
            0, 18, 6, 2,
            115, 116, 97, 116, 101, 108, 101, 115, 115, 32, 100, 97, 116, 97, 0, 0
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 8, 0, 1,
            0, 1,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 8, 7, 0,
            0, 1,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 8, 6, 0,
            0, 1,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 20, 6, 2,
            115, 116, 97, 116, 101, 108, 101, 115, 115, 32, 100, 97, 116, 97
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            0, 18, 6, 2,
            115, 116, 97, 116, 101, 108, 101, 115, 115, 32, 100, 97, 116, 97, 0, 0
    };

    @Override
	public void base() {
        testBaseDecisionFlag();
        testBaseDecisionData();

        Map<CopsObject, String> map = new HashMap<CopsObject, String>();
        map.put(get(), "A");
        map.put(get(), "B");
        assertNotNull(map.get(get()));

        map.put(get("STATELESS"), "C");
        map.put(get("STATELESS"), "D");
        assertNotNull(map.get(get("STATELESS")));

        assertEquals(2, map.size());
    }

    @Override
	public void logic() throws Exception {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException1() throws IllegalCopsObjectException {
        new Decision((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new Decision((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new Decision((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        Decision e = get(getBuffer(STATELESS_ARRAY));
        e.byteMe(ByteBuffer.allocate(STATELESS_ARRAY.length - 1));
    }

    @Override
    protected Decision get(ByteBuffer buffer) {
        try {
            return new Decision(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Decision get() {
        return new Decision(Decision.Command.INSTALL, 1);
    }

    @Override
    protected byte[] getBytes() {
        return FLAG_ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testBaseDecisionFlag() {
        assertEquals(get(25), get(25));
        assertEquals(get(25).hashCode(), get(25).hashCode());

        assertNotEquals(get(100), get(25));
        assertNotEquals(get(100).hashCode(), get(25).hashCode());

        assertNotEquals(get(25), get("STATELESS"));
    }

    private void testBaseDecisionData() {
        assertEquals(get("STATELESS"), get("STATELESS"));
        assertEquals(get("STATELESS").hashCode(), get("STATELESS").hashCode());

        assertNotEquals(get("NAMED"), get("STATELESS"));
        assertNotEquals(get("NAMED").hashCode(), get("STATELESS").hashCode());

        assertNotEquals(get("STATELESS"), get(25));
    }

    private void testEquals() throws IllegalCopsObjectException {
        assertEquals(new Decision(byteMe(get("stateless"))), get("stateless"));

        assertEquals(byteMe(get(getBuffer(FLAG_ARRAY))), getBuffer(FLAG_ARRAY));
        assertEquals(byteMe(get()), getBuffer(FLAG_ARRAY));

        assertEquals(byteMe(get(getBuffer(STATELESS_ARRAY))), getBuffer(STATELESS_ARRAY));
        assertEquals(byteMe(get("stateless data")), getBuffer(STATELESS_ARRAY));
    }

    private void testLength() {
        Decision e = get();
        System.out.println(e);
        assertEquals(4 + 2 + 2, e.getLength());

        String data = "stateless data";
        Decision s = get(data);
        System.out.println(s);
        assertEquals(4 + data.length(), s.getLength());
    }

    private Decision get(int flag) {
        return new Decision(Decision.Command.INSTALL, flag);
    }

    private Decision get(String data) {
        return new Decision(Decision.CType.STATELESS, data.getBytes());
    }
}
