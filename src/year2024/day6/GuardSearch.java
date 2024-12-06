package year2024.day6;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GuardSearch {
    private int height;
    private int width;
    private Guard initialGuard;
    private Set<Position> crates;
    private final static char CRATE_SYMBOL = '#';
    private final static Set<Character> GUARD_SYMBOLS = Set.of('^','v','<','>');

    public GuardSearch(String input) {
        parseInput(input);
    }

    private void parseInput(String input) {
        String[] lines = input.split("\n");
        height = lines.length;
        width = lines[0].length();

        initializeCrates(input);
        initializeGuard(input);
    }

    private void initializeCrates(String input) {
        crates = getPositions()
                .stream()
                .filter(position -> getCharAtPosition(input,position) == CRATE_SYMBOL)
                .collect(Collectors.toSet());
    }

    private void initializeGuard(String input) {
        Position initialGuardPosition = getPositions()
                .stream()
                .filter(position -> GUARD_SYMBOLS.contains(getCharAtPosition(input, position)))
                .findFirst()
                .get();
        initialGuard = new Guard(initialGuardPosition,parseDirection(initialGuardPosition,input));
    }

    private Direction parseDirection(Position initialGuardPosition,String input) {
        char symbol = getCharAtPosition(input,initialGuardPosition);
        return switch (symbol) {
            case '^' -> Direction.UP;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            default -> Direction.RIGHT;
        };
    }

    private char getCharAtPosition(String input, Position position) {
        int index = position.row() * (width + 1) + position.col();
        return input.charAt(index);
    }

    private List<Position> getPositions() {
        return IntStream.range(0, height)
                .boxed()
                .flatMap(row -> IntStream.range(0, width)
                        .mapToObj(col -> new Position(row, col)))
                .toList();
    }

    public int getPart1() {
        return getPart1Aux(initialGuard, crates, new HashSet<>()).size();
    }

    private Set<Position> getPart1Aux(Guard currentGuard, Set<Position> crates, Set<Position> visited) {
        if (isGuardOutsideGrid(currentGuard)) {
            return visited;
        }

        visited.add(currentGuard.getCurrentPosition());
        Guard nextGuard = currentGuard.moveForward();
        return crates.contains(nextGuard.getCurrentPosition())
                ? getPart1Aux(currentGuard.rotate90Degrees(), crates, visited)
                : getPart1Aux(nextGuard, crates, visited);
    }
    

    public int getPart2() {
        return (int) getPositions().stream()
                .filter(potentialObstruction -> !potentialObstruction.equals(initialGuard.getCurrentPosition()) &&
                        !crates.contains(potentialObstruction))
                .filter(this::wouldCauseLoop)
                .count();
    }

    private boolean wouldCauseLoop(Position newObstruction) {
        Set<Position> simulatedCrates = new HashSet<>(crates);
        simulatedCrates.add(newObstruction);

        return wouldCauseLoopAux(initialGuard, simulatedCrates, new HashSet<>());
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
        return guardPosition.row() < 0 || guardPosition.row() >= height
                || guardPosition.col() < 0 || guardPosition.col() >= width;
    }
}
