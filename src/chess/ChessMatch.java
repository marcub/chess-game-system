package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Board board;
    private Integer turn;
    private Color currentPlayer;
    private Boolean check;

    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPieces;

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        piecesOnTheBoard = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        check = false;
        initialSetup();
    }

    public Integer getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
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
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check!");
        }
        check = testCheck(opponent(currentPlayer));
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    public Piece makeMove(Position source, Position target) {
        Piece movedPiece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(movedPiece, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void validateSourcePosition(Position source) {
        if (!board.thereIsAPiece(source)) {
            throw new ChessException("There is no piece on source position!");
        }
        ChessPiece auxChessPiece = (ChessPiece) board.piece(source);
        if (auxChessPiece.getColor() != currentPlayer) {
            throw new ChessException("This piece isn't yours.");
        }
        if (!board.piece(source).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible move to source piece.");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if(!board.piece(source).possibleMove(target)) {
            throw new ChessException("Chosen piece can't move to target position!");
        }
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        if (capturedPiece != null) {
            Piece movedPiece = board.removePiece(target);
            board.placePiece(capturedPiece, target);
            board.placePiece(movedPiece, source);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        } else {
            board.placePiece(board.removePiece(target), source);
        }
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> filterList = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece e : filterList) {
            if (e instanceof King) {
                return (ChessPiece) e;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board!");
    }

    private boolean testCheck(Color color) {
        List<Piece> opponentList = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        ChessPiece king = king(color);
        Position positionKing = king.getChessPosition().toPosition();
        for (Piece e : opponentList) {
            boolean[][] auxMoves = e.possibleMoves();
            if (auxMoves[positionKing.getRow()][positionKing.getColumn()]) {
                return true;
            }
        }
        return false;
    }

}
