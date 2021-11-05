package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Grader {
    private int[] secretCode;
    private int secretCodeLength;

    public Grader(int secretCodeLength) {
        this.secretCodeLength = secretCodeLength;
    }

    public long getSecretCode() {
        return createNumberfromArray(this.secretCode);
    }

    private int[] createArrayFromNumber(long number) {
        char[] numberAsCharArray = String.valueOf(number).toCharArray();
        int[] intArray = new int[numberAsCharArray.length];
        for (int i = 0; i < numberAsCharArray.length; i++) {
            intArray[i] = Character.getNumericValue(numberAsCharArray[i]);
        }
        return intArray;
    }

    private long createNumberfromArray(int[] number) {
        StringBuilder s = new StringBuilder();
        for (int i: number)
        {
            s.append(i);
        }
        return Long.parseLong(s.toString());
    }

    private boolean isBull(int number, int index) {
        return (number == this.secretCode[index]);
    }

    private boolean isCow(int number, int index, int length) {
        for (int i = 0; i < length; i++) {
            if (i != index && number == secretCode[i]) {
                return true;
            }
        }
        return false;
    }


    public void createSecretCode() {
        List<Integer> randomList = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(randomList);
        while (randomList.get(0) == 0) {
            Collections.shuffle(randomList);
        }
        this.secretCode = new int[secretCodeLength];
        for (int i = 0; i < secretCodeLength; i++) {
            this.secretCode[i] = randomList.get(i);
        }
    }

    public int[] grade(int guessNumber) {
        int[] bullsCows = new int[2];
        int[] guessNumberArray = createArrayFromNumber(guessNumber);
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
        int secretCodeLength = scanner.nextInt();
        if (secretCodeLength > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + secretCodeLength + " because there aren't enough unique digits.");
        } else {
            Grader grader = new Grader(secretCodeLength);
            grader.createSecretCode();
            System.out.println("The random secret number is " + grader.getSecretCode() + ".");
        }
    }
}
