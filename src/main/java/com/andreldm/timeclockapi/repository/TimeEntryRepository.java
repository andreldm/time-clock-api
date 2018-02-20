package com.andreldm.timeclockapi.repository;

import com.andreldm.timeclockapi.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for the TimeEntry model.
 */
@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    /**
     * Tells whether there is already an entry in the specified time interval.
     *
     * @param pis PIS Number
     * @param d1  Interval beginning
     * @param d2  Interval end
     * @return Indicates if there is already entry or not
     */
    boolean existsByPisAndDatetimeBetween(Long pis, LocalDateTime d1, LocalDateTime d2);

    /**
     * Finds all entries of the specified time interval ordered by PIS Number and its Date and Time.
     *
     * @param d1 Interval beginning
     * @param d2 Interval end
     * @return List of all time entries of the interval
     */
    List<TimeEntry> findByDatetimeBetweenOrderByPisAscDatetimeAsc(LocalDateTime d1, LocalDateTime d2);
}
