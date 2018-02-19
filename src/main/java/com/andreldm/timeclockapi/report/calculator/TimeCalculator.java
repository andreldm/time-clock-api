package com.andreldm.timeclockapi.report.calculator;

import com.andreldm.timeclockapi.report.model.DayRecord;
import org.threeten.extra.Interval;

import java.time.*;
import java.util.List;

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

    public abstract void calculate();

    protected void calcStart(int t) {
        calc(LocalTime.MIN, times.get(t));
    }

    protected void calcEnd(int t) {
        calc(times.get(t), LocalTime.MAX);
        incomplete = true;
    }

    protected void calc(int t1, int t2) {
        calc(times.get(t1), times.get(t2));
    }

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

    protected void rest(int t1, int t2) {
        rest += Duration.between(times.get(t1), times.get(t2)).toMinutes();
    }

    protected void fillRecord() {
        record.setWork(work);
        record.setExtra(extra);
        record.setRest(rest);
        record.setIncomplete(incomplete);

        long total = work + extra;
        record.setMissingRest(total > 360 && rest < 60 || total > 240 && rest < 15);
    }

    protected int getTimesCount() {
        return times.size();
    }
}
