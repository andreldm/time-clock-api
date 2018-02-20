package com.andreldm.timeclockapi.report.calculator;

import com.andreldm.timeclockapi.report.model.DayRecord;

/**
 * Factory to get appropriate TimeCalculator instances.
 */
public class TimeCalculatorFactory {
    private TimeCalculatorFactory() {
        throw new IllegalStateException("Factory class");
    }

    /**
     * Decides the correct TimeCalculator implementation to be created.
     *
     * @param previous The previous DayRecord.
     * @param current  Current DayRecord to be calculated.
     * @return Instance of the appropriate TimeCalculator implementation.
     */
    public static TimeCalculator getCalculator(DayRecord previous, DayRecord current) {
        if (current == null)
            throw new IllegalArgumentException("Current day record cannot be null");

        if (previous != null && previous.isIncomplete())
            return new OvernightCalculator(current);

        return new RegularCalculator(current);
    }
}
