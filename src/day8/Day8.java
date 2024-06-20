package day8;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The type Day 8.
 */
public class Day8 extends Day {
    GameConsole gameConsole;

    /**
     * Instantiates a new Day 8.
     */
    public Day8() throws IOException {
        super(8);
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
}
