package year2020.day2;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day2 extends Day {

    private final List<Password> passwordList;

    public Day2(int year) throws IOException {
        super(year);
        passwordList = Files.readString(Path.of(getFile()))
                .lines()
                .map(Password::new)
                .toList();
    }

    @Override
    public String getPart1() {
        return String.valueOf(
                passwordList.stream().filter(Password::isValidPart1).count()
        );
    }

    @Override
    public String getPart2() {
        return String.valueOf(
                passwordList.stream().filter(Password::isValidPart2).count()
        );
    }

    @Override
    public int getDay() {
        return 2;
    }
}
