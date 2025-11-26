package test;

import analysis.SleeplessNightsFunction;
import model.SleepAnalysisResult;
import model.SleepQuality;
import model.SleepSession;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightsFunctionTest {

    @Test
    public void testSleeplessNights() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,10,1,22,0),
                        LocalDateTime.of(2025,10,2,5,30),
                        SleepQuality.GOOD),
                new SleepSession(LocalDateTime.of(2025,10,2,14,0),
                        LocalDateTime.of(2025,10,2,15,0),
                        SleepQuality.NORMAL)
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(0, result.getValue());
    }

    @Test
    public void testSleeplessNightExists() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,10,1,14,0),
                        LocalDateTime.of(2025,10,1,15,0),
                        SleepQuality.GOOD)
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(1, result.getValue());
    }

    @Test
    public void testSleeplessNightStartAfterNoon() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,10,1,13,0),
                        LocalDateTime.of(2025,10,1,14,0),
                        SleepQuality.GOOD)
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(1, result.getValue());
    }

    @Test
    public void testMultipleNightsAcrossMonths() {
        List<SleepSession> sessions = List.of(
                new SleepSession(LocalDateTime.of(2025,9,30,23,0),
                        LocalDateTime.of(2025,10,1,6,30),
                        SleepQuality.GOOD),

                new SleepSession(LocalDateTime.of(2025,10,2,12,0),
                        LocalDateTime.of(2025,10,2,13,0),
                        SleepQuality.NORMAL),

                new SleepSession(LocalDateTime.of(2025,10,3,1,0),
                        LocalDateTime.of(2025,10,3,7,0),
                        SleepQuality.GOOD)
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(1, result.getValue());
    }

}
