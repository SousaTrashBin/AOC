package year2024.day5;

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

public record OrderingRule(int pageNumber, Set<Integer> pagesAfter) implements Comparable<OrderingRule> {
    public OrderingRule {
        pagesAfter = Collections.unmodifiableSet(pagesAfter);
    }

    public OrderingRule(int pageNumber){
        this(pageNumber, Collections.emptySet());
    }

    public boolean happensBeforeThan(int pageNumber) {
        return pagesAfter.contains(pageNumber);
    }

    @Override
    public int compareTo(OrderingRule o) {
        if (this.happensBeforeThan(o.pageNumber)) {
            return -1;
        } else if (o.happensBeforeThan(this.pageNumber)) {
            return 1;
        }
        return 0;
    }

    public OrderingRule withPageAfter(int pageNumber) {
        Set<Integer> updatedPagesAfter = new HashSet<>(this.pagesAfter);
        updatedPagesAfter.add(pageNumber);
        return new OrderingRule(this.pageNumber, updatedPagesAfter);
    }

}

