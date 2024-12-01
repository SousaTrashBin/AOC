package year2020.day7;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day7 extends Day {
    private final BagCatalog bagCatalog = new BagCatalog();

    public Day7(int year) throws IOException {
        super(year);
        Files.readString(Path.of(getFile()))
                .lines()
                .forEach(bagCatalog::add);
    }

    @Override
    public String getPart1() {
        return String.valueOf(bagCatalog.howManyBagsContain("shinygold"));
    }

    @Override
    public String getPart2() {
        return String.valueOf(bagCatalog.howManyBagsInside("shinygold"));
    }

    @Override
    public int getDay() {
        return 7;
    }
}
