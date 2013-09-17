package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.ClientSI;
import net.protocol.cops.object.IllegalCopsObjectException;
import net.protocol.cops.object.Integrity;
import net.protocol.cops.object.LastPDPAddr;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class CopsOPNTest extends CopsMessageTest {
    public final static int TPS_UNIT = 50 * 10000;

    public final static byte[] BASE_ARRAY = {
            16, 6, 127, -11, 0, 0, 0, 15,
            0, 7, 11, 1, 79, 80, 78, 0
    };
    public final static byte[] ARRAY = {
            16, 6, -128, 11, 0, 0, 0, 29,
            0, 13, 11, 1, 118, 111, 108, 64, 69, 82, 88, 45, 49, 0, 0, 0,
            0, 8, 14, 1, 0, 0, 0, 0
    };
    public final static byte[] FULL_ARRAY = {
            16, 6, 127, -11, 0, 0, 0, 51,
            0, 7, 11, 1, 79, 80, 78, 0,
            0, 7, 9, 2, 67, 83, 73,
            0, 8, 14, 1, 10, 60, 3, 78,
            0, 21, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            16, 16, 127, -11, 0, 0, 0, 15,
            0, 7, 11, 1, 79, 80, 78, 0
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            16, 1, 127, -11, 0, 0, 0, 15,
            0, 7, 11, 1, 79, 80, 78, 0
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            16, 6, 127, -11, 0, 0, 0, 15,
            0, 7, 0, 1, 79, 80, 78, 0
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            16, 6, 127, -11, 0, 0, 0, 8
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            16, 6, 127, -11, 0, 0, 0, 16,
            0, 8, 1, 1, 0, 0, 0, 10
    };
    public final static byte[] ILLEGAL_ARRAY_6 = {
            16, 6, 127, -11, 0, 0, 0, 51,
            0, 7, 11, 1, 79, 80, 78, 0,
            0, 7, 9, 2, 67, 83, 73,
            0, 12, 13, 1, 10, 60, 3, 78, 0, 0, 31, -112,
            0, 21, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };
    public final static byte[] ILLEGAL_ARRAY_7 = {
            16, 6, 127, -11, 0, 0, 0, 16,
            0, 8, 11, 1, 79, 80, 78
    };
    public final static byte[] ILLEGAL_ARRAY_8 = {
            16, 6, 127, -11, 0, 0, 0, 12,
            0, 8, 11, 1, 79, 80, 78, 0
    };

    @Override
	public void base() {
        assertEquals(get(), get());
        assertEquals(get().hashCode(), get().hashCode());

        assertNotEquals(get("OP"), get("OPN"));
        assertNotEquals(get("OP").hashCode(), get("OPN").hashCode());

        Map<CopsMessage, String> map = new HashMap<CopsMessage, String>();
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

    @Test(expected = IllegalCopsMsgException.class)
    public void testUnknownCopsMsgException1() throws CopsException {
        new CopsOPN(getBuffer(ILLEGAL_ARRAY_1));
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testUnknownCopsMsgException2() throws CopsException {
        new CopsOPN(getBuffer(ILLEGAL_ARRAY_2));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testUnknownCopsObjException() throws CopsException {
        new CopsOPN(getBuffer(ILLEGAL_ARRAY_3));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException1() throws CopsException {
        new CopsOPN(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException2() throws CopsException {
        new CopsOPN(getBuffer(ILLEGAL_ARRAY_5));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException3() throws CopsException {
        new CopsOPN(getBuffer(ILLEGAL_ARRAY_6));
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
    protected CopsOPN get(ByteBuffer buffer) {
        try {
            return new CopsOPN(buffer);
        } catch (CopsException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected CopsOPN get() {
        return new CopsOPN(32757, "OPN".getBytes());
    }

    @Override
    protected byte[] getBytes() {
        return ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testEquals() throws Exception {
        assertEquals(byteMe(new CopsOPN(32757, "OPN".getBytes())), getBuffer(BASE_ARRAY));

        assertEquals(byteMe(new CopsOPN(getBuffer(ARRAY))), getBuffer(ARRAY));
        assertEquals(byteMe(new CopsOPN(getBuffer(FULL_ARRAY))), getBuffer(FULL_ARRAY));

        CopsOPN opn = new CopsOPN(32757, "OPN".getBytes());
        opn.setClientSI(new ClientSI(ClientSI.CType.NAMED, "CSI".getBytes()));
        opn.setLastPdpAddr(new LastPDPAddr(InetAddress.getByName("10.60.3.78")));
        opn.setIntegrity(new Integrity(1, 1, "Integrity".getBytes()));
        assertEquals(byteMe(opn), getBuffer(FULL_ARRAY));
    }

    private void testLength() throws Exception {
        CopsOPN e = new CopsOPN(32757, "OPN".getBytes());
        int base_length = CopsHeader.LENGTH + 4 + "OPN".length();
        System.out.println(e);
        assertEquals(base_length, e.getMessageLength());

        fill(e);
        System.out.println(e);
        int length = base_length + (4 + "OPN".length()) + 8 + (4 + 4 + 4 + 9);
        assertEquals(length, e.getMessageLength());

        empty(e);
        System.out.println(e);
        assertEquals(base_length, e.getMessageLength());
    }

    private CopsOPN fill(CopsOPN e) throws UnknownHostException {
        e.setClientSI(new ClientSI(ClientSI.CType.NAMED, "CSI".getBytes()));
        e.setLastPdpAddr(new LastPDPAddr(InetAddress.getByName("10.60.3.78")));
        e.setIntegrity(new Integrity(1, 1, "Integrity".getBytes()));

        return e;
    }

    private CopsOPN empty(CopsOPN e) {
        e.setClientSI(null);
        e.setLastPdpAddr(null);
        e.setIntegrity(null);

        return e;
    }

    private CopsOPN get(String pepid) {
        return new CopsOPN(32757, pepid.getBytes());
    }
}
