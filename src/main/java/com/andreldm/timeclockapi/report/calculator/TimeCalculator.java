package com.andreldm.timeclockapi.report.calculator;

import com.andreldm.timeclockapi.report.model.DayRecord;
import org.threeten.extra.Interval;

import java.time.*;
import java.util.List;

/**
 * Base TimeCalculator. It computes the amount of worked time,
 * extra time (22:00~06:00), rest time (all three in minutes), if the working day
 * is complete or not and finally if the rest time is sufficient or missing.
 *
 * @see RegularCalculator
 * @see OvernightCalculator
 */
public abstract class TimeCalculator {
    private static final Interval EXTRA1 = Interval.of(
            LocalDateTime.MIN.toInstant(ZoneOffset.UTC), Duration.ofHours(6));

    private static final Interval EXTRA2 = Interval.of(
            LocalDateTime.MIN.plusHours(22).toInstant(ZoneOffset.UTC), Duration.ofHours(2));

    private long work = 0;
    private long extra = 0;
    private long rest = 0;
    private boolean incomplete = false;
    private final DayRecord record;
    private final List<LocalTime> times;

    TimeCalculator(DayRecord record) {
        this.record = record;
        this.times = record.getTimes();
    }

    /**
     * Calculates time entries and updates the {@link DayRecord} passed into the constructor.
     */
    public final void calculate() {
        compute();
        fillRecord();
    }

    /**
     * Subclasses should implement their business logic calling either {@code calcBegin},
     * {@code calcEnd}, {@code calc} and/or {@code rest} methods.
     */
    protected abstract void compute();

    /**
     * Calculates the work time from the beginning of the day (00:00) until the specified time.
     *
     * @param t End of the time interval to be calculated.
     */
    protected void calcBegin(int t) {
        calc(LocalTime.MIN, times.get(t));
    }

    /**
     * Calculates the work time from the beginning of the day (00:00) until the specified time.
     *
     * @param t End of the time interval to be calculated.
     */
    protected void calcEnd(int t) {
        calc(times.get(t), LocalTime.MAX);
        incomplete = true;
    }

    /**
     * Calculates the work time of the interval.
     *
     * @param t1 Index of DayRecord's times list for the interval beginning.
     * @param t2 Index of DayRecord's times list for the interval end.
     */
    protected void calc(int t1, int t2) {
        calc(times.get(t1), times.get(t2));
    }

    /**
     * Calculates the work time of the interval.
     *
     * @param t1 Time of the interval beginning.
     * @param t2 Time of the interval end.
     */
    protected void calc(LocalTime t1, LocalTime t2) {
        Duration durationExtra = Duration.ZERO;
        Interval i = Interval.of(
                LocalDate.MIN.atTime(t1).toInstant(ZoneOffset.UTC),
                LocalDate.MIN.atTime(t2).toInstant(ZoneOffset.UTC)
        );

        if (i.overlaps(EXTRA1))
            durationExtra = durationExtra.plus(i.intersection(EXTRA1).toDuration());

        if (i.overlaps(EXTRA2))
            durationExtra = durationExtra.plus(i.intersection(EXTRA2).toDuration());

        Duration durationWork = i.toDuration().minus(durationExtra);

        // compensate missing second of 23:59:59
        if (t2.equals(LocalTime.MAX))
            durationExtra = durationExtra.plusSeconds(1);

        work += durationWork.toMinutes();
        extra += durationExtra.toMinutes();
    }

    /**
     * Calculates the rest time of the interval.
     *
     * @param t1 Index of DayRecord's times list for the interval beginning.
     * @param t2 Index of DayRecord's times list for the interval end.
     */
    protected void rest(int t1, int t2) {
        rest += Duration.between(times.get(t1), times.get(t2)).toMinutes();
    }

    /**
     * Fills the DayRecord instance passed into the constructors with the calculated data.
     */
    protected void fillRecord() {
        record.setWork(work);
        record.setExtra(extra);
        record.setRest(rest);
        record.setIncomplete(incomplete);

        long total = work + extra;
        record.setMissingRest(total > 360 && rest < 60 || total > 240 && rest < 15);
    }

    /**
     * Subclasses have no access to the DayRecord, so this method is useful to know
     * how many entries are there.
     *
     * @return Number of times entries in the DayRecord to be calculated.
     */
    protected int getTimesCount() {
        return times.size();
    }
}
