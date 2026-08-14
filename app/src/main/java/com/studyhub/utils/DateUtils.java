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

    public static long getNextOccurrence(int dayOfWeek, String time) {
        // dayOfWeek: 2 (Mon) to 8 (Sun) in our app
        // Calendar dayOfWeek: 1 (Sun), 2 (Mon) ... 7 (Sat)
        int calendarDayOfWeek = dayOfWeek == 8 ? Calendar.SUNDAY : dayOfWeek;
        
        Calendar now = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        
        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            
            next.set(Calendar.HOUR_OF_DAY, hour);
            next.set(Calendar.MINUTE, minute);
            next.set(Calendar.SECOND, 0);
            next.set(Calendar.MILLISECOND, 0);
            
            while (next.get(Calendar.DAY_OF_WEEK) != calendarDayOfWeek || next.before(now)) {
                next.add(Calendar.DAY_OF_YEAR, 1);
            }
            
            return next.getTimeInMillis();
        } catch (Exception e) {
            return -1;
        }
    }
}
