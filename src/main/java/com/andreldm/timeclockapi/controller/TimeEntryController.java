package com.andreldm.timeclockapi.controller;

import com.andreldm.timeclockapi.dto.TimeEntryDTO;
import com.andreldm.timeclockapi.report.model.PeriodSheet;
import com.andreldm.timeclockapi.service.TimeEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;

/**
 * Web controller for time entries.
 */
@RestController
@RequestMapping("/clockin")
public class TimeEntryController {
    private TimeEntryService service;

    @Autowired
    public TimeEntryController(TimeEntryService service) {
        this.service = service;
    }

    /**
     * @see TimeEntryService#generateReport(YearMonth)
     */
    @GetMapping("report")
    public PeriodSheet report(@RequestParam("period") String period) {
        try {
            return service.generateReport(YearMonth.parse(period));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid period: " + period);
        }
    }

    /**
     * @see TimeEntryService#create(TimeEntryDTO)
     */
    @PostMapping("create")
    public void create(@RequestBody TimeEntryDTO dto) {
        service.create(dto);
    }
}
