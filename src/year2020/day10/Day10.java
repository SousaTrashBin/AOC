package year2020.day10;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day10 extends Day {
    AdapterBag adapterBag;

    public Day10(int year) throws IOException {
        super(year);
        adapterBag = new AdapterBag(Files.readString(Path.of(getFile())));
    }

    @Override
    public String Part1() {
        int result = adapterBag.findNumberDifferences(1) * adapterBag.findNumberDifferences(3);
        return String.valueOf(result);
    }

    @Override
    public String Part2() {
        long validCombinations = adapterBag.numberOfValidCombinations();
        return String.valueOf(validCombinations);
    }

    @Override
    public int getDay() {
        return 10;
    }
}
