package com.andreldm.timeclockapi.model;

import com.andreldm.timeclockapi.dto.TimeEntryDTO;
import com.andreldm.timeclockapi.util.ValidationUtil;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A (very) simple model to persist time entries data.
 */
@Entity
@Table(indexes = {
        @Index(name = "INDX", columnList = "pis"),
        @Index(name = "INDX", columnList = "datetime")})
public class TimeEntry implements Comparable<TimeEntry> {
    /**
     * A sequential identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * PIS (Programa de Integração Social) Number, the brazilian identification for workers
     */
    @Column
    private long pis;

    /**
     * Time entry timestamp
     */
    @Column(nullable = false)
    private LocalDateTime datetime;

    public TimeEntry() {
        // empty ctor
    }

    public TimeEntry(long pis, LocalDateTime datetime) {
        this.pis = pis;
        this.datetime = datetime;
    }

    /**
     * Converts the DTO into a valid model.
     *
     * @param dto The DTO to be converted
     * @return A valid model instance.
     */
    public static TimeEntry fromDTO(TimeEntryDTO dto) {
        if (dto == null)
            return null;

        if (!ValidationUtil.validatePis(dto.getPis()))
            throw new IllegalArgumentException("Invalid PIS number");

        if (dto.getTime() == null)
            dto.setTime(LocalDateTime.now());

        return new TimeEntry(dto.getPis(), dto.getTime());
    }

    /* Getters & Setters */

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

    /**
     * Shortcut for time entry's date
     *
     * @return Date of time entry
     */
    public LocalDate getDate() {
        return datetime.toLocalDate();
    }

    /**
     * Shortcut for time entry's time
     *
     * @return Time of time entry
     */
    public LocalTime getTime() {
        return datetime.toLocalTime();
    }

    @Override
    public int compareTo(TimeEntry other) {
        int res = Long.compare(pis, other.getPis());
        if (res != 0)
            return res;

        return datetime.compareTo(other.getDatetime());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeEntry timeEntry = (TimeEntry) o;

        if (id != timeEntry.id) return false;
        if (pis != timeEntry.pis) return false;
        return datetime != null ? datetime.equals(timeEntry.datetime) : timeEntry.datetime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (pis ^ (pis >>> 32));
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        return result;
    }
}
