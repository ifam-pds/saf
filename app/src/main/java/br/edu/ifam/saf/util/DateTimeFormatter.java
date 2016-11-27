package br.edu.ifam.saf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateTimeFormatter {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");

    static {
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

    }

    private DateTimeFormatter() {

    }

    public static String format(Date date) {
        return formatter.format(date);
    }

    public static Date parse(String dateText) throws ParseException {
        return formatter.parse(dateText);
    }
}
