package org.example.models;

import org.example.interfaces.BoardOperations;

import java.util.ArrayList;
import java.util.List;

public class Board implements BoardOperations {
    private final int SIZE = 3; // Defines the size of the board
    private String[][] board = new String[SIZE][SIZE]; // 2D-array that represents the board
    private int emptyCells = SIZE * SIZE; // keeps track of empty cells
    private List<Board> childBoards; // A list that keeps track of possible moves from the current position
    private String lastMove;  // keeps track of the last move

    // Constructor, game board is initialized with empty strings in every cell
    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = "";
            }
        }
    }

    // keeps track of if a cell is empty
    @Override
    public boolean isValidMove(int row, int col) {
        return board[row][col].equals("");
    }

    // get the last move
    public String getLastMove() {
        return lastMove;
    }

    // set the last move
    public void setLastMove(int row, int col) {
        this.lastMove = "Row: " + row + ", Col: " + col;
    }


    // places either x or o mark if cell is empty and updates lastMove and decreases the amount of emptyCells
    @Override
    public void placeMark(String mark, int row, int col) {
        if (isValidMove(row, col)) {
            board[row][col] = mark;
            setLastMove(row, col);  // Set the last move whenever a mark is placed
            emptyCells--;
        }
    }
// checks if x or o is winner (true or false)
    @Override
    public boolean isWinner() {
        return isXWinner() || isOWinner();
    }


    // checks if it's a draw
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


    // returns a copy of current Board object
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

    // checks if there are empty cells in board
    @Override
    public boolean hasEmptyCells() {
        return emptyCells > 0;
    }


// returns current player based on the amount of empty cells(even or odd)
    private char getCurrentPlayer() {
        return emptyCells % 2 == 1 ? 'X' : 'O';
    }

    // creates all possible boards from current position by going through every empty cell
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


    // returns array of all childBoards, returns an empty array if childBoards is not initialized
    @Override
    public Board[] getChildBoards() {
        if (childBoards == null) {
            return new Board[0];
        }
        return childBoards.toArray(new Board[0]);
    }

    public int getSize() {
        return SIZE;
    }
}
