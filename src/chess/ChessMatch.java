package chess;

import boardgame.Board;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
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
}
