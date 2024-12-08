package year2024.day7;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Equation {
    private final static Pattern EQUATION_PATTERN = Pattern.compile("(?<result>\\d+):(?<numbers>(( \\d+)+))");
    private long result;
    private List<Integer> numbers;
    
    public Equation(String inputLine) {
        parseInputLine(inputLine);
    }

    private void parseInputLine(String inputLine) {
        Matcher matcher = EQUATION_PATTERN.matcher(inputLine);
        if (matcher.find()) {
            result = Long.parseLong(matcher.group("result"));
            numbers = Arrays.stream(matcher.group("numbers").split(" "))
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
    }

    public boolean isValidPart1() {
        List<BiFunction<Long, Integer, Long>> operations = List.of(
                Long::sum,
                (currentValue, nextNumber) -> currentValue * nextNumber
        );
        return isValidAux(1, numbers.getFirst(), operations);
    }

    public boolean isValidPart2() {
        List<BiFunction<Long, Integer, Long>> operations = List.of(
                Long::sum,
                (currentValue, nextNumber) -> currentValue * nextNumber,
                this::concatenateValues
        );
        return isValidAux(1, numbers.getFirst(), operations);
    }

    private boolean isValidAux(int nextIndex, long currentValue, List<BiFunction<Long, Integer, Long>> operations) {
        if (currentValue > result) {
            return false;
        }
        if (nextIndex >= numbers.size()) {
            return currentValue == result;
        }
        return operations.stream()
                .map(operation -> operation.apply(currentValue, numbers.get(nextIndex)))
                .anyMatch(nextValue -> isValidAux(nextIndex + 1, nextValue, operations));

    }

    public long getResult() {
        return result;
    }


    private long concatenateValues(Long currentValue, Integer valueToConcatenate) {
        int numberOfDigits = String.valueOf(valueToConcatenate).length();
        long concatenateFactor = (long) Math.pow(10, numberOfDigits);
        return currentValue * concatenateFactor + valueToConcatenate;
    }
}
