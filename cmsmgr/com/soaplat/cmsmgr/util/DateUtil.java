/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-9-8 下午07:58:30
 */
public class DateUtil {
	private static DateFormat dateFormat;
	
	/**
	 * 格式化日期为字符串类型
	 * @param pattern 格式化模式
	 * @param date 待格式化的日期对象
	 * @return 返回指定模式格式化的日期字符串
	 */
	public static String getDateStr(String pattern, Date date) {
		dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(null == date ? new Date() : date);
	}
	
	/**
	 * 将日期字符串转换为日期对象
	 * @param pattern 格式化模式
	 * @param dateStr 待转换的日期字符串
	 * @return 返回转换后的日期对象
	 * @throws ParseException 日期字符串格式同模式不匹配时抛出解析异常
	 */
	public static Date parseDate(String pattern, String dateStr) throws ParseException {
		dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.parse(dateStr);
	}
	
	/**
	 * 获取日期字符串中该月有多少天
	 * @param dateStr 日期字符串
	 * @return 返回指定年月中有多少天
	 * @throws ParseException 日期字符串格式错误时抛出解析异常
	 */
	public static int getDaysOfMonth(String dateStr) throws ParseException {
		int curyear = getCalendar(dateStr).get(Calendar.YEAR);			// 当前年份
		int curMonth = getCalendar(dateStr).get(Calendar.MONTH);		// 当前月份
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// 判断闰年的情况 ，2月份有29天；
		if ((curyear % 400 == 0)
				|| ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		return mArray[curMonth];
		// 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；
		// 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；
	}
	
	/**
	 * 根据日期字符串获取日历对象
	 * @param dateStr 日期字符串
	 * @return 返回字符串对应的日历对象
	 * @throws ParseException 日期字符串格式错误时抛出解析异常
	 */
	private static Calendar getCalendar(String dateStr) throws ParseException {
		String pattern = null;
		if (6 == dateStr.length()) {
			pattern = "yyMMdd";
		} else if (8 == dateStr.length()) {
			pattern = "yyyyMMdd";
		} else if (10 == dateStr.length()) {
			pattern = -1 != dateStr.indexOf("-") ? "yyyy-MM-dd" 
					: -1 != dateStr.indexOf("/") ? "yyyy/MM/dd"
							: -1 != dateStr.indexOf(" ") ? "yyyy MM dd"
									: "yyyy.MM.dd";
		} else if (11 == dateStr.length()) {
			pattern = "yyyy年MM月dd日";
		} else if (14 == dateStr.length()) {
			pattern = "yyyyMMddHHmmss";
		} else if (19 == dateStr.length()) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		} else if (21 == dateStr.length()) {
			pattern = "yyyy年MM月dd日 HH时mm分ss秒";
		}
		
		dateFormat = new SimpleDateFormat(pattern);
		Date date = dateFormat.parse(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar;
	}
	
	/**
	 * 根据日期格式字符串解析出该日期的0点0分, 到第二天的0点0分
	 * @param time 日期字符串, 格式: 2010-01-01
	 * @return Date[2]
	 * date[0]: 字符串代表的当天0点的日期对象
	 * date[1]: 字符串代表的当天24点的日期对象
	 * @throws ParseException 待解析的日期字符格式不正确
	 */
	public static Date[] parseDate(String time) throws ParseException {
		Calendar calendar = getCalendar(time);
		Date beforeDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return new Date[] {beforeDate, calendar.getTime()};
	}
	
	/**
	 * 根据日期格式字符串解析出该月中每天的编单ID
	 * @param dateStr 日期字符串
	 * @return 返回该月的编单ID
	 * @throws ParseException 日期字符串格式错误时抛出解析异常
	 */
	public static List<String> getCurrMonthDateStrList(String dateStr) 
			throws ParseException {
		int days = getDaysOfMonth(dateStr);			// 根据日期字符串获取当月天数
		
		List<String> progList = new ArrayList<String>();
		Calendar calendar = getCalendar(dateStr);	// 根据日期字符串获取日历对象, 并设置时间为0时0分0秒
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		for (int i = 0; i < days; ++ i) {
			calendar.set(Calendar.DAY_OF_MONTH, i + 1);
			progList.add(dateFormat.format(calendar.getTime()));
		}
		
		return progList;
	}
	
	/**
	 * 根据日期格式字符串解析出往后days天数的编单ID
	 * @param dateStr 日期字符串
	 * @param days 天数
	 * @return 返回往后days天数的编单ID
	 * @throws ParseException
	 */
	public static List<String> getAfterDaysStrList(String dateStr, int days) 
			throws ParseException {
		List<String> progList = new ArrayList<String>();
		Calendar calendar = getCalendar(dateStr);	// 根据日期字符串获取日历对象, 并设置时间为0时0分0秒
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		for (int i = 0; i < days; ++ i) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			progList.add(dateFormat.format(calendar.getTime()));
		}
		
		return progList;
	}
	
	/**
	 * 根据日期格式字符串解析出往前days天数的编单ID
	 * @param dateStr 日期字符串
	 * @param days 天数
	 * @return 返回往后days天数的编单ID
	 * @throws ParseException
	 */
	public static List<String> getBeforeDaysStrList(String dateStr, int days) 
			throws ParseException {
		List<String> progList = new ArrayList<String>();
		Calendar calendar = getCalendar(dateStr);	// 根据日期字符串获取日历对象, 并设置时间为0时0分0秒
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		for (int i = 0; i < days; ++ i) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			progList.add(dateFormat.format(calendar.getTime()));
		}
		
		return progList;
	}
	
	/**
	 * 获取当前日期 0时0分0秒的日期对象
	 * @return 
	 */
	public static Date getCurrDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前日期0时0分0秒的日期对象的字符串表达形式
	 * @param pattern 格式化日期模式
	 * @return 返回指定模式格式化后的当前日期0时0分0秒的日期对象的字符串表达形式
	 */
	public static String getCurrentDateStr(String pattern) {
		return getDateStr(pattern, getCurrDate());
	}
	
	/**
	 * 获取当前日期前后 days 天数的编单日期
	 * @param days 偏移天数
	 * @return
	 */
	public static String getAfterScheduleDate(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return getDateStr("yyyyMMddHHmmss", calendar.getTime());
	}
	
	public static void main(String[] args) throws ParseException {
//		try {
//			List<String> progList = getCurrMonthDateStrList("2010-09-09");
//			
//			for (String string : progList) {
//				System.out.println(string);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println("2010 09 09".replaceAll("[-|/| |\\.]", ""));
//		List<String> list = getCurrMonthDateStrList("2011-04-08");
//		for (String string : list) {
//			System.out.println(string);
//		}
		System.out.println(getAfterScheduleDate(3));
	}
}
