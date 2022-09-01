package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Board board;
    private Integer turn;
    private Color currentPlayer;
    private Boolean check;

    private Boolean checkMate;

    private ChessPiece enPassantVulnerable;

    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPieces;

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        piecesOnTheBoard = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        check = false;
        checkMate = false;
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

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
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
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
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

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        //Special Move - Validate En Passant
        ChessPiece movedPiece = (ChessPiece) board.piece(target);
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() + 2 || target.getRow() == source.getRow() - 2)) {
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }

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
        ((ChessPiece) movedPiece).increaseMoveCount();

        //Special Move - Castling
        //NearHook
        if (((ChessPiece) movedPiece) instanceof King && source.getColumn() + 2 == target.getColumn()) {
            Position oldPositionHook = new Position(target.getRow(), target.getColumn() + 1);
            Piece hookCastling = board.removePiece(oldPositionHook);
            Position newPositionHook = new Position(source.getRow(), source.getColumn() + 1);
            board.placePiece(hookCastling, newPositionHook);
            ((ChessPiece) hookCastling).increaseMoveCount();
        }
        //FarHook
        if (((ChessPiece) movedPiece) instanceof King && source.getColumn() - 2 == target.getColumn()) {
            Position oldPositionHook = new Position(target.getRow(), target.getColumn() - 2);
            Piece hookCastling = board.removePiece(oldPositionHook);
            Position newPositionHook = new Position(source.getRow(), source.getColumn() - 1);
            board.placePiece(hookCastling, newPositionHook);
            ((ChessPiece) hookCastling).increaseMoveCount();
        }

        //Special Move - En Passant
        if (((ChessPiece) movedPiece) instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (((ChessPiece) movedPiece).getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                piecesOnTheBoard.remove(capturedPiece);
                capturedPieces.add(capturedPiece);
            }
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
        Piece movedPiece = board.removePiece(target);
        ((ChessPiece) movedPiece).decreaseMoveCount();
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            board.placePiece(movedPiece, source);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        } else {
            board.placePiece(movedPiece, source);
        }

        //Special Move - Castling
        //NearHook
        if (((ChessPiece) movedPiece) instanceof King && source.getColumn() + 2 == target.getColumn()) {
            Position newPositionHook = new Position(source.getRow(), source.getColumn() + 1);
            Piece hookCastling = board.removePiece(newPositionHook);
            Position oldPositionHook = new Position(target.getRow(), target.getColumn() + 1);
            board.placePiece(hookCastling, oldPositionHook);
            ((ChessPiece) hookCastling).decreaseMoveCount();
        }
        //FarHook
        if (((ChessPiece) movedPiece) instanceof King && source.getColumn() + 2 == target.getColumn()) {
            Position newPositionHook = new Position(source.getRow(), source.getColumn() - 1);
            Piece hookCastling = board.removePiece(newPositionHook);
            Position oldPositionHook = new Position(target.getRow(), target.getColumn() - 2);
            board.placePiece(hookCastling, oldPositionHook);
            ((ChessPiece) hookCastling).decreaseMoveCount();
        }

        //Special Move - En Passant
        if (((ChessPiece) movedPiece) instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawnRemoved = (ChessPiece) board.removePiece(target);
                Position pawnPosition;
                if (((ChessPiece) movedPiece).getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                }
                else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawnRemoved, pawnPosition);
            }
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

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> colorList = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece e : colorList) {
            boolean[][] auxMoves = e.possibleMoves();
            for (int i = 0; i<auxMoves.length; i++) {
                for (int j = 0; j< auxMoves.length; j++) {
                    if (auxMoves[i][j]) {
                        Position source = ((ChessPiece) e).getChessPosition().toPosition();
                        Position target = new Position(i,j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
