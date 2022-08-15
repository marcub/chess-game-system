package chess;

import boardgame.Board;
import boardgame.BoardException;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    /*Método responsável por transformar a matriz Piece em ChessPiece,
    pois o ChessLayer não deve ter acesso ao BoardLayer.*/
    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrizAux = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i=0; i<board.getRows(); i++) {
            for (int f=0; f<board.getColumns(); f++){
                matrizAux[i][f] = (ChessPiece) board.piece(i,f); //Downcasting de Piece para ChessPiece
            }
        }
        return matrizAux;
    }

    private void placeNewPiece (char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece('b', 6, new Rook(board, Color.WHITE));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
    }
}
