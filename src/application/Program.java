package application;

import chess.*;
import chessConsole.UI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> listCapturedPieces = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {

            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, listCapturedPieces);

                System.out.print("Source: ");
                ChessPosition sourcePosition = UI.readChessPosition(input);

                boolean[][] possibleMoves = chessMatch.possibleMoves(sourcePosition);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.print("Target: ");
                ChessPosition targetPosition = UI.readChessPosition(input);

                ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, targetPosition);
                if (capturedPiece != null) {
                    listCapturedPieces.add(capturedPiece);
                }

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
                    String pieceChoice = input.nextLine().toUpperCase();
                    while (!pieceChoice.equals("B") && !pieceChoice.equals("N") && !pieceChoice.equals("R") && !pieceChoice.equals("Q")) {
                        System.out.print("Invalid piece! Enter piece for promotion (B/N/R/Q): ");
                        pieceChoice = input.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(pieceChoice);
                }
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

        UI.clearScreen();
        UI.printMatch(chessMatch, listCapturedPieces);
        //input.close();
    }
}
