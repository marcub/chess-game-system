package application;

import boardgame.Position;
import chess.ChessMatch;
import chessConsole.UI;

public class Program {
    public static void main(String[] args) {

        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}
