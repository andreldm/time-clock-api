package com.andreldm.timeclockapi.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Model that groups times entries of the same day and their calculation result.
 */
public class DayRecord {
    private final Set<LocalTime> times = new TreeSet<>();
    private long work = 0;
    private long extra = 0;
    private final DayOfWeek dayOfWeek;
    private boolean missingRest = false;
    @JsonIgnore
    private long rest = 0;
    @JsonIgnore
    private boolean incomplete = false;

    public DayRecord(LocalDate date) {
        dayOfWeek = date.getDayOfWeek();
    }

    public void addTime(LocalTime time) {
        times.add(time);
    }

    /* Getters & Setters */

    public List<LocalTime> getTimes() {
        return new ArrayList<>(times);
    }

    public long getWork() {
        return work;
    }

    public void setWork(long work) {
        this.work = work;
    }

    public long getExtra() {
        return extra;
    }

    public void setExtra(long extra) {
        this.extra = extra;
    }

    public long getRest() {
        return rest;
    }

    public void setRest(long rest) {
        this.rest = rest;
    }

    public boolean isMissingRest() {
        return missingRest;
    }

    public void setMissingRest(boolean missingRest) {
        this.missingRest = missingRest;
    }

    public boolean isIncomplete() {
        return incomplete;
    }

    public void setIncomplete(boolean incomplete) {
        this.incomplete = incomplete;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}
