package net.protocol.common.util;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author jinhongw@gmail.com
 */
public class IPUtils {

	/**
	 * Returns an <code>int</code> representing the given IP address. if this
	 * address is <code>Inet6Address</code> then this address must an
	 * IPv4-mapped address else will throw IllegalArgumentException.
	 * 
	 * @param address
	 *            the IP address
	 * @return a <code>int</code> representing this IP address
	 */
	public static int toInt(InetAddress address) {
		byte[] addr;
		if (address instanceof Inet6Address)
			addr = toIPv4Bytes(address);
		else
			addr = address.getAddress();
		return Bits.fromByteArray(addr);
	}

	/**
	 * Returns an <code>Inet4Address</code> representing the given IP address
	 * specified by the <code>int</code> argument.
	 * 
	 * @param address
	 *            the IP address
	 * @return an {@link Inet4Address} equivalent of the argument
	 * @throws UnknownHostException
	 */
	public static InetAddress toIPv4(int address) throws UnknownHostException {
		// a 4-element byte array
		byte[] addr = Bits.toByteArray(address);
		return InetAddress.getByAddress(addr);
	}

	/**
	 * Returns a <code>BigInteger</code> representing the given IP address.
	 * 
	 * @param address
	 *            the IP address
	 * @return a {@link BigInteger} representing this IP address
	 */
	public static BigInteger toBigInteger(InetAddress address) {
		byte[] ipv6;
		if (address instanceof Inet4Address)
			ipv6 = toIPv6Bytes(address);
		else
			ipv6 = address.getAddress();
		if (ipv6[0] == -1) return new BigInteger(1, ipv6);
		return new BigInteger(ipv6);
	}

	/**
	 * Returns an <code>InetAddress</code> representing the given IP address
	 * specified by the <code>BigInteger</code> argument.
	 * 
	 * @param address
	 *            the IP address
	 * @return an {@link InetAddress} equivalent of the argument
	 * @throws UnknownHostException
	 */
	public static InetAddress toIPv6(BigInteger address)
			throws UnknownHostException {
		// The length of an IPv6 address is 128 bits
		int ipv6Length = 16;
		byte[] a = new byte[ipv6Length];
		byte[] addr = address.toByteArray();
		if (addr.length > ipv6Length && !(addr.length == 17 && addr[0] == 0)) {
			String s = "Invalid IPv6 address: " + address + " (too big)";
			throw new IllegalArgumentException(s);
		}
		if (addr.length == ipv6Length) return InetAddress.getByAddress(addr);

		// handle the case where the IPv6 address starts with "FF".
		if (addr.length == 17) {
			System.arraycopy(addr, 1, a, 0, ipv6Length);
		} else { // copy the address into a 16 byte array, zero-filled.
			int destPos = ipv6Length - addr.length;
			System.arraycopy(addr, 0, a, destPos, addr.length);
		}
		return InetAddress.getByAddress(a);
	}

	/**
	 * Returns an <code>Inet4Address</code> representing the given IP address,
	 * if this address is <code>Inet6Address</code> then this addresses must an
	 * IPv4-mapped address else will throw IllegalArgumentException.
	 * 
	 * @param address
	 *            the IP address
	 * @return an {@link Inet4Address} representing the IP address
	 * @throws UnknownHostException
	 */
	public static InetAddress toIPv4(InetAddress address)
			throws UnknownHostException {
		return Inet4Address.getByAddress(toIPv4Bytes(address));
	}

	/**
	 * <pre>
	 * 135.75.43.52 hexadecimal is 0x874B2B34
	 * 
	 * 135.75.43.52 => ::ffff:874B:2B34
	 * 135.75.43.52 => ::ffff:135.75.43.52
	 * 135.75.43.52 => 0000:0000:0000:0000:0000:ffff:874B:2B34
	 * </pre>
	 * 
	 * @param address
	 *            the IP address
	 * @return an {@link Inet6Address} representing the IP address
	 * @throws UnknownHostException
	 */
	public static InetAddress toIPv6(InetAddress address)
			throws UnknownHostException {
		return Inet6Address.getByAddress(toIPv6Bytes(address));
	}

