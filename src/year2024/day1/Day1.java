package year2024.day1;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day1 extends Day {
    private static final Pattern pattern = Pattern.compile("(?<leftNumber>\\d+)\\s+(?<rightNumber>\\d+)");
    private final List<Integer> leftList = new ArrayList<>();
    private final List<Integer> rightList = new ArrayList<>();

    public Day1(int year) throws IOException {
        super(year);
        Files.readAllLines(Path.of(super.getFile())).forEach(this::parseLine);
    }

    private void parseLine(String line) {
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            return;
        }
        leftList.add(Integer.parseInt(matcher.group("leftNumber")));
        rightList.add(Integer.parseInt(matcher.group("rightNumber")));
    }

    @Override
    public String Part1() {
        List<Integer> leftListCopy = new ArrayList<>(leftList);
        leftListCopy.sort(Comparator.reverseOrder());

        List<Integer> rightListCopy = new ArrayList<>(rightList);
        rightListCopy.sort(Comparator.reverseOrder());

        return String.valueOf(
                IntStream.range(0, rightListCopy.size())
                        .map(index -> Math.abs(rightListCopy.get(index) - leftListCopy.get(index)))
                        .sum()
        );
    }

    @Override
    public String Part2() {
        Map<Integer, Integer> rightFrequencyMap = rightList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(_ -> 1)));

        return String.valueOf(
                leftList.stream()
                        .map(leftListNumber -> leftListNumber * rightFrequencyMap.getOrDefault(leftListNumber, 0))
                        .mapToInt(Integer::intValue)
                        .sum()
        );
    }

    @Override
    public int getDay() {
        return 1;
    }
}
