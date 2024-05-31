package Day1;

import AuxiliarClasses.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 extends Day {
    private final List<Integer> expenseList;

    public Day1() throws IOException {
        super(1);
        expenseList = Files.readString(Path.of(getFile())).lines().map(Integer::parseInt).toList();
    }

    //1721,979,366,299
    //(299,1721), (2020-979,979)
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
