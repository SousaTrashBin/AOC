package year2024.day7;

import java.util.List;

public class EquationCatalog {
    private final List<Equation> equations;

    public EquationCatalog(String input) {
        equations = input.lines()
                .map(Equation::new)
                .toList();
    }

    public long getValidEquationCountPart1() {
        return equations.stream()
                .parallel()
                .filter(Equation::isValidPart1)
                .mapToLong(Equation::getResult)
                .sum();
    }

    public long getValidEquationCountPart2() {
        return equations.stream()
                .parallel()
                .filter(Equation::isValidPart2)
                .mapToLong(Equation::getResult)
                .sum();
    }
}
