package com.andreldm.timeclockapi.controller;

import com.andreldm.timeclockapi.dto.TimeEntryDTO;
import com.andreldm.timeclockapi.report.model.PeriodSheet;
import com.andreldm.timeclockapi.service.TimeEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/clockin")
public class TimeEntryController {
    private TimeEntryService service;

    @Autowired
    public TimeEntryController(TimeEntryService service) {
        this.service = service;
    }

    @GetMapping("report")
    public PeriodSheet report(@RequestParam("period") YearMonth period) {
        return service.generateReport(period);
    }

    @PostMapping("create")
    public void create(@RequestBody TimeEntryDTO dto) {
        service.create(dto);
    }
}
