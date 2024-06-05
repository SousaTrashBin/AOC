package Day9;

import java.util.*;

public class XMASDecoder {

    private static final int PREAMBLE_SIZE = 25;
    private final Deque<Long> preamble;
    private final List<Long> numbers;
    private long finalValue;

    public XMASDecoder(List<Long> numbers) {
        preamble = new ArrayDeque<>(PREAMBLE_SIZE);
        this.numbers = numbers;
    }

    private boolean isValid(long number) {
        Set<Long> set = new HashSet<>(preamble);
        for (long num1 : preamble) {
            long num2 = number - num1;
            if (set.contains(num2) && num1 != num2)
                return true;
        }
        return false;
    }

    public long findFirstInvalidNumber() {
        for (long number : numbers) {
            if (preamble.size() == PREAMBLE_SIZE && !isValid(number)) {
                finalValue = number;
                return number;
            }
            if (preamble.size() == PREAMBLE_SIZE)
                preamble.poll();
            preamble.offer(number);
        }
        throw new IllegalArgumentException("No invalid number found");
    }

    public List<Long> findContiguousSet() {
        for (int start = 0; start < numbers.size(); start++) {
            long sum = numbers.get(start);
            for (int end = start + 1; end < numbers.size(); end++) {
                sum += numbers.get(end);
                if (sum == finalValue)
                    return numbers.subList(start, end + 1);
                if (sum > finalValue) break;
            }
        }
        throw new RuntimeException("No contiguous Set found!");
    }

    public long getEncryptionWeakness() {
        List<Long> firstAndLast = findContiguousSet();
        long first = firstAndLast.stream().min(Long::compareTo).orElseGet(() -> (long) -1);
        long last = firstAndLast.stream().max(Long::compareTo).orElseGet(() -> 0L);
        return first + last;
    }
}
