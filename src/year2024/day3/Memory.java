package year2024.day3;

import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Gatherer;

sealed interface Operation permits Multiplication, StartCounting, StopCounting {
}

record Multiplication(int first, int second) implements Operation {
    public int evaluate() {
        return first * second;
    }
}

record StartCounting() implements Operation {
}

record StopCounting() implements Operation {
}

public class Memory {
    private static final Pattern OPERATION_PATTERN = Pattern.compile("mul\\((?<first>\\d+),(?<second>\\d+)\\)|do\\(\\)|don't\\(\\)");
    private final List<Operation> operations;

    public Memory(String input) {
        Matcher matcher = OPERATION_PATTERN.matcher(input);
        operations = matcher.results()
                .map(this::parseOperation)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<Operation> parseOperation(MatchResult matchResult) {
        return switch (matchResult.group()){
            case String multiplication when multiplication.startsWith("mul") -> {
                int first = Integer.parseInt(matchResult.group("first"));
                int second = Integer.parseInt(matchResult.group("second"));
                yield Optional.of(new Multiplication(first, second));
            }
            case "do()" -> Optional.of(new StartCounting());
            case "don't()" -> Optional.of(new StopCounting());
            default -> Optional.empty();
        };
    }

    public int getPart1(){
        return operations.stream()
                .filter(operation -> operation instanceof Multiplication)
                .<Multiplication>mapMulti((operation, downstream) -> {
                    if (operation instanceof Multiplication multiplication) {
                        downstream.accept(multiplication);
                    }
                })
                .mapToInt(Multiplication::evaluate)
                .sum();
    }
    public int getPart2(){
        class CountingState {
            private boolean counting = true;

            public boolean isCounting() {
                return counting;
            }

            public void setCounting(boolean counting) {
                this.counting = counting;
            }
        }

        return operations.stream().<Multiplication>gather(Gatherer.ofSequential(
                CountingState::new,
                (countingState, operation, downstream) -> {
                    if (downstream.isRejecting()){
                        return false;
                    }
                    switch (operation) {
                        case StartCounting _ -> countingState.setCounting(true);
                        case StopCounting _ -> countingState.setCounting(false);
                        case Multiplication multiplication when countingState.isCounting() ->
                                downstream.push(multiplication);
                        case Multiplication _ -> {}
                    }
                    return true;
                }
        )).mapToInt(Multiplication::evaluate).sum();
    }
}
