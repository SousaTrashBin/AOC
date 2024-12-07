package year2024.day7;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day7 extends Day {
    private final EquationCatalog equationCatalog;

    public Day7(int year) throws IOException {
        super(year);
        equationCatalog = new EquationCatalog(Files.readString(Path.of(super.getFile())));
    }

    @Override
    public String getPart1() {
        return String.valueOf(equationCatalog.getValidEquationCountPart1());
    }

    @Override
    public String getPart2() {
        return String.valueOf(equationCatalog.getValidEquationCountPart2());
    }

    @Override
    public int getDay() {
        return 7;
    }
}
