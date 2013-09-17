package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.*;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class CopsREQTest extends CopsMessageTest {
    public final static int TPS_UNIT = 50 * 10000;

    public final static byte[] BASE_ARRAY = {
            16, 1, 31, 64, 0, 0, 0, 24,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 2, 1, 0, 1, 0, 1
    };
    public final static byte[] FULL_ARRAY = {
            16, 1, 31, 64, 0, 0, 0, 89,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 2, 1, 0, 1, 0, 1,
            0, 12, 3, 1, 10, 60, 3, 78, 0, 0, 35, 29,
            0, 12, 4, 1, 10, 60, 3, 78, 0, 0, 38, -86,
            0, 12, 9, 2, 67, 108, 105, 101, 110, 116, 83, 73,
            0, 8, 7, 1, 0, 1, 0, 1,
            0, 21, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };

    public final static byte[] ILLEGAL_ARRAY_1 = {
            16, 25, 31, 64, 0, 0, 0, 24,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 2, 1, 0, 1, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_2 = {
            16, 2, 31, 64, 0, 0, 0, 24,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 2, 1, 0, 1, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_3 = {
            16, 1, 31, 64, 0, 0, 0, 24,
            0, 8, 0, 1, 0, 0, 0, 10,
            0, 8, 2, 1, 0, 1, 0, 1
    };
    public final static byte[] ILLEGAL_ARRAY_4 = {
            16, 1, 31, 64, 0, 0, 0, 24,
            0, 8, 2, 1, 0, 1, 0, 1,
            0, 8, 1, 1, 0, 0, 0, 10
    };
    public final static byte[] ILLEGAL_ARRAY_5 = {
            16, 1, 31, 64, 0, 0, 0, 76,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 2, 1, 0, 1, 0, 1,
            0, 12, 3, 1, 10, 60, 3, 78, 0, 0, 35, 29,
            0, 12, 4, 1, 10, 60, 3, 78, 0, 0, 38, -86,
            0, 12, 9, 2, 67, 108, 105, 101, 110, 116, 83, 73,
            0, 8, 7, 1, 0, 1, 0, 1,
            0, 8, 10, 1, 0, 0, 0, 60
    };
    public final static byte[] ILLEGAL_ARRAY_6 = {
            16, 1, 31, 64, 0, 0, 0, 48,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 2, 1, 0, 1, 0, 1,
            0, 24, 16, 1, 0, 0, 0, 1, 0, 0, 0, 1, 73, 110, 116, 101, 103, 114, 105, 116, 121
    };
    public final static byte[] ILLEGAL_ARRAY_7 = {
            16, 1, 31, 64, 0, 0, 0, 20,
            0, 8, 1, 1, 0, 0, 0, 10,
            0, 8, 2, 1, 0, 1, 0, 1
    };

    @Override
	public void base() {
        assertEquals(get(), get());
        assertEquals(get().hashCode(), get().hashCode());

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
    public void testIllegalCopsMsgException1() throws CopsException {
        new CopsREQ(getBuffer(ILLEGAL_ARRAY_1));
    }

    @Test(expected = IllegalCopsMsgException.class)
    public void testIllegalCopsMsgException2() throws CopsException {
        new CopsREQ(getBuffer(ILLEGAL_ARRAY_2));
    }

    @Test(expected = IllegalCopsObjectException.class)
    public void testIllegalCopsObjectException() throws CopsException {
        new CopsREQ(getBuffer(ILLEGAL_ARRAY_3));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException1() throws CopsException {
        new CopsREQ(getBuffer(ILLEGAL_ARRAY_4));
    }

    @Test(expected = CopsFormatErrorException.class)
    public void testCopsFormatErrorException2() throws CopsException {
        new CopsREQ(getBuffer(ILLEGAL_ARRAY_5));
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflowException() {
        get(getBuffer(ILLEGAL_ARRAY_6));
    }

    @Test(expected = BufferOverflowException.class)
    public void testBufferOverflowException() {
        byteMe(get(getBuffer(ILLEGAL_ARRAY_7)));
    }

    @Override
    protected CopsREQ get(ByteBuffer buffer) {
        try {
            return new CopsREQ(buffer);
        } catch (CopsException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected CopsREQ get() {
        CopsREQ e = new CopsREQ(32757, 10, new Context(Context.RType.INCOMING_MESSAGE, 1));
        return e;
    }

    @Override
    protected byte[] getBytes() {
        return BASE_ARRAY;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private void testEquals() throws Exception {
        assertEquals(byteMe(new CopsREQ(8000, 10, getContext())), getBuffer(BASE_ARRAY));
        assertEquals(byteMe(fill(new CopsREQ(8000, 10, getContext()))), getBuffer(FULL_ARRAY));
    }

    private void testLength() throws Exception {
        CopsREQ e = new CopsREQ(32757, 10, getContext());
        System.out.println(e);
        int base_length = CopsHeader.LENGTH + Handle.LENGTH + Context.LENGTH;
        assertEquals(base_length, e.getMessageLength());

        fill(e);
        System.out.println(e);
        int req_length = base_length + 12 * 2 + 12 + 8 + 21;
        assertEquals(req_length, e.getMessageLength());

        empty(e);
        System.out.println(e);
        assertEquals(base_length, e.getMessageLength());
    }

    private CopsREQ fill(CopsREQ e) throws UnknownHostException {
        e.setiNInt(this.getiNInt());
        e.setoUTInt(this.getoUTInt());
        e.setClientSIs(this.getClientSIs());
        e.setLDecs(this.getLDecs());
        e.setIntegrity(this.getIntegrity());

        return e;
    }

    private CopsREQ empty(CopsREQ e) throws UnknownHostException {
        e.setiNInt(null);
        e.setoUTInt(null);
        e.setClientSIs(null);
        e.setLDecs(null);
        e.setIntegrity(null);

        return e;
    }

    private net.protocol.cops.object.Context getContext() {
        return new Context(Context.RType.INCOMING_MESSAGE, 1);
    }

    private Int getiNInt() throws UnknownHostException { // 12
        return new INInt(InetAddress.getByName("10.60.3.78"), 8989);
    }

    private Int getoUTInt() throws UnknownHostException { // 12
        return new OUTInt(InetAddress.getByName("10.60.3.78"), 9898);
    }

    private List<ClientSI> getClientSIs() { // 12
        List<ClientSI> list = new ArrayList<ClientSI>();
        list.add(new ClientSI(ClientSI.CType.NAMED, "ClientSI".getBytes()));
        return list;
    }

    private List<LDec> getLDecs() { // 8
        List<LDec> list = new ArrayList<LDec>();
        list.add(new LDec(new LPDPDecision(Decision.Command.INSTALL, 1)));
        return list;
    }

    private Integrity getIntegrity() throws UnknownHostException { // 21
        return new Integrity(1, 1, "Integrity".getBytes());
    }
}
