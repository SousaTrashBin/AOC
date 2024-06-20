package day3;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day3 extends Day {
    private final Tobogan toggoBan;

    public Day3() throws IOException {
        super(3);
        toggoBan = new Tobogan(Files.readString(Path.of(getFile())));
    }

    @Override
    public String Part1() {
        return String.valueOf(toggoBan.getTrees());
    }

    @Override
    public String Part2() {
        return String.valueOf(toggoBan.getTreesPart2());
    }
}
