package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI {

    private final Board board;
    private final String PLAYER_MARK = "X";
    private final String COMPUTER_MARK = "O";
    private JFrame frame;
    private JButton[][] buttons;

    public GameGUI() {
        this.board = new Board();
        this.frame = new JFrame("Tic Tac Toe");
        this.buttons = new JButton[board.getSize()][board.getSize()];
    }

    public void startGame() {
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                buttons[i][j] = new JButton();
                frame.add(buttons[i][j]);
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedButton = (JButton) e.getSource();
                        int clickedRow = -1;
                        int clickedCol = -1;

                        // Find which button was clicked
                        for (int row = 0; row < board.getSize(); row++) {
                            for (int col = 0; col < board.getSize(); col++) {
                                if (buttons[row][col] == clickedButton) {
                                    clickedRow = row;
                                    clickedCol = col;
                                    break;
                                }
                            }
                        }

                        if (board.isValidMove(clickedRow, clickedCol)) {
                            board.placeMark(PLAYER_MARK, clickedRow, clickedCol);
                            clickedButton.setText(PLAYER_MARK);

                            if (!board.isWinner() && board.hasEmptyCells()) {
                                computerTurn();
                            }
                            displayResult();
                        }
                    }
                });
            }
        }

        frame.setVisible(true);
    }

    private void computerTurn() {
        board.createPossibleChildBoards(); // Add this line
        Board bestMove = GameLogic.getComputerMove(board, COMPUTER_MARK);
        if (bestMove != null) {
            String lastMove = bestMove.getLastMove().replace("Row: ", "").replace(", Col: ", " ");
            int bestRow = Integer.parseInt(lastMove.split(" ")[0]);
            int bestCol = Integer.parseInt(lastMove.split(" ")[1]);
            board.placeMark(COMPUTER_MARK, bestRow, bestCol);
            buttons[bestRow][bestCol].setText(COMPUTER_MARK);
        }
    }


    private void displayResult() {
        if (board.isXWinner()) {
            showMessage("Player Wins!");
            System.exit(0);  // avslutar programmet
        } else if (board.isOWinner()) {
            showMessage("Computer Wins!");
            System.exit(0);  // avslutar programmet
        } else if (!board.hasEmptyCells()) {
            showMessage("It's a draw!");
            System.exit(0);  // avslutar programmet
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

}
