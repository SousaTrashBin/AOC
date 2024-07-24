package day11;

import utils.Pair;

import java.util.Arrays;
import java.util.List;

public class Plane {
    private SeatType[][] seatGrid;

    public Plane(String input) {
        seatGrid = input
                .lines()
                .map(line -> line.chars()
                        .mapToObj(c -> SeatFactory.getSeatType((char) c))
                        .toArray(SeatType[]::new))
                .toArray(SeatType[][]::new);
    }

    public boolean processRoundPart1(){
        return processRound(this::getNewSeatPart1);
    }

    public boolean processRoundPart2(){
        return processRound(this::getNewSeatPart2);
    }

    private boolean processRound(NewSeatFunction seatFunction) {
        SeatType[][] newGrid = new SeatType[seatGrid.length][seatGrid[0].length];
        fillNewGrid(newGrid, seatFunction);
        boolean isStabilized = Arrays.deepEquals(newGrid, seatGrid);
        if (!isStabilized) {
            seatGrid = newGrid;
        }
        return !isStabilized;
    }

    private void fillNewGrid(SeatType[][] newGrid, NewSeatFunction seatFunction) {
        for (int i = 0; i < seatGrid.length; i++) {
            for (int j = 0; j < seatGrid[0].length; j++) {
                newGrid[i][j] = seatFunction.getNewSeat(i, j);
            }
        }
    }

    private SeatType getNewSeatPart1(int x, int y) {
        if (seatGrid[x][y] == SeatType.EMPTY && howManyOccupiedSeatsNear(x, y) == 0) {
            return SeatType.OCCUPIED;
        } else if (seatGrid[x][y] == SeatType.OCCUPIED && howManyOccupiedSeatsNear(x, y) >= 4) {
            return SeatType.EMPTY;
        }
        return seatGrid[x][y];
    }

    private SeatType getNewSeatPart2(int x, int y) {
        if (seatGrid[x][y] == SeatType.EMPTY && howManyOccupiedSeatsNearPart2(x, y) == 0) {
            return SeatType.OCCUPIED;
        } else if (seatGrid[x][y] == SeatType.OCCUPIED && howManyOccupiedSeatsNearPart2(x, y) >= 5) {
            return SeatType.EMPTY;
        }
        return seatGrid[x][y];
    }

    private int howManyOccupiedSeatsNear(int x, int y) {
        return countOccupiedSeatsNear(x, y);
    }

    private int howManyOccupiedSeatsNearPart2(int x, int y) {
        List<Pair<Integer,Integer>> positionsToVerify = List.of(new Pair<>(-1,-1),
                new Pair<>(-1,0),
                new Pair<>(-1,1),
                new Pair<>(0,-1),
                new Pair<>(0,1),
                new Pair<>(1,-1),
                new Pair<>(1,0),
                new Pair<>(1,1));
        return getOccupiedSeatsNear(x, y, positionsToVerify);
    }

    private int getOccupiedSeatsNear(int x, int y, List<Pair<Integer, Integer>> positionsToVerify) {
        int occupiedSeats = 0;
        for (Pair<Integer, Integer> positionToVerify : positionsToVerify) {
            for (int i = x + positionToVerify.f(), j = y + positionToVerify.s(); isPositionValid(i,j); i += positionToVerify.f(),j += positionToVerify.s()) {
                if(seatGrid[i][j] == SeatType.OCCUPIED) {
                    occupiedSeats++;
                    break;
                }
                else if(seatGrid[i][j] == SeatType.EMPTY) {
                    break;
                }
            }
        }
        return occupiedSeats;
    }

    private int countOccupiedSeatsNear(int x, int y) {
        int occupiedNear = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newX = x + i;
                int newY = y + j;
                if (isPositionValid(newX, newY) && seatGrid[newX][newY] == SeatType.OCCUPIED) {
                    occupiedNear++;
                }
            }
        }
        return occupiedNear;
    }

    private boolean isPositionValid(int x, int y) {
        return x >= 0 && y >= 0 && x < seatGrid.length && y < seatGrid[0].length;
    }

    public int countOccupied() {
        return (int) Arrays.stream(seatGrid)
                .flatMap(Arrays::stream)
                .filter(seat -> seat == SeatType.OCCUPIED)
                .count();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SeatType[] row : seatGrid) {
            for (SeatType seat : row) {
                switch (seat) {
                    case OCCUPIED -> sb.append('#');
                    case EMPTY -> sb.append('L');
                    case FLOOR -> sb.append('.');
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @FunctionalInterface
    private interface NewSeatFunction {
        SeatType getNewSeat(int x, int y);
    }
}