	/**
	 * Returns an 4-element byte array(raw IP address) representing this
	 * <code>InetAddress</code> argument.
	 * 
	 * @param address
	 *            the IP address
	 * @return an 4-element byte array
	 */
	public static byte[] toIPv4Bytes(InetAddress address) {
		byte[] a = address.getAddress();
		if (address instanceof Inet4Address)
			return a;
		String s = "This IPv6 address cannot be used in IPv4 context";
		for (int i = 0; i < 9; i++) {
			if (a[i] != 0) throw new IllegalArgumentException(s);
		}
		if (a[10] != 0 && a[10] != 0xFF || a[11] != 0 && a[11] != 0xFF) {
			throw new IllegalArgumentException(s);
		}
		return new byte[] { a[12], a[13], a[14], a[15] };
	}

	/**
	 * Returns an 16-element byte array(raw IP address) representing this
	 * <code>InetAddress</code> argument.
	 * 
	 * @param address
	 *            the IP address
	 * @return an 16-element byte array
	 */
	public static byte[] toIPv6Bytes(InetAddress address) {
		byte[] a = address.getAddress();
		if (address instanceof Inet6Address)
			return a;
		return new byte[] { 0, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 0, 0, a[0], a[1], a[2], a[3] };
	}

	/**
	 * Returns the network prefix length for this subnet. This is also known
	 * as the subnet mask in the context of IPv4 addresses. Typical IPv4 values
	 * would be 8(255.0.0.0), 16(255.255.0.0) or 24(255.255.255.0).
	 * 
	 * <pre>
	 * For examples:
	 *     /255.0.0.0 => 8
	 *     /255.128.0.0 => 9
	 *     /255.192.0.0 => 10
	 *     /255.224.0.0 => 11
	 *     /255.240.0.0 => 12
	 *     /255.248.0.0 => 13
	 *     /255.252.0.0 => 14
	 *     /255.254.0.0 => 15
	 *     /255.255.0.0 => 16
	 *     /255.255.128.0 => 17
	 *     /255.255.192.0 => 18
	 *     /255.255.224.0 => 19
	 *     /255.255.240.0 => 20
	 *     /255.255.248.0 => 21
	 *     /255.255.252.0 => 22
	 *     /255.255.254.0 => 23
	 *     /255.255.255.0 => 24
	 *     /255.255.255.128 => 25
	 *     /255.255.255.192 => 26
	 *     /255.255.255.224 => 27
	 *     /255.255.255.240 => 28
	 *     /255.255.255.248 => 29
	 *     /255.255.255.252 => 30
	 *     /255.255.255.254 => 31
	 *     /255.255.255.255 => 32
	 * </pre>
	 * 
	 * @param subnet
	 *            a ipv4 sub network mask
	 * @return an <code>int</code> representing the prefix length for the subnet
	 */
	public static int getNetworkPrefixLength(String subnet) {
		String[] array = subnet.split("\\.");
		int mask = 0;
		for (String e : array) {
			mask += Integer.bitCount(Integer.parseInt(e));
		}
		return mask;
	}

	/**
	 * Returns the subnet for this network prefix length.
	 * <code>
	 * int mask = IPUtils.getIPv4SubNetMask(24);  // -256
	 * InetAddress address = IPUtils.toIPv4(mask); // 255.255.255.0
	 * </code>
	 * <pre>
	 * Prefix Length => Decimal(Dotted decimal) 
	 *     24 => -256(255.255.255.0) 
	 *     8 => -16777216(255.0.0.0)
	 * </pre>
	 * 
	 * @param prefixLength
	 *            The network prefix length
	 * @return an <code>int</code> representing the decimal notation for the subnet.
	 */
	public static int getIPv4SubnetMask(int prefixLength) {
		return ~((1 << (32 - prefixLength)) - 1);
	}

	/**
	 * Returns the number of addresses for the subnet
	 * 
	 * <pre>
	 * For examples:
	 *     24(255.255.255.0) => 256
	 *     8(255.0.0.0) => 16777216
	 * </pre>
	 * 
	 * @param prefixLength
	 *            The network prefix length
	 * @return the number of addresses
	 */
	public static int getNumberOfIPv4(int prefixLength) {
		// 00000000 00000000 00000000 00000001 << (32 - 24)
		// 00000000 00000000 00000001 00000000 => 256
		return 1 << (32 - prefixLength);
	}

	public static BigInteger getIPv6SubnetMask(int prefixLength) {
		return BigInteger.ONE.shiftLeft(128 - prefixLength)
				.subtract(BigInteger.ONE).not();
	}

	public static BigInteger getNumberOfIPv6(int prefixLength) {
		return BigInteger.ONE.shiftLeft(128 - prefixLength);
	}
}
