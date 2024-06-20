package day4;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day4 extends Day {
    private final List<PersonID> idList;

    public Day4() throws IOException {
        super(4);
        idList = Arrays.stream(Files.readString(Path.of(getFile())).split("\n\n")).map(PersonID::new).toList();
    }

    @Override
    public String Part1() {
        long validIDCount = idList.stream().filter(personID -> personID.isIDValidPart1()).count();
        return String.valueOf(validIDCount);
    }

    @Override
    public String Part2() {
        long validIDCount = idList.stream().filter(personID -> personID.isIDValidPart2()).count();
        return String.valueOf(validIDCount);
    }
}
