package com.andreldm.timeclockapi.service;

import com.andreldm.timeclockapi.dto.TimeEntryDTO;
import com.andreldm.timeclockapi.model.TimeEntry;
import com.andreldm.timeclockapi.report.model.PeriodSheet;
import com.andreldm.timeclockapi.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

/**
 * Service for time entries
 */
@Service
public class TimeEntryService {
    private TimeEntryRepository repository;

    @Autowired
    public TimeEntryService(TimeEntryRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets all time entries from database.<br/>
     * USE WITH CAUTION!!!
     *
     * @return List of all time entries
     */
    public List<TimeEntry> findAll() {
        return repository.findAll();
    }

    /**
     * Generate the monthly time entries report.
     * @param period Month period, e.g. 2018-02
     * @return Aggregation grouping entries by employees and days.
     */
    public PeriodSheet generateReport(YearMonth period) {
        LocalDateTime d1 = period.atDay(1).atStartOfDay();
        LocalDateTime d2 = period.atEndOfMonth().atTime(LocalTime.MAX);
        List<TimeEntry> entries = repository.findByDatetimeBetweenOrderByPisAscDatetimeAsc(d1, d2);

        return new PeriodSheet(period, entries);
    }

    /**
     * Creates a new time entry.<br/>
     * The PIS number will be validated and there cannot be two entries in less
     * than one minutes for the same PIS number.
     *
     * @param dto A DTO containing a valid PIS number and a timestamp.
     */
    @Transactional
    public void create(@RequestBody TimeEntryDTO dto) {
        TimeEntry entry = TimeEntry.fromDTO(dto);

        LocalDateTime d1 = entry.getDatetime().minusMinutes(1L);
        LocalDateTime d2 = entry.getDatetime().plusMinutes(1L);
        boolean exists = repository.existsByPisAndDatetimeBetween(entry.getPis(), d1, d2);

        if (exists)
            throw new IllegalStateException("The time entry has already been registered");

        repository.save(entry);
    }
}
