package analysis;

import model.SleepAnalysisResult;
import model.SleepSession;

import java.util.List;

@FunctionalInterface
public interface SleepAnalysisFunction {
    SleepAnalysisResult analyze(List<SleepSession> sessions);
}