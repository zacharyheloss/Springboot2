package com.zachary.springboot.blog.pushlian.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zachary
 * @desc 日期处理类
 *
 */
public class DateUtils {
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String SPLIT_FORMAT = "yyyyMMddHHmmss";
	public static final String LACK_SECOND_FORMAT = "yyyy-MM-dd HH:mm";

	public static String getYear() {
		return new SimpleDateFormat("yyyy").format(new Date());
	}

	public static String getDay() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static String getDays() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public static String getTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getTimes() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static String formatDate(Date date, String formatStr) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isBlank(formatStr)) {
			formatStr = "yyyy-MM-dd";
		}
		DateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}

	public static Date parse(String str, String dateFormat) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat format = new SimpleDateFormat(dateFormat);
		try {
			return format.parse(str);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static Date parseDate(String str, SimpleDateFormat format) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		if (null == format) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			return format.parse(str);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static Date addYear(Date date, int years) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(1, years);
		return c.getTime();
	}

	public static Date addDay(Date date, int days) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(5, days);
		return c.getTime();
	}

	public static Date addHour(Date date, int hours) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(11, hours);
		return c.getTime();
	}

	public static Date addMin(Date date, int mins) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(12, mins);
		return c.getTime();
	}

	public static Date addSecond(Date date, int seconds) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(13, seconds);
		return c.getTime();
	}

	public static int daysDifference(Date preDate, Date date) {
		long milliSeconds = date.getTime() - preDate.getTime();
		return new Long(milliSeconds / 1000L / 3600L / 24L).intValue();
	}

	public static int secondDifference(Date beforedate, Date afterdate) {
		long timeDelta = (beforedate.getTime() - afterdate.getTime()) / 1000L;
		int secondsDelta = timeDelta > 0L ? (int) timeDelta : (int) Math.abs(timeDelta);
		return secondsDelta;
	}

	public static Date addDay(Date date, int days, int hours, int mins, int seconds, int milliSeconds) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(5, days);
		ca.set(11, hours);
		ca.set(12, mins);
		ca.set(13, seconds);
		ca.set(14, milliSeconds);
		return ca.getTime();
	}

	public static Boolean dayCompare(Date date1, Date date2) {
		if ((date1 == null) || (date2 == null)) {
			throw new IllegalArgumentException("The date must not be null");
		}
		int result = date1.compareTo(date2);
		if (result >= 0) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}

	public static Long getDateDiff(Date date1, Date date2, String type) {
		long diff = date1.getTime() - date2.getTime();
		if ("1".equals(type)) {
			return Long.valueOf(diff / 86400000L);
		}
		if ("2".equals(type)) {
			return Long.valueOf(diff / 3600000L % 24L);
		}
		if ("3".equals(type)) {
			return Long.valueOf(diff / 60000L % 60L);
		}
		if ("4".equals(type)) {
			return Long.valueOf(diff / 1000L % 60L);
		}
		if ("5".equals(type)) {
			return Long.valueOf(diff / 60000L);
		}
		return Long.valueOf(0L);
	}

	public static int compareDate(Date bdate, Date adate) {
		String beforeDate = formatDate(bdate, "");
		String afterDate = formatDate(adate, "");
		return compareDate(beforeDate, afterDate);
	}

	private static int compareDate(String bdate, String adate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(bdate);
			Date dt2 = df.parse(adate);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			}
			if (dt1.getTime() < dt2.getTime()) {
				return -1;
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("比较日期错误！", e);
		}
		return 0;
	}
}
