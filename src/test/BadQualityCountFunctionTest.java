package test;

import analysis.BadQualityCountFunction;
import model.SleepQuality;
import model.SleepSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BadQualityCountFunctionTest {

    @Test
    void testBadQualityCount() {
        var sessions = List.of(
                new SleepSession(LocalDateTime.now(), LocalDateTime.now().plusHours(1), SleepQuality.BAD),
                new SleepSession(LocalDateTime.now(), LocalDateTime.now().plusHours(1), SleepQuality.GOOD),
                new SleepSession(LocalDateTime.now(), LocalDateTime.now().plusHours(1), SleepQuality.BAD)
        );

        var result = new BadQualityCountFunction().analyze(sessions);
        assertEquals(2L, (Long) result.getValue());
    }

    @Test
    void testBadQualityZero() {
        var sessions = List.of(
                new SleepSession(LocalDateTime.now(), LocalDateTime.now().plusHours(1), SleepQuality.NORMAL),
                new SleepSession(LocalDateTime.now(), LocalDateTime.now().plusHours(1), SleepQuality.GOOD)
        );

        var result = new BadQualityCountFunction().analyze(sessions);
        assertEquals(0L, (Long) result.getValue());
    }
}
