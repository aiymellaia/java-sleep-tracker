package analysis;

import model.SleepAnalysisResult;
import model.SleepQuality;
import model.SleepSession;

import java.util.List;

public class BadQualityCountFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {
        long count = sessions.stream()
                .filter(s -> s.getQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", count);
    }
}
