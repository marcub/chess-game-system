package boardgame;

import chess.ChessPiece;

public abstract class Piece {

    protected Position position;
    private Board board;


    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        for (boolean[] x : possibleMoves()) {
            for (boolean y : x) {
                if (y) {
                    return true;
                }
            }
        }
        return false;
    }

}
