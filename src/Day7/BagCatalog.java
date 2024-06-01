package Day7;

import AuxiliarClasses.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BagCatalog {
    private final Map<String, List<Pair<Integer, String>>> bagMap = new HashMap<>();

    public void add(String bagString) {
        String bagName = bagString.substring(0, bagString.indexOf("bags"))
                .replace(" ", "");

        String[] bags = bagString.substring(bagString.indexOf("contain") + "contain".length())
                .replace(" ", "").split(",");

        List<Pair<Integer, String>> pairList = new ArrayList<>();
        for (String bag : bags) {
            if (!bag.equals("nootherbags."))
                pairList.add(getPair(bag));
        }
        bagMap.put(bagName, pairList);
    }

    private Pair<Integer, String> getPair(String bag) {
        int value = bag.charAt(0) - '0';
        String bagName = bag.substring(1, bag.indexOf("bag"));
        return new Pair<>(value, bagName);
    }

    public int howManyBagsContain(String bagName) {
        int count = 0;
        for (Map.Entry<String, List<Pair<Integer, String>>> stringListEntry : bagMap.entrySet()) {
            if (howManyBagsContain(bagName, stringListEntry.getKey()) >= 1) count++;
        }
        return count;
    }

    private int howManyBagsContain(String bagName, String currentBag) {
        if (bagMap.get(currentBag) == null || bagMap.get(currentBag).isEmpty()) return 0;
        int sum = 0;
        for (Pair<Integer, String> integerStringPair : bagMap.get(currentBag)) {
            if (integerStringPair.s().equals(bagName)) sum += integerStringPair.f();
            else sum += integerStringPair.f() * howManyBagsContain(bagName, integerStringPair.s());
        }
        return sum;
    }

    public int howManyBagsInside(String currentBag) {
        if (bagMap.get(currentBag).isEmpty()) return 0;
        int sum = 0;
        for (Pair<Integer, String> integerStringPair : bagMap.get(currentBag))
            sum += integerStringPair.f() * (1 + howManyBagsInside(integerStringPair.s()));
        return sum;
    }
}
