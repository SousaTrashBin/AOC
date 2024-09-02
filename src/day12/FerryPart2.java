package day12;

public class FerryPart2 extends Ferry{
    Waypoint waypoint;

    public FerryPart2(){
        waypoint = new Waypoint();
    }

    @Override
    public void processAction(Action action) {
        switch (action.instruction){
            case('N') -> waypoint.moveY(action.value);
            case('S') -> waypoint.moveY(-action.value);
            case('E') -> waypoint.moveX(action.value);
            case('W') -> waypoint.moveX(-action.value);
            case('L') -> waypoint.rotateLeft(action);
            case('R') -> waypoint.rotateRight(action);
            case('F') -> {
                int yValue = waypoint.getY() * action.value;
                int xValue = waypoint.getX() * action.value;
                super.moveX(xValue);
                super.moveY(yValue);
            }
        }
    }
}
