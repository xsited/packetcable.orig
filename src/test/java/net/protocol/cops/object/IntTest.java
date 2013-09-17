package net.protocol.cops.object;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public abstract class IntTest extends CopsObjectTest {
    public final static int TPS_UNIT = 300 * 10000;

    @Override
	public void base() throws UnknownHostException {
        testBaseINInt();
        testBaseOUTInt();

        Map<CopsObject, String> map = new HashMap<CopsObject, String>();
        map.put(getINInt(8080), "A");
        map.put(getINInt(8080), "B");
        assertNotNull(map.get(getINInt(8080)));
        map.put(getOUTInt(8080), "C");
        map.put(getOUTInt(8080), "D");
        assertNotNull(map.get(getOUTInt(8080)));

        assertEquals(2, map.size());
    }

    @Override
    protected Int get(ByteBuffer buffer) {
        try {
            return new Int(buffer);
        } catch (IllegalCopsObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    protected InetAddress getIpv4() {
        try {
            return InetAddress.getByName("10.60.3.78");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected InetAddress getIpv6() {
        try {
            return InetAddress.getByName("2001:da8:9000:b255:200:e8ff:feb0:5c5e");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void testBaseINInt() throws UnknownHostException {
        assertEquals(getINInt(8989), getINInt(8989));
        assertEquals(getINInt(8989).hashCode(), getINInt(8989).hashCode());

        assertNotEquals(getINInt(8989), getINInt(9898));
        assertNotEquals(getINInt(8989).hashCode(), getINInt(9898).hashCode());

        assertNotEquals(getINInt(9090), getOUTInt(9090));
    }

    private void testBaseOUTInt() throws UnknownHostException {
        assertEquals(getOUTInt(8989), getOUTInt(8989));
        assertEquals(getOUTInt(8989).hashCode(), getOUTInt(8989).hashCode());

        assertNotEquals(getOUTInt(8989), getOUTInt(9898));
        assertNotEquals(getOUTInt(8989).hashCode(), getOUTInt(9898).hashCode());

        assertNotEquals(getOUTInt(9090), getINInt(9090));
    }

    private Int getINInt(int intf) throws UnknownHostException {
        return new INInt(InetAddress.getByName("10.60.3.78"), intf);
    }

    private Int getOUTInt(int intf) throws UnknownHostException {
        return new OUTInt(InetAddress.getByName("10.60.3.78"), intf);
    }
}
