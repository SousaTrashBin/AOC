package year2024.day4;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Position(int row, int col) {
    Position move(Direction direction) {
        return new Position(row + direction.rowDelta(), col + direction.colDelta());
    }
}

record Direction(int rowDelta, int colDelta) {
    Direction opposite() {
        return new Direction(-rowDelta, -colDelta);
    }
}

record WordChecker(String word,int currentIndex){
    boolean isComplete(){
        return currentIndex >= word.length();
    }

    WordChecker next(){
        return new WordChecker(word,currentIndex+1);
    }

    char currentCharacter(){
        return word.charAt(currentIndex);
    }
}

public class MonitoringStation {
    private final char[][] grid;

    private static final List<Direction> DIAGONALS = List.of(
            new Direction(-1, -1), new Direction(-1, 1), new Direction(1, -1), new Direction(1, 1)
    );

    private static final List<Direction> DIRECTIONS = List.of(
            new Direction(0, -1), new Direction(0, 1), new Direction(-1, 0), new Direction(1, 0),
            new Direction(-1, -1), new Direction(-1, 1), new Direction(1, -1), new Direction(1, 1)
    );

    public MonitoringStation(String input) {
        grid = input.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public int countXMASPresents() {
        return (int) getPositionsStream()
                .mapToLong(this::countXMASInAllDirections)
                .sum();
    }

    private Stream<Position> getPositionsStream() {
        return IntStream.range(0, grid.length)
                .boxed()
                .flatMap(row -> IntStream.range(0, grid[row].length)
                        .mapToObj(col -> new Position(row, col)));
    }

    private long countXMASInAllDirections(Position start) {
        return DIRECTIONS.stream()
                .filter(direction -> containsWord("XMAS", start, direction))
                .count();
    }

    private boolean containsWord(String word, Position start, Direction direction) {
        return containsWordRecursive(new WordChecker(word,0), start, direction);
    }

    private boolean containsWordRecursive(WordChecker wordChecker, Position current, Direction direction) {
        if (wordChecker.isComplete()) {
            return true;
        }
        if (!isValidPosition(current) || wordChecker.currentCharacter() != grid[current.row()][current.col()]) {
            return false;
        }
        Position nextPosition = current.move(direction);
        return containsWordRecursive(wordChecker.next(), nextPosition, direction);
    }

    private boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < grid.length &&
                position.col() >= 0 && position.col() < grid[position.row()].length;
    }

    public int countCrossedMASPresents() {
        return (int) getPositionsStream()
                .filter(pos -> grid[pos.row()][pos.col()] == 'A')
                .filter(this::hasCrossedMAS)
                .count();
    }

    private boolean hasCrossedMAS(Position position) {
        return DIAGONALS.stream()
                .filter(diagonal -> containsWord("MAS", position.move(diagonal.opposite()), diagonal))
                .count() == 2;
    }

}
