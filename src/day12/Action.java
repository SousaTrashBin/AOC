package day12;

public class Action {
    public char instruction;
    public int value;

    public Action(char instruction, int value) {
        this.instruction = instruction;
        this.value = value;
    }

    public static Action fromString(String string) {
        char instruction = string.charAt(0);
        int value = Integer.parseInt(string.substring(1));
        return new Action(instruction,value);
    }
}
