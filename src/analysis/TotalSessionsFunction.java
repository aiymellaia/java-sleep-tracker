package analysis;

import model.SleepAnalysisResult;
import model.SleepSession;

import java.util.List;

public class TotalSessionsFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepSession> sessions) {
        int total = sessions.size();
        return new SleepAnalysisResult("Общее количество сессий сна", total);
    }
}
