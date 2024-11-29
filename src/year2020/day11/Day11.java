package year2020.day11;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day11 extends Day {
    private final Plane part1Plane;
    private final Plane part2Plane;

    public Day11(int year) throws IOException {
        super(year);
        part1Plane = new Plane(Files.readString(Path.of(super.getFile())));
        part2Plane = new Plane(Files.readString(Path.of(super.getFile())));
    }

    @Override
    public String Part1() {
        while (part1Plane.processRoundPart1()) {
        }
        return String.valueOf(part1Plane.countOccupied());
    }

    @Override
    public String Part2() {
        while (part2Plane.processRoundPart2()) {
        }
        return String.valueOf(part2Plane.countOccupied());
    }

    @Override
    public int getDay() {
        return 11;
    }
}
