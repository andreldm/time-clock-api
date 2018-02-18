package com.andreldm.timeclockapi.model;

import com.andreldm.timeclockapi.dto.TimeEntryDTO;
import com.andreldm.timeclockapi.util.ValidationUtil;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(indexes = {
        @Index(name = "INDX", columnList = "pis"),
        @Index(name = "INDX", columnList = "datetime")})
public class TimeEntry implements Comparable<TimeEntry> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long pis;

    @Column(nullable = false)
    private LocalDateTime datetime;

    public TimeEntry() {
        // empty ctor
    }

    public TimeEntry(long pis, LocalDateTime datetime) {
        this.pis = pis;
        this.datetime = datetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPis() {
        return pis;
    }

    public void setPis(long pis) {
        this.pis = pis;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public LocalDate getDate() {
        return datetime.toLocalDate();
    }

    public LocalTime getTime() {
        return datetime.toLocalTime();
    }

    public static TimeEntry fromDTO(TimeEntryDTO dto) {
        if (dto == null)
            return null;

        if (!ValidationUtil.validatePis(dto.getPis()))
            throw new IllegalArgumentException("Invalid PIS number");

        if (dto.getTime() == null)
            dto.setTime(LocalDateTime.now());

        return new TimeEntry(dto.getPis(), dto.getTime());
    }

    @Override
    public int compareTo(TimeEntry other) {
        int res;

        res = Long.compare(pis, other.getPis());
        if (res != 0)
            return res;

        return datetime.compareTo(other.getDatetime());
    }
}
