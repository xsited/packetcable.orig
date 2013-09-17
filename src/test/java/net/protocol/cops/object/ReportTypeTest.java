package net.protocol.cops.object;

import net.protocol.cops.object.ReportType.Type;
import org.junit.Test;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class ReportTypeTest extends CopsObjectTest {
    public final static int TPS_UNIT = 300 * 10000;

    public final static byte[] ARRAY = {
            0, 8, 12, 1,
            0, 3, 0, 0
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 8, 0, 1,
            0, 3, 0, 0
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 8, 1, 1,
            0, 3, 0, 0
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 8, 12, 0,
            0, 3, 0, 0
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 8, 12, 1,
            0, 3
    };

    @Override
	public void base() {
        assertEquals(get(Type.Success), get(Type.Success));
        assertEquals(get(Type.Success).hashCode(), get(Type.Success).hashCode());

        assertNotEquals(get(Type.Failure), get(Type.Success));
        assertNotEquals(get(Type.Failure).hashCode(), get(Type.Success).hashCode());


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
        new ReportType((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new ReportType((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new ReportType((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        ReportType e = get(getBuffer(ARRAY));
        e.byteMe(ByteBuffer.allocate(ARRAY.length -1));
    }

    @Override
    protected ReportType get(ByteBuffer buffer) {
        try {
            return new ReportType(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected ReportType get() {
        return new ReportType(ReportType.Type.Accounting);
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
        ReportType e = get();
        System.out.println(e);
        assertEquals(ReportType.LENGTH, e.getLength());
    }

    private ReportType get(ReportType.Type type) {
        return new ReportType(type);
    }
}
