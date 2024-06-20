package day3;

import utils.Pair;

import java.util.Arrays;
import java.util.List;

public class Tobogan {
    private static final List<Pair<Integer, Integer>> slopeList = List.of(new Pair<>(1, 1),
            new Pair<>(3, 1),
            new Pair<>(5, 1),
            new Pair<>(7, 1),
            new Pair<>(1, 2));

    private final char[][] board;

    public Tobogan(String s) {
        board = Arrays.stream(s.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public int getTrees() {
        return getTrees(3, 1);
    }

    public long getTreesPart2() {
        return slopeList.stream()
                .map(pair -> (long) getTrees(pair.f(), pair.s()))
                .reduce(1L, (x, acc) -> x * acc);
    }

    public int getTrees(int xSlope, int ySlope) {
        int x = 0, y = 0, count = 0;
        while (y < board.length) {
            if (board[y][x] == '#') count++;
            x = (x + xSlope) % board[0].length;
            y += ySlope;
        }
        return count;
    }
}
