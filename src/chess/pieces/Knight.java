package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matPossibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxPosition = new Position(0,0);

        //Above
        auxPosition.setValues(position.getRow() - 2, position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() - 2, position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Below
        auxPosition.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() + 2, position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Right
        auxPosition.setValues(position.getRow() - 1, position.getColumn() + 2);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() + 1, position.getColumn() + 2);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Left

        auxPosition.setValues(position.getRow() + 1, position.getColumn() - 2);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() - 1, position.getColumn() - 2);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        return matPossibleMoves;
    }

    private boolean canMove (Position position) {
        ChessPiece auxPiece = (ChessPiece) getBoard().piece(position);
        return auxPiece == null || auxPiece.getColor() != getColor();
    }
}