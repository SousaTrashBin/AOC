package year2024.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

record RockBlink(long currentValue, int iterationsLeft) {
    public Stream<RockBlink> getRockAfterBlink() {
        if (iterationsLeft == 0) {
            return Stream.empty();
        }
        if (currentValue == 0) {
            return Stream.of(new RockBlink(1, iterationsLeft - 1));
        }
        boolean isNumDigitsEven = Math.floor(Math.log10(currentValue) + 1) % 2 == 0;
        if (isNumDigitsEven) {
            String rockString = String.valueOf(currentValue);
            return Stream.of(
                    new RockBlink(Long.parseLong(rockString.substring(0, rockString.length() / 2)), iterationsLeft - 1),
                    new RockBlink(Long.parseLong(rockString.substring(rockString.length() / 2)), iterationsLeft - 1)
            );
        }
        return Stream.of(new RockBlink(currentValue * 2024, iterationsLeft - 1));
    }
}

public class Rockline {
    private final List<Integer> rocksValue;

    public Rockline(String input) {
        rocksValue = Arrays.stream(input.split(" "))
                .map(Integer::parseInt)
                .toList();
    }

    public long getRocksCountAfterNIterations(int n) {
        return getRocksCountAfterNIterations(n, new HashMap<>());
    }

    private long getRocksCountAfterNIterations(int n, Map<RockBlink, Long> cache) {
        return rocksValue.stream()
                .map(val -> new RockBlink(val, n))
                .mapToLong(rock -> getRockCountAfterNIterations(rock, cache))
                .sum();
    }

    private long getRockCountAfterNIterations(RockBlink rock, Map<RockBlink, Long> cache) {
        if (rock.iterationsLeft() == 0) {
            return 1;
        }

        if (cache.containsKey(rock)) {
            return cache.get(rock);
        }

        long rockCount = rock.getRockAfterBlink()
                .mapToLong(rockBlink -> getRockCountAfterNIterations(rockBlink, cache))
                .sum();

        cache.put(rock, rockCount);
        return rockCount;
    }
}
