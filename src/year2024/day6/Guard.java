package year2024.day6;

import java.util.Objects;

public class Guard {
    private final Position currentPosition;
    private final Direction currentDirection;
    private static final Direction[] directions = Direction.values();

    public Guard(Position initialPosition, Direction initialDirection) {
        this.currentPosition = initialPosition;
        this.currentDirection = initialDirection;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public Guard rotate90Degrees() {
        Direction newDirection = directions[(currentDirection.index + 1) % directions.length];
        return new Guard(currentPosition, newDirection);
    }

    public Guard moveForward() {
        Position newPosition = currentPosition.move(currentDirection);
        return new Guard(newPosition, currentDirection);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Guard guard = (Guard) o;
        return Objects.equals(getCurrentPosition(), guard.getCurrentPosition()) && getCurrentDirection() == guard.getCurrentDirection();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentPosition(), getCurrentDirection());
    }
}
