package com.andreldm.timeclockapi.unit;

import com.andreldm.timeclockapi.report.calculator.OvernightCalculator;
import com.andreldm.timeclockapi.report.calculator.RegularCalculator;
import com.andreldm.timeclockapi.report.calculator.TimeCalculator;
import com.andreldm.timeclockapi.report.calculator.TimeCalculatorFactory;
import com.andreldm.timeclockapi.report.model.DayRecord;
import org.junit.Test;
import org.springframework.util.Assert;

import java.time.LocalDate;

public class TimeCalculatorFactoryTest {
    @Test
    public void testRegular() {
        DayRecord r1 = new DayRecord(LocalDate.now());
        TimeCalculator c1 = TimeCalculatorFactory.getCalculator(null, r1);
        TimeCalculator c2 = TimeCalculatorFactory.getCalculator(r1, r1);

        Assert.isInstanceOf(RegularCalculator.class, c1);
        Assert.isInstanceOf(RegularCalculator.class, c2);
    }

    @Test
    public void testOvernight() {
        DayRecord r1 = new DayRecord(LocalDate.now());
        DayRecord r2 = new DayRecord(LocalDate.now());
        r1.setIncomplete(true);

        TimeCalculator c1 = TimeCalculatorFactory.getCalculator(r1, r2);

        Assert.isInstanceOf(OvernightCalculator.class, c1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFail() {
        TimeCalculatorFactory.getCalculator(null, null);
    }
}
