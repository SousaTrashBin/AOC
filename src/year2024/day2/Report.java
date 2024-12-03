package year2024.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;
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
                .mapToObj(this::generateLevelsWithoutIndex)
                .anyMatch(this::areLevelsMonotonic);
    }

    private boolean areLevelsMonotonic(List<Integer> levels) {
        boolean allIncreasing = levels.stream()
                .gather(Gatherers.windowSliding(2))
                .allMatch(window -> isWithinRange(window.getFirst(), window.getLast()));

        boolean allDecreasing = levels.stream()
                .gather(Gatherers.windowSliding(2))
                .allMatch(window -> isWithinRange(window.getLast(), window.getFirst()));

        return allIncreasing || allDecreasing;
    }

    private static boolean isWithinRange(int lowerValue, int upperValue) {
        return upperValue - lowerValue >= MIN_LEVEL_DIFF
                && upperValue - lowerValue <= MAX_LEVEL_DIFF;
    }

    private List<Integer> generateLevelsWithoutIndex(int index) {
        List<Integer> levelsWithoutIndex = new ArrayList<>(levels);
        levelsWithoutIndex.remove(index);
        return levelsWithoutIndex;
    }
}
