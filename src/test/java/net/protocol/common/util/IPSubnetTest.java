package net.protocol.common.util;

import net.protocol.common.AbstractTest;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

/**
 * @author jinhongw@gmail.com
 */
public class IPSubnetTest extends AbstractTest {

    @Override
    public void base() throws Exception {
        IPSubnet v4subnet23 = new IPSubnet("10.60.4.63/255.255.254.0");
        assertEquals(v4subnet23, new IPSubnet("10.60.4.63/23"));
        assertEquals(v4subnet23.hashCode(), new IPSubnet("10.60.4.63/23").hashCode());
        
        assertEquals(IPUtils.getNetworkPrefixLength("255.255.254.0"), v4subnet23.getPrefixLength());
        assertEquals(true, v4subnet23.contains("10.60.4.60"));
        assertEquals(Inet4Address.getByName("10.60.4.0"), v4subnet23.getStartAddress());
        assertEquals(Inet4Address.getByName("10.60.5.255"), v4subnet23.getEndAddress());
        
        IPSubnet v4subnet24 = new IPSubnet("10.60.4.63/255.255.255.0");
        assertEquals(IPUtils.getNetworkPrefixLength("255.255.255.0"), v4subnet24.getPrefixLength());
        assertEquals(true, v4subnet24.contains("10.60.4.60"));
        assertEquals(Inet4Address.getByName("10.60.4.63"), v4subnet24.getAddress());
        assertEquals(Inet4Address.getByName("10.60.4.0"), v4subnet24.getStartAddress());
        assertEquals(Inet4Address.getByName("10.60.4.255"), v4subnet24.getEndAddress());

        IPSubnet v4subnet26 = new IPSubnet(Inet4Address.getByName("192.168.5.5"), 26);
        assertEquals(26, v4subnet26.getPrefixLength());
        assertEquals(true, v4subnet26.contains("192.168.5.60"));
        assertEquals(Inet4Address.getByName("192.168.5.0"), v4subnet26.getStartAddress());
        assertEquals(Inet4Address.getByName("192.168.5.63"), v4subnet26.getEndAddress());
        
        IPSubnet v6subnet24 = new IPSubnet("1fff:0:0a88:85a3:0:0:0:0/24");
        assertEquals(24, v6subnet24.getPrefixLength());
        assertEquals(true, v6subnet24.contains("1fff:0:0a88:85a3:0:0:ac1f:8001"));
        assertEquals(Inet6Address.getByName("1fff:0:0:0:0:0:0:0"), v6subnet24.getStartAddress());
    }

    @Override
    public void logic() throws Exception {
        InetAddress addr = Inet4Address.getByName("192.168.5.63");
        byte[] src = addr.getAddress();
        System.out.println(Bytes.dump(src));
        System.out.println(Bytes.dump(IPUtils.toIPv6Bytes(addr)));
    }
}
