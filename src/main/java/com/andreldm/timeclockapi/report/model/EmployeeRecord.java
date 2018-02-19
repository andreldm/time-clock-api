package com.andreldm.timeclockapi.report.model;

import com.andreldm.timeclockapi.report.calculator.TimeCalculatorFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class EmployeeRecord {
    private final Map<LocalDate, DayRecord> records = new TreeMap<>();
    private long work = 0;
    private long workSaturday = 0;
    private long workSunday = 0;
    private long extra = 0;
    private long total = 0;

    public void addTime(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        records.computeIfAbsent(date, DayRecord::new).addTime(time);
    }

    public void process() {
        DayRecord previous = null;

        for (DayRecord r : records.values()) {
            TimeCalculatorFactory.getCalculator(previous, r).calculate();

            if (r.getDayOfWeek() == SUNDAY)
                workSunday += r.getWork() + r.getExtra();
            else if (r.getDayOfWeek() == SATURDAY)
                workSaturday += r.getWork() + r.getExtra();
            else {
                work += r.getWork();
                extra += r.getExtra();
            }

            previous = r;
        }

        total += work;

        total += workSaturday % 60;
        total += workSaturday / 60 * 90;

        total += workSunday % 60;
        total += workSunday / 60 * 120;

        total += extra % 60;
        total += extra / 60 * 72;
    }

    /* Getters */

    public Map<LocalDate, DayRecord> getRecords() {
        return records;
    }

    public long getWork() {
        return work;
    }

    public long getWorkSaturday() {
        return workSaturday;
    }

    public long getWorkSunday() {
        return workSunday;
    }

    public long getExtra() {
        return extra;
    }

    public long getTotal() {
        return total;
    }
}
