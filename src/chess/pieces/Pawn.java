package chess.pieces;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matPossibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxPosition = new Position(0, 0);

        if (getColor() == Color.WHITE) {
            auxPosition.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
                matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            }
            if (getMoveCount() == 0) {
                auxPosition.setValues(position.getRow() - 2, position.getColumn());
                Position auxPosition2 = new Position(position.getRow() - 1, position.getColumn());
                if (getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition) && getBoard().positionExists(auxPosition2) && !getBoard().thereIsAPiece(auxPosition2)) {
                    matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
                }
            }
            auxPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition)) {
                matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            }
            auxPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition)) {
                matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            }

            //Special Move - En Passant (White)
            if (position.getRow() == 3) {
                Position leftPosition = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(leftPosition) && isThereOpponentPiece(leftPosition) && getBoard().piece(leftPosition) == chessMatch.getEnPassantVulnerable()) {
                    matPossibleMoves[position.getRow() - 1][position.getColumn() - 1] = true;
                }
                Position rightPosition = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(rightPosition) && isThereOpponentPiece(rightPosition) && getBoard().piece(rightPosition) == chessMatch.getEnPassantVulnerable()) {
                    matPossibleMoves[position.getRow() - 1][position.getColumn() + 1] = true;
                }
            }
        }


        if (getColor() == Color.BLACK) {
            auxPosition.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition)) {
                matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            }
            if (getMoveCount() == 0) {
                auxPosition.setValues(position.getRow() + 2, position.getColumn());
                Position auxPosition2 = new Position(position.getRow() + 1, position.getColumn());
                if (getBoard().positionExists(auxPosition) && !getBoard().thereIsAPiece(auxPosition) && getBoard().positionExists(auxPosition2) && !getBoard().thereIsAPiece(auxPosition2)) {
                    matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
                }
            }
            auxPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition)) {
                matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            }
            auxPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(auxPosition) && isThereOpponentPiece(auxPosition)) {
                matPossibleMoves[auxPosition.getRow()][auxPosition.getColumn()] = true;
            }

            //Special Move - En Passant (Black)
            if (position.getRow() == 4) {
                Position leftPosition = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(leftPosition) && isThereOpponentPiece(leftPosition) && getBoard().piece(leftPosition) == chessMatch.getEnPassantVulnerable()) {
                    matPossibleMoves[position.getRow() + 1][position.getColumn() - 1] = true;
                }
                Position rightPosition = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(rightPosition) && isThereOpponentPiece(rightPosition) && getBoard().piece(rightPosition) == chessMatch.getEnPassantVulnerable()) {
                    matPossibleMoves[position.getRow() + 1][position.getColumn() + 1] = true;
                }
            }
        }

        return matPossibleMoves;
    }

    private boolean testEnPassant (ChessPiece enPassantPiece) {
        char enPassantColumn = enPassantPiece.getChessPosition().getColumn();
        char currentPawnColumn = getChessPosition().getColumn();
        return enPassantColumn == currentPawnColumn + 1 || enPassantColumn == currentPawnColumn - 1;
    }
}
