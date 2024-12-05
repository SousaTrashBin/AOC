package year2024.day5;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day5 extends Day {
    private final SafetyManual safetyManual;

    public Day5(int year) throws IOException {
        super(year);
        safetyManual = new SafetyManual(Files.readString(Path.of(getFile())));
    }

    @Override
    public String getPart1() {
        return String.valueOf(safetyManual.getPart1());
    }

    @Override
    public String getPart2() {
        return String.valueOf(safetyManual.getPart2());
    }

    @Override
    public int getDay() {
        return 5;
    }
}
