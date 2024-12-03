package year2024.day3;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day3 extends Day {
    private final Memory memory;

    public Day3(int year) throws IOException {
        super(year);
        memory = new Memory(Files.readString(Path.of(super.getFile())));
    }

    @Override
    public String getPart1() {
        return String.valueOf(memory.getPart1());
    }

    @Override
    public String getPart2() {
        return String.valueOf(memory.getPart2());
    }

    @Override
    public int getDay() {
        return 3;
    }
}
