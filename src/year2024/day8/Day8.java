package year2024.day8;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day8 extends Day {
    private final AntenaGrid antenaGrid;

    public Day8(int year) throws IOException {
        super(year);
        antenaGrid = new AntenaGrid(Files.readString(Path.of(getFile())));
    }

    @Override
    public String getPart1() {
        return String.valueOf(antenaGrid.calculateAntiNodesCountPart1());
    }

    @Override
    public String getPart2() {
        return String.valueOf(antenaGrid.calculateAntiNodesCountPart2());
    }

    @Override
    public int getDay() {
        return 8;
    }
}
