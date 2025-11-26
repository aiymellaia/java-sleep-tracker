package test;

import analysis.MaxDurationFunction;
import model.SleepQuality;
import model.SleepSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MaxDurationFunctionTest {

    @Test
    void testMaxDurationSimple() {
        var sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,1,1,22,0),
                        LocalDateTime.of(2025,1,2,8,0),
                        SleepQuality.GOOD), // 10h
                new SleepSession(LocalDateTime.of(2025,1,2,23,0),
                        LocalDateTime.of(2025,1,3,6,0),
                        SleepQuality.NORMAL) // 7h
        );

        var result = new MaxDurationFunction().analyze(sessions);
        assertEquals(600L, result.getValue());
    }

    @Test
    void testMaxDurationWithEqualDurations() {
        var sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,1,1,22,0),
                        LocalDateTime.of(2025,1,2,0,0),
                        SleepQuality.GOOD), // 120 min
                new SleepSession(LocalDateTime.of(2025,1,2,1,0),
                        LocalDateTime.of(2025,1,2,3,0),
                        SleepQuality.NORMAL) // 120 min
        );

        var result = new MaxDurationFunction().analyze(sessions);
        assertEquals(120L, result.getValue());
    }
}
