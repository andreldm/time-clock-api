package com.andreldm.timeclockapi.report.calculator;

import com.andreldm.timeclockapi.report.model.DayRecord;

public class TimeCalculatorFactory {
    private TimeCalculatorFactory() {
        throw new IllegalStateException("Factory class");
    }

    public static TimeCalculator getCalculator(DayRecord previous, DayRecord current) {
        if (current == null)
            throw new IllegalArgumentException("Current day record cannot be null");

        if (previous != null && previous.isIncomplete())
            return new OvernightCalculator(current);

        return new RegularCalculator(current);
    }
}
