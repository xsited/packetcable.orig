package net.protocol.common.util;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class allows to check subnet contains(address is in the
 * subnet-address-range) the specified address.
 * <p>
 * The prefix length is just a shorthand way of expressing the subnet mask. The
 * prefix length is the number of bits set in the subnet mask; for instance, if
 * the subnet mask is 255.255.255.0, there are 24 1’s in the binary version of
 * the subnet mask, so the prefix length is 24 bits.
 * <p>
 * <pre>
 * Below examples illustrates network masks and prefix lengths.
 *     Binary Mask                           Prefix Length    SubNet Mask
 *     11111111 00000000 00000000 00000000   /8               255.0.0.0
 *     11111111 10000000 00000000 00000000   /9               255.128.0.0
 *     11111111 11000000 00000000 00000000   /10              255.192.0.0
 *     ...
 *     11111111 11111111 11111111 00000000   /24              255.255.255.0
 *     ...
 *     11111111 11111111 11111111 11111111   /32              255.255.255.255
 * @author jinhongw@gmail.com
 */
public class IPSubnet {
	private Impl impl;

	/**
	 * <code>
	 * IpSubnet subnet = new IpSubnet("192.168.25.1/24"); or<br>
	 * IpSubnet subnet = new IpSubnet("192.168.25.1/255.255.255.0"); or<br>
	 * IpSubnet subnet = new IpSubnet("1fff:0:0a88:85a3:0:0:0:0/24");
	 * </code>
	 * 
	 * @param network a string form of IP/prefix-length or IP/mask
	 */
	public IPSubnet(String network) throws UnknownHostException {
		this.impl = Impl.newImpl(network);
	}

	/**
	 * @param address
	 *            the base address i.e.: 192.168.5.75
	 * @param prefixLength
	 *            the prefix length i.e.: 24
	 * @throws UnknownHostException
	 */
	public IPSubnet(String address, int prefixLength)
			throws UnknownHostException {
		this(Inet4Address.getByName(address), prefixLength);
	}

	/**
	 * @param address
	 *            the base address
	 * @param prefixLength
	 *            the prefix length
	 * @throws UnknownHostException
	 */
	public IPSubnet(InetAddress address, int prefixLength)
			throws UnknownHostException {
		String message = "Invalid prefix length be used: ";
		if (prefixLength < 0)
			throw new UnknownHostException(message + prefixLength);
		if (address instanceof Inet4Address) {
			if (prefixLength > 32)
				throw new UnknownHostException(message + prefixLength);
			impl = Impl.newImpl(address, prefixLength);
		}
		// Inet6Address
		if (prefixLength > 128)
			throw new UnknownHostException(message + prefixLength);
		impl = Impl.newImpl(address, prefixLength);
	}

	/**
	 * Returns the base address of subnet.
	 *  
	 * @return the base address of subnet
	 * @throws UnknownHostException 
	 */
	public InetAddress getAddress() throws UnknownHostException {
		return impl.getAddress();
	}
	
	/**
	 * Returns the start address of subnet-address-range.
	 * 
	 * @return the start address of subnet
	 * @throws UnknownHostException
	 */
	public InetAddress getStartAddress() throws UnknownHostException {
		return impl.getStartAddress();
	}

	/**
	 * Returns the end address of subnet-address-range.
	 * 
	 * @return the end address of subnet
	 * @throws UnknownHostException
	 */
	public InetAddress getEndAddress() throws UnknownHostException {
		return impl.getEndAddress();
	}
	
	/**
	 * Returns the network prefix length for this subnet.
	 * 
	 * @return a <code>int</code> representing the prefix length for the subnet
	 */
	public int getPrefixLength() {
		return impl.getPrefixLength();
	}
	
	/**
	 * Returns <tt>true</tt> if this subnet contains(address is in the
	 * subnet-address-range) the specified address.
	 * 
	 * @param address the IP address
	 * @return returns true if the given address is inside subnet
	 * @throws UnknownHostException
	 */
    public boolean contains(String address) throws UnknownHostException {
        return contains(InetAddress.getByName(address));
    }

	/**
	 * Returns <tt>true</tt> if this subnet contains(address is in the
	 * subnet-address-range) the specified address.
	 * 
	 * @param address the IP address
	 * @return returns true if the given address is inside subnet
	 */
	public boolean contains(InetAddress address) {
		return impl.contains(address);
	}
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((impl == null) ? 0 : impl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IPSubnet other = (IPSubnet) obj;
		if (impl == null) {
			if (other.impl != null)
				return false;
		} else if (!impl.equals(other.impl))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return impl.toString();
    }

