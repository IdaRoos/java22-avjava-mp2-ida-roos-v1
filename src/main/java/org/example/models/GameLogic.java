package org.example.models;

public class GameLogic {

    /**
     * Minimax algorithm to find the best move for the current player.
     *
     * @param board The current game board.
     * @param depth Current depth of the recursive call.
     * @param isMaximizing True if the current player is maximizing score, false if minimizing.
     * @return The best score for the current board state.
     */

    public static int minMax(Board board, int depth, boolean isMaximizing) {
        // Base case: stop if depth is 3 or if the game is over (win or draw).
        if ( depth == 9 || board.isWinner() || board.isDraw()) {
            return evaluateBoard(board, depth);  // Passed depth to evaluate
        }

        // Create possible child boards
        board.createPossibleChildBoards();

        depth++;

        if (isMaximizing) {
            int maxVal = Integer.MIN_VALUE;

            for (Board childBoard : board.getChildBoards()) {
                int eval = minMax(childBoard, depth, false);

                if (eval > maxVal) {
                    maxVal = eval;
                }
            }

            return maxVal;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (Board childBoard : board.getChildBoards()) {
                int eval = minMax(childBoard, depth, true);
                minEval = Math.min(minEval, eval);
            }

            return minEval;
        }
    }



    /**
     * Determines the computer's best move based on the current board state.
     *
     * @param board The current game board.
     * @return The best board state for the computer's move.
     */
    public static Board getComputerMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        Board bestMove = null;

        // Iterate through each possible move and find the one with the highest score.
        for (Board childBoard : board.getChildBoards()) {
            int score = minMax(childBoard, 0, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = childBoard;
            }
        }
        return bestMove;
    }

    public static Board getPlayerMove(Board board) {
        int bestScore = Integer.MAX_VALUE;
        Board bestMove = null;

        for (Board child : board.getChildBoards()) {
            int score = minMax(child, 0, true); // false indicates the computer's turn, i.e., maximizer
            if (score < bestScore) {
                bestScore = score;
                bestMove = child;
            }
        }
        return bestMove;
    }





    /**
     * Evaluates the current board state and assigns a score based on the game's outcome.
     *
     * @param board The current game board.
     * @param depth Current depth of the recursive call.
     * @return Score for the current board state (-10 to 10).
     */
    private static int evaluateBoard(Board board, int depth) {
        if (board.isXWinner()) {
            return -10 + depth;
        }
        if (board.isOWinner()) {
            return 10 - depth;
        }
        return 0;
    }
}
