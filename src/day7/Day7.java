package day7;

import utils.Day;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day7 extends Day {
    private final BagCatalog bagCatalog = new BagCatalog();

    public Day7() throws IOException {
        super(7);
        Files.readString(Path.of(getFile()))
            .lines()
            .forEach(bagLine -> bagCatalog.add(bagLine));
    }

    @Override
    public String Part1() {
        return String.valueOf(bagCatalog.howManyBagsContain("shinygold"));
    }

    @Override
    public String Part2() {
        return String.valueOf(bagCatalog.howManyBagsInside("shinygold"));
    }
}
