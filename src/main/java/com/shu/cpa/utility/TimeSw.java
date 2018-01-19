package com.shu.cpa.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeSw {
	public static String convert(Date mill) {
		return convert(mill, "yyyy-MM-dd");
	}

	public static String convert_m_d(Date mill) {
		return convert(mill, "MM月dd日");
	}

	public static String convertTime(Date mill) {
		return convert(mill, "HH:mm");
	}

	public static boolean istoday(Date mill) {
		if (convert(mill, "yyyy-MM-dd").equals(convert(new Date(), "yyyy-MM-dd")))
			return true;
		else
			return false;
	}

	public static boolean isToday(Date mill) {
		if (convert(mill, "yyyy-MM-dd").equals(convert(new Date(), "yyyy-MM-dd")))
			return true;
		else
			return false;
	}

	public static String convert(Date mill, String format) {
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strs = sdf.format(mill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;

	}

	public static Date convert(String StringDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(StringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date convertTime(String StringDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = null;
		try {
			date = sdf.parse(StringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getWeekOfDate(Date date) {
		String[] weekOfDays = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
		int w = getIntWeekOfDate(date);
		return weekOfDays[w - 1];
	}

	private static int getIntWeekOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w == 0) {
			w = 7;
		}
		return w;
	}

	@SuppressWarnings("deprecation")
	public static boolean sameDate(Date date1, Date date2) {
		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate()) {
			return true;
		}
		return false;
	}

	public static Date nextDate(Date date) {
		return nextDate(date, 1);
	}

	public static Date nextDate(Date date, int nextDays) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, nextDays);
		Date next = calendar.getTime();
		return next;
	}

	/** 判断是不是在今天后面的日期 */
	public static boolean afterToday(Date date) {
		return compareDate(new Date(), date);
	}

	/** 判断是不是在今天前面的日期 */
	public static boolean beforeToday(Date date) {
		return compareDate(date, new Date());
	}

	/**
	 * 比较两个日期，当第一个日期是较早的，返回true 只判断日期，小时分钟秒都不判断
	 */
	public static boolean compareDate(Date before, Date after) {

		before = convert(convert(before));
		after = convert(convert(after));
		Calendar bc = new GregorianCalendar();
		bc.setTime(before);
		Calendar ac = new GregorianCalendar();
		ac.setTime(after);
		return bc.before(ac);
	}
	
	/** 比较两个时间，返回两个时间相差的分钟数 */
	public static int compareTime(Date before, Date after) {
		long a = after.getTime() - before.getTime();
		double mistake = (double) a / (1000 * 3600);
		double min = mistake * 60;
		return (int) min;
	}
	public static boolean isToday1(Date mill) {
		if (convert(mill, "yyyy-MM-dd").equals(convert(new Date(), "yyyy-MM-dd"))) {
			return true;
		} else {
			return false;
		}
	}
}
