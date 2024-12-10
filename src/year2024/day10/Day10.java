package year2024.day10;

import utils.Day;
import year2024.day6.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day10 extends Day {
    TopographicMapSearch topographicMapSearch;

    public Day10(int year) throws IOException {
        super(year);
        topographicMapSearch = new TopographicMapSearch(Files.readString(Path.of(getFile())));
    }

    @Override
    public String getPart1() {
        return String.valueOf(topographicMapSearch.getTrailCountPart1());
    }

    @Override
    public String getPart2() {
        return String.valueOf(topographicMapSearch.getTrailCountPart2());
    }

    @Override
    public int getDay() {
        return 10;
    }
}
