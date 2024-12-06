package year2024.day6;

public record DirectionVector(int x,int y) {
    static DirectionVector fromDirection(Direction direction){
        return switch (direction) {
            case UP -> new DirectionVector(0, -1);
            case DOWN -> new DirectionVector(0, 1);
            case LEFT -> new DirectionVector(-1, 0);
            case RIGHT -> new DirectionVector(1, 0);
        };
    }
}
