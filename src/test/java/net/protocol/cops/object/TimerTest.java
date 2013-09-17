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
public class TimerTest extends CopsObjectTest {
    public final static int TPS_UNIT = 400 * 10000;

    public final static byte[] KA_ARRAY = {
            0, 8, 10, 1,
            0, 0,
            0, 1
    };
    public final static byte[] ACCT_ARRAY = {
            0, 8, 15, 1,
            0, 0,
            0, 1
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            0, 8, 0, 1,
            0, 0,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            0, 8, 1, 1,
            0, 0,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            0, 8, 10, 2,
            0, 0,
            0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            0, 8, 10, 1,
            0, 0
    };

    @Override
	public void base() {
        testBaseKATimer();
        testBaseAcctTimer();

        Map<CopsObject, String> map = new HashMap<CopsObject, String>();
        map.put(getKa(1), "C");
        map.put(getKa(1), "D");
        assertNotNull(map.get(getKa(1)));

        map.put(getAcct(1), "C");
        map.put(getAcct(1), "D");
        assertNotNull(map.get(getAcct(1)));

        assertEquals(2, map.size());
    }

    @Override
	public void logic() {
        testEquals();
        testLength();
    }


    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException1() throws IllegalCopsObjectException {
        new Timer((getBuffer(ILLEGAL_ARRAY_1)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException2() throws IllegalCopsObjectException {
        new Timer((getBuffer(ILLEGAL_ARRAY_2)));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException3() throws IllegalCopsObjectException {
        new Timer((getBuffer(ILLEGAL_ARRAY_3)));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        Timer e = get(getBuffer(KA_ARRAY));
        e.byteMe(ByteBuffer.allocate(KA_ARRAY.length -1));
    }

    @Override
    protected Timer get(ByteBuffer buffer) {
        try {
            return new Timer(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Timer get() {
        return new KATimer(1);
    }

    @Override
    protected byte[] getBytes() {
        return KA_ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testBaseKATimer() {
        assertEquals(getKa(60), getKa(60));
        assertEquals(getKa(60).hashCode(), getKa(60).hashCode());

        assertNotEquals(getKa(59), getKa(60));
        assertNotEquals(getKa(59).hashCode(), getKa(60).hashCode());

        assertNotEquals(getKa(60), getAcct(60));
    }

    private void testBaseAcctTimer() {
        assertEquals(getAcct(60), getAcct(60));
        assertEquals(getAcct(60).hashCode(), getAcct(60).hashCode());

        assertNotEquals(getAcct(59), getAcct(60));
        assertNotEquals(getAcct(59).hashCode(), getAcct(60).hashCode());

        assertNotEquals(getAcct(60), getKa(60));
    }

    private void testEquals() {
        assertEquals(byteMe(get(getBuffer(KA_ARRAY))), getBuffer(KA_ARRAY));
        assertEquals(byteMe(get()), getBuffer(KA_ARRAY));

        assertEquals(byteMe(get(getBuffer(ACCT_ARRAY))), getBuffer(ACCT_ARRAY));
        assertEquals(byteMe(getAcct(1)), getBuffer(ACCT_ARRAY));
    }

    private void testLength() {
        Timer ka = getKa(1);
        System.out.println(ka);
        assertEquals(4 + 2 + 2, ka.getLength());

        Timer ac = getKa(1);
        System.out.println(ac);
        assertEquals(4 + 2 + 2, ac.getLength());
    }

    private Timer getKa(int timer) {
        return new KATimer(timer);
    }

    private Timer getAcct(int timer) {
        return new AcctTimer(timer);
    }
}
