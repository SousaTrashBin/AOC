package AuxiliarClasses;

/**
 * The type Day.
 */
public abstract class Day {
    private final int day;
    private final String file;

    /**
     * Instantiates a new Day.
     *
     * @param day the day
     */
    public Day(int day) {
        this.day = day;
        file = "inputFiles/Day" + day + ".txt";
    }

    /**
     * Part 1 string.
     *
     * @return the string
     */
    public abstract String Part1();

    /**
     * Part 2 string.
     *
     * @return the string
     */
    public abstract String Part2();

    /**
     * Gets day.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets file.
     *
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * Gets result.
     */
    public void getResult() {
        System.out.println("Day " + getDay() + ": Part 1 -> " + Part1() + " / Part 2 -> " + Part2());
    }
}
