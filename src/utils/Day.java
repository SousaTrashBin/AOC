package utils;

public abstract class Day {
    private final String file;

    public Day(int year) {
        file = "inputFiles/" + year + "/day" + getDay() + ".txt";
    }

    public abstract String getPart1();

    public abstract String getPart2();

    public abstract int getDay();

    public String getFile() {
        return this.file;
    }

    public String getResult() {
        return "Day " + getDay() + ": Part 1 -> " + getPart1() + " / Part 2 -> " + getPart2();
    }
}
