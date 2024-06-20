package day4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonID {

    private final Map<String, String> fieldMap = new HashMap<>();

    public PersonID(String s) {
        //Reformatar a string
        s = s.replace("\n", " ");

        for (String field : s.split(" ")) {
            String key = field.split(":")[0];
            String value = field.split(":")[1];
            fieldMap.put(key, value);
        }
    }

    public static boolean verifyHCL(String string) {
        String pattern = "^#[0-9a-fA-F]{6}$";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(string);

        return matcher.matches();
    }

    boolean isIDValidPart1() {
        return fieldMap.entrySet().stream()
                .filter(e -> !e.getKey().equals("cid"))
                .count() == 7;
    }

    boolean isIDValidPart2() {
        return fieldMap.entrySet().stream()
                .filter(e -> !e.getKey().equals("cid") && validadeField(e.getKey(), e.getValue()))
                .count() == 7;
    }

    boolean validadeField(String key, String value) {
        switch (key) {
            case "byr" -> {
                int val = Integer.parseInt(value);
                return val >= 1920 && val <= 2002;
            }
            case "iyr" -> {
                int val = Integer.parseInt(value);
                return val >= 2010 && val <= 2020;
            }
            case "eyr" -> {
                int val = Integer.parseInt(value);
                return val >= 2020 && val <= 2030;
            }
            case "hgt" -> {
                if (value.contains("cm")) {
                    String temp = value.replace("cm", "");
                    int val = Integer.parseInt(temp);
                    return val >= 150 && val <= 193;
                } else if (value.contains("in")) {
                    String temp = value.replace("in", "");
                    int val = Integer.parseInt(temp);
                    return val >= 59 && val <= 76;
                } else return false;
            }
            case "hcl" -> {
                return verifyHCL(value);
            }
            case "ecl" -> {
                List<String> possibleValues = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
                return possibleValues.contains(value);
            }
            case "pid" -> {
                return value.chars().map(c -> (char) c)
                        .filter(Character::isDigit)
                        .count() == 9;
            }
            default -> {
                return true;
            }
        }
    }
}
