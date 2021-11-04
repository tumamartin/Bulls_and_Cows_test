package com.company;

import java.util.Scanner;

class Grader {
    private int secretCodePrimitive;
    private int[] secretCode;

    public Grader(int secretCode) {
        this.secretCodePrimitive = secretCode;
        this.secretCode = createArrayFromNumber(secretCode);
    }

    private int[] createArrayFromNumber(int number) {
        char[] numberAsCharArray = String.valueOf(number).toCharArray();
        int[] intArray = new int[numberAsCharArray.length];
        for (int i = 0; i < numberAsCharArray.length; i++) {
            intArray[i] = Character.getNumericValue(numberAsCharArray[i]);
        }
        return intArray;
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
            System.out.println("Grade: " + bullsCows[0] + " bull(s) and " + bullsCows[1] + " cow(s). The secret code is " + secretCodePrimitive + ".");
        } else if (bullsCows[0] > 0) {
            System.out.println("Grade: " + bullsCows[0] + " bull(s). The secret code is " + secretCodePrimitive + ".");
        } else if (bullsCows[1] > 0) {
            System.out.println("Grade: " + bullsCows[1] + " cow(s). The secret code is " + secretCodePrimitive + ".");
        } else {
            System.out.println("Grade: none. The secret code is " + secretCodePrimitive + ".");
        }
    }
}


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int guessNumber = scanner.nextInt();
	    Grader grader = new Grader(9305);
        grader.print(grader.grade(guessNumber));
    }
}
