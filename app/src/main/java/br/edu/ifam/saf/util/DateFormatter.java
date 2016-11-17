package br.edu.ifam.saf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatter {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private DateFormatter() {

    }
    public static String format(Date date) {
        return formatter.format(date);
    }

    public static Date parse(String dataNascimentoText) throws ParseException {
        return formatter.parse(dataNascimentoText);
    }
}
