package year2024.day9;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day9 extends Day {
    private final DiskMap diskMap;
    public Day9(int year) throws IOException {
        super(year);
        diskMap = new DiskMap(Files.readString(Path.of(getFile())));
    }

    @Override
    public String getPart1() {
        return String.valueOf(diskMap.getPart1());
    }

    @Override
    public String getPart2() {
        return String.valueOf(diskMap.getPart2());
    }

    @Override
    public int getDay() {
        return 9;
    }
}
