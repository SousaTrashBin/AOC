package day12;

import static day12.Direction.*;

public class FerryPart1 extends Ferry{
    Direction facingDirection;

    public FerryPart1(){
        facingDirection = EAST;
    }

    public void processAction(Action action){
        switch (action.instruction){
            case('N') -> super.moveY(action.value);
            case('S') -> super.moveY(-action.value);
            case('E') -> super.moveX(action.value);
            case('W') -> super.moveX(-action.value);
            case('L') -> {
                int currentIDX = facingDirection.ordinal();
                int finalIDX = (currentIDX - action.value / 90) % 4;
                if(finalIDX < 0)
                    finalIDX += 4;
                facingDirection = Direction.values()[finalIDX];
            }
            case('R') -> {
                int currentIDX = facingDirection.ordinal();
                int finalIDX = (currentIDX + action.value / 90) % 4;
                facingDirection = Direction.values()[finalIDX];
            }
            case('F') -> processAction(new Action(directionToChar(facingDirection), action.value));
        }
    }

    private char directionToChar(Direction direction){
        return switch (direction){
            case NORTH -> 'N';
            case EAST -> 'E';
            case SOUTH -> 'S';
            case WEST -> 'W';
        };
    }
}
