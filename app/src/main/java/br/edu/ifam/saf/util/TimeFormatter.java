package br.edu.ifam.saf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public final class TimeFormatter {
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH'h':mm'm'");

    static {
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private TimeFormatter() {

    }


    public static String format(Date date) {
        return formatter.format(date);
    }

    public static String format(int hours, int minutes) {
        return String.format("%02dh:%02dm", hours, minutes);
    }

    public static String format(int timeInMinutes) {
        int hours = timeInMinutes / 60;
        int minutes = timeInMinutes % 60;

        return format(hours, minutes);
    }

    public static Date parse(String text) {
        try {
            return formatter.parse(text);
        } catch (ParseException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public static int parseToMinutes(String text) {
        return (int) TimeUnit.MILLISECONDS.toMinutes(parse(text).getTime());
    }

}
