package day12;

public class Waypoint extends SeaEntity {

    Waypoint() {
        super(10, 1);
    }

    public void rotateLeft(Action action) {
        int times = (action.value / 90) % 4;
        for (int i = 0; i < times; i++) {
            int temp = getX();
            setX(-getY());
            setY(temp);
        }
    }

    public void rotateRight(Action action) {
        int times = (action.value / 90) % 4;
        for (int i = 0; i < times; i++) {
            int temp = getX();
            setX(getY());
            setY(-temp);
        }
    }
}

