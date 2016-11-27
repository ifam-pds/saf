package br.edu.ifam.saf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeFormatter {
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH'h':mm'm'");

    private TimeFormatter() {

    }


    public static String format(Date date) {
        return formatter.format(date);
    }

    public static String format(int hours, int minutes) {
        return String.format("%02dh:%02dm", hours, minutes);


    }

    public static Date parse(String dateText) throws ParseException {
        return formatter.parse(dateText);
    }
}
