package year2024.day9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

sealed interface MemoryBlock permits FilledBlock, FreeBlock {
    int getID();
    boolean isEmpty();
}

record FilledBlock(int id) implements MemoryBlock {
    @Override
    public int getID() {
        return id;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

record FreeBlock() implements MemoryBlock {
    @Override
    public int getID() {
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}

sealed interface CompressedMemoryBlock permits CompressedFilledBlock, CompressedEmptyBlock {
    int getID();
    boolean isEmpty();
    int getSize();
    long getChecksum();
}

record CompressedFilledBlock(int start, int id, int size) implements CompressedMemoryBlock {
    @Override
    public int getID() {
        return id;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public long getChecksum() {
        return IntStream.range(0, size)
                .mapToLong(currIdx -> (long) (start + currIdx) * id).sum();
    }

    public int getStart() {
        return start;
    }
}

record CompressedEmptyBlock(int start, int size) implements CompressedMemoryBlock {
    @Override
    public int getID() {
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public long getChecksum() {
        return 0L;
    }

    public int getStart() {
        return start;
    }
}

public class DiskMap {
    private final List<MemoryBlock> memoryBlocks = new ArrayList<>();
    private final List<CompressedMemoryBlock> compressedMemoryBlocks = new ArrayList<>();

    public DiskMap(String input) {
        int currentID = 0;
        int currentPosition = 0;
        for (int i = 0; i < input.length(); i++) {
            int blockLength = Integer.parseInt(String.valueOf(input.charAt(i)));
            if (i % 2 == 0) {
                int filledBlockID = currentID;
                IntStream.range(0, blockLength).forEach(idx -> memoryBlocks.add(new FilledBlock(filledBlockID)));
                compressedMemoryBlocks.add(new CompressedFilledBlock(currentPosition, filledBlockID, blockLength));
                currentID++;
            } else {
                IntStream.range(0, blockLength).forEach(idx -> memoryBlocks.add(new FreeBlock()));
                compressedMemoryBlocks.add(new CompressedEmptyBlock(currentPosition, blockLength));
            }
            currentPosition += blockLength;
        }
    }

    public long getPart1() {
        record IndexedMemoryBlock(int index, MemoryBlock block) {}
        List<MemoryBlock> compactedFiles = getCompactedFiles();
        return IntStream.range(0, compactedFiles.size())
                .mapToObj(index -> new IndexedMemoryBlock(index, compactedFiles.get(index)))
                .filter(indexedBlock -> !indexedBlock.block.isEmpty())
                .mapToLong(indexedBlock -> (long) indexedBlock.index * indexedBlock.block.getID())
                .sum();
    }

    private List<MemoryBlock> getCompactedFiles() {
        List<MemoryBlock> blocks = new ArrayList<>(memoryBlocks);
        for (int low = 0, high = blocks.size() - 1; low < high; low++, high--) {
            low = findNextEmptyBlock(blocks, low);
            high = findPreviousFilledBlock(blocks, high);

            if (low >= high) break;

            swapBlocks(blocks, low, high);
        }
        return blocks;
    }

    private int findNextEmptyBlock(List<MemoryBlock> blocks, int startIndex) {
        while (startIndex <= blocks.size() && !blocks.get(startIndex).isEmpty()) {
            startIndex++;
        }
        return startIndex;
    }

    private int findPreviousFilledBlock(List<MemoryBlock> blocks, int startIndex) {
        while (startIndex >= 0 && blocks.get(startIndex).isEmpty()) {
            startIndex--;
        }
        return startIndex;
    }

    private void swapBlocks(List<MemoryBlock> blocks, int emptyIndex, int filledIndex) {
        blocks.set(emptyIndex, blocks.get(filledIndex));
        blocks.set(filledIndex, new FreeBlock());
    }

    public long getPart2() {
        List<CompressedFilledBlock> sortedFilledBlocks = compressedMemoryBlocks.stream()
                .filter(block -> block instanceof CompressedFilledBlock)
                .map(block -> (CompressedFilledBlock) block)
                .sorted(Comparator.comparingInt(CompressedMemoryBlock::getID).reversed())
                .toList();

        List<CompressedEmptyBlock> emptyBlocks = compressedMemoryBlocks.stream()
                .filter(block -> block instanceof CompressedEmptyBlock)
                .map(block -> (CompressedEmptyBlock) block)
                .collect(Collectors.toList());

        return sortedFilledBlocks.stream()
                .map(filledBlock -> reallocateFilledBlock(filledBlock, emptyBlocks))
                .mapToLong(CompressedMemoryBlock::getChecksum)
                .sum();
    }

    private static CompressedFilledBlock reallocateFilledBlock(CompressedFilledBlock filledBlock, List<CompressedEmptyBlock> emptyBlocks) {
        int filledBlockSize = filledBlock.getSize();
        int filledBlockStart = filledBlock.getStart();

        for (CompressedEmptyBlock emptyBlock : emptyBlocks) {
            int emptyBlockStart = emptyBlock.getStart();
            int emptyBlockSize = emptyBlock.getSize();
            if (emptyBlockStart >= filledBlockStart) {
                break;
            }
            if (filledBlockSize <= emptyBlockSize) {
                CompressedFilledBlock newFilledBlock = new CompressedFilledBlock(emptyBlockStart, filledBlock.getID(), filledBlockSize);
                if (filledBlockSize == emptyBlockSize) {
                    emptyBlocks.remove(emptyBlock);
                } else {
                    emptyBlocks.set(emptyBlocks.indexOf(emptyBlock), new CompressedEmptyBlock(emptyBlockStart + filledBlockSize, emptyBlockSize - filledBlockSize));
                }
                return newFilledBlock;
            }
        }
        return filledBlock;
    }

    @Override
    public String toString() {
        return memoryBlocks.toString();
    }
}