package year2020.day6;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day6 extends Day {
    private final List<Group> groupList;

    public Day6(int year) throws IOException {
        super(year);
        groupList = Arrays.stream(
                        Files.readString(Path.of(getFile())).split("\n\n")
                )
                .map(Group::new)
                .toList();
    }

    private static int StringToInt(String string) {
        if (string.isEmpty()) return 0;
        return Integer.parseInt(String.valueOf(string.charAt(string.length() - 1)))
                + StringToInt(string.substring(0, string.length() - 1)) * 10;
    }

    public static void main(String[] args) {
        System.out.println(StringToInt("12345"));
    }

    @Override
    public String getPart1() {
        return groupList
                .stream()
                .map(Group::getNumberOfQuestions)
                .reduce(Integer::sum)
                .get()
                .toString();
    }

    @Override
    public String getPart2() {
        return groupList
                .stream()
                .map(Group::getNumberOfEveryoneYes)
                .reduce(Integer::sum)
                .get()
                .toString();
    }

    @Override
    public int getDay() {
        return 6;
    }
}
