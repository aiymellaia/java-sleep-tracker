package test;

import analysis.BadQualityCountFunction;
import model.SleepQuality;
import model.SleepSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadQualityCountFunctionTest {

    @Test
    void testBadQualityCount() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        SleepQuality.BAD),
                new SleepSession(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 0, 0),
                        SleepQuality.GOOD),
                new SleepSession(LocalDateTime.of(2025, 10, 2, 0, 30),
                        LocalDateTime.of(2025, 10, 2, 1, 30),
                        SleepQuality.BAD)
        );

        var result = new BadQualityCountFunction().analyze(sessions);
        assertEquals(2L, (Long) result.getValue());
    }

    @Test
    void testBadQualityZero() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        SleepQuality.NORMAL),
                new SleepSession(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 0, 0),
                        SleepQuality.GOOD)
        );

        var result = new BadQualityCountFunction().analyze(sessions);
        assertEquals(0L, (Long) result.getValue());
    }
}
