package year2024.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Report {
    private static final int MIN_LEVEL_DIFF = 1;
    private static final int MAX_LEVEL_DIFF = 3;
    private final List<Integer> levels;

    public Report(String line) {
        levels = Stream.of(line.split(" "))
                .map(Integer::parseInt)
                .toList();
    }

    public boolean isSafePart1() {
        return areLevelsMonotonic(levels);
    }

    public boolean isSafePart2() { // currently creating new lists for every removed index, could be optimized by just skipping the index
        return isSafePart1() || IntStream.range(0, levels.size())
                .mapToObj(this::getLevelsWithoutIndex)
                .anyMatch(this::areLevelsMonotonic);
    }

    private boolean areLevelsMonotonic(List<Integer> levels) {
        boolean allIncreasing = IntStream.range(0, levels.size() - 1)
                .allMatch(index -> isWithinRange(levels.get(index), levels.get(index+1)));

        boolean allDecreasing = IntStream.range(0, levels.size() - 1)
                .allMatch(index -> isWithinRange(levels.get(index+1), levels.get(index)));

        return allIncreasing || allDecreasing;
    }

    private static boolean isWithinRange(int lowerValue, int upperValue) {
        return upperValue - lowerValue >= MIN_LEVEL_DIFF
                && upperValue - lowerValue <= MAX_LEVEL_DIFF;
    }

    private List<Integer> getLevelsWithoutIndex(int index) {
        List<Integer> levelsWithoutIndex = new ArrayList<>(levels);
        levelsWithoutIndex.remove(index);
        return levelsWithoutIndex;
    }
}
