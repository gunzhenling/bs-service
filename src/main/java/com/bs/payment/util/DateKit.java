/**
 * 
 */
package com.bs.payment.util;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * 描述：基于java8的时间操作简单封装,TemporalAdjusters:当月第一天/最后一天，下月的第一天
 * 
 * <pre>
 * 日期比较：1.Period 年、月、日 2.Duration 时间 3.ChronoUnit
 * </pre>
 * 
 * @author qizai
 * @version: 0.0.1 2018年10月17日-下午6:14:53
 *
 */
public class DateKit {
	public final static ZoneId				CHINA_ZONE_ID			= ZoneId.of("Asia/Shanghai");
	public final static Locale				CHINA_LOCALE			= Locale.CHINA;
	public final static ZoneOffset			CHINA_ZONE_OFFSET		= ZoneOffset.ofHours(8);

	public final static String				DATE_YMD				= "yyyy-MM-dd";
	public final static String				DATE_YMD_ZH				= "yyyy年MM月dd日";
	public final static String				TIME_HMS				= "HH:mm:ss";
	public final static String				DATE_YMD_NUM			= "yyyyMMdd";
	public final static String				DATETIME_YMD_HMS_NUM	= "yyyyMMddHHmmss";
	public final static String				DATETIME_YMD_HMS		= "yyyy-MM-dd HH:mm:ss";
	public final static String				DATETIME_YMD_HM			= "yyyy-MM-dd HH:mm";
	public final static String				DATETIME_ISO			= "yyyy-MM-dd HH:mm:ss:SSS";
	/**
	 * {@value #DATETIME_YMD_HMS}
	 */
	public final static DateTimeFormatter	FORMAT_DATETIME_YMD_HMS	= DateTimeFormatter.ofPattern(DATETIME_YMD_HMS);
	/**
	 * {@value #DATETIME_YMD_HM}
	 */
	public final static DateTimeFormatter	FORMAT_DATETIME_YMD_HM	= DateTimeFormatter.ofPattern(DATETIME_YMD_HM);
	/**
	 * {@value #DATE_YMD}
	 */
	public final static DateTimeFormatter	FORMAT_DATE_YMD			= DateTimeFormatter.ofPattern(DATE_YMD);
	/**
	 * {@value #DATE_YMD_ZH}
	 */
	public final static DateTimeFormatter	FORMAT_DATE_YMD_ZH		= DateTimeFormatter.ofPattern(DATE_YMD_ZH);
	/**
	 * {@value #DATE_YMD_NUM}
	 */
	public final static DateTimeFormatter	FORMAT_DATE_YMD_NUM		= DateTimeFormatter.ofPattern(DATE_YMD_NUM);
	/**
	 * {@value #DATETIME_ISO}
	 */
	public final static DateTimeFormatter	FORMAT_DATETIME_ISO		= DateTimeFormatter.ofPattern(DATETIME_ISO);
	/**
	 * {@value #TIME_HMS}
	 */
	public final static DateTimeFormatter	FORMAT_TIME_HMS			= DateTimeFormatter.ofPattern(TIME_HMS);

	public static LocalDateTime nowLdt() {
		return LocalDateTime.now(CHINA_ZONE_ID);
	}

	public static LocalTime toNullLt(String text, DateTimeFormatter pattern) {
		try {
			return LocalTime.parse(text, pattern);
		} catch (Exception e) {
		}
		return null;
	}

	public static LocalDate toNullLd(String text, DateTimeFormatter pattern) {
		try {
			return LocalDate.parse(text, pattern);
		} catch (Exception e) {
		}
		return null;
	}

