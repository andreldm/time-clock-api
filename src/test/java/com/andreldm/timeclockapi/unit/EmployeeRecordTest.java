package com.andreldm.timeclockapi.unit;

import com.andreldm.timeclockapi.report.model.EmployeeRecord;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.andreldm.timeclockapi.util.TimeCalculatorUtil.stringToMinutes;
import static org.junit.Assert.assertEquals;

public class EmployeeRecordTest {
    @Test
    public void simple() {
        EmployeeRecord r = new EmployeeRecord();
        addTimes(r, "2018-02-05", "08:00", "12:00", "13:00", "18:00"); // Monday
        r.process();

        // record, work, workSaturday, workSunday, extra, total
        checkRecord(r, "09:00", "00:00", "00:00", "00:00", "09:00");
    }

    @Test
    public void extraWork() {
        EmployeeRecord r = new EmployeeRecord();
        addTimes(r, "2018-02-05", "08:00", "12:00", "14:00"); // Monday
        addTimes(r, "2018-02-06", "04:00", "12:00", "18:00"); // Tuesday
        r.process();

        // record, work, workSaturday, workSunday, extra, total
        checkRecord(r, "18:00", "00:00", "00:00", "06:00", "25:12");
        // 18:00 + 06:00 > 07:12 (extra)
    }

    @Test
    public void weekendWork() {
        EmployeeRecord r = new EmployeeRecord();
        addTimes(r, "2018-02-09", "08:00", "12:00", "14:00"); // Friday > 12:00 work + 02:00 extra
        addTimes(r, "2018-02-10", "04:00", "12:00", "18:00"); // Saturday > 10:00 saturday work
        addTimes(r, "2018-02-11", "19:00", "23:30"); // Sunday > 04:30 sunday work
        r.process();

        // record, work, workSaturday, workSunday, extra, total
        checkRecord(r, "12:00", "10:00", "04:30", "02:00", "37:54");
        // 12:00 + 10:00 > 15:00 (saturday) + 04:30 > 08:30 (sunday) + 02:00 > 02:24 (extra)
    }

    private void addTimes(EmployeeRecord r, String date, String... times) {
        LocalDate d = LocalDate.parse(date);
        for (String t : times) {
            r.addTime(LocalTime.parse(t).atDate(d));
        }
    }

    private void checkRecord(EmployeeRecord r, String work, String workSaturday,
                             String workSunday, String extra, String total) {
        assertEquals("Wrong work time", stringToMinutes(work), r.getWork());
        assertEquals("Wrong saturday work time", stringToMinutes(workSaturday), r.getWorkSaturday());
        assertEquals("Wrong sunday work time", stringToMinutes(workSunday), r.getWorkSunday());
        assertEquals("Wrong extra time", stringToMinutes(extra), r.getExtra());
        assertEquals("Wrong total time", stringToMinutes(total), r.getTotal());
    }
}
