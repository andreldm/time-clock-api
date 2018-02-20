package com.andreldm.timeclockapi.util;

import com.andreldm.timeclockapi.report.model.DayRecord;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

/**
 * Utility methods for test classes.
 */
public class TimeCalculatorUtil {
    public static void checkRecord(DayRecord r, DayOfWeek day, String work, String extra,
                             String rest, boolean missingRest, boolean incomplete) {
        assertEquals("Wrong day of week", day, r.getDayOfWeek());
        assertEquals("Wrong work time", stringToMinutes(work), r.getWork());
        assertEquals("Wrong extra time", stringToMinutes(extra), r.getExtra());
        assertEquals("Wrong rest time", stringToMinutes(rest), r.getRest());
        assertEquals("Wrong missing rest value", missingRest, r.isMissingRest());
        assertEquals("Wrong incomplete value", incomplete, r.isIncomplete());
    }

    public static DayRecord createRecord(String day, String... times) {
        DayRecord r = new DayRecord(LocalDate.parse(day));
        for (String time : times)
            r.addTime(LocalTime.parse(time));

        return r;
    }

    /**
     * Converts duration represented as strings into minutes.
     * @param t duration in "HH:MM" format
     * @return the amount of minutes (floor rounded)
     */
    public static long stringToMinutes(String t) {
        if (t == null || t.isEmpty() || t.equals("0") || t.equals("00:00"))
            return 0;

        String[] tokens = t.split(":");
        int hours = Integer.parseInt(tokens[0]);
        int minutes = Integer.parseInt(tokens[1]);

        return hours * 60 + minutes;
    }
}
