package year2024.day5;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SafetyManual {
    private final static Pattern ORDERING_RULE_PATTERN = Pattern.compile("(?<pageBefore>\\d+)\\|(?<pageAfter>\\d+)");
    private final Map<Integer, OrderingRule> pageOrderMap = new HashMap<>();
    private final List<List<Integer>> pageUpdates;

    public SafetyManual(String input) {
        String[] sections = input.split("\n\n");
        parsePageOrderRules(sections[0]);
        pageUpdates = parsePageUpdates(sections[1]);
    }

    private void parsePageOrderRules(String inputPart) {
        inputPart.lines().forEach(this::parsePageOrderRuleLine);
    }

    private void parsePageOrderRuleLine(String line) {
        Matcher matcher = ORDERING_RULE_PATTERN.matcher(line);
        if (matcher.matches()) {
            int pageBefore = Integer.parseInt(matcher.group("pageBefore"));
            int pageAfter = Integer.parseInt(matcher.group("pageAfter"));
            pageOrderMap.put(pageBefore,pageOrderMap.computeIfAbsent(pageBefore, OrderingRule::new).withPageAfter(pageAfter));
        }
    }

    private List<List<Integer>> parsePageUpdates(String inputPart) {
        return inputPart.lines()
                .map(this::parsePageUpdateLine)
                .toList();
    }

    private List<Integer> parsePageUpdateLine(String line) {
        return Arrays.stream(line.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    private boolean isUpdateValid(List<Integer> line) {
        Set<Integer> seen = new HashSet<>();
        return line.stream().allMatch(currentValue -> {
            OrderingRule currentRule = pageOrderMap.get(currentValue);
            seen.add(currentValue);
            return currentRule.pagesAfter().stream().noneMatch(seen::contains);
        });
    }


    public int getPart1() {
        return pageUpdates.stream()
                .filter(this::isUpdateValid)
                .mapToInt(this::getMiddlePageNumber)
                .sum();
    }

    public int getPart2() {
        return pageUpdates.stream()
                .filter(update -> !isUpdateValid(update))
                .map(this::sortUpdateAccordingToRules)
                .mapToInt(this::getMiddlePageNumber)
                .sum();
    }

    private int getMiddlePageNumber(List<Integer> update) {
        return update.get(update.size()/2);
    }

    private List<Integer> sortUpdateAccordingToRules(List<Integer> update) {
        return update.stream()
                .map(pageOrderMap::get)
                .sorted()
                .map(OrderingRule::pageNumber)
                .toList();
    }
}
