package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

import java.util.Objects;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean [][] matPossibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxPosition = new Position(0, 0);

        //Above
        auxPosition.setValues(position.getRow() - 1, position.getColumn());
        while(getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setRow(auxPosition.getRow() - 1);
        }
        if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Below
        auxPosition.setValues(position.getRow() + 1, position.getColumn());
        while(getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setRow(auxPosition.getRow() + 1);
        }
        if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Left
        auxPosition.setValues(position.getRow(), position.getColumn() - 1);
        while(getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setColumn(auxPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Right
        auxPosition.setValues(position.getRow(), position.getColumn() + 1);
        while(getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setColumn(auxPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        return matPossibleMoves;
    }
}
