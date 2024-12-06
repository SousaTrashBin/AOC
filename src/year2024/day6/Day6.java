package year2024.day6;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day6 extends Day {
    private GuardSearch guardSearch;

    public Day6(int year) throws IOException {
        super(year);
        guardSearch = new GuardSearch(Files.readString(Path.of(getFile())));
    }

    @Override
    public String getPart1() {
        return String.valueOf(guardSearch.getPart1());
    }

    @Override
    public String getPart2() {
        return String.valueOf(guardSearch.getPart2());
    }

    @Override
    public int getDay() {
        return 6;
    }
}
