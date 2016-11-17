package br.edu.ifam.saf.util;


public final class DinheiroFormatter {
    public static String format(double valor) {
        return String.format("R$ %.2f", valor);
    }
}
