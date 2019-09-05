package com.zh.java.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.apache.commons.lang3.time.DateUtils.*;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/3/26 17:50
 */
@Slf4j
public class DateUtil {

    public static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";
    public static final int TIME_DAY_MILLISECOND = 86400000;
    public static final int TIME_DAY_MILLIHOUR = 3600000;
    public static final int TIME_DAY_MILLIMINUS = 60000;
    private static final int RATIO = 1000;
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public DateUtil() {
    }

    public static synchronized String getDate2Str(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        return simpleDateFormat.format(date);
    }

    public static Date addDays(Date date, int amount) {
        return addDay(date, 5, amount);
    }

    public static Date addDay(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }

    public static synchronized Date strToDateByT(String str) {
        return str.length() < 10 ? null : strToDate("yyyy-MM-dd", str.substring(0, 10));
    }

    public static synchronized Date strToDate(String format, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        ParsePosition parseposition = new ParsePosition(0);
        return simpleDateFormat.parse(str, parseposition);
    }

    public static Date strToDate(String str) {
        return strToDate("yyyy-MM-dd HH:mm:ss", str);
    }

    public static String date2String(Date date) {
        return getDate2Str("yyyy-MM-dd HH:mm:ss", date);
    }

    public static String date2String(Date date, String format) {
        return getDate2Str(format, date);
    }

    public static String date2String1(Date date) {
        return getDate2Str("yyyy-MM-dd", date);
    }

    public static String dateString2String(String dateStr, String pattern) {
        return StringUtils.isBlank(dateStr) ? null : date2String(strToDate(pattern, dateStr), pattern);
    }

