package com.andreldm.timeclockapi.report.calculator;

import com.andreldm.timeclockapi.report.model.DayRecord;

public class OvernightCalculator extends TimeCalculator {
    OvernightCalculator(DayRecord record) {
        super(record);
    }

    @Override
    public void compute() {
        switch (getTimesCount()) {
            case 1: // worked overnight, left but didn't return to work
                calcBegin(0);
                break;
            case 2: // worked overnight, left and returned just to work overnight again
                calcBegin(0);
                calcEnd(1);
                break;
            case 3: // worked overnight, left and returned to work part-time
                calcBegin(0);
                calc(1, 2);
                break;
            case 4: // worked overnight, left, returned to work, took a break and worked overnight again
                calcBegin(0);
                calc(1, 2);
                rest(2, 3);
                calcEnd(3);
                break;
            case 5: // worked overnight, left, returned to work, took a break and went home
                calcBegin(0);
                calc(1, 2);
                rest(2, 3);
                calc(3, 4);
                break;
            default: // corner cases are not currently handled
                break;
        }
    }
}
