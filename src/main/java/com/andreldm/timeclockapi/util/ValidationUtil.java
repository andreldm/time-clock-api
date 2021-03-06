package com.andreldm.timeclockapi.util;

/**
 * Utility class for validation methods.
 */
public class ValidationUtil {
    private static final Integer[] WEIGHTS = {3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private ValidationUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Shorcut for {@code validatePis(String)}
     * @param pis PIS Number to be validated
     * @return if the PIS valid or not
     */
    public static boolean validatePis(Long pis) {
        if (pis == null || pis > 99999999999L)
            return false;

        return validatePis(String.format("%011d", pis));
    }

    /**
     * Validates the PIS number's check digit
     *
     * @param pis PIS Number to be validated
     * @return if the PIS valid or not
     */
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
