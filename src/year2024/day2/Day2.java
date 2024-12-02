package year2024.day2;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day2 extends Day {
    private final List<Report> reports;

    public Day2(int year) throws IOException {
        super(year);
        reports = Files.readAllLines(Path.of(getFile()))
                .stream()
                .map(Report::new)
                .toList();
    }

    @Override
    public String getPart1() {
        return String.valueOf(
                reports.stream()
                        .filter(Report::isSafePart1)
                        .count()
        );
    }

    @Override
    public String getPart2() {
        return String.valueOf(
                reports.stream()
                        .filter(Report::isSafePart2)
                        .count()
        );
    }

    @Override
    public int getDay() {
        return 2;
    }
}
