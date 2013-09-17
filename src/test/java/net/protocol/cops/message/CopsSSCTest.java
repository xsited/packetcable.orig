package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.Handle;
import net.protocol.cops.object.IllegalCopsObjectException;
import net.protocol.cops.object.Integrity;
import org.junit.Test;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public class CopsSSCTest extends CopsSSTest {

    public final static byte[] BASE_ARRAY = {16, 10, -128, 11, 0, 0, 0, 8};
    public final static byte[] ARRAY = {
            16, 10, 127, -11, 0, 0, 0, 16,
            0, 8, 1, 1, 0, 0, 0, 1
    };
    public final static byte[] FULL_ARRAY = {
            16, 10, 127, -11, 0, 0, 0, 37,
            0, 8, 1, 1, 0, 0, 0, 1,
            0, 21, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {16, 0, -128, 11, 0, 0, 0, 8};
    public final static byte[] ILLEGAL_ARRAY_2 = {16, 6, -128, 11, 0, 0, 0, 8};
    public final static byte[] ILLEGAL_ARRAY_3 = {
            16, 10, 127, -11, 0, 0, 0, 16,
            0, 8, 20, 1, 0, 0, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            16, 10, -128, 11, 0, 0, 0, 16,
            0, 8, 10, 1, 0, 0, 0, 60
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            16, 10, 127, -11, 0, 0, 0, 40,
            0, 8, 1, 1, 0, 0, 0, 1,
            0, 24, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };
    public final static byte[] ILLEGAL_ARRAY_6 = {
            16, 10, 127, -11, 0, 0, 0, 14,
            0, 8, 1, 1, 0, 0, 0, 1
    };

    @Override
	public void logic() throws CopsException {
        testEquals();
        testLength();
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException1() throws CopsException {
        new CopsSSC(getBuffer(ILLEGAL_ARRAY_1));
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException2() throws CopsException {
        new CopsSSC(getBuffer(ILLEGAL_ARRAY_2));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException() throws CopsException {
        new CopsSSC(getBuffer(ILLEGAL_ARRAY_3));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException() throws CopsException {
        new CopsSSQ(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_5));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        byteMe(get(getBuffer(ILLEGAL_ARRAY_6, ILLEGAL_ARRAY_6.length - 1)));
    }

    @Override
    protected CopsSS get(ByteBuffer buffer) {
        try {
            return new CopsSSC(buffer);
        } catch (CopsException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected CopsSS get() {
        return new CopsSSC(32757, 1);
    }

    @Override
    protected byte[] getBytes() {
        return BASE_ARRAY;
    }

    private void testEquals() throws CopsException {
        assertEquals(byteMe(new CopsSSC(getBuffer(BASE_ARRAY))), getBuffer(BASE_ARRAY));
        assertEquals(byteMe(new CopsSSC(32757, 1)), getBuffer(ARRAY));

        CopsSS e = new CopsSSC(32757, 1);
        e.setIntegrity(new Integrity(1, 1, "Integrity".getBytes()));
        assertEquals(byteMe(e), getBuffer(FULL_ARRAY));
    }

    private void testLength() throws CopsException {
        CopsSS o = new CopsSSC(getBuffer(BASE_ARRAY));
        assertEquals(CopsHeader.LENGTH, o.getMessageLength());

        CopsSS e = new CopsSSC(32757, 1);
        System.out.println(e);
        int length = CopsHeader.LENGTH + Handle.LENGTH;
        assertEquals(length, e.getMessageLength());

        e.setIntegrity(new Integrity(1, 1, "Integrity".getBytes()));
        assertEquals(length + 4 + 4 + 4 + "Integrity".length(), e.getMessageLength());

        e.setIntegrity(null);
        assertEquals(length, e.getMessageLength());

        e.setHandle(null);
        System.out.println(e);
        assertEquals(CopsHeader.LENGTH, e.getMessageLength());
    }
}
