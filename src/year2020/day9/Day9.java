package year2020.day9;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day9 extends Day {
    XMASDecoder xmasDecoder;

    public Day9(int year) throws IOException {
        super(year);
        xmasDecoder = new XMASDecoder(Files.readString(Path.of(getFile())).lines().map(Long::parseLong).toList());
    }

    @Override
    public String getPart1() {
        return String.valueOf(xmasDecoder.findFirstInvalidNumber());
    }

    @Override
    public String getPart2() {
        return String.valueOf(xmasDecoder.getEncryptionWeakness());
    }

    @Override
    public int getDay() {
        return 9;
    }
}
