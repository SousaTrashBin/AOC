package day9;

import utils.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day9 extends Day {
    XMASDecoder xmasDecoder;

    public Day9() throws IOException {
        super(9);
        xmasDecoder = new XMASDecoder(Files.readString(Path.of(getFile())).lines().map(Long::parseLong).toList());
    }

    @Override
    public String Part1() {
        return String.valueOf(xmasDecoder.findFirstInvalidNumber());
    }

    @Override
    public String Part2() {
        return String.valueOf(xmasDecoder.getEncryptionWeakness());
    }
}
