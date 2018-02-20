package com.andreldm.timeclockapi.unit;

import com.andreldm.timeclockapi.report.calculator.TimeCalculator;
import com.andreldm.timeclockapi.report.calculator.TimeCalculatorFactory;
import com.andreldm.timeclockapi.report.model.DayRecord;
import org.junit.Test;

import static com.andreldm.timeclockapi.util.TimeCalculatorUtil.*;
import static java.time.DayOfWeek.*;

/**
 * Unit test for {@link com.andreldm.timeclockapi.report.calculator.RegularCalculator} implementation.
 */
public class RegularCalculatorTest {
    @Test
    public void simpleWorkDay() {
        DayRecord r1 = createRecord("2018-02-02", "08:00", "12:00", "13:00", "17:00"); // 8 hours
        DayRecord r2 = createRecord("2018-02-03", "08:00", "11:00", "11:15", "14:15"); // 6 hours
        DayRecord r3 = createRecord("2018-02-04", "08:00", "12:00"); // 4 hours

        createCalculator(r1).calculate();
        createCalculator(r2).calculate();
        createCalculator(r3).calculate();

        // record, day, work, extra, rest, missingRest, incomplete
        checkRecord(r1, FRIDAY, "08:00", "00:00", "01:00", false, false);
        checkRecord(r2, SATURDAY, "06:00", "00:00", "00:15", false, false);
        checkRecord(r3, SUNDAY, "04:00", "00:00", "00:00", false, false);
    }

    @Test
    public void workOvernight() {
        DayRecord r1 = createRecord("2018-02-01", "08:00", "12:00", "14:00");
        DayRecord r2 = createRecord("2018-02-01", "18:00");
        DayRecord r3 = createRecord("2018-02-01", "15:00");

        createCalculator(r1).calculate();
        createCalculator(r2).calculate();
        createCalculator(r3).calculate();

        // record, day, work, extra, rest, missingRest, incomplete
        checkRecord(r1, THURSDAY, "12:00", "02:00", "02:00", false, true);
        checkRecord(r2, THURSDAY, "04:00", "02:00", "00:00", true, true);
        checkRecord(r3, THURSDAY, "07:00", "02:00", "00:00", true, true);
    }

    @Test
    public void missingRest() {
        DayRecord r1 = createRecord("2018-02-01", "08:00", "12:00", "12:59", "17:00"); // 00:59 of rest
        DayRecord r2 = createRecord("2018-02-02", "08:00", "11:00", "11:14", "14:15"); // 00:14 of rest

        createCalculator(r1).calculate();
        createCalculator(r2).calculate();

        // record, day, work, extra, rest, missingRest, incomplete
        checkRecord(r1, THURSDAY, "08:01", "00:00", "00:59", true, false);
        checkRecord(r2, FRIDAY, "06:01", "00:00", "00:14", true, false);
    }

    /**
     * Creates an {@link com.andreldm.timeclockapi.report.calculator.RegularCalculator} instance.
     *
     * @param current {@link DayRecord} to be processed.
     * @return a newly created TimeCalculator instance.
     */
    private TimeCalculator createCalculator(DayRecord current) {
        return TimeCalculatorFactory.getCalculator(null, current);
    }
}
