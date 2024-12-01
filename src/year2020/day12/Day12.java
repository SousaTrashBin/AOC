package year2020.day12;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day12 extends Day {
    Ferry ferry1;
    Ferry ferry2;
    List<Action> actions;

    public Day12(int year) throws IOException {
        super(year);
        ferry1 = new FerryPart1();
        ferry2 = new FerryPart2();
        actions = Files.readString(Path.of(super.getFile())).lines().map(Action::fromString).toList();
    }

    @Override
    public String getPart1() {
        actions.forEach(action -> ferry1.processAction(action));
        return String.valueOf(ferry1.getManhattanDistance());
    }

    @Override
    public String getPart2() {
        actions.forEach(action -> ferry2.processAction(action));
        return String.valueOf(ferry2.getManhattanDistance());
    }

    @Override
    public int getDay() {
        return 12;
    }
}
