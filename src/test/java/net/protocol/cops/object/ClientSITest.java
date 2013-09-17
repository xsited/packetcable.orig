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
public class ClientSITest extends CopsObjectTest {
    public final static int TPS_UNIT = 300 * 10000;

    public final static byte[] ARRAY = {
            0, 12, 9, 2,
            67, 108, 105, 101, 110, 116, 83, 73
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 12, 0, 2,
            67, 108, 105, 101, 110, 116, 83, 73
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 12, 8, 2,
            67, 108, 105, 101, 110, 116, 83, 73
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 12, 9, 3,
            67, 108, 105, 101, 110, 116, 83, 73
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 14, 9, 2,
            67, 108, 105, 101, 110, 116, 83, 73
    };

    @Override
	public void base() {
        assertEquals(get("CSI"), get("CSI"));
        assertEquals(get("CSI").hashCode(), get("CSI").hashCode());

        assertNotEquals(get("ClientSI"), get("CSI"));
        assertNotEquals(get("ClientSI").hashCode(), get("CSI").hashCode());

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
        new ClientSI((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new ClientSI((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new ClientSI((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        ClientSI e = get(getBuffer(ARRAY));
        e.byteMe(ByteBuffer.allocate(ARRAY.length -1));
    }

    @Override
    protected ClientSI get(ByteBuffer buffer) {
        try {
            return new ClientSI(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected ClientSI get() {
        return new ClientSI(ClientSI.CType.NAMED, "ClientSI".getBytes());
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
        assertEquals(new ClientSI(byteMe(get("CSI"))), get("CSI"));

        assertEquals(byteMe(get(getBuffer(ARRAY))), getBuffer(ARRAY));

        assertEquals(byteMe(get()), getBuffer(ARRAY));
    }

    private void testLength() {
        ClientSI e = get();
        System.out.println(e);
        assertEquals(4 + 8, e.getLength());
    }

    private ClientSI get(String data) {
        return new ClientSI(ClientSI.CType.NAMED, data.getBytes());
    }
}