	public static LocalDateTime toNullLdt(String text, DateTimeFormatter pattern) {
		try {
			return LocalDateTime.parse(text, pattern);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * <pre>
	 * yyyy-MM-dd
	 * yyyyMMdd 
	 * yyyy年MM月dd日
	 * </pre>
	 * 
	 * @param text
	 * @return
	 * @see #toDate(String)
	 */
	public static boolean isDate(String text) {
		return toDate(text) != null;
	}

	/**
	 * <pre>
	 * yyyy-MM-dd
	 * yyyyMMdd 
	 * yyyy年MM月dd日
	 * </pre>
	 * 
	 * @param text
	 * @return
	 */
	public static LocalDate toDate(String text) {
		LocalDate dt = toNullLd(text, DateTimeFormatter.ISO_LOCAL_DATE);
		if (null == dt) {
			dt = toNullLd(text, FORMAT_DATE_YMD_NUM);
		}
		if (null == dt) {
			dt = toNullLd(text, FORMAT_DATE_YMD_ZH);
		}
		return dt;
	}

	/**
	 * <pre>
	 * HH:mm:ss
	 * HH:mm
	 * </pre>
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isTime(String text) {
		LocalTime dt = toNullLt(text, DateTimeFormatter.ISO_LOCAL_TIME);
		return dt != null;
	}

	/**
	 * <pre>
	 * yyyy-MM-dd HH:mm:ss
	 * yyyy-MM-dd HH:mm
	 * yyyy-MM-ddTHH:mm:ss
	 * </pre>
	 * 
	 * @param text
	 * @return
	 * @see #toDateTime(String)
	 */
	public static boolean isDateTime(String text) {
		return toDateTime(text) != null;
	}

	/**
	 * <pre>
	 * yyyy-MM-dd HH:mm:ss
	 * yyyy-MM-dd HH:mm
	 * yyyy-MM-ddTHH:mm:ss
	 * </pre>
	 * 
	 * @param text
	 * @return
	 */
	public static LocalDateTime toDateTime(String text) {
		LocalDateTime dt = toNullLdt(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		if (null == dt) {
			dt = toNullLdt(text, FORMAT_DATETIME_YMD_HMS);
		}
		if (null == dt) {
			dt = toNullLdt(text, FORMAT_DATETIME_YMD_HM);
		}
		return dt;
	}

	/**
	 * 时间戳（秒）EpochSecond
	 * 
	 * @return
	 */
	public static long timestmp() {
		return Instant.now().getEpochSecond();
	}

	public static long timestmp(LocalDateTime ldt) {
		return ldt.toInstant(CHINA_ZONE_OFFSET).getEpochSecond();
	}

	/**
	 * 时间戳（毫秒）EpochMilli,等同于new Date.getTime()
	 * 
	 * @return
	 */
	public static long timestmpNano() {
		return Instant.now().toEpochMilli();
	}

	public static Date nowAtStartOfDay() {
		return atStartOfDay(nowLd());
	}

	public static Date nowAtEndOfDay() {
		return atEndOfDay(nowLd());
	}

	/**
	 * 时间设置为开始时间：00:00
	 * 
	 * @param ldt
	 * @return 返回当前日期的最小时间：00:00
	 */
	public static Date atStartOfDay(LocalDateTime ldt) {
		return toDate(LocalDateTime.of(ldt.toLocalDate(), LocalTime.MIN));
	}

	/**
	 * 时间设置为结束时间：23:59:59.999999999
	 * 
	 * @param ldt
	 * @return 返回当前日期的最大时间
	 */
	public static Date atEndOfDay(LocalDateTime ldt) {
		return toDate(LocalDateTime.of(ldt.toLocalDate(), LocalTime.MAX));
	}

	/**
	 * 
	 * @param ld
	 * @return 返回当前日期的最小时间：00:00
	 */
	public static Date atStartOfDay(LocalDate ld) {
		return toDate(ld.atTime(LocalTime.MIN));
	}

	/**
	 * 
	 * @param ld
	 * @return 返回当前日期的最大时间
	 */
	public static Date atEndOfDay(LocalDate ld) {
		return toDate(ld.atTime(LocalTime.MAX));
	}

	/**
	 * 
	 * @param date
	 * @return 返回当前日期的最小时间：00:00
	 */
	public static Date atStartOfDay(Date date) {
		return toDate(toLd(date).atTime(LocalTime.MIN));
	}

	/**
	 * 
	 * @param date
	 * @return 返回当前日期的最大时间
	 */
	public static Date atEndOfDay(Date date) {
		return toDate(toLd(date).atTime(LocalTime.MAX));
	}

	public static Date nowPlusMinutes(long minutes) {
		return toDate(nowLdt().plusMinutes(minutes));
	}

	public static Date nowPlusHours(long hours) {
		return toDate(nowLdt().plusHours(hours));
	}

	public static Date nowPlusDays(long days) {
		return toDate(nowLdt().plusDays(days));
	}

	public static LocalDateTime nowLdtPlusDays(long days) {
		return nowLdt().plusDays(days);
	}

	public static LocalDate nowLdPlusDays(long days) {
		return nowLd().plusDays(days);
	}

	public static Date nowPlusMonths(long months) {
		return toDate(nowLdt().plusMonths(months));
	}

	/**
	 * 时间格式化为字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		return toString(date, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 时间格式化为字符串
	 * 
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String toString(Date date, DateTimeFormatter formatter) {
		return toLdt(date).format(formatter);
	}

	/**
	 * 时间转字符串,格式为：{@value #DATE_YMD}
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringYmd(Date date) {
		return toLdt(date).format(FORMAT_DATE_YMD);
	}

	/**
	 * 
	 * @param text
	 * @return 字符串转时间,格式为：{@value #DATE_YMD}
	 */
	public static Date toDateYmd(String text) {
		return toDate(toLdYmd(text).atTime(nowLt()));
	}

	public static Date toDateYmdAtNow(String dateYmd) {
		return toDate(toLdYmd(dateYmd).atTime(nowLt()));
	}

	public static Date toDateYmdAtStartOfDay(String dateYmd) {
		return toDate(toLdYmd(dateYmd).atStartOfDay());
	}

	/**
	 * @param text
	 * @param pattern
	 *            带时间的格式转换
	 * @return
	 */
	public static Date toDate(String text, String pattern) {
		return toDate(toLdt(text, pattern));
	}

	/**
	 * 
	 * @param text
	 * @return 字符串转时间,格式为：{@value #DATE_YMD}
	 */
	public static LocalDate toLdYmd(String text) {
		return LocalDate.parse(text, FORMAT_DATE_YMD);
	}

	public static LocalDate toLd(Date date) {
		return toLdt(date).toLocalDate();
	}

	public static LocalDateTime toLdt(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), CHINA_ZONE_ID);
	}

	/**
	 * 
	 * @param text
	 * @param pattern
	 *            带时间的格式转换
	 * @return
	 */
	public static LocalDateTime toLdt(String text, String pattern) {
		if (StringUtils.isBlank(text) || text.length() != pattern.length()) {
			return null;
		}
		return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 
	 * @param text
	 *            格式为：{@value #DATETIME_YMD_HMS}
	 * @return
	 */
	public static LocalDateTime toLdt(String text) {
		if (StringUtils.isBlank(text) || text.length() != DATETIME_YMD_HMS.length()) {
			return null;
		}
		return LocalDateTime.parse(text, FORMAT_DATETIME_YMD_HMS);
	}

	/**
	 * 格式：{@value #DATETIME_YMD_HMS}
	 * 
	 * @param text
	 * @return
	 */
	public static LocalDateTime toLdtYmdHms(String text) {
		if (StringUtils.isBlank(text) || text.length() != DATETIME_YMD_HMS.length()) {
			return null;
		}
		return LocalDateTime.parse(text, FORMAT_DATETIME_YMD_HMS);
	}

	/**
	 * 
	 * @param text
	 *            格式：{@value #DATETIME_YMD_HMS}
	 * @return
	 */
	public static Date toDateYmdHms(String text) {
		if (StringUtils.isBlank(text) || text.length() != DATETIME_YMD_HMS.length()) {
			return null;
		}
		return toDate(LocalDateTime.parse(text, FORMAT_DATETIME_YMD_HMS));
	}

	/**
	 * 
	 * @param text
	 *            格式：{@value #DATETIME_YMD_HMS_NUM}
	 * @return
	 */
	public static Date toDateYmdHmsNum(String text) {
		if (StringUtils.isBlank(text) || text.length() != DATETIME_YMD_HMS_NUM.length()) {
			return null;
		}
		return toDate(LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DATETIME_YMD_HMS_NUM)));
	}

	/**
	 * 
	 * @param text
	 * @param pattern
	 *            日期格式转换，注意不支持带时间的格式
	 * @return
	 */
	public static LocalDate toLdYmd(String text, String pattern) {
		return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 
	 * @param text
	 * @param pattern
	 *            时间格式转换，注意不支持带日期的格式
	 * @return
	 */
	public static LocalTime toLt(String text, String pattern) {
		return LocalTime.parse(text, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 
	 * @param date
	 * @param days
	 * @return 返回指定日期前/后的n天集合，不含当天，日期格式为：{@value #DATE_YMD}
	 */
	public static List<String> plusDaysYmd(Date date, long days) {
		List<String> dates = new ArrayList<String>();
		LocalDateTime ldt = toLdt(date);
		if (days > 0) {
			int index = 1;
			while (index <= days) {
				dates.add(ldt.plusDays(index).format(FORMAT_DATE_YMD));
				index++;
			}
			return dates;
		}
		while (days < 0) {
			dates.add(ldt.plusDays(days).format(FORMAT_DATE_YMD));
			days++;
		}
		return dates;
	}

	public static Date plusDays(Date date, long days) {
		return toDate(toLdt(date).plusDays(days));
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @return 时间间隔中文表示：x天y小时z分n秒。大于0表示from在to之后
	 */
	public static String betweenZh(Date from, Date to) {
		long millis = from.getTime() - to.getTime();
		long dayMillis = 24 * 60 * 60 * 1000;
		long days = millis / dayMillis;
		long hours = (millis / (60 * 60 * 1000) - days * 24);

		long minutes = ((millis / (60 * 1000)) - days * 24 * 60 - hours * 60);
		long seconds = (millis / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);
		return MessageFormat.format("{0}天{1}小时{2}分{3}秒", days, hours, minutes, seconds);
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @return 计算两个时间的天数差，小于0表示from在to之前
	 */
	public static long days(Date from, Date to) {
		long l = from.getTime() - to.getTime();
		long dayMs = 24 * 60 * 60 * 1000;
		long day = l / dayMs;
		return day;
	}

	public static LocalDate nowLd() {
		return LocalDate.now(CHINA_ZONE_ID);
	}

	public static LocalTime nowLt() {
		return LocalTime.now(CHINA_ZONE_ID);
	}

	public static Date now() {
		return toDate(nowLdt());
	}

	/**
	 * 获取当天的秒时间
	 * 
	 * @return
	 */
	public static int nowDaySecond() {
		LocalDateTime ldt = nowLdt();
		return ldt.getHour() * 60 * 60 + ldt.getMinute() * 60 + ldt.getSecond();
	}

	public static void main(String[] args) {
		System.out.println(nowDaySecond());
		System.out.println(timestmp(toLdt("2019-01-01 13:12:11")));
		System.out.println(timestmp(toLdt("2019-01-01 13:12:12")));
		System.out.println(timestmp(toLdt("2019-01-01 13:12:13")));
		System.out.println(timestmp(toLdt("2019-01-02 13:12:13")));
		System.out.println(timestmp(toLdt("2019-01-03 13:12:13")));
	}

	public static String nowToString(String pattern) {
		return nowLdt().format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 
	 * @return 返回当前时间：{@value #DATETIME_YMD_HMS_NUM}
	 */
	public static String nowToStringDateTime() {
		return nowLdt().format(DateTimeFormatter.ofPattern(DATETIME_YMD_HMS_NUM));
	}

	/**
	 * @return 返回当前时间：{@value #DATE_YMD_NUM}
	 */
	public static String nowToStringDate() {
		return nowLd().format(DateTimeFormatter.ofPattern(DATE_YMD_NUM));
	}

	/**
	 * @return 返回当前时间：{@value #DATETIME_ISO}
	 */
	public static String nowToStringDateTimeIso() {
		return nowLdt().format(FORMAT_DATETIME_ISO);
	}

	/**
	 * @return 返回当前时间：{@value #DATE_YMD}
	 */
	public static String nowToStringYmd() {
		return nowLd().format(FORMAT_DATE_YMD);
	}

	public static Date toDate(LocalDateTime ldt) {
		if (null == ldt) {
			return null;
		}
		return Date.from(ldt.atZone(CHINA_ZONE_ID).toInstant());
	}

	public static Date toDate(LocalDate ld, LocalTime lt) {
		return toDate(ld.atTime(lt));
	}

	public static Date toDateAtStartOfDay(LocalDate ld) {
		return toDate(ld, LocalTime.MIN);
	}

	public static Date toDateAtNow(LocalDate ld) {
		return toDate(ld, nowLt());
	}

	/**
	 * 
	 * @param ld
	 * @return 返回当前日期所在周的周一
	 */
	public static Date firstDayOfWeek(TemporalAccessor ld) {
		TemporalField weekField = WeekFields.of(Locale.CHINA).dayOfWeek();
		return toDate(LocalDate.from(ld).with(weekField, 2).atStartOfDay());
	}

	/**
	 * 
	 * @param dt
	 * @return 返回当前日期所在周的周日
	 */
	public static Date lastDayOfWeek(TemporalAccessor dt) {
		TemporalField weekField = WeekFields.of(Locale.CHINA).dayOfWeek();
		return toDate(LocalDate.from(dt).with(weekField, 7).plusDays(1).atTime(LocalTime.MAX));
	}

	/**
	 * 
	 * @param ld
	 * @return 返回当前日期所在月的第一天，时间为最小
	 */
	public static Date firstDayOfMonthAtStartOfDay(TemporalAccessor ld) {
		return toDate(firstDayOfMonth(ld).atStartOfDay());
	}

	/**
	 * 返回当前日期所在月的第一天
	 * 
	 * @param ld
	 * @return
	 */
	public static LocalDate firstDayOfMonth(TemporalAccessor ld) {
		return LocalDate.from(ld).with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 
	 * @param ld
	 * @return 返回当前日期所在月的最后一天，时间为最大
	 */
	public static Date lastDayOfMonthAtEndOfDay(TemporalAccessor ld) {
		return toDate(lastDayOfMonth(ld).atTime(LocalTime.MAX));
	}

	/**
	 * 
	 * @param ld
	 * @return 返回当前日期所在月的最后一天
	 */
	public static LocalDate lastDayOfMonth(TemporalAccessor ld) {
		return LocalDate.from(ld).with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return 从start到end的天数
	 */
	public static long days(Temporal start, Temporal end) {
		return ChronoUnit.DAYS.between(start, end);
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return 从start到end的小时
	 */
	public static long hours(Temporal start, Temporal end) {
		return ChronoUnit.HOURS.between(start, end);
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return 从start到end的分钟
	 */
	public static long minutes(Temporal start, Temporal end) {
		return ChronoUnit.MINUTES.between(start, end);
	}

}
