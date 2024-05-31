package Day6;

import AuxiliarClasses.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day6 extends Day {
    private final List<Group> groupList;

    public Day6() throws IOException {
        super(6);
        groupList = Arrays.stream(Files.readString(Path.of(getFile()))
                        .split("\n\n"))
                .map(Group::new).toList();
    }

    @Override
    public String Part1() {
        return groupList
                .stream()
                .map(Group::getNumberOfQuestions)
                .reduce(Integer::sum)
                .get()
                .toString();
    }

    @Override
    public String Part2() {
        return groupList
                .stream()
                .map(Group::getNumberOfEveryoneYes)
                .reduce(Integer::sum)
                .get()
                .toString();
    }
}
