package net.protocol.common.util;

import net.protocol.common.AbstractTest;
import org.junit.Test;

import java.math.BigInteger;
import java.net.*;

/**
 * @author jinhongw@gmail.com
 */
public class IPUtilsTest extends AbstractTest {

	@Override
	public void base() throws Exception {
		testForIPv4(InetAddress.getByName("10.60.4.63"));
		testForIPv6(InetAddress
				.getByName("2001:0db8:0000:0000:0000:ff00:0042:8329"));
        testForIPv6(InetAddress.getByName("2001:db8:0:0:0:ff00:42:8329"));
        testForIPv6(InetAddress.getByName("2001:0db8::ff00:0042:8329"));
        testForIPv6(InetAddress.getByName("2001:db8::ff00:42:8329"));

        testForIPv6(InetAddress.getByName("0000:0000:0000:0000:0000:0000:0000:0001"));
        testForIPv6(InetAddress.getByName("::0001"));
        testForIPv6(InetAddress.getByName("::1"));

		assertEquals(8, IPUtils.getNetworkPrefixLength("255.0.0.0"));
		assertEquals(9, IPUtils.getNetworkPrefixLength("255.128.0.0"));
		assertEquals(24, IPUtils.getNetworkPrefixLength("255.255.255.0"));
		assertEquals(32, IPUtils.getNetworkPrefixLength("255.255.255.255"));
	}

	@Override
	public void logic() throws Exception {
		for (int p = 8; p <= 32; p++) {
			// Decimal subnet mask
			int mask = IPUtils.getIPv4SubnetMask(p); 
			// Dotted decimal subnet mask
			InetAddress smask = IPUtils.toIPv4(mask); 
			int addresses = IPUtils.getNumberOfIPv4(p);
			System.out.println(p + " => " + mask + " => " + smask
					+ " addresses(" + addresses + ")");
		}
		
		InetAddress localHost = Inet4Address.getLocalHost();
		NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
		System.out.println(networkInterface);
		for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
		    System.out.println(address.getAddress() + "/" + address.getNetworkPrefixLength());
		}
		
		int prefixLength = 128;
		BigInteger mask = IPUtils.getIPv6SubnetMask(prefixLength);
		// Dotted decimal subnet mask
		InetAddress smask = IPUtils.toIPv6(mask);
		BigInteger addresses = IPUtils.getNumberOfIPv6(prefixLength);
		// 128 => -1 => /0:0:0:0:0:0:0:ff addresses(1)
		System.out.println(prefixLength + " => " + mask + " => " + smask
				+ " addresses(" + addresses + ")");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentException1() throws IllegalArgumentException {
		try {
			IPUtils.toIPv4(InetAddress
					.getByName("2001:0db8:0000:0000:0000:ff00:0042:8329"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void testForIPv4(InetAddress ipv4) throws UnknownHostException {
		// 10.60.4.63 = > 0:0:0:0:0:0:a3c:43f
		InetAddress ipv6 = IPUtils.toIPv6(ipv4);

		assertEquals(IPUtils.toIPv4(ipv6), ipv4);
		assertEquals(IPUtils.toIPv4(IPUtils.toInt(ipv4)), ipv4);
		assertEquals(IPUtils.toIPv6(IPUtils.toBigInteger(ipv6)), ipv6);
		assertEquals(IPUtils.toBigInteger(ipv6).intValue(), IPUtils.toInt(ipv4));

		assertEquals(Bytes.dump(IPUtils.toIPv6Bytes(ipv4)),
				Bytes.dump(ipv6.getAddress()));
		assertEquals(Bytes.dump(IPUtils.toIPv4Bytes(ipv6)),
				Bytes.dump(ipv4.getAddress()));
	}

	private void testForIPv6(InetAddress ipv6) throws UnknownHostException {
		assertEquals(IPUtils.toIPv6(ipv6), ipv6);
		assertEquals(IPUtils.toIPv6(IPUtils.toBigInteger(ipv6)), ipv6);

		assertEquals(Bytes.dump(IPUtils.toIPv6Bytes(ipv6)),
				Bytes.dump(ipv6.getAddress()));
	}
}
