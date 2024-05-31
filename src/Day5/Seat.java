package Day5;

public class Seat {
    int row;
    int column;

    public Seat(String s) {
        char[] charArray = s.toCharArray();
        int rowLow = 0;
        int rowHigh = 127;
        for (int i = 0; i < 7; i++) {
            char direction = charArray[i];
            int middle = (rowLow + rowHigh) / 2;
            switch (direction) {
                case ('F') -> rowHigh = middle;
                case ('B') -> rowLow = middle;
            }
        }
        row = rowHigh;

        int colLow = 0;
        int colHigh = 7;
        for (int i = 7; i < 10; i++) {
            char direction = charArray[i];
            int middle = (colLow + colHigh) / 2;
            switch (direction) {
                case ('R') -> colLow = middle;
                case ('L') -> colHigh = middle;
            }
        }
        column = colHigh;
    }

    int getSeatId() {
        return row * 8 + column;
    }
}
