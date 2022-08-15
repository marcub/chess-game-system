package chess;

import boardgame.Position;

public class ChessPosition {

    private Character column;
    private Integer row;

    public ChessPosition(char column, Integer row) {
        if (column < 'a' || column > 'g' || row < 1 || row > 8) {
            throw new ChessException("Error in instantiating ChessPosition, put a valid position.");
        }
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }


    public Integer getRow() {
        return row;
    }

    public Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char) ('a' - position.getColumn()), 8 - position.getRow());
    }

    public String toString() {
        return "" + column + row;
    }

}
