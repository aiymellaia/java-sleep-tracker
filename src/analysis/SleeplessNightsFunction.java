package analysis;

import model.SleepAnalysisResult;
import model.SleepSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class SleeplessNightsFunction implements SleepAnalysisFunction {

    private static final String DESCRIPTION = "Количество бессонных ночей";

    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, 0);
        }

        LocalDateTime firstStart = sessions.stream()
                .map(SleepSession::getStart)
                .min(LocalDateTime::compareTo)
                .get();

        LocalDateTime lastEnd = sessions.stream()
                .map(SleepSession::getEnd)
                .max(LocalDateTime::compareTo)
                .get();

        LocalDate startAnalysisDate = firstStart.toLocalDate();
        if (firstStart.toLocalTime().isBefore(LocalTime.NOON)) {
            startAnalysisDate = startAnalysisDate.minusDays(1);
        }

        LocalDate endAnalysisDate = lastEnd.toLocalDate();

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(startAnalysisDate, endAnalysisDate);

        Stream<LocalDate> allNights;
        if (daysBetween < 0) {
            allNights = Stream.of(endAnalysisDate);
        } else {
            allNights = startAnalysisDate.datesUntil(endAnalysisDate.plusDays(1));
        }

        long sleeplessCount = allNights
                .filter(night -> {
                    LocalDateTime nightStart = night.atTime(22, 0);
                    LocalDateTime nightEnd = night.plusDays(1).atTime(6, 0);

                    return sessions.stream()
                            .noneMatch(s ->
                                    s.getStart().isBefore(nightEnd) &&
                                            s.getEnd().isAfter(nightStart)
                            );
                })
                .count();

        return new SleepAnalysisResult(DESCRIPTION, (int) sleeplessCount);
    }
}
