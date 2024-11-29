package utils;

public class YearExecutor {
    public static void main(String[] args) {
        Year currentYear = new Year(2024);
        int currentDay = 1;
        currentYear.addDay(currentDay);
        System.out.println(currentYear);
    }
}
