package com.andreldm.timeclockapi.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for time entries.
 *
 * @see com.andreldm.timeclockapi.model.TimeEntry
 */
public class TimeEntryDTO {
    private long pis;
    private LocalDateTime time;

    public TimeEntryDTO() {
    }

    public TimeEntryDTO(long pis) {
        this.pis = pis;
    }

    public TimeEntryDTO(long pis, LocalDateTime time) {
        this.pis = pis;
        this.time = time;
    }

    /* Getters & Setters */

    public long getPis() {
        return pis;
    }

    public void setPis(long pis) {
        this.pis = pis;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
