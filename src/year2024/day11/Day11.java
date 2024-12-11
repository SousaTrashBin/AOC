package year2024.day11;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day11 extends Day {
    private final Rockline rockline;
    public Day11(int year) throws IOException {
        super(year);
        rockline = new Rockline(Files.readString(Path.of(getFile())));

    }

    @Override
    public String getPart1() {
        return String.valueOf(rockline.getRocksCountAfterNIterations(25));
    }

    @Override
    public String getPart2() {
        return String.valueOf(rockline.getRocksCountAfterNIterations(75));
    }

    @Override
    public int getDay() {
        return 11;
    }
}
