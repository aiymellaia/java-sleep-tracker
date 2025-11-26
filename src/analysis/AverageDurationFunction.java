package analysis;

import model.SleepAnalysisResult;
import model.SleepSession;

import java.util.List;

public class AverageDurationFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {
        double avgMinutes = sessions.stream()
                .mapToLong(s -> java.time.Duration.between(s.getStart(), s.getEnd()).toMinutes())
                .average()
                .orElse(0);

        return new SleepAnalysisResult("Средняя продолжительность сессии (мин)", avgMinutes);
    }
}
