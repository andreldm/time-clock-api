package com.andreldm.timeclockapi.report.model;

import com.andreldm.timeclockapi.model.TimeEntry;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PeriodSheet {
    private final YearMonth period;
    private final Map<Long, EmployeeRecord> records = new TreeMap<>();

    public PeriodSheet(YearMonth period, List<TimeEntry> entries) {
        this.period = period;

        entries.forEach(e -> {
            EmployeeRecord record = records.get(e.getPis());

            if (record == null)
                records.put(e.getPis(), record = new EmployeeRecord());

            record.addTime(e.getDatetime());
        });

        records.values().forEach(EmployeeRecord::process);
    }

    /* Getters */

    public Map<Long, EmployeeRecord> getRecords() {
        return records;
    }

    public YearMonth getPeriod() {
        return period;
    }
}
