package utils;

import java.util.Objects;

public class Pos {
	private int x;
	private int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move(Direction direction) {
		switch (direction) {
		case UP -> y += 1;
		case DOWN -> y -= 1;
		case LEFT -> x -= 1;
		case RIGHT -> x += 1;
		}
	}

	public Pos moveTo(Direction direction) {
		Pos copy = copy();
		copy.move(direction);
		return copy;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public Pos copy() {
		return new Pos(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Pos pos)) return false;
        return x == pos.x && y == pos.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
