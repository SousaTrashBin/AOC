package year2020.day11;

import java.util.NoSuchElementException;

public enum SeatFactory {
    INSTANCE;

    public static SeatType getSeatType(char type) {
        return switch (type) {
            case 'L' -> SeatType.EMPTY;
            case '.' -> SeatType.FLOOR;
            case '#' -> SeatType.OCCUPIED;
            default -> throw new NoSuchElementException();
        };
    }
}
