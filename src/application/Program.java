package application;

import chess.ChessMatch;
import chess.ChessPosition;
import chessConsole.UI;

public class Program {
    public static void main(String[] args) {

        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());

        ChessPosition chessPosition = new ChessPosition('d', 2);
        System.out.print("\n" + chessPosition.toPosition());
    }
}
