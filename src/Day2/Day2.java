package Day2;

import AuxiliarClasses.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day2 extends Day {
    List<Password> passwordList;

    public Day2() throws IOException {
        super(2);
        passwordList = Files.readString(Path.of(getFile())).lines().map(Password::new).toList();
    }

    @Override
    public String Part1() {
        return String.valueOf(passwordList.stream().filter(Password::isValidPart1).count());
    }

    @Override
    public String Part2() {
        return String.valueOf(passwordList.stream().filter(Password::isValidPart2).count());
    }
}
