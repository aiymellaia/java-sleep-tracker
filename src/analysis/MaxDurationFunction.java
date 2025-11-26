package analysis;

import model.SleepAnalysisResult;
import model.SleepSession;

import java.util.List;

public class MaxDurationFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {
        long maxMinutes = sessions.stream()
                .mapToLong(s -> java.time.Duration.between(s.getStart(), s.getEnd()).toMinutes())
                .max()
                .orElse(0);

        return new SleepAnalysisResult("Максимальная продолжительность сессии (мин)", maxMinutes);
    }
}
