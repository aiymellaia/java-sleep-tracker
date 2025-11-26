package analysis;

import model.Chronotype;
import model.SleepAnalysisResult;
import model.SleepSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {
        List<SleepSession> nightSessions = sessions.stream()
                .filter(s -> !s.getStart().toLocalDate().isEqual(s.getEnd().toLocalDate()))
                .collect(Collectors.toList());
        Map<Chronotype, Long> counts = nightSessions.stream()
                .collect(Collectors.groupingBy(s -> {
                    LocalTime sleepTime = s.getStart().toLocalTime();
                    LocalTime wakeTime = s.getEnd().toLocalTime();

                    if (sleepTime.isAfter(LocalTime.of(23, 0)) && wakeTime.isAfter(LocalTime.of(9, 0))) {
                        return Chronotype.NIGHT_OWL;
                    } else if (sleepTime.isBefore(LocalTime.of(22, 0)) && wakeTime.isBefore(LocalTime.of(7, 0))) {
                        return Chronotype.EARLY_BIRD;
                    } else {
                        return Chronotype.DOVE;
                    }
                }, Collectors.counting()));

        long nightOwlCount = counts.getOrDefault(Chronotype.NIGHT_OWL, 0L);
        long earlyBirdCount = counts.getOrDefault(Chronotype.EARLY_BIRD, 0L);
        long doveCount = counts.getOrDefault(Chronotype.DOVE, 0L);

        Chronotype type;
        if (nightOwlCount > earlyBirdCount && nightOwlCount > doveCount) {
            type = Chronotype.NIGHT_OWL;
        } else if (earlyBirdCount > nightOwlCount && earlyBirdCount > doveCount) {
            type = Chronotype.EARLY_BIRD;
        } else {
            type = Chronotype.DOVE;
        }

        return new SleepAnalysisResult("Хронотип пользователя", type);
    }
}