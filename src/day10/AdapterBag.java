package day10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

public class AdapterBag {
    private final List<Integer> adapters;
    private ConcurrentMap<Integer,Long> memoMap = new ConcurrentHashMap<>();

    public AdapterBag(String s) {
        adapters = s.lines()
                .map(Integer::parseInt)
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        adapters.addFirst(0);
        adapters.addLast(adapters.getLast() + 3);
    }

    public int findNumberDifferences(int diff) {
        return adapters.stream().reduce(
                new State(),
                (stateAcc, currentAdaptor) -> getNextState(diff, stateAcc, currentAdaptor),
                (state, _) -> state
        ).differenceCounter;
    }

    private static State getNextState(int diff, State stateAcc, Integer currentAdaptor) {
        if (currentAdaptor - stateAcc.lastNumber == diff) stateAcc.differenceCounter++;
        stateAcc.lastNumber = currentAdaptor;
        return stateAcc;
    }

    public long numberOfValidCombinations(){
        return numberOfValidCombinations(0);
    }

    private long numberOfValidCombinations(int index) {
        if (index >= adapters.size() - 1) return 1;
        if (memoMap.containsKey(index)) return memoMap.get(index);

        long numberOfValidCombinations = getNumberOfValidCombinationsAux(index);
        memoMap.put(index, numberOfValidCombinations);
        return numberOfValidCombinations;
    }

    private long getNumberOfValidCombinationsAux(int index) {
        return IntStream.range(index + 1, adapters.size())
                .parallel()
                .takeWhile(i -> adapters.get(i) - adapters.get(index) <= 3)
                .mapToLong(this::numberOfValidCombinations)
                .sum();
    }


    private static class State {
        int lastNumber = Integer.MIN_VALUE;
        int differenceCounter = 0;
    }
}
