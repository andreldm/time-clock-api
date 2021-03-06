package com.andreldm.timeclockapi.report.calculator;

import com.andreldm.timeclockapi.report.model.DayRecord;

/**
 * Implementation of {@link TimeCalculator} that considers regulars work days,
 * when the worker arrived to work and is going to leave home (or not).
 */
public class RegularCalculator extends TimeCalculator {
    /**
     * Hidden constructor, always use {@link TimeCalculatorFactory}.
     */
    RegularCalculator(DayRecord record) {
        super(record);
    }

    @Override
    public void compute() {
        switch (getTimesCount()) {
            case 1: // arrived to work, but stayed to work overnight without a break
                calcEnd(0);
                break;
            case 2: // arrived to work, but either worked part-time or didn't take a break
                calc(0, 1);
                break;
            case 3: // arrived to work, took a break but stayed to work overnight
                calc(0, 1);
                rest(1, 2);
                calcEnd(2);
                break;
            case 4: // arrived to work, took a break and went home
                calc(0, 1);
                rest(1, 2);
                calc(2, 3);
                break;
            default: // corner cases are not currently handled
                break;
        }
    }
}
