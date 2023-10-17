package org.example;

import interfaces.BoardOperations;

import java.util.ArrayList;
import java.util.List;

public class Board implements BoardOperations {
    private final int SIZE = 3;
    private String[][] board = new String[SIZE][SIZE];
    private int emptyCells = SIZE * SIZE;
    private List<Board> childBoards; // Deklarera variabeln här
    private String lastMove;  // New attribute to keep track of the last move

    // Konstruktor
    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = "";
            }
        }
    }

    @Override
    public boolean isValidMove(int row, int col) {
        return board[row][col].equals("");
    }

    // New method to get the last move
    public String getLastMove() {
        return lastMove;
    }

    // New method to set the last move
    public void setLastMove(int row, int col) {
        this.lastMove = "Row: " + row + ", Col: " + col;
    }

    @Override
    public void placeMark(String mark, int row, int col) {
        if (isValidMove(row, col)) {
            board[row][col] = mark;
            setLastMove(row, col);  // Set the last move whenever a mark is placed
            emptyCells--;
        }
    }

    @Override
    public boolean isWinner() {
        return isXWinner() || isOWinner();
    }

    @Override
    public boolean isDraw() {
        return emptyCells == 0 && !isWinner();
    }

    @Override
    public boolean isXWinner() {
        return checkWinner("X");
    }

    @Override
    public boolean isOWinner() {
        return checkWinner("O");
    }

    private boolean checkWinner(String mark) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0].equals(mark) && board[i][1].equals(mark) && board[i][2].equals(mark)) {
                return true;
            }
            if (board[0][i].equals(mark) && board[1][i].equals(mark) && board[2][i].equals(mark)) {
                return true;
            }
        }
        if (board[0][0].equals(mark) && board[1][1].equals(mark) && board[2][2].equals(mark)) {
            return true;
        }
        if (board[0][2].equals(mark) && board[1][1].equals(mark) && board[2][0].equals(mark)) {
            return true;
        }
        return false;
    }

    @Override
    public Board copyBoard() {
        Board newBoard = new Board();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                newBoard.board[i][j] = this.board[i][j];
            }
        }
        newBoard.emptyCells = this.emptyCells;
        return newBoard;
    }

    @Override
    public boolean hasEmptyCells() {
        return emptyCells > 0;
    }

    @Override
    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j].equals("") ? "-" : board[i][j]);
                if (j != SIZE - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i != SIZE - 1) {
                System.out.println("---------");
            }
        }
    }


    /**
     * After adding a move to a copy of a board.
     * This method should be used to add the previous one
     */
    @Override
    public void setPreviousCell() {
        // TODO: Implement this method
    }


    /**
     * After creating a copy of this board. The copy should be added as next board
     */
    @Override
    public void setNextBoard() {
        // TODO: Implement this method
    }

    private char getCurrentPlayer() {
        return emptyCells % 2 == 1 ? 'X' : 'O';  // Om udda tomma celler så är det X:s tur, annars O:s
    }

    @Override
    public void createPossibleChildBoards() {
        childBoards = new ArrayList<>();
        char currentPlayer = getCurrentPlayer();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == null || board[i][j].isEmpty()) {
                    Board newBoard = this.copyBoard();
                    newBoard.placeMark(String.valueOf(currentPlayer), i, j);
                    childBoards.add(newBoard);
                }
            }
        }
    }

    @Override
    public Board[] getChildBoards() {
        if (childBoards == null) {
            return new Board[0];  // Return an empty array if childBoards is not initialized.
        }
        return childBoards.toArray(new Board[0]);
    }

    public int getSize() {
        return SIZE;
    }
}
