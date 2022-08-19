package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chessConsole.UI;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());

        System.out.print("Source: ");
        ChessPosition sourcePosition = UI.readChessPosition(input);
        System.out.print("Target: ");
        ChessPosition targetPosition = UI.readChessPosition(input);
        ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, targetPosition);
        System.out.print(capturedPiece + "\n");

        UI.printBoard(chessMatch.getPieces());

        input.close();

    }
}
