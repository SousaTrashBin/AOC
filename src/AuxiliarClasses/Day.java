package AuxiliarClasses;

public abstract class Day {
    private final int day;
    private final String file;

    public Day(int day) {
        this.day = day;
        file = "inputFiles/Day" + day + ".txt";
    }

    public abstract String Part1();

    public abstract String Part2();

    public int getDay() {
        return day;
    }

    public String getFile() {
        return file;
    }

    public void getResult() {
        System.out.println("Day " + getDay() + ": Part 1 -> " + Part1() + " / Part 2 -> " + Part2() + "\n");
    }
}
