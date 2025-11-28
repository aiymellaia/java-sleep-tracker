package analysis;

import model.Chronotype;
import model.SleepAnalysisResult;
import model.SleepSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeFunction implements SleepAnalysisFunction {

    private static final LocalTime LATE_SLEEP_THRESHOLD = LocalTime.of(23, 0);
    private static final LocalTime LATE_WAKE_THRESHOLD = LocalTime.of(9, 0);
    private static final LocalTime EARLY_SLEEP_THRESHOLD = LocalTime.of(22, 0);
    private static final LocalTime EARLY_WAKE_THRESHOLD = LocalTime.of(7, 0);

    private static final LocalTime NIGHT_START = LocalTime.of(22, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(9, 0);

    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {

        List<SleepSession> nightSessions = sessions.stream()
                .filter(s -> {
                    LocalTime start = s.getStart().toLocalTime();
                    LocalTime end = s.getEnd().toLocalTime();

                    boolean startsAtNight = !start.isBefore(NIGHT_START);
                    boolean endsAtNight = end.isBefore(NIGHT_END);
                    boolean spansMidnight = s.getEnd().toLocalDate().isAfter(s.getStart().toLocalDate());

                    return startsAtNight || endsAtNight || spansMidnight;
                })
                .collect(Collectors.toList());

        Map<Chronotype, Long> counts = nightSessions.stream()
                .collect(Collectors.groupingBy(s -> {
                    LocalTime sleepTime = s.getStart().toLocalTime();
                    LocalTime wakeTime = s.getEnd().toLocalTime();
                    if (sleepTime.isAfter(LATE_SLEEP_THRESHOLD) && wakeTime.isAfter(LATE_WAKE_THRESHOLD)) {
                        return Chronotype.NIGHT_OWL;
                    } else if (sleepTime.isBefore(EARLY_SLEEP_THRESHOLD) && wakeTime.isBefore(EARLY_WAKE_THRESHOLD)) {
                        return Chronotype.EARLY_BIRD;
                    } else {
                        return Chronotype.DOVE;
                    }
                }, Collectors.counting()));

        long nightOwl = counts.getOrDefault(Chronotype.NIGHT_OWL, 0L);
        long earlyBird = counts.getOrDefault(Chronotype.EARLY_BIRD, 0L);
        long dove = counts.getOrDefault(Chronotype.DOVE, 0L);

        long max = Math.max(nightOwl, Math.max(earlyBird, dove));

        boolean owlTop = nightOwl == max;
        boolean birdTop = earlyBird == max;
        boolean doveTop = dove == max;

        int winners = (owlTop ? 1 : 0) + (birdTop ? 1 : 0) + (doveTop ? 1 : 0);

        Chronotype finalType = winners == 1
                ? (owlTop ? Chronotype.NIGHT_OWL : birdTop ? Chronotype.EARLY_BIRD : Chronotype.DOVE)
                : Chronotype.DOVE;

        return new SleepAnalysisResult("Хронотип пользователя", finalType);
    }
}
