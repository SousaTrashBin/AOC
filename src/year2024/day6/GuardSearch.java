package year2024.day6;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static year2024.day6.Direction.*;

public class GuardSearch {
    private final static char CRATE_SYMBOL = '#';
    private final static Map<Character, Direction> GUARD_SYMBOLS_TO_DIRECTION = Map.of(
            '^', UP,
            'v', DOWN,
            '<', LEFT,
            '>', RIGHT
    );
    private int gridHeight;
    private int gridWidth;
    private Guard startingGuard;
    private Set<Position> crates;

    public GuardSearch(String input) {
        parseInput(input);
    }

    private void parseInput(String input) {
        String[] lines = input.split("\n");
        gridHeight = lines.length;
        gridWidth = lines[0].length();

        initializeCrates(input);
        initializeStartingGuard(input);
    }

    private void initializeCrates(String input) {
        crates = generateGridPositions()
                .stream()
                .filter(position -> getCharAtPosition(input, position) == CRATE_SYMBOL)
                .collect(Collectors.toSet());
    }

    private void initializeStartingGuard(String input) {
        record GuardInfo(Position position, char symbol) {
        }
        GuardInfo startingGuardGuardInfo = generateGridPositions()
                .stream()
                .map(position -> new GuardInfo(position, getCharAtPosition(input, position)))
                .filter(guardInfo -> GUARD_SYMBOLS_TO_DIRECTION.containsKey(guardInfo.symbol))
                .findFirst()
                .get();
        startingGuard = new Guard(startingGuardGuardInfo.position, GUARD_SYMBOLS_TO_DIRECTION.get(startingGuardGuardInfo.symbol));
    }

    private char getCharAtPosition(String input, Position position) {
        int index = position.row() * (gridWidth + 1) + position.col(); // +1 for \n
        return input.charAt(index);
    }

    private List<Position> generateGridPositions() {
        return IntStream.range(0, gridHeight)
                .boxed()
                .flatMap(row -> IntStream.range(0, gridWidth)
                        .mapToObj(col -> new Position(row, col)))
                .toList();
    }

    public int getPart1() {
        return getPart1Aux(startingGuard, crates, new HashSet<>());
    }

    private int getPart1Aux(Guard currentGuard, Set<Position> crates, Set<Position> visited) {
        if (isGuardOutsideGrid(currentGuard)) {
            return visited.size();
        }

        visited.add(currentGuard.getCurrentPosition());
        Guard nextGuard = currentGuard.moveForward();
        return crates.contains(nextGuard.getCurrentPosition())
                ? getPart1Aux(currentGuard.rotate90Degrees(), crates, visited)
                : getPart1Aux(nextGuard, crates, visited);
    }


    public int getPart2() {
        return (int) generateGridPositions().stream()
                .filter(potentialCrate -> !potentialCrate.equals(startingGuard.getCurrentPosition()) &&
                        !crates.contains(potentialCrate))
                .filter(this::wouldCauseLoop)
                .count();
    }

    private boolean wouldCauseLoop(Position newCrate) {
        Set<Position> simulatedCrates = new HashSet<>(crates);
        simulatedCrates.add(newCrate);

        return wouldCauseLoopAux(startingGuard, simulatedCrates, new HashSet<>());
    }

    private boolean wouldCauseLoopAux(Guard currentGuard, Set<Position> simulatedCrates, Set<Guard> visitedStates) {
        if (isGuardOutsideGrid(currentGuard)) {
            return false;
        }
        if (visitedStates.contains(currentGuard)) {
            return true;
        }

        visitedStates.add(currentGuard);
        Guard nextGuard = currentGuard.moveForward();
        return simulatedCrates.contains(nextGuard.getCurrentPosition())
                ? wouldCauseLoopAux(currentGuard.rotate90Degrees(), simulatedCrates, visitedStates)
                : wouldCauseLoopAux(nextGuard, simulatedCrates, visitedStates);
    }

    private boolean isGuardOutsideGrid(Guard guard) {
        Position guardPosition = guard.getCurrentPosition();
        return guardPosition.row() < 0 || guardPosition.row() >= gridHeight
                || guardPosition.col() < 0 || guardPosition.col() >= gridWidth;
    }
}
