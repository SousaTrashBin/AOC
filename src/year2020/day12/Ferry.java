package year2020.day12;

public abstract class Ferry extends SeaEntity {

    public int getManhattanDistance() {
        return Math.abs(super.getX()) + Math.abs(super.getY());
    }

    public abstract void processAction(Action action);

}
