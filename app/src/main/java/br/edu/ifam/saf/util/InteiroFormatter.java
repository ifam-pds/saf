package br.edu.ifam.saf.util;


public final class InteiroFormatter {
    public static String format(double valor) {
        return String.format("%d", ((int) valor));
    }
}
