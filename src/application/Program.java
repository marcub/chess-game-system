package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chessConsole.UI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {

            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces());

                System.out.print("Source: ");
                ChessPosition sourcePosition = UI.readChessPosition(input);

                boolean[][] possibleMoves = chessMatch.possibleMoves(sourcePosition);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.print("Target: ");
                ChessPosition targetPosition = UI.readChessPosition(input);

                ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, targetPosition);
                System.out.print(capturedPiece + "\n");
            }
            catch (ChessException e) {
                System.out.println(e.getMessage());
                input.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                input.nextLine();
            }
        }
        //input.close();
    }
}
