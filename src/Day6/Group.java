package Day6;

import java.util.HashMap;

public class Group {
    private final HashMap<Character, Integer> questionMap = new HashMap<>();
    private int numberOfPersons = 0;

    public Group(String s) {
        s.lines()
                .map(String::chars)
                .forEach(chars -> {
                    numberOfPersons++;
                    chars.forEach(c -> {
                        if (!questionMap.containsKey((char) c)) questionMap.put((char) c, 1);
                        else questionMap.put((char) c, questionMap.get((char) c) + 1);
                    });
                });
    }

    public int getNumberOfQuestions() {
        return questionMap.keySet().size();
    }

    public int getNumberOfEveryoneYes() {
        return (int) questionMap.values().stream().filter(v -> v == numberOfPersons).count();
    }
}
