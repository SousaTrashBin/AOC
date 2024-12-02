package utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Year {
    private final Map<Integer, Day> dayMap = new HashMap<>();
    private final int year;

    public Year(int year) {
        this.year = year;
    }

    public void addAllPossibleDays() {
        for (int i = 1; i <= 25; i++) {
            addDay(i);
        }
    }

    public void addDay(int day) {
        try {
            String className = String.format("year%d.day%d.Day%d", year, day, day);
            Class<?> dayClass = Class.forName(className);
            Constructor<?> dayConstructor = dayClass.getConstructor(int.class);
            Day dayInstance = (Day) dayConstructor.newInstance(year);
            dayMap.put(day, dayInstance);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException _) {
            //System.out.println("Couldn't add day " + day);
        }
    }

    public Day getDay(int day) {
        return dayMap.get(day);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Year %d:\n".formatted(year));
        dayMap.values().forEach(day -> sb.append(day.getResult().indent(5)));
        return sb.toString();
    }
}
