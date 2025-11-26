package test;

import analysis.ChronotypeFunction;
import model.Chronotype;
import model.SleepAnalysisResult;
import model.SleepQuality;
import model.SleepSession;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeFunctionTest {

    @Test
    public void testNightOwl() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,10,1,23,30),
                        LocalDateTime.of(2025,10,2,10,0),
                        SleepQuality.GOOD)
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.NIGHT_OWL, result.getValue());
    }

    @Test
    public void testEarlyBird() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,10,1,21,0),
                        LocalDateTime.of(2025,10,2,6,30),
                        SleepQuality.GOOD)
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.EARLY_BIRD, result.getValue());
    }

    @Test
    public void testDove() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,10,1,22,30),
                        LocalDateTime.of(2025,10,2,8,30),
                        SleepQuality.GOOD)
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }
}

