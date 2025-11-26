package test;

import analysis.TotalSessionsFunction;
import model.SleepAnalysisResult;
import model.SleepQuality;
import model.SleepSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSessionsFunctionTest {

    @Test
    public void testTotalSessions() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025, 10, 1, 22, 15),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD),
                new SleepSession(LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.NORMAL)
        );

        TotalSessionsFunction function = new TotalSessionsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(2, result.getValue());
        assertEquals("Общее количество сессий сна", result.getDescription());
    }
}
