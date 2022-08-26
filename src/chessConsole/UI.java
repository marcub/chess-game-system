package chessConsole;

import boardgame.Piece;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedPieces) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(capturedPieces);
        System.out.println();
        System.out.printf("Turn : %d\n", chessMatch.getTurn());
        System.out.printf("Waiting player : %s\n", chessMatch.getCurrentPlayer());
        if (chessMatch.getCheck()) {
            System.out.println("CHECK!");
        }
    }
    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i<pieces.length; i++) {
            System.out.print((pieces.length-i) + " ");
            for (int f = 0; f<pieces.length; f++) {
                printPiece(pieces[i][f], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i<pieces.length; i++) {
            System.out.print((pieces.length-i) + " ");
            for (int f = 0; f<pieces.length; f++) {
                printPiece(pieces[i][f], possibleMoves[i][f]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_RED_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    public static void printCapturedPieces(ChessMatch chessMatch) {
        System.out.println("Captured pieces:");
    }

    public static ChessPosition readChessPosition(Scanner input) {
        try {
            String chessPosition = input.nextLine();
            char chessPositionColumn = chessPosition.charAt(0);
            int chessPositionRow = Integer.parseInt(chessPosition.substring(1));
            return new ChessPosition(chessPositionColumn, chessPositionRow);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPosition. Put a valid value from a1 to h8.");
        }
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> whiteCaptured = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> blackCaptured = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());

        System.out.println("Captured pieces:");
        System.out.print(ANSI_WHITE);
        System.out.println("White: " + whiteCaptured);
        System.out.print(ANSI_RESET);
        System.out.print(ANSI_YELLOW);
        System.out.println("Black: " + blackCaptured);
        System.out.print(ANSI_RESET);
    }
}
