package year2020.day1;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day1 extends Day {

    private final List<Integer> expenseList;

    public Day1(int year) throws IOException {
        super(year);
        expenseList = Files.lines(Path.of(getFile()))
                .map(Integer::parseInt)
                .toList();
    }

    @Override
    public String Part1() {
        int twoEntries = getTwoEntries(2020);
        if (twoEntries != -1) return String.valueOf(twoEntries);
        return "";
    }

    @Override
    public String Part2() {
        for (Integer i : expenseList) {
            int threeEntries = getTwoEntries(2020 - i);
            if (threeEntries != -1) return String.valueOf(i * threeEntries);
        }
        return "";
    }

    @Override
    public int getDay() {
        return 1;
    }

    private int getTwoEntries(int val) {
        Map<Integer, Integer> complementMap = new HashMap<>();
        for (Integer i : expenseList) {
            if (complementMap.containsKey(i)) {
                int firstValue = i;
                int secondValue = complementMap.get(i);
                return firstValue * secondValue;
            } else {
                complementMap.put(val - i, i);
            }
        }
        return -1;
    }
}
