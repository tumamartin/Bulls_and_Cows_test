package com.company;

import java.util.*;

class Grader {
    private char[] secretCode;
    private int secretCodeLength;

    public Grader(int secretCodeLength) {
        this.secretCodeLength = secretCodeLength;
    }

    public String getSecretCode() {
        return createNumberfromArray(this.secretCode);
    }

    private String createNumberfromArray(char[] number) {
        StringBuilder s = new StringBuilder();
        for (char i: number)
        {
            s.append(i);
        }
        return s.toString();
    }

    private boolean isBull(char character, int index) {
        return (character == this.secretCode[index]);
    }

    private boolean isCow(char character, int index, int length) {
        for (int i = 0; i < length; i++) {
            if (i != index && character == this.secretCode[i]) {
                return true;
            }
        }
        return false;
    }

    private void printStars(int numberOfsymbols) {
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < secretCodeLength; i++) {
            System.out.print("*");
        }
        if (numberOfsymbols < 10) {
            System.out.println(" (0 - " + (char) (numberOfsymbols + 48) + ").");
        } else {
            System.out.println(" (0 - 9, a - " + (char) (numberOfsymbols + 86) + ").");
        }
    }

    private boolean isInSecretCode(char character) {
        for (int i = 0; i < secretCodeLength; i++) {
            if (secretCode[i] == character) {return true;}
        }
        return false;
    }

    public void createSecretCode(int numberOfsymbols) {
        Random random = new Random();
        this.secretCode = new char[secretCodeLength];
        int i = 0;
        while (i < secretCodeLength) {
            int rn = random.nextInt(numberOfsymbols);
            if (rn < 10) {
                rn += 48;
            } else {
                rn += 87;
            }
            if (!isInSecretCode((char) rn)) {
                this.secretCode[i] = (char) rn;
                i++;
            }
        }
        printStars(numberOfsymbols);
    }

    public int[] grade(String guessNumber) {
        int[] bullsCows = new int[2];
        char[] guessNumberArray = guessNumber.toCharArray();
        for (int i = 0; i < guessNumberArray.length; i++) {
            if (isBull(guessNumberArray[i], i)) {
                bullsCows[0]++;
            } else if (isCow(guessNumberArray[i], i, this.secretCode.length)) {
                bullsCows[1]++;
            }
        }
        return bullsCows;
    }

    public void print(int[] bullsCows) {
        if (bullsCows[0] > 0 && bullsCows[1] > 0) {
            System.out.println("Grade: " + bullsCows[0] + " bull(s) and " + bullsCows[1] + " cow(s). The secret code is " + createNumberfromArray(secretCode) + ".");
        } else if (bullsCows[0] > 0) {
            System.out.println("Grade: " + bullsCows[0] + " bull(s). The secret code is " + createNumberfromArray(secretCode) + ".");
        } else if (bullsCows[1] > 0) {
            System.out.println("Grade: " + bullsCows[1] + " cow(s). The secret code is " + createNumberfromArray(secretCode) + ".");
        } else {
            System.out.println("Grade: none. The secret code is " + createNumberfromArray(secretCode) + ".");
        }
    }
}


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        try {
            int secretCodeLength = scanner.nextInt();
            if (secretCodeLength > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else {
                Grader grader = new Grader(secretCodeLength);
                System.out.println("Input the number of possible symbols in the code:");
                int numberOfSymbols = scanner.nextInt();
                if (numberOfSymbols < secretCodeLength | secretCodeLength == 0 | numberOfSymbols > 36) {
                    System.out.println("Error: it's not possible to generate a code with a length of " + secretCodeLength + " with " + numberOfSymbols + " unique symbols.");
                } else {
                    grader.createSecretCode(numberOfSymbols);
                    int i = 1;
                    System.out.println("Okay, let's start a game!");
                    while (true) {
                        System.out.println("Turn " + i + ":");
                        String guessNumber = scanner.next();
                        grader.print(grader.grade(guessNumber));
                        if (grader.grade(guessNumber)[0] == secretCodeLength) {
                            System.out.println("Congratulations! You guessed the secret code.");
                            break;
                        }
                        i++;
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Error: You entered invalid input");
        }
    }
}