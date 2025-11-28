package test;

import analysis.MinDurationFunction;
import model.SleepAnalysisResult;
import model.SleepQuality;
import model.SleepSession;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinDurationFunctionTest {

    @Test
    public void testMinDuration() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,10,1,22,0),
                        LocalDateTime.of(2025,10,2,6,0),
                        SleepQuality.GOOD),
                new SleepSession(LocalDateTime.of(2025,10,2,23,0),
                        LocalDateTime.of(2025,10,3,7,30),
                        SleepQuality.BAD)
        );

        MinDurationFunction function = new MinDurationFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(480L, result.getValue());
        assertEquals("Минимальная продолжительность сессии (мин)", result.getDescription());
    }

    @Test
    public void testMinDurationZero() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,10,1,22,0),
                        LocalDateTime.of(2025,10,1,22,0), // 0 минут
                        SleepQuality.GOOD),
                new SleepSession(LocalDateTime.of(2025,10,2,23,0),
                        LocalDateTime.of(2025,10,3,6,0), // 7h
                        SleepQuality.BAD)
        );

        MinDurationFunction function = new MinDurationFunction();
        var result = function.analyze(sessions);

        assertEquals(0L, result.getValue());
    }
}

