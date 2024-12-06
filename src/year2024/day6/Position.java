package year2024.day6;

public record Position(int row,int col) {
    Position move(Direction direction) {
        DirectionVector directionVector = DirectionVector.fromDirection(direction);
        return new Position(row + directionVector.y(), col + directionVector.x());
    }
}
