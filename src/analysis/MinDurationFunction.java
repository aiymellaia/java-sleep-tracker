package analysis;

import model.SleepAnalysisResult;
import model.SleepSession;

import java.util.List;

public class MinDurationFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {
        long minMinutes = sessions.stream()
                .mapToLong(s -> java.time.Duration.between(s.getStart(), s.getEnd()).toMinutes())
                .min()
                .orElse(0);

        return new SleepAnalysisResult("Минимальная продолжительность сессии (мин)", minMinutes);
    }
}

