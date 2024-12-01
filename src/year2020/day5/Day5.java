package year2020.day5;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class Day5 extends Day {
    private final List<Seat> seatList;

    public Day5(int year) throws IOException {
        super(year);
        seatList = Files.readString(Path.of(getFile()))
                .lines()
                .map(Seat::new)
                .sorted(Comparator.comparingInt(Seat::getSeatId))
                .toList();
    }

    @Override
    public String getPart1() {
        return String.valueOf(seatList.getLast().getSeatId());
    }

    @Override
    public String getPart2() {
        return String.valueOf(getMissingSeat());
    }

    @Override
    public int getDay() {
        return 5;
    }

    public int getMissingSeat() {
        for (int i = 0; i < seatList.size() - 1; i++) {
            //all seats should be one after another, if there's one missing, return it
            if (seatList.get(i).getSeatId() != seatList.get(i + 1).getSeatId() - 1)
                return seatList.get(i).getSeatId() + 1;
        }
        return -1;
    }
}
