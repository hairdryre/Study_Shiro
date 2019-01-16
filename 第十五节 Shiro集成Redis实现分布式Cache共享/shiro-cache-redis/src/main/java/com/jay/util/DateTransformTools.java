package com.jay.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author jay.zhou
 * @date 2018/12/15
 * @time 14:24
 */
public final class DateTransformTools {
    private DateTransformTools() {
    }

    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String HOUR = "HH";
    public static final String MINUTE = "mm";
    public static final String SECOND = "ss";
    public static final String AM = "AM";
    public static final String PM = "PM";
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String EMPTY = "";

    public static String dateStrToDateStr(String dateStr, String sourcePattern, String targetPattern) throws ParseException {
        if (checkFormatPatternIsNullOrEmpty(sourcePattern)) {
            sourcePattern = DEFAULT_FORMAT;
        }
        if (checkFormatPatternIsNullOrEmpty(targetPattern)) {
            targetPattern = DEFAULT_FORMAT;
        }
        Long seconds = new SimpleDateFormat(sourcePattern, Locale.ENGLISH).parse(dateStr).getTime();
        return new SimpleDateFormat(targetPattern, Locale.ENGLISH).format(new Date(seconds));
    }

    public static Long dateStrToMillis(String dateStr, String sourcePattern) throws ParseException {
        if (checkFormatPatternIsNullOrEmpty(sourcePattern)) {
            sourcePattern = DEFAULT_FORMAT;
        }
        return new SimpleDateFormat(sourcePattern, Locale.ENGLISH).parse(dateStr).getTime();
    }

    public static Date dateStrToDate(String dateStr, String sourcePattern) throws ParseException {
        if (checkFormatPatternIsNullOrEmpty(sourcePattern)) {
            sourcePattern = DEFAULT_FORMAT;
        }
        return new Date(dateStrToMillis(dateStr, sourcePattern));
    }

    public static String dateToDateStr(Date sourceDate, String targetPattern) {
        if (checkSourceDateIsNull(sourceDate)) {
            return EMPTY;
        }
        if (checkFormatPatternIsNullOrEmpty(targetPattern)) {
            targetPattern = DEFAULT_FORMAT;
        }
        return new SimpleDateFormat(targetPattern, Locale.ENGLISH).format(sourceDate);
    }

    public static Long dateToMillis(Date sourceDate) {
        if (checkSourceDateIsNull(sourceDate)) {
            return 0L;
        }
        return sourceDate.getTime();
    }

    public static Date millisToDate(Long millis) {
        if (millis < 0) {
            return new Date(0);
        }
        return new Date(millis);
    }

    public static String millisToDateStr(Long millis, String targetPattern) {
        if (millis < 0) {
            return EMPTY;
        }
        if (checkFormatPatternIsNullOrEmpty(targetPattern)) {
            targetPattern = DEFAULT_FORMAT;
        }
        return new SimpleDateFormat(targetPattern, Locale.ENGLISH).format(new Date(millis));
    }

    /**
     * @param sourceDate    sourceDate
     * @param sourcePattern year month day
     * @return timeStr
     * <p>
     * for example , currentTime is 2018/12/25 11:22 morning
     * System.out.println(generateAmPmTime(new Date(), "yyyy/MM/dd"));
     * the result is 2018/12/25 11:23:30 AM
     * And then , you can add 12 hours later
     * <p>
     * Calendar calendar = Calendar.getInstance();
     * calendar.setTime(new Date());
     * calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 12);
     * Date runDate = calendar.getTime();
     * System.out.println(generateAmPmTime(runDate, "yyyy/MM/dd"));
     * <p>
     * the result is 2018/12/25 11:23:30 PM
     */
    public static String generateAmPmTime(Date sourceDate, String sourcePattern) {
        if (checkSourceDateIsNull(sourceDate)) {
            return EMPTY;
        }
        if (checkFormatPatternIsNullOrEmpty(sourcePattern)) {
            sourcePattern = DEFAULT_FORMAT;
        }
        String result = new SimpleDateFormat(sourcePattern.concat(" HH:mm:ss a"), Locale.ENGLISH).format(sourceDate);
        if (result.contains(PM)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sourceDate);
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 12);
            Date runDate = calendar.getTime();
            result = dateToDateStr(runDate, sourcePattern.concat(" HH:mm:ss"));
            result = result.concat(" PM");
        }
        return result;
    }

    private static Boolean checkSourceDateIsNull(Date sourceDate) {
        if (sourceDate == null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static boolean checkFormatPatternIsNullOrEmpty(String targetPattern) {
        return targetPattern == null || EMPTY.equals(targetPattern);
    }

    public static void main(String[] args) {
        System.out.println(generateAmPmTime(null, "yyyy/MM/dd"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 12);
        Date runDate = calendar.getTime();
        System.out.println(generateAmPmTime(runDate, "yyyy/MM/dd"));
    }
}
