package year2024.day10;

import year2024.day6.Direction;
import year2024.day6.Position;

import java.util.*;

public class TopographicMapSearch {
    private final HashMap<Position,Integer> positions = new HashMap<>();
    private final Set<Position> possibleTrailHeads = new HashSet<>();
    private final int gridHeight;
    private final int gridWidth;

    public TopographicMapSearch(String input) {
        String[] lines = input.split("\n");
        gridHeight = lines.length;
        gridWidth = lines[0].length();
        for (int col = 0; col < gridHeight; col++) {
            for (int row = 0; row < gridWidth; row++) {
                int currentValue = Character.getNumericValue(lines[row].charAt(col));
                Position currentPosition = new Position(row, col);
                positions.put(currentPosition, currentValue);
                if (currentValue == 0) possibleTrailHeads.add(currentPosition);
            }
        }
    }

    public int getTrailCountPart1() {
        return possibleTrailHeads.stream().mapToInt(this::getHikingTrailCountPart1).sum();
    }

    private int getHikingTrailCountPart1(Position possibleTrailheadPosition) {
        return getHikingTrailCountDFS(possibleTrailheadPosition,new HashSet<>());
    }

    public int getTrailCountPart2() {
        return possibleTrailHeads.stream().mapToInt(this::getHikingTrailCountPart2).sum();
    }

    private int getHikingTrailCountPart2(Position possibleTrailheadPosition) {
        return getHikingTrailCountDFS(possibleTrailheadPosition,new ArrayList<>());
    }

    private int getHikingTrailCountDFS(Position currentPosition, Collection<Position> collection) {
        int currentValue = positions.get(currentPosition);
        for (Direction direction : Direction.values()) {
            Position newPosition = currentPosition.move(direction);
            if (isValidPosition(newPosition)) {
                int newValue = positions.get(newPosition);
                if (newValue == currentValue + 1) {
                    getHikingTrailCountDFS(newPosition,collection);
                    if (newValue == 9) collection.add(newPosition);
                }
            }
        }
        return collection.size();
    }

    private boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < gridHeight
                && position.col() >= 0 && position.col() < gridWidth;
    }
}
