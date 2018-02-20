package com.andreldm.timeclockapi.unit;

import com.andreldm.timeclockapi.report.calculator.TimeCalculator;
import com.andreldm.timeclockapi.report.calculator.TimeCalculatorFactory;
import com.andreldm.timeclockapi.report.model.DayRecord;
import org.junit.Test;

import java.time.LocalDate;

import static com.andreldm.timeclockapi.util.TimeCalculatorUtil.checkRecord;
import static com.andreldm.timeclockapi.util.TimeCalculatorUtil.createRecord;
import static java.time.DayOfWeek.*;

/**
 * Unit test for {@link com.andreldm.timeclockapi.report.calculator.OvernightCalculator} implementation.
 */
public class OvernightCalculatorTest {
    @Test
    public void test() {
        DayRecord r1 = createRecord("2018-02-05", "06:00");
        DayRecord r2 = createRecord("2018-02-06", "02:00", "18:00");
        DayRecord r3 = createRecord("2018-02-07", "01:00", "08:00", "12:00");
        DayRecord r4 = createRecord("2018-02-08", "01:30", "08:00", "12:00", "14:00");
        DayRecord r5 = createRecord("2018-02-09", "01:30", "09:00", "13:00", "14:00", "18:00");

        createCalculator(r1).calculate();
        createCalculator(r2).calculate();
        createCalculator(r3).calculate();
        createCalculator(r4).calculate();
        createCalculator(r5).calculate();

        // record, day, work, extra, rest, missingRest, incomplete
        checkRecord(r1, MONDAY, "00:00", "06:00", "00:00", true, false);
        checkRecord(r2, TUESDAY, "04:00", "04:00", "00:00", true, true);
        checkRecord(r3, WEDNESDAY, "04:00", "01:00", "00:00", true, false);
        checkRecord(r4, THURSDAY, "12:00", "03:30", "02:00", false, true);
        checkRecord(r5, FRIDAY, "08:00", "01:30", "01:00", false, false);
    }

    /**
     * Creates an {@link com.andreldm.timeclockapi.report.calculator.OvernightCalculator} instance.
     *
     * @param current {@link DayRecord} to be processed.
     * @return a newly created TimeCalculator instance.
     */
    private TimeCalculator createCalculator(DayRecord current) {
        DayRecord previous = new DayRecord(LocalDate.MIN);
        previous.setIncomplete(true);

        return TimeCalculatorFactory.getCalculator(previous, current);
    }
}
