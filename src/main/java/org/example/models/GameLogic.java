package org.example;

public class GameLogic {

    public static int minMax(Board board, int depth, boolean isMaximizing) {
        // Base case
        if (depth == 3 || board.isWinner() || board.isDraw()) {
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

    public static Board getComputerMove(Board board, String computerMark) {
        int bestScore = Integer.MIN_VALUE;
        Board bestMove = null;

        for (Board childBoard : board.getChildBoards()) {
            int score = minMax(childBoard, 0, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = childBoard;
            }
        }

        return bestMove;
    }

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
