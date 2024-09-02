package day12;

public abstract class SeaEntity {
    protected int x;
    protected int y;

    protected SeaEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }
    protected SeaEntity() {
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void moveX(int value){
        x += value;
    }

    public void moveY(int value){
        y += value;
    }
}
