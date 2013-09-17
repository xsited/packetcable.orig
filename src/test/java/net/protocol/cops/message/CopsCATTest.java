package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.AcctTimer;
import net.protocol.cops.object.IllegalCopsObjectException;
import net.protocol.cops.object.Integrity;
import net.protocol.cops.object.Timer;
import org.junit.Test;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class CopsCATTest extends CopsMessageTest {
    public final static int TPS_UNIT = 100 * 10000;

    public final static byte[] BASE_ARRAY = {
            16, 7, -128, 11, 0, 0, 0, 16,
            0, 8, 10, 1, 0, 0, 0, 60
    };
    public final static byte[] FULL_ARRAY = {
            16, 7, -128, 11, 0, 0, 0, 45,
            0, 8, 10, 1, 0, 0, 0, 60,
            0, 8, 15, 1, 0, 0, 0, 99,
            0, 21, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            16, 15, -128, 11, 0, 0, 0, 16,
            0, 8, 10, 1, 0, 0, 0, 60
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            16, 7, -128, 11, 0, 0, 0, 16,
            0, 8, 20, 1, 0, 0, 0, 60
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            16, 7, -128, 11, 0, 0, 0, 16,
            0, 8, 5, 1, 0, 13, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            16, 7, -128, 11, 0, 0, 0, 8
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            16, 7, -128, 11, 0, 0, 0, 32,
            0, 8, 10, 1, 0, 0, 0, 60,
            0, 8, 15, 1, 0, 0, 0, 99,
            0, 8, 1, 1, 0, 0, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_6 = {
            16, 7, -128, 11, 0, 0, 0, 16,
            0, 8, 15, 1, 0, 0, 0, 99,
    };
    public final static byte[] ILLEGAL_ARRAY_7 = {
            16, 7, -128, 11, 0, 0, 0, 16,
            0, 8, 10, 1
    };
    public final static byte[] ILLEGAL_ARRAY_8 = {
            16, 7, -128, 11, 0, 0, 0, 10,
            0, 8, 10, 1, 0, 0, 0, 60
    };

    @Override
	public void base() {
        assertEquals(get(), get());
        assertEquals(get().hashCode(), get().hashCode());

        assertNotEquals(get(59), get(60));
        assertNotEquals(get(59).hashCode(), get(60).hashCode());

        Map<CopsMessage, String> map = new HashMap<CopsMessage, String>();
        map.put(get(), "A");
        map.put(get(), "B");
        assertNotNull(map.get(get()));
        assertEquals(1, map.size());
    }

    @Override
	public void logic() throws CopsException {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testUnknownCopsMsgException() throws CopsException {
        new CopsCAT(getBuffer(ILLEGAL_ARRAY_1));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testUnknownCopsObjException() throws CopsException {
        new CopsCAT(getBuffer(ILLEGAL_ARRAY_2));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException1() throws CopsException {
        new CopsCAT(getBuffer(ILLEGAL_ARRAY_3));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException2() throws CopsException {
        new CopsCAT(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException3() throws CopsException {
        new CopsCAT(getBuffer(ILLEGAL_ARRAY_5));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException4() throws CopsException {
        new CopsCAT(getBuffer(ILLEGAL_ARRAY_6));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_7));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        byteMe(get(getBuffer(ILLEGAL_ARRAY_8)));
    }

    @Override
    protected CopsCAT get(ByteBuffer buffer) {
        try {
            return new CopsCAT(buffer);
        } catch (CopsException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected CopsCAT get() {
        return new CopsCAT(32757, 88);
    }

    @Override
    protected byte[] getBytes() {
        return BASE_ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testEquals() throws CopsException {
        CopsCAT e = new CopsCAT(getBuffer(BASE_ARRAY));
        assertEquals(byteMe(e), getBuffer(BASE_ARRAY));

        fill(e);
        assertEquals(byteMe(e), getBuffer(FULL_ARRAY));
    }

    private void testLength() {
        CopsCAT e = new CopsCAT(32757, 88);
        int base_length = CopsHeader.LENGTH + Timer.LENGTH;
        System.out.println(e);
        assertEquals(base_length, e.getMessageLength());

        fill(e);
        System.out.println(e);
        assertEquals(base_length + Timer.LENGTH + 21, e.getMessageLength());

        empty(e);
        System.out.println(e);
        assertEquals(base_length, e.getMessageLength());
    }

    private CopsCAT fill(CopsCAT e) {
        e.setAcctTimer(new AcctTimer(99));
        e.setIntegrity(new Integrity(1, 1, "Integrity".getBytes()));

        return e;
    }

    private CopsCAT empty(CopsCAT e) {
        e.setAcctTimer(null);
        e.setIntegrity(null);

        return e;
    }

    private CopsCAT get(int kat) {
        return new CopsCAT(32757, kat);
    }
}
