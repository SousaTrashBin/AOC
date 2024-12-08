package year2024.day8;

import year2024.day6.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AntenaGrid {
    private final char[][] grid;
    private final int width;
    private final int height;
    private Map<Character, List<Position>> antenaPositions;

    public AntenaGrid(String input) {
        grid = input.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        height = grid.length;
        width = grid[0].length;
        mapAntenaPositions();
    }

    private void mapAntenaPositions() {
        antenaPositions = IntStream.range(0, height)
                .boxed()
                .flatMap(row -> IntStream.range(0, width)
                        .filter(col -> grid[row][col] != '.')
                        .mapToObj(col -> new Position(row, col)))
                .collect(Collectors.groupingBy(pos -> grid[pos.row()][pos.col()]));
    }

    public int calculateAntiNodesCountPart1() {
        return (int) antenaPositions.values().stream()
                .flatMap(this::generateAntenaPositionPairs)
                .flatMap(pair -> calculateAntinodes(pair.getFirst(), pair.getLast(), 2).stream())
                .distinct()
                .count();
    }

    public int calculateAntiNodesCountPart2() {
        return (int) antenaPositions.values().stream()
                .flatMap(this::generateAntenaPositionPairs)
                .flatMap(pair -> calculateAntinodesPart2(pair.getFirst(), pair.getLast()).stream())
                .distinct()
                .count();
    }

    private List<Position> calculateAntinodesPart2(Position antena1, Position antena2) {
        return calculateAntinodesPart2Aux(antena1, antena2, new ArrayList<>(), 1);
    }

    private List<Position> calculateAntinodesPart2Aux(Position antena1, Position antena2,
                                                      ArrayList<Position> antinodesAcc, int factor) {
        List<Position> antinodes = calculateAntinodes(antena1, antena2, factor);
        if (antinodes.isEmpty()) return antinodesAcc;

        antinodesAcc.addAll(antinodes);
        return calculateAntinodesPart2Aux(antena1, antena2, antinodesAcc, factor + 1);
    }

    private boolean isPositionInsideGrid(Position position) {
        return position.row() >= 0 && position.row() < height
                && position.col() >= 0 && position.col() < width;
    }

    private Stream<List<Position>> generateAntenaPositionPairs(List<Position> positions) {
        return IntStream.range(0, positions.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, positions.size())
                        .mapToObj(j -> List.of(positions.get(i), positions.get(j))));
    }

    private List<Position> calculateAntinodes(Position antena1, Position antena2, int factor) {
        Position firstAntinode = new Position(
                factor * antena1.row() - (factor - 1) * antena2.row(),
                factor * antena1.col() - (factor - 1) * antena2.col()
        );
        Position secondAntinode = new Position(
                factor * antena2.row() - (factor - 1) * antena1.row(),
                factor * antena2.col() - (factor - 1) * antena1.col()
        );
        return Stream.of(firstAntinode, secondAntinode)
                .filter(this::isPositionInsideGrid)
                .toList();
    }

}
