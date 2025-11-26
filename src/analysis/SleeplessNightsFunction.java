package analysis;

import model.SleepAnalysisResult;
import model.SleepSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class SleeplessNightsFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0);
        }

        LocalDateTime firstStart = sessions.stream()
                .map(SleepSession::getStart)
                .min(LocalDateTime::compareTo)
                .get();

        LocalDateTime lastEnd = sessions.stream()
                .map(SleepSession::getEnd)
                .max(LocalDateTime::compareTo)
                .get();

        LocalDate startAnalysisDate = firstStart.toLocalTime().isBefore(LocalTime.NOON)
                ? firstStart.toLocalDate().minusDays(1)
                : firstStart.toLocalDate().plusDays(1);

        LocalDate endAnalysisDate = lastEnd.toLocalDate();

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(startAnalysisDate, endAnalysisDate);

        Stream<LocalDate> allNights;
        if (daysBetween < 0) {
            allNights = Stream.of(endAnalysisDate);
        } else {
            allNights = startAnalysisDate.datesUntil(endAnalysisDate.plusDays(1));
        }

        long sleeplessCount = allNights
                .filter(night -> sessions.stream()
                        .noneMatch(s -> {
                            LocalDateTime nightStartCheck = night.atStartOfDay();
                            LocalDateTime nightEndCheck = night.atTime(6, 0);
                            return s.getStart().isBefore(nightEndCheck) && s.getEnd().isAfter(nightStartCheck);
                        })
                )
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей", (int) sleeplessCount);
    }
}