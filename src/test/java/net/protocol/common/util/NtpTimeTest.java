package net.protocol.common.util;

import net.protocol.common.AbstractTest;

import java.util.Date;

/**
 * @author jinhongw@gmail.com
 */
public class NtpTimeTest extends AbstractTest {

	@Override
	public void base() throws Exception {
		long t = System.currentTimeMillis();
		NtpTime nt = NtpTime.get(t);
		Date date = nt.getDate();
		assertEquals(nt.toString(), date.toString());
		assertEquals(t, nt.getTime());
		assertEquals(t, date.getTime());
	}

	@Override
	public void logic() throws Exception {
		NtpTime o = new NtpTime(new Date());
		System.out.println(o.getNtpTime() < Long.MIN_VALUE);
		System.out.println(o);
	}
}
