package utils;

public class YearExecutor {
    public static void main(String[] args) {
        Year currentYear = new Year(2024);
        currentYear.addDay(5);
        System.out.println(currentYear);
    }
}
