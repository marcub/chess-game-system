package chess.pieces;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Objects;

public class King extends ChessPiece {

    private ChessMatch chessMatch;
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matPossibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position auxPosition = new Position(0, 0);

        auxPosition.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() + 1 , position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() , position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        auxPosition.setValues(position.getRow() - 1 , position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Special Move - Castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            //NearHook
            Position nearHook = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(nearHook)) {
                Position position1 = new Position(position.getRow(), position.getColumn() + 1);
                Position position2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null) {
                    matPossibleMoves[position2.getRow()][position2.getColumn()] = true;
                }
            }
            //FarHook
            Position farHook = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(farHook)) {
                Position position1 = new Position(position.getRow(), position.getColumn() - 1);
                Position position2 = new Position(position.getRow(), position.getColumn() - 2);
                Position position3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null && getBoard().piece(position3) == null) {
                    matPossibleMoves[position2.getRow()][position2.getColumn()] = true;
                }
            }
        }

        return matPossibleMoves;
    }

    private boolean canMove (Position position) {
        ChessPiece auxPiece = (ChessPiece) getBoard().piece(position);
        return auxPiece == null || auxPiece.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece hook = (ChessPiece) getBoard().piece(position);
        return hook != null && hook instanceof Rook && hook.getMoveCount() == 0 && hook.getColor() == getColor();
    }
}
