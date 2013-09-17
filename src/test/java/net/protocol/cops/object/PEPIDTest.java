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
public class PEPIDTest extends CopsObjectTest {
    public final static int TPS_UNIT = 100 * 10000;

    public final static byte[] ARRAY = {
            0, 9, 11, 1,
            80, 69, 80, 73, 68, 0, 0, 0
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 9, 21, 1,
            80, 69, 80, 73, 68, 0, 0, 0
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 9, 10, 1,
            80, 69, 80, 73, 68, 0, 0, 0
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 9, 11, 2,
            80, 69, 80, 73, 68, 0, 0, 0
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 12, 11, 1,
            80, 69, 80, 73, 68
    };

    @Override
	public void base() {
        assertEquals(get("PEPID"), get("PEPID"));
        assertEquals(get("PEPID").hashCode(), get("PEPID").hashCode());

        assertNotEquals(get("PEPID"), get("P"));
        assertNotEquals(get("PEPID").hashCode(), get("P").hashCode());

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

    @Override
    protected PEPID get(ByteBuffer buffer) {
        try {
            return new PEPID(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException1() throws IllegalCopsObjectException {
        new PEPID((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new PEPID((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new PEPID((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }
    
    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
    	PEPID e = get(getBuffer(ARRAY));
        e.byteMe(ByteBuffer.allocate(ARRAY.length - 1));
    }

    @Override
    protected PEPID get() {
        return new PEPID("PEPID".getBytes());
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
        assertEquals(new PEPID(byteMe(get("pepid"))), get("pepid"));

        assertEquals(byteMe(get(getBuffer(ARRAY))), getBuffer(ARRAY));

        assertEquals(byteMe(get()), getBuffer(ARRAY));
    }

    private void testLength() {
        String data = "pepid";
        PEPID e = get(data);
        System.out.println(e);
        assertEquals(4 + data.length(), e.getLength());
    }

    private PEPID get(String pepid) {
        return new PEPID(pepid.getBytes());
    }
}
