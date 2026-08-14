package com.studyhub.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.studyhub.constant.AppConstants;

public class DateUtils {

    public static String formatDateTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATETIME_FORMAT, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.TIME_FORMAT, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static long getStartOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getEndOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
    
    public static int getDaysRemaining(long dueDateTimestamp) {
        long now = System.currentTimeMillis();
        long diff = dueDateTimestamp - now;
        return (int) TimeUnit.MILLISECONDS.toDays(diff);
    }

    public static int getHoursRemaining(long dueDateTimestamp) {
        long now = System.currentTimeMillis();
        long diff = dueDateTimestamp - now;
        return (int) TimeUnit.MILLISECONDS.toHours(diff);
    }
}
