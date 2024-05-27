package Day2;

public class Password {
    private final int min;
    private final int max;
    private final char character;
    private final String password;

    public Password(String s) {
        String[] sArray = s.split(" ");
        min = Integer.valueOf(sArray[0].split("-")[0]);
        max = Integer.valueOf(sArray[0].split("-")[1]);
        character = sArray[1].charAt(0);
        password = sArray[2];
    }

    boolean isValidPart1() {
        int charCount = (int) password.chars().filter(c -> c == character).count();
        return charCount >= min && charCount <= max;
    }

    boolean isValidPart2() {
        boolean A = password.charAt(min - 1) == character;
        boolean B = password.charAt(max - 1) == character;

        //Só uma das posições é que pode ter o caracter
        return (A && !B) || (!A && B);
    }

}
