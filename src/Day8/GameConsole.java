package Day8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class GameConsole {
    private final List<String> initialInstructionList;
    private Set<Integer> visitedLines = new HashSet<>();
    private int acc = 0;
    private int line = 0;

    public GameConsole(List<String> instructionList) {
        initialInstructionList = instructionList;
    }

    public boolean processInstruction(List<String> instructionList) {
        String instruction = instructionList.get(line);
        String com = instruction.split(" ")[0];
        int value = Integer.parseInt(instruction.split(" ")[1]);
        switch (com) {
            case ("acc") -> {
                if (!visitedLines.add(line)) return false;
                acc += value;
                line++;
            }
            case ("jmp") -> {
                if (!visitedLines.add(line)) return false;
                line += value;
            }
            case ("nop") -> {
                if (!visitedLines.add(line)) return false;
                line++;
            }
        }
        return true;
    }

    public int findLoop() {
        if (!runProgram(initialInstructionList)) return acc;
        resetGame();
        return -1;
    }

    private void resetGame() {
        acc = 0;
        line = 0;
        visitedLines = new HashSet<>();
    }

    public int findFix() {
        for (Integer nop : find("nop")) {
            List<String> instructionList = new ArrayList<>(initialInstructionList);
            String instruction = instructionList.get(nop);
            instructionList.set(nop, instruction.replace("nop", "jmp"));
            if (runProgram(instructionList)) {
                return acc;
            }
            resetGame();
        }
        for (Integer jmp : find("jmp")) {
            List<String> instructionList = new ArrayList<>(initialInstructionList);
            String instruction = instructionList.get(jmp);
            instructionList.set(jmp, instruction.replace("jmp", "nop"));
            if (runProgram(instructionList)) {
                return acc;
            }
            resetGame();
        }
        return -1;
    }

    private boolean runProgram(List<String> instructionList) {
        while (line < instructionList.size())
            if (!processInstruction(instructionList))
                return false;
        return true;
    }

    private List<Integer> find(String com) {
        return IntStream.range(0, initialInstructionList.size())
                .filter(i -> initialInstructionList.get(i).startsWith(com))
                .boxed()
                .toList();
    }
}
