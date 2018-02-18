package com.andreldm.timeclockapi.util;

public class ValidationUtil {
    private static final Integer[] WEIGHTS = {3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean validatePis(Long pis) {
        if (pis == null || pis > 99999999999L)
            return false;

        return validatePis(String.format("%011d", pis));
    }

    public static boolean validatePis(String pis) {
        if (pis == null || !pis.matches("^[0-9]{11}$"))
            return false;

        int sum = 0;
        for (int i = 0; i < WEIGHTS.length; i++)
            sum += WEIGHTS[i] * Character.getNumericValue(pis.charAt(i));

        int remainder = sum % 11;
        int digit = remainder < 2 ? 0 : 11 - remainder;
        return digit == Character.getNumericValue(pis.charAt(10));
    }
}
