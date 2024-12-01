package year2020.day8;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day8 extends Day {
    GameConsole gameConsole;

    public Day8(int year) throws IOException {
        super(year);
        gameConsole = new GameConsole(Files.readString(Path.of(getFile())).lines().toList());
    }

    @Override
    public String Part1() {
        return String.valueOf(gameConsole.findLoop());
    }

    @Override
    public String Part2() {
        return String.valueOf(gameConsole.findFix());
    }

    @Override
    public int getDay() {
        return 8;
    }
}
