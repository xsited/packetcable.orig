package net.protocol.cops.object;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public abstract class PDPAddrTest extends CopsObjectTest {
    public final static int TPS_UNIT = 300 * 10000;

    @Override
	public void base() throws UnknownHostException {
        testBaseLastPDPAddr();
        testBasePDPRedirAddr();

        Map<CopsObject, String> map = new HashMap<CopsObject, String>();
        map.put(get("127.0.0.1"), "A");
        map.put(get("127.0.0.1"), "B");
        assertNotNull(map.get(get("127.0.0.1")));
        map.put(get("127.0.0.1", 8080), "C");
        map.put(get("127.0.0.1", 8080), "D");
        assertNotNull(map.get(get("127.0.0.1", 8080)));

        assertEquals(2, map.size());
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

    private void testBaseLastPDPAddr() throws UnknownHostException {
        assertEquals(get("127.0.0.1"), get("127.0.0.1"));
        assertEquals(get("127.0.0.1").hashCode(), get("127.0.0.1").hashCode());

        assertNotEquals(get("127.0.0.1"), get("0.0.0.0"));
        assertNotEquals(get("127.0.0.1").hashCode(), get("0.0.0.0").hashCode());

        assertNotEquals(get("127.0.0.1"), get("127.0.0.1", 8080));
    }

    private void testBasePDPRedirAddr() throws UnknownHostException {
        assertEquals(get("127.0.0.1", 8080), get("127.0.0.1", 8080));
        assertEquals(get("127.0.0.1", 8080).hashCode(), get("127.0.0.1", 8080)
                .hashCode());

        assertNotEquals(get("127.0.0.1", 8080), get("0.0.0.0", 8989));
        assertNotEquals(get("127.0.0.1", 8080).hashCode(), get("0.0.0.0", 8989)
                .hashCode());

        assertNotEquals(get("127.0.0.1", 8080), get("127.0.0.1"));
    }

    private LastPDPAddr get(String host) throws UnknownHostException {
        return new LastPDPAddr(InetAddress.getByName(host));
    }

    private PDPRedirAddr get(String host, int port) throws UnknownHostException {
        return new PDPRedirAddr(InetAddress.getByName(host), port);
    }
}
