package tictactoe;

import java.util.Arrays;
import java.util.Scanner;


public class Main {

    private final static int LENGTH = 3;
    private static char[][] gameBoard;
    private final static String LINE = "---------";
    private static final Scanner sc = new Scanner(System.in);
    private static final int[] coordinatesArray = new int[2];
    private static int rowCoordinate;
    private static int colCoordinate;
    private static boolean isGameOver;

    public static void main(String[] args) {
        createEmptyArray();
        printGameBoard(gameBoard);
        playGame();
    }

    public static void createEmptyArray() {
        char[][] emptyBoard = new char[LENGTH][LENGTH];
        for (char[] emptyBoardRow : emptyBoard) {
            Arrays.fill(emptyBoardRow, '_');
        }
        gameBoard = emptyBoard;
    }

    public static void printGameBoard(char[][] twoDArray) {
        System.out.println(LINE);
        for (char[] array : twoDArray) {
            System.out.print("|");
            for (char character : array) {
                System.out.printf(" %c", character);
            }
            System.out.printf(" |%n");
        }
        System.out.println(LINE);
    }

    public static void checkReturnCoordinates() {
        boolean isValidSize = false;
        boolean isValidInput = false;
        do {
            System.out.print("Enter coordinates: ");
            String input = sc.nextLine();
            String [] inputCoordinates = input.split(" ");
            if (inputCoordinates.length != coordinatesArray.length) {
                System.out.println("You should enter numbers!");
            } else {
                for (int i = 0; i < coordinatesArray.length; i++) {
                    try {
                        coordinatesArray[i] = Integer.parseInt(inputCoordinates[i]);
                    } catch (NumberFormatException nfe) {
                        System.out.println("You should enter numbers!");
                        break;
                    }
                    if (coordinatesArray[i] <= 0 || coordinatesArray[i] > LENGTH) {
                        System.out.printf("Coordinates should be from 1 to %d!%n", LENGTH);
                        isValidInput = false;
                        break;
                    } else {
                        isValidInput = true;
                    }
                }
                isValidSize = true;
            }
        } while (!isValidSize || !isValidInput);
        rowCoordinate = coordinatesArray[0] - 1;
        colCoordinate = coordinatesArray[1] - 1;
    }


    public static void checkCellOccupied(char shape) {
        boolean isOccupied;
        do {
            if (gameBoard[rowCoordinate][colCoordinate] == '_' || gameBoard[rowCoordinate][colCoordinate] == ' ') {
                gameBoard[rowCoordinate][colCoordinate] = shape;
                isOccupied = false;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
                isOccupied = true;
                checkReturnCoordinates();
            }
        } while (isOccupied);
    }

    public static void checkWhoWins(char[][] charArray) {

        boolean isXWinner = false;
        boolean isOWinner = false;
        boolean isEmptyCell = false;
        int winXCounter = 0;
        int winOCounter = 0;
        int length = charArray.length;
        char[] rightDiagonal = new char[length];
        char[] leftDiagonal = new char[length];
        char[] vertical = new char[length];
        char[] arrayX = new char[length];
        Arrays.fill(arrayX, 'X');
        char[] arrayO = new char[length];
        Arrays.fill(arrayO, 'O');

        for (int i = 0; i < charArray.length; i++) {

            if (Arrays.equals(charArray[i], arrayO)) {
                isOWinner = true;
                winOCounter++;
            } else if (Arrays.equals(charArray[i], arrayX)) {
                isXWinner = true;
                winXCounter++;
            }
            for (int j = 0; j < charArray[i].length; j++) {
                vertical[j] = charArray[j][i];
                if (i == j) {
                    rightDiagonal[j] = charArray[i][j];
                }if (j == charArray[i].length - 1 - i) {
                    leftDiagonal[j] = charArray[i][j];
                }
                if (charArray[i][j] == '_' || charArray[i][j] == ' ') {
                    isEmptyCell = true;
                }
            }
            if (Arrays.equals(vertical, arrayO)) {
                isOWinner = true;
                winOCounter++;
            } else if (Arrays.equals(vertical, arrayX)) {
                isXWinner = true;
                winXCounter++;
            }
            if (Arrays.equals(rightDiagonal, arrayO) || Arrays.equals(leftDiagonal, arrayO)) {
                isOWinner = true;
                winOCounter++;
            } else if (Arrays.equals(rightDiagonal, arrayX) || Arrays.equals(leftDiagonal, arrayX)) {
                isXWinner = true;
                winXCounter++;
            }
        }
      if ((winXCounter == 0 && winOCounter == 0) && isEmptyCell) {
          //TODO
          checkReturnCoordinates();
          //checkCellOccupied();
        } else if (winXCounter == 0 && winOCounter == 0) {
            System.out.println("Draw");
            isGameOver = true;
        } else if (isOWinner) {
            System.out.println("O wins");
            isGameOver = true;
        } else if (isXWinner) {
            System.out.println("X wins");
            isGameOver = true;
        }
    }

    public static void playGame() {

        int turnCounter = 1;
        char shape;
        checkReturnCoordinates();
        do {

            turnCounter++;
            shape = turnCounter % 2 == 0 ? 'X' : 'O';
            switch(shape) {
                case 'X':
                    checkCellOccupied('X');
                    break;
                case 'O':
                    checkCellOccupied('O');
                    break;
            }
            printGameBoard(gameBoard);
            checkWhoWins(gameBoard);
        } while (!isGameOver);
    }
}


