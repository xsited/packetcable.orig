package net.protocol.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author jinhongw@gmail.com
 */
public class DateUtils {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(DEFAULT_FORMAT);

	public static String getCalendar(long millis, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(millis));
	}

	public static Calendar getCalendar(String date) {
		return getCalendar(date, DEFAULT_SDF);
	}

	public static Calendar getCalendar(String date, SimpleDateFormat format) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	public static Calendar getCalendar(String date, String format, Locale locale) {
		Calendar calendar = Calendar.getInstance(locale);
		try {
			calendar.setTime(new SimpleDateFormat(format, locale).parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	public static String format(Calendar calendar) {
		if (calendar == null || calendar.getTime() == null)
			return null;
		return DEFAULT_SDF.format(calendar.getTime());
	}

	public static String format(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return format(calendar, DEFAULT_FORMAT);
	}

	public static String format(long millis, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return format(calendar, format);
	}

	public static String format(Calendar calendar, String format) {
		if (calendar == null || calendar.getTime() == null)
			return null;
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	public static String format(Calendar calendar, String format, Locale locale) {
		if (calendar == null || calendar.getTime() == null)
			return null;
		return new SimpleDateFormat(format, locale).format(calendar.getTime());
	}
}
