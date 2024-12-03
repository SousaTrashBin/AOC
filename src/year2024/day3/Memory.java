package year2024.day3;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Gatherer;

sealed interface Operation permits Multiplication, StartCounting, StopCounting {
    default int evaluate(){
        return 0;
    }
}

record Multiplication(int firstNumber, int secondNumber) implements Operation {
    public int evaluate() {
        return firstNumber * secondNumber;
    }
}

record StartCounting() implements Operation {
}

record StopCounting() implements Operation {
}

public class Memory {
    private static final Pattern pattern = Pattern.compile("mul\\((?<firstNumber>\\d+),(?<secondNumber>\\d+)\\)|do\\(\\)|don't\\(\\)");
    private final List<Operation> operations;

    public Memory(String input) {
        Matcher matcher = pattern.matcher(input);
        operations = matcher.results()
                .map(this::parseOperation)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<Operation> parseOperation(MatchResult matchResult) {
        return switch (matchResult.group()){
            case String s when s.startsWith("mul") -> {
                int firstNumber = Integer.parseInt(matchResult.group("firstNumber"));
                int secondNumber = Integer.parseInt(matchResult.group("secondNumber"));
                yield Optional.of(new Multiplication(firstNumber, secondNumber));
            }
            case "do()" -> Optional.of(new StartCounting());
            case "don't()" -> Optional.of(new StopCounting());
            default -> Optional.empty();
        };
    }

    public int getPart1(){
        return operations.stream()
                .mapToInt(Operation::evaluate)
                .sum();
    }

    public int getPart2(){
        return operations.stream().<Multiplication>gather(Gatherer.ofSequential(
                () -> new AtomicBoolean(true),
                (state,element,downstream) -> {
                    if (downstream.isRejecting()){
                        return false;
                    }
                    switch (element) {
                        case StartCounting _ -> state.getAndSet(true);
                        case StopCounting _ -> state.getAndSet(false);
                        case Multiplication multiplication when state.get() -> downstream.push(multiplication);
                        case Multiplication _ -> {}
                    }
                    return true;
                }
        )).mapToInt(Multiplication::evaluate).sum();
    }
}