    public static boolean isDateTime(String dateTime, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        Date dt = df.parse(dateTime, pos);
        return dt != null;
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;

        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            xmlCalendar = dtf.newXMLGregorianCalendar(calendar.get(1), calendar.get(2) + 1, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13), calendar.get(14), calendar.get(15) / '\uea60');
        } catch (Exception var4) {
            log.error("getXMLGregorianCalendar error!", var4);
        }

        return xmlCalendar;
    }

    public static Date convertToDate(XMLGregorianCalendar cal) {
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }

    public boolean passTime(Date tempDate, int hour) {
        return tempDate != null && hour > 0 && tempDate.before(this.getLimitDate(hour));
    }

    private Date getLimitDate(int hour) {
        Calendar cl = Calendar.getInstance();
        Long clTemp = cl.getTimeInMillis() - (long)(hour * 60 * 60 * 1000);
        cl.setTimeInMillis(clTemp);
        return cl.getTime();
    }

    private boolean compareDate(Date date1, Date date2) {
        boolean flag = false;
        if (date1.after(date2)) {
            flag = true;
        }

        return flag;
    }

    public static String date2Week(Date date) {
        String[] weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(7) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static String date2WeekNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(7) - 1;
        return w == 0 ? "7" : String.valueOf(w);
    }

    public static String date2WeekCnNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(7) - 1;
        if (w == 0) {
            w = 7;
        }

        return weekCn(String.valueOf(w));
    }

    private static String weekCn(String w) {
        if (w.equals("1")) {
            return "周一";
        } else if (w.equals("2")) {
            return "周二";
        } else if (w.equals("3")) {
            return "周三";
        } else if (w.equals("4")) {
            return "周四";
        } else if (w.equals("5")) {
            return "周五";
        } else {
            return w.equals("6") ? "周六" : "周日";
        }
    }

    public static String date2StringAndWeekNum(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer[] da = new Integer[]{c.get(1), c.get(2) + 1, c.get(5)};
        return c.get(1) + "年" + (c.get(2) + 1) + "月" + c.get(5) + "日";
    }

    public static String date2MonthAndWeekNum(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer[] da = new Integer[]{c.get(1), c.get(2) + 1, c.get(5)};
        String zhou = weekCn(date2WeekNum(date));
        return c.get(2) + 1 + "-" + c.get(5);
    }

    public static String date2CnMonthAndWeekNum(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer[] da = new Integer[]{c.get(1), c.get(2) + 1, c.get(5)};
        String zhou = weekCn(date2WeekNum(date));
        return c.get(2) + 1 + "月" + c.get(5) + "日";
    }

    public static String date2WeekForString(String date) {
        return date != null && !date.trim().equals(0) ? date2Week(new Date(Long.valueOf(date.trim()) * 1000L)) : null;
    }

    public static Date dateResetZero(Date date) {
        Date rDate = setHours(date, 0);
        rDate = setMinutes(rDate, 0);
        rDate = setSeconds(rDate, 0);
        return rDate;
    }

    public static Date dateReset23(Date date) {
        Date rDate = setHours(date, 23);
        rDate = setMinutes(rDate, 59);
        rDate = setSeconds(rDate, 59);
        return rDate;
    }

    public static long str2startTime(String dateStr) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return dateTimeFormat.parse(dateStr + " 00:00:00").getTime() / 1000L;
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static long str2endTime(String dateStr) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return dateTimeFormat.parse(dateStr + " 23:59:59").getTime() / 1000L;
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String str2startDateStr(String dateStr) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            return getFormatDateTime(dateTimeFormat.parse(dateStr + "000000"));
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String str2endDateStr(String dateStr) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            return getFormatDateTime(dateTimeFormat.parse(dateStr + "235959"));
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static Date getFormatDate(String currDate, String format) {
        if (currDate == null) {
            return null;
        } else {
            SimpleDateFormat dtFormatdB = null;

            try {
                dtFormatdB = new SimpleDateFormat(format);
                return dtFormatdB.parse(currDate);
            } catch (Exception var6) {
                dtFormatdB = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    return dtFormatdB.parse(currDate);
                } catch (Exception var5) {
                    return null;
                }
            }
        }
    }

    public static String getFormatDateTime(Date currDate, String format) {
        if (currDate == null) {
            return "";
        } else {
            SimpleDateFormat dtFormatdB = null;

            try {
                dtFormatdB = new SimpleDateFormat(format);
                return dtFormatdB.format(currDate);
            } catch (Exception var6) {
                dtFormatdB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    return dtFormatdB.format(currDate);
                } catch (Exception var5) {
                    return "";
                }
            }
        }
    }

    public static String getFormatDate(Date currDate, String format) {
        if (currDate == null) {
            return "";
        } else {
            SimpleDateFormat dtFormatdB = null;

            try {
                dtFormatdB = new SimpleDateFormat(format);
                return dtFormatdB.format(currDate);
            } catch (Exception var6) {
                dtFormatdB = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    return dtFormatdB.format(currDate);
                } catch (Exception var5) {
                    return null;
                }
            }
        }
    }

    public static String getFormatDateTime(Date currDate) {
        return getFormatDateTime(currDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getCurrDate() {
        return new Date();
    }

    public static Timestamp getCurrTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static int getHourFromTimeString(String timestr) {
        return StringUtils.isBlank(timestr) ? 0 : Integer.parseInt(timestr.substring(timestr.length() - 8, timestr.length() - 6));
    }

    public static String getDateBeforeMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(2, -1);
        return getFormatDate(cal.getTime(), "yyyy-MM-dd");
    }

    public static String getDateBeforeDay(int number) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -number);
        return getFormatDate(cal.getTime(), "yyyy-MM-dd");
    }

    public static String getDateBeforeMonth(int number) {
        Calendar cal = Calendar.getInstance();
        cal.add(2, -number);
        return getFormatDate(cal.getTime(), "yyyy-MM-dd");
    }

    public static int getDaysBetweenDates(Date first, Date second) {
        Long mils = (second.getTime() - first.getTime()) / 86400000L;
        return mils.intValue();
    }

    public static int getHoursBetweenDays(Date first, Date second) {
        Long mils = (second.getTime() - first.getTime()) / 3600000L;
        return mils.intValue();
    }

    public static int getMinusBetweenDays(Date first, Date second) {
        Long mils = (second.getTime() - first.getTime()) / 60000L;
        return mils.intValue();
    }

    public static int compareTime(Date date1, Date date2) {
        return date2.compareTo(date1);
    }

    public static boolean compareTimeEquals(Date date1, Date date2) {
        String d1 = DateFormatUtils.format(date1, "yyyyMMdd");
        String d2 = DateFormatUtils.format(date2, "yyyyMMdd");
        return d1.equals(d2);
    }

    public static boolean compareTime(Date nowDate, String minute) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String subDate = dateTimeFormat.format(nowDate).toString().substring(0, 10);

        try {
            long nowTime = nowDate.getTime();
            long minuteTime = dateTimeFormat.parse(subDate + " " + minute).getTime();
            if (nowTime - minuteTime > 0L) {
                return true;
            }
        } catch (ParseException var8) {
            ;
        }

        return false;
    }

    public static boolean compareTimeMi(Date nowDate, String start, String end) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String subDate = dateTimeFormat.format(nowDate).toString().substring(0, 10);

        try {
            long st = dateTimeFormat.parse(subDate + " " + start).getTime();
            long en = dateTimeFormat.parse(subDate + " " + end).getTime();
            if (en - st > 0L) {
                return true;
            }
        } catch (Exception var9) {
            ;
        }

        return false;
    }

    public static boolean compareHourMinute(String start, String end) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm");

        try {
            long st = dateTimeFormat.parse(start).getTime();
            long en = dateTimeFormat.parse(end).getTime();
            if (st - en >= 0L) {
                return true;
            }
        } catch (Exception var7) {
            log.error("输入的日期有误！：", var7);
        }

        return false;
    }

    public static Date stringToDate(String str, String pattern) {
        Date time = new Date();
        SimpleDateFormat sd = new SimpleDateFormat(pattern);

        try {
            time = sd.parse(str);
        } catch (ParseException var5) {
            log.error("输入的日期格式有误！：", var5);
        }

        return time;
    }

    public static String dateToString(Date date, String pattern) {
        String time = "";
        SimpleDateFormat sd = new SimpleDateFormat(pattern);

        try {
            time = sd.format(date);
        } catch (Exception var5) {
            log.error("输入的日期格式有误！：", var5);
        }

        return time;
    }

    public static Boolean juneScoreSalePromotion() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean result = false;

        try {
            Date startTime = dateTimeFormat.parse("2012-06-09 00:00:00");
            Date endTime = dateTimeFormat.parse("2012-06-14 23:59:59");
            Date now = new Date();
            if (now.after(startTime) && now.before(endTime)) {
                result = true;
            }
        } catch (Exception var5) {
            log.error("酒店 6月9日零点至6月14日23点59分59秒 全场返100%积分，判断时间异常：", var5);
        }

        return result;
    }

    public static String getFitYearMonth(String yearString) {
        String yearCn = "";

        try {
            String[] yearAndMonth = yearString.split("-");
            int m = Integer.parseInt(yearAndMonth[1]);
            yearCn = yearAndMonth[0] + "年" + m + "月";
        } catch (Exception var4) {
            log.error("参数错误：" + yearString);
        }

        return yearCn;
    }

    public static Integer[] getCurrentYearMonthDate() {
        Calendar c = Calendar.getInstance();
        Integer[] date = new Integer[]{c.get(1), c.get(2) + 1, c.get(5)};
        System.out.println(c.get(1) + "年" + (c.get(2) + 1) + "月" + c.get(5) + "日");
        return date;
    }

    public static Date timeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000L;
        String date = date2String(new Date(timestamp));
        log.info("转换成的时间：date=" + date);
        return stringToDate(date, formats);
    }

    public static boolean compareTheTime(long time, int compareHours) {
        long current = System.currentTimeMillis();
        return current - time > (long)(3600000 * compareHours);
    }

    public static String getNumDayOfMonth(int num) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(5, num);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public static String getPreviousMonthFirst(int num) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(2, num);
        lastDate.set(5, 1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public static Date getLastDayOfLastMonth(Date currDate) {
        currDate = setDays(currDate, 1);
        return addDays(currDate, -1);
    }

    public static int compareDateIgnoreTime(Date date1, Date date2) {
        return date2String(date2, "yyyy-MM-dd").compareTo(dateToString(date1, "yyyy-MM-dd"));
    }

    public static long getTwoDateSubIgnoreTime(String date1, String date2) {
        return (strToDate("yyyy-MM-dd", date2).getTime() - strToDate("yyyy-MM-dd", date1).getTime()) / 86400000L;
    }


}
