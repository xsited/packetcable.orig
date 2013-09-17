package net.protocol.common.util;

import java.util.Date;

/***
 * NtpTime class represents the Network Time Protocol (NTP) timestamp as defined
 * in RFC-1305 and SNTP (RFC-2030).
 * 
 * @author jinhongw@gmail.com
 * @see java.util.Date
 * @see <a href="http://tools.ietf.org/html/rfc1305">Network Time Protocol</a>
 * @see <a href="http://datatracker.ietf.org/doc/rfc2030/">Simple Network Time Protocol</a>
 */
public class NtpTime implements java.io.Serializable, Comparable<NtpTime> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3077930232411597336L;

	/**
	 * baseline NTP time if bit-0=0 -> 7-Feb-2036 @ 06:28:16 UTC
	 */
	private static final long msb0baseTime = 2085978496000L;

	/**
	 * baseline NTP time if bit-0=1 -> 1-Jan-1900 @ 01:00:00 UTC
	 */
	private static final long msb1baseTime = -2208988800000L;

	/**
	 * NTP timestamp value: 64-bit unsigned fixed-point number as defined in
	 * RFC-1305 with high-order 32 bits the seconds field and the low-order
	 * 32-bits the fractional field.
	 */
	private final long ntpTime;

	public NtpTime(long ntpTime) {
		this.ntpTime = ntpTime;
	}

	public NtpTime(Date date) {
		ntpTime = (date == null) ? 0 : toNtpTime(date.getTime());
	}

	public static NtpTime get(long date) {
		return new NtpTime(toNtpTime(date));
	}

	/***
	 * Returns the value of this NtpTime as a long value.
	 * 
	 * @return the 64-bit long value represented by this object.
	 */
	public long getNtpTime() {
		return ntpTime;
	}

	/***
	 * Convert NTP timestamp to Java standard time.
	 * 
	 * @return NTP Timestamp in Java time
	 */
	public long getTime() {
		return getTime(ntpTime);
	}
	
	/***
	 * Returns high-order 32-bits representing the seconds of this NTP
	 * timestamp.
	 * 
	 * @return seconds represented by this NTP timestamp.
	 */
	public long getSeconds() {
		return (ntpTime >>> 32) & 0xffffffffL;
	}
	
	/***
	 * Returns low-order 32-bits representing the fractional seconds.
	 * 
	 * @return fractional seconds represented by this NTP timestamp.
	 */
	public long getFraction() {
		return ntpTime & 0xffffffffL;
	}

	/***
	 * Convert 64-bit NTP timestamp to Java standard time.
	 * 
	 * @param ntpTime
	 * @return the number of milliseconds since January 1, 1970, 00:00:00 GMT
	 *         represented by this NTP timestamp value.
	 */
	public static long getTime(long ntpTime) {
		// high-order 32-bits
		long seconds = (ntpTime >>> 32) & 0xffffffffL;
		// low-order 32-bits
		long fraction = ntpTime & 0xffffffffL;

		// Use round-off on fractional part to preserve going to lower precision
		fraction = Math.round(1000D * fraction / 0x100000000L);

		/*
		 * If the most significant bit (MSB) on the seconds field is set we use
		 * a different time base. The following text is a quote from RFC-2030
		 * (SNTP v4):
		 * 
		 * If bit 0 is set, the UTC time is in the range 1968-2036 and UTC time
		 * is reckoned from 0h 0m 0s UTC on 1 January 1900. If bit 0 is not set,
		 * the time is in the range 2036-2104 and UTC time is reckoned from 6h
		 * 28m 16s UTC on 7 February 2036.
		 */
		long msb = seconds & 0x80000000L;
		// use base: 7-Feb-2036 @ 06:28:16 UTC
		if (msb == 0) return msb0baseTime + (seconds * 1000) + fraction;
		// use base: 1-Jan-1900 @ 01:00:00 UTC
		return msb1baseTime + (seconds * 1000) + fraction;
	}

	/***
	 * Convert NTP timestamp to Java Date object.
	 * 
	 * @return NTP Timestamp in Java Date
	 */
	public Date getDate() {
		return new Date(getTime(ntpTime));
	}

	/***
	 * Convert Java time in milliseconds to 64-bit NTP time representation.
	 * 
	 * @param millis
	 *            Java time in milliseconds since Jan 1, 1970 00:00:00 UTC
	 * @return NTP timestamp representation of Java time value.
	 */
	public static long toNtpTime(long millis) {
		boolean base = millis < msb0baseTime; // time < Feb-2036
		long baseTime;
		// dates <= Feb-2036
		if (base)  baseTime = millis - msb1baseTime;
		// if base0 needed for dates >= Feb-2036
		else  baseTime = millis - msb0baseTime;

		long seconds = baseTime / 1000;
		long fraction = ((baseTime % 1000) * 0x100000000L) / 1000;

		// set high-order bit if msb1baseTime 1900 used
		if (base) seconds |= 0x80000000L;

		return (seconds << 32 | fraction);
	}
	
	/***
	 * Converts this <code>TimeStamp</code> object to a <code>String</code>.
	 * 
	 * @return NTP timestamp 64-bit long value as hex string with seconds
	 *         separated by fractional seconds.
	 */
	@Override
	public String toString() {
		return getDate().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ntpTime ^ (ntpTime >>> 32));
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
		NtpTime other = (NtpTime) obj;
		if (ntpTime != other.ntpTime)
			return false;
		return true;
	}

	@Override
	public int compareTo(NtpTime o) {
		long tv = this.ntpTime;
		long atv = o.ntpTime;
		return (tv < atv ? -1 : (tv == atv ? 0 : 1));
	}
}
