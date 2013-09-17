package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.*;
import net.protocol.cops.object.Error;
import org.junit.Test;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class CopsDECTest extends CopsMessageTest {
    public final static int TPS_UNIT = 25 * 10000;

    public final static byte[] ERROR_ARRAY = {
            16, 2, 127, -11, 0, 0, 0, 24,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 8, 1, 0, 14, 0, -1
    };
    public final static byte[] ARRAY = {
            16, 2, -128, 11, 0, 0, 0, 32,
            0, 8, 1, 1, 0, 0, 0, 1,
            0, 8, 2, 1, 0, 8, 0, 0,
            0, 8, 6, 1, 0, 0, 0, 1
    };
    public final static byte[] FULL_ARRAY = {
            16, 2, 127, -11, 0, 0, 0, 71,
            0, 8, 1, 1, 0, 0, 0, 15,
            0, 8, 2, 1, 0, 8, 0, 5,
            0, 8, 6, 1, 0, 1, 0, 1,
            0, 18, 6, 2, 115, 116, 97, 116, 101, 108, 101, 115, 115, 32, 100, 97, 116, 97, 0, 0,
            0, 21, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            16, 0, 127, -11, 0, 0, 0, 24,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 8, 1, 0, 14, 0, -1
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            16, 3, 127, -11, 0, 0, 0, 24,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 8, 1, 0, 14, 0, -1
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            16, 2, 127, -11, 0, 0, 0, 24,
            0, 8, 0, 1, 0, 0, 0, 10,
            0, 8, 8, 1, 0, 14, 0, -1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            16, 2, 127, -11, 0, 0, 0, 8,
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            16, 2, 127, -11, 0, 0, 0, 24,
            0, 8, 11, 1, 79, 80, 78, 0,
            0, 8, 8, 1, 0, 14, 0, -1
    };
    public final static byte[] ILLEGAL_ARRAY_6 = {
            16, 2, 127, -11, 0, 0, 0, 68,
            0, 8, 1, 1, 0, 0, 0, 15,
            0, 8, 2, 1, 0, 8, 0, 5,
            0, 8, 6, 1, 0, 1, 0, 1,
            0, 18, 6, 2, 115, 116, 97, 116, 101, 108, 101, 115, 115, 32, 100, 97, 116, 97, 0, 0,
            0, 8, 14, 1, 10, 60, 3, 78
    };
    public final static byte[] ILLEGAL_ARRAY_7 = {
            16, 2, 127, -11, 0, 0, 0, 76,
            0, 8, 1, 1, 0, 0, 0, 15,
            0, 8, 2, 1, 0, 8, 0, 5,
            0, 8, 6, 1, 0, 1, 0, 1,
            0, 18, 6, 2, 115, 116, 97, 116, 101, 108, 101, 115, 115, 32, 100, 97, 116, 97, 0, 0,
            0, 24, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };

    @Override
	public void base() {
        assertEquals(get(), get());
        assertEquals(get().hashCode(), get().hashCode());

        assertNotEquals(getD(1), getE(1));
        assertNotEquals(getD(1).hashCode(), getE(1).hashCode());

        assertNotEquals(getD(1), getE(2));
        assertNotEquals(getD(1).hashCode(), getE(2).hashCode());

        Map<CopsMessage, String> map = new HashMap<CopsMessage, String>();
        map.put(getD(1), "A");
        map.put(getD(1), "B");
        assertNotNull(map.get(getD(1)));

        map.put(getE(1), "A");
        map.put(getE(1), "B");
        assertNotNull(map.get(getE(1)));

        assertEquals(2, map.size());
    }

    @Override
	public void logic() throws CopsException {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException1() throws CopsException {
        new CopsDEC(getBuffer(ILLEGAL_ARRAY_1));
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException2() throws CopsException {
        new CopsDEC(getBuffer(ILLEGAL_ARRAY_2));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException() throws CopsException {
        new CopsDEC(getBuffer(ILLEGAL_ARRAY_3));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException1() throws CopsException {
        new CopsDEC(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException2() throws CopsException {
        new CopsDEC(getBuffer(ILLEGAL_ARRAY_5));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException3() throws CopsException {
        new CopsDEC(getBuffer(ILLEGAL_ARRAY_6));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_7));
    }

    @Override
    protected CopsDEC get(ByteBuffer buffer) {
        try {
            return new CopsDEC(buffer);
        } catch (CopsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected CopsDEC get() {
        return new CopsDEC(32757, 15, getDecs());
    }

    @Override
    protected byte[] getBytes() {
        return ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testEquals() throws CopsException {
        assertEquals(byteMe(new CopsDEC(32757, 10, getError())), getBuffer(ERROR_ARRAY));
        assertEquals(byteMe(new CopsDEC(getBuffer(ARRAY))), getBuffer(ARRAY));

        CopsDEC e = new CopsDEC(32757, 15, getDecs());
        e.setIntegrity(new Integrity(1, 1, "Integrity".getBytes()));
        assertEquals(byteMe(e, FULL_ARRAY.length + 2), getBuffer(FULL_ARRAY, FULL_ARRAY.length + 2));
    }

    private void testLength() {
        CopsDEC error = new CopsDEC(32757, 10, getError());
        System.out.println(error);
        int error_length = CopsHeader.LENGTH + Handle.LENGTH + Error.LENGTH;
        assertEquals(error_length, error.getMessageLength());

        CopsDEC dec = new CopsDEC(32757, 15, getDecs());
        System.out.println(dec);
        int dec_length = CopsHeader.LENGTH + Handle.LENGTH + size(getDecs());
        assertEquals(dec_length, dec.getMessageLength());

        dec.setIntegrity(new Integrity(1, 1, "Integrity".getBytes()));
        System.out.println(dec);
        int no_integrity_length = CopsHeader.LENGTH + Handle.LENGTH + size(getDecs());
        int integrity_length = 4 + 4 + 4 + "Integrity".length();
        assertEquals(no_integrity_length + integrity_length, dec.getMessageLength());

        dec.setIntegrity(null);
        System.out.println(dec);
        assertEquals(no_integrity_length, dec.getMessageLength());
    }

    private net.protocol.cops.object.Error getError() {
        return new Error(Error.Code.AUTHENTICATION_FAILURE, 255);
    }

    private int size(List<Dec> decs) {
        int length = 0;
        for (Dec e : decs) {
            length += e.size();
        }
        return length;
    }

    private List<Dec> getDecs() {
        List<Dec> decs = new ArrayList<Dec>();
        try {
            Context cxt = new Context(Context.RType.CONFIGURATION, 5);
            Dec e = new Dec(cxt);
            e.add(new Decision(Decision.Command.INSTALL, 1));
            e.add(new Decision(Decision.CType.STATELESS, "stateless data".getBytes()));
            decs.add(e);
        } catch (CopsFormatErrorException ex) {
            ex.printStackTrace();
        }
        return decs;
    }

    private CopsDEC getD(int handle) {
        return new CopsDEC(32757, handle, getDecs());
    }

    private CopsDEC getE(int handle) {
        return new CopsDEC(32757, handle, getError());
    }
}
