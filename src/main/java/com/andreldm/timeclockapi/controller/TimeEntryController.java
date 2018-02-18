package com.andreldm.timeclockapi.controller;

import com.andreldm.timeclockapi.dto.TimeEntryDTO;
import com.andreldm.timeclockapi.model.TimeEntry;
import com.andreldm.timeclockapi.repository.TimeEntryRepository;
import com.andreldm.timeclockapi.service.TimeEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/clockin")
public class TimeEntryController {
    private TimeEntryService service;

    @Autowired
    public TimeEntryController(TimeEntryService service) {
        this.service = service;
    }

    @GetMapping
    public List<TimeEntry> list() {
        return service.findAll();
    }

    @PostMapping("create")
    public void create(@RequestBody TimeEntryDTO dto) {
        service.create(dto);
    }
}
