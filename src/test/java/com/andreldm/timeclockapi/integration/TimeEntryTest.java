package com.andreldm.timeclockapi.integration;

import com.andreldm.timeclockapi.dto.TimeEntryDTO;
import com.andreldm.timeclockapi.repository.TimeEntryRepository;
import com.andreldm.timeclockapi.service.TimeEntryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * Integration tests for time entry service and repository.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeEntryTest {
    @Autowired private TimeEntryService service;
    @Autowired private TimeEntryRepository repository;

    @Before
    public void clearEntries() {
        repository.deleteAll();
    }

    @Test
    public void testSimple() {
        service.create(new TimeEntryDTO(0L, null));

        Assert.assertEquals(1, service.findAll().size());
    }

    @Test
    public void testValidEntries() {
        service.create(new TimeEntryDTO(48030020160L, LocalDateTime.parse("2018-02-15T08:02:34")));
        service.create(new TimeEntryDTO(48030020160L, LocalDateTime.parse("2018-02-15T12:04:14")));
        service.create(new TimeEntryDTO(48030020160L, LocalDateTime.parse("2018-02-15T14:10:48")));
        service.create(new TimeEntryDTO(48030020160L, LocalDateTime.parse("2018-02-15T18:08:21")));

        service.create(new TimeEntryDTO(48030020160L, LocalDateTime.parse("2018-02-16T08:05:05")));
        service.create(new TimeEntryDTO(48030020160L, LocalDateTime.parse("2018-02-16T11:59:33")));
        service.create(new TimeEntryDTO(48030020160L, LocalDateTime.parse("2018-02-16T14:05:55")));
        service.create(new TimeEntryDTO(48030020160L, LocalDateTime.parse("2018-02-16T18:35:12")));

        Assert.assertEquals(8, service.findAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPis() {
        service.create(new TimeEntryDTO(1L, null));
    }

    @Test(expected = IllegalStateException.class)
    public void testConflict() {
        service.create(new TimeEntryDTO(0L, LocalDateTime.parse("2018-02-15T08:02:34")));
        service.create(new TimeEntryDTO(0L, LocalDateTime.parse("2018-02-15T08:03:33")));
    }
}