	static abstract class Impl {
		protected InetAddress address;
		protected int prefixLength; // network prefix length

		public static Impl newImpl(String network) throws UnknownHostException {
			String[] tokens = network.split("/\\s*");
			if (tokens.length != 2)
				throw new IllegalArgumentException(network);
			InetAddress address = InetAddress.getByName(tokens[0]);
			if (tokens[1].indexOf('.') < 0) { // 192.168.5.0/24
				return newImpl(address, Integer.parseInt(tokens[1]));
			} else { // 192.168.5.0/255.255.255.0
				return newImpl(address,
						IPUtils.getNetworkPrefixLength(tokens[1]));
			}
		}

		public static Impl newImpl(InetAddress address, int mask)
				throws UnknownHostException {
			if (address instanceof Inet4Address) {
				return new Impl4((Inet4Address) address, mask);
			}
			return new Impl6((Inet6Address) address, mask);
		}
		
		public InetAddress getAddress() {
			return address;
		}
		
		public int getPrefixLength() {
			return prefixLength;
		}
		
		public String getMask() {
			int mask = IPUtils.getIPv4SubnetMask(prefixLength);
			InetAddress address;
			try {
				address = IPUtils.toIPv4(mask);
			} catch (UnknownHostException e) {
				return null;
			}
			return address.getHostAddress();
		}

		public abstract InetAddress getStartAddress() throws UnknownHostException;
		
		public abstract InetAddress getEndAddress() throws UnknownHostException;

		public abstract boolean contains(InetAddress address);

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((address == null) ? 0 : address.hashCode());
			result = prime * result + prefixLength;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Impl other = (Impl) obj;
			if (address == null) {
				if (other.address != null)
					return false;
			} else if (!address.equals(other.address))
				return false;
			if (prefixLength != other.prefixLength)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return address.getHostAddress() + '/' + prefixLength;
		}
	}

	static class Impl4 extends Impl {
		private int startAddressInt;
		private final int endAddressInt;

		protected Impl4(Inet4Address address, int prefixLength)
				throws UnknownHostException {
			this.address = address;
			this.prefixLength = prefixLength;
			this.startAddressInt = IPUtils.toInt(address);

			int mask = IPUtils.getIPv4SubnetMask(prefixLength);
			this.startAddressInt &= mask;

			this.endAddressInt = startAddressInt
					+ IPUtils.getNumberOfIPv4(prefixLength) - 1;
		}

		@Override
		public InetAddress getStartAddress() throws UnknownHostException {
			return IPUtils.toIPv4(startAddressInt);
		}
		
		@Override
		public InetAddress getEndAddress() throws UnknownHostException {
			return IPUtils.toIPv4(endAddressInt);
		}

		@Override
		public boolean contains(InetAddress address) {
			int search = IPUtils.toInt(address);
			return search >= startAddressInt && search <= endAddressInt;
		}
	}

	static class Impl6 extends Impl {
		private BigInteger startAddressBigInt;
		private BigInteger endAddressBigInt;

		protected Impl6(Inet6Address address, int prefixLength)
				throws UnknownHostException {
			this.address = address;
			this.prefixLength = prefixLength;
			this.startAddressBigInt = IPUtils.toBigInteger(address);

			BigInteger mask = IPUtils.getIPv6SubnetMask(prefixLength);
			this.startAddressBigInt = startAddressBigInt.and(mask);

			this.endAddressBigInt = startAddressBigInt.add(
					IPUtils.getNumberOfIPv6(prefixLength)).subtract(
					BigInteger.ONE);
		}

		@Override
		public InetAddress getStartAddress() throws UnknownHostException {
			return IPUtils.toIPv6(startAddressBigInt);
		}
		
		@Override
		public InetAddress getEndAddress() throws UnknownHostException {
			return IPUtils.toIPv6(startAddressBigInt);
		}

		@Override
		public boolean contains(InetAddress address) {
			BigInteger search = IPUtils.toBigInteger(address);
			return search.compareTo(startAddressBigInt) >= 0
					&& search.compareTo(endAddressBigInt) <= 0;
		}
	}
}
