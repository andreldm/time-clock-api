package com.andreldm.timeclockapi.repository;

import com.andreldm.timeclockapi.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    boolean existsByPisAndDatetimeBetween(Long pis, LocalDateTime d1, LocalDateTime d2);
    List<TimeEntry> findByDatetimeBetweenOrderByPisAscDatetimeAsc(LocalDateTime d1, LocalDateTime d2);
}
