package year2024.day9;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

sealed interface CompressedMemoryBlock permits CompressedFilledBlock, CompressedEmptyBlock {
    int getID();
    boolean isEmpty();
    int getSize();
    long getChecksum();
    int getStart();
}

record CompressedFilledBlock(int start, int id, int size) implements CompressedMemoryBlock {
    @Override
    public int getID() { return id; }
    @Override
    public boolean isEmpty() { return false; }
    @Override
    public int getSize() { return size; }
    @Override
    public long getChecksum() {
        return IntStream.range(0, size)
                .mapToLong(currIdx -> (long) (start + currIdx) * id)
                .sum();
    }
    @Override
    public int getStart() { return start; }
}

record CompressedEmptyBlock(int start, int size) implements CompressedMemoryBlock {
    @Override
    public int getID() { return -1; }
    @Override
    public boolean isEmpty() { return true; }
    @Override
    public int getSize() { return size; }
    @Override
    public long getChecksum() { return 0L; }
    @Override
    public int getStart() { return start; }
}

public class DiskMap {
    private final List<CompressedMemoryBlock> compressedMemoryBlocks = new ArrayList<>();

    public DiskMap(String input) {
        int currentPosition = 0;
        for (int i = 0; i < input.length(); i++) {
            int blockLength = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) {
                compressedMemoryBlocks.add(new CompressedFilledBlock(currentPosition, i/2, blockLength));
            } else {
                compressedMemoryBlocks.add(new CompressedEmptyBlock(currentPosition, blockLength));
            }
            currentPosition += blockLength;
        }
    }

    private List<CompressedFilledBlock> getSortedCopyOfFilledBlocks() {
        return compressedMemoryBlocks.stream()
                .filter(block -> block instanceof CompressedFilledBlock)
                .map(block -> (CompressedFilledBlock) block)
                .sorted(Comparator.comparingInt(CompressedFilledBlock::getStart).reversed())
                .collect(Collectors.toList());
    }

    private List<CompressedEmptyBlock> getCopyOfEmptyBlocks() {
        return compressedMemoryBlocks.stream()
                .filter(block -> block instanceof CompressedEmptyBlock)
                .map(block -> (CompressedEmptyBlock) block)
                .collect(Collectors.toList());
    }

    public long getPart1() {
        List<CompressedFilledBlock> remainingFilledBlocks = getSortedCopyOfFilledBlocks();
        List<CompressedEmptyBlock> emptyBlocks = getCopyOfEmptyBlocks();

        return getCompressedPart1(emptyBlocks, remainingFilledBlocks).stream()
                .mapToLong(CompressedMemoryBlock::getChecksum)
                .sum();
    }

    private static Deque<CompressedFilledBlock> getCompressedPart1(List<CompressedEmptyBlock> emptyBlocks, List<CompressedFilledBlock> remainingFilledBlocks) {
        Deque<CompressedFilledBlock> result = new ArrayDeque<>();

        while (!emptyBlocks.isEmpty() && !remainingFilledBlocks.isEmpty()) {
            CompressedFilledBlock filledBlock = remainingFilledBlocks.getFirst();
            CompressedEmptyBlock emptyBlock = emptyBlocks.getFirst();

            if (filledBlock.getStart() < emptyBlock.getStart()) {
                break;
            }

            CompressedFilledBlock newFilledBlock = processBlock(filledBlock, emptyBlock,emptyBlocks,remainingFilledBlocks);
            result.addLast(newFilledBlock);
        }

        result.addAll(remainingFilledBlocks);
        return result;
    }

    private static CompressedFilledBlock processBlock(CompressedFilledBlock filledBlock,
                                                      CompressedEmptyBlock emptyBlock,
                                                      List<CompressedEmptyBlock> emptyBlocks,
                                                      List<CompressedFilledBlock> remainingFilledBlocks) {
        int filledBlockSize = filledBlock.getSize();
        int emptyBlockSize = emptyBlock.getSize();
        int emptyBlockStart = emptyBlock.getStart();

        if (filledBlockSize > emptyBlockSize) {
            CompressedFilledBlock newFilledBlock = new CompressedFilledBlock(emptyBlockStart, filledBlock.getID(), emptyBlockSize);
            CompressedFilledBlock remainingFilledBlock = new CompressedFilledBlock(filledBlock.getStart(), filledBlock.getID(), filledBlockSize - emptyBlockSize);
            emptyBlocks.removeFirst();
            remainingFilledBlocks.set(0, remainingFilledBlock);
            return newFilledBlock;
        } else {
            CompressedFilledBlock newFilledBlock = new CompressedFilledBlock(emptyBlockStart, filledBlock.getID(), filledBlockSize);
            updateEmptyBlock(emptyBlocks, filledBlockSize, emptyBlockSize, 0, emptyBlockStart);
            remainingFilledBlocks.removeFirst();
            return newFilledBlock;
        }
    }

    public long getPart2() {
        List<CompressedFilledBlock> remainingFilledBlocks = getSortedCopyOfFilledBlocks();
        List<CompressedEmptyBlock> emptyBlocks = getCopyOfEmptyBlocks();

        return getCompressedPart2(remainingFilledBlocks, emptyBlocks).stream()
                .mapToLong(CompressedMemoryBlock::getChecksum)
                .sum();
    }

    private static Deque<CompressedFilledBlock> getCompressedPart2(List<CompressedFilledBlock> remainingFilledBlocks, List<CompressedEmptyBlock> emptyBlocks) {
        Deque<CompressedFilledBlock> result = new ArrayDeque<>();

        for (CompressedFilledBlock filledBlock : remainingFilledBlocks) {
            int filledBlockSize = filledBlock.getSize();
            int filledBlockStart = filledBlock.getStart();
            int i = 0;
            for (; i < emptyBlocks.size(); i++) {
                CompressedEmptyBlock emptyBlock = emptyBlocks.get(i);
                if (emptyBlock.getStart() >= filledBlockStart) {
                    break;
                }
                if (emptyBlock.getSize() >= filledBlockSize) {
                    break;
                }
            }

            if (i == emptyBlocks.size() || emptyBlocks.get(i).getStart() >= filledBlockStart || emptyBlocks.get(i).getSize() < filledBlockSize) {
                result.addLast(filledBlock);
                continue;
            }

            CompressedEmptyBlock emptyBlock = emptyBlocks.get(i);
            int emptyBlockStart = emptyBlock.getStart();
            int emptyBlockSize = emptyBlock.getSize();

            CompressedFilledBlock newFilledBlock = new CompressedFilledBlock(emptyBlockStart, filledBlock.getID(), filledBlockSize);
            updateEmptyBlock(emptyBlocks, filledBlockSize, emptyBlockSize, i, emptyBlockStart);
            result.addLast(newFilledBlock);
        }
        return result;
    }

    private static void updateEmptyBlock(List<CompressedEmptyBlock> emptyBlocks, int filledBlockSize, int emptyBlockSize, int index, int emptyBlockStart) {
        if (filledBlockSize == emptyBlockSize) {
            emptyBlocks.remove(index);
        } else {
            CompressedEmptyBlock newEmptyBlock = new CompressedEmptyBlock(emptyBlockStart + filledBlockSize, emptyBlockSize - filledBlockSize);
            emptyBlocks.set(index, newEmptyBlock);
        }
    }

}