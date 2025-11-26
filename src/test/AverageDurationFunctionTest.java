package test;

import analysis.AverageDurationFunction;
import model.SleepQuality;
import model.SleepSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AverageDurationFunctionTest {

    @Test
    void testAverageSimple() {
        var sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,1,1,22,0),
                        LocalDateTime.of(2025,1,2,0,0),
                        SleepQuality.GOOD), // 120
                new SleepSession(LocalDateTime.of(2025,1,2,23,0),
                        LocalDateTime.of(2025,1,3,3,0),
                        SleepQuality.NORMAL) // 240
        );

        var result = new AverageDurationFunction().analyze(sessions);
        assertEquals(180.0, result.getValue()); // (120 + 240) / 2
    }

    @Test
    void testAverageOneElement() {
        var sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,1,1,0,0),
                        LocalDateTime.of(2025,1,1,2,0),
                        SleepQuality.GOOD) // 120
        );

        var res = new AverageDurationFunction().analyze(sessions);
        assertEquals(120.0, res.getValue());
    }
}

