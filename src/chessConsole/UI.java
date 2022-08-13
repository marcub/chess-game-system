package chessConsole;

import chess.ChessPiece;

public class UI {

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i<pieces.length; i++) {
            System.out.print((pieces.length-i) + " ");
            for (int f = 0; f<pieces.length; f++) {
                printPiece(pieces[i][f]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printPiece(ChessPiece piece) {
        if (piece == null) {
            System.out.print("-");
        } else {
            System.out.print(piece);
        }
        System.out.print(" ");
    }
}
