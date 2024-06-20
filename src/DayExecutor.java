import utils.Day;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DayExecutor {
    public static void main(String[] args) {
        for (int i = 1; i < 26; i++) {
            try {
                String className = String.format("day%d.Day%d", i, i);
                Class<?> dayClass = Class.forName(className);
                Constructor<?> dayConstructor = dayClass.getConstructor();
                Day dayInstance = (Day) dayConstructor.newInstance();
                dayInstance.getResult();
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException _) {
            }
        }
    }
}
