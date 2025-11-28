package app;

import analysis.*;
import model.SleepQuality;
import model.SleepSession;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу с логом сна!");
            return;
        }

        String filePath = args[0];

        List<SleepSession> sessions = Files.lines(Path.of(filePath))
                .map(line -> {
                    String[] parts = line.split(";");
                    try {
                        LocalDateTime start = LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
                        LocalDateTime end = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
                        SleepQuality quality = SleepQuality.valueOf(parts[2]);
                        return new SleepSession(start, end, quality);
                    } catch (Exception e) {
                        throw new RuntimeException("Ошибка парсинга строки: " + line, e);
                    }
                })
                .toList();

        List<SleepAnalysisFunction> functions = List.of(
                new TotalSessionsFunction(),
                new MinDurationFunction(),
                new MaxDurationFunction(),
                new AverageDurationFunction(),
                new BadQualityCountFunction(),
                new SleeplessNightsFunction(),
                new ChronotypeFunction()
        );

        functions.stream()
                .map(f -> f.analyze(sessions))
                .forEach(System.out::println);
    }
}
