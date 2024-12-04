package year2024.day4;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day4 extends Day {
    private final MonitoringStation monitoringStation;

    public Day4(int year) throws IOException {
        super(year);
        monitoringStation = new MonitoringStation(Files.readString(Path.of(getFile())));
    }

    @Override
    public String getPart1() {
        return String.valueOf(monitoringStation.countXMASPresents());
    }

    @Override
    public String getPart2() {
        return String.valueOf(monitoringStation.countCrossedMASPresents());
    }

    @Override
    public int getDay() {
        return 4;
    }
}
