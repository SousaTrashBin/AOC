package year2024.day6;

public enum Direction {
    UP(0), RIGHT(1), DOWN(2), LEFT(3);

    public final int index;

    Direction(int index) {
        this.index = index;
    }
}
