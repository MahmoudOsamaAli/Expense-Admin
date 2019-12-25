package com.expense.expenseadmin.Utilities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtils {

    public final static String _1_DIGITS_AFTER_DOT = ".0";
    public final static String _2_DIGITS_AFTER_DOT = ".00";
    public final static String _2_DIGITS_BEFORE_DOT = "00.00";
    public final static String _6_DIGITS_AFTER_DOT = ".000000";
    public final static String _3_DIGITS_BEFORE_DOT = "000.00";
    public final static String _4_DIGITS_BEFORE_DOT = "0000.00";
    public final static String _5_DIGITS_BEFORE_DOT = "00000.00";
    public final static String _6_DIGITS_BEFORE_DOT = "000000.00";
    public final static String _4_DIGITS = "0000";
    public final static String COMMA_FORMAT = "%,d";

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        double result = (double) tmp / factor;
        return result;
    }

    public static String format(double value, String pattern) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        return new DecimalFormat(pattern, symbols).format(value);
    }

    /* round the number to closest 5 piasters...*/
    public static double roundTo5Piaster(double val) {

        double result;
        boolean isNegativeVal = val < 0;
        val = Math.abs(val);
        int piasterToRound = 5; // 5 piasters...
        double numberBeforeDot = Math.floor(val);
        int numberAfterDot = (int) ((val - numberBeforeDot) * 100);
        double numberTo5Piaster = (numberAfterDot / piasterToRound * piasterToRound) +
                (numberAfterDot % piasterToRound > 0 ? piasterToRound : 0);
        result = (numberBeforeDot + (numberTo5Piaster / 100.0));
        return isNegativeVal ? result * -1 : result;
    }
}
