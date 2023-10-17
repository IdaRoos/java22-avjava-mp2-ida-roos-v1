package interfaces;

import org.example.Board;

/**
 * This interface is meant to be implemented by board class
 * it holds methods used in board manipulation
 * Some methods specific for a specific code
 * might be missing and should be added.
 * @author lukas kurasinski
 *
 */

public interface BoardOperations {

    /**
     * Method should check if the location row/col on the board
     * is a valid move. Can x or o be placed on a cell or
     * is it already taken
     * @param // x
     * @param // o
     * @return
     */
    boolean isValidMove(int row, int col);

    /**
     * Used after checking if move is valid. Places x or o on the board.
     * Maybe should be modified to return a copy of the board which
     * includes the additional move.
     * @param mark
     * @param row
     * @param col
     * @return
     */
    void placeMark(String mark, int row, int col);

    /**
     * Method checks if there is a winner on the board
     * 3x or 3o. This method returns boolean and could be replaced
     * with a similar method returning the winner, x or o,
     * n for no winner yet, d draw.
     * @param
     * @return
     */
    boolean isWinner();

    /**
     * Like isWinner but checks only if its a draw
     * @return
     */
    boolean isDraw();

    /**
     * Like isWinner but checks only if x wins
     * @return
     */
    boolean isXWinner();

    /**
     * Like isWinner but checks only if o wins
     * @return
     */
    boolean isOWinner();

    /**
     * Board makes a copy of a current board. This is important
     * because the board is going to be passed as reference and every
     * next change will change the original board. Copies will have to
     * be made to preserve a specific board state in the algorithm
     * @return
     */
    Board copyBoard();

    /**
     * Can be used to keep ongoing count of taken cells for
     * quick reference. Every time a mark is placed number of
     * empty cells go down from 9 to 0. When 0 it should be
     * a base case an a winner or draw should be checked
     * @return
     */
    boolean hasEmptyCells();

    /**
     * Prints board to the console. Probably double for loop
     * for rows and columns
     */
    void printBoard();

    /**
     * After adding a move to a copy of a board.
     * This method should be used to add the previous one
     */
    void setPreviousCell();

    /**
     * After creating a copy of this board. The copy should be added as next board
     */
    void setNextBoard();

    /**
     * An array of child boards with all possible mark placements
     * @return
     */
    Board[] getChildBoards();

    /**
     * Every board except the base case boards should have
     * an array of Boards, each representing one possible placement
     * of x for maximizer and o for minimizer
     */
    void createPossibleChildBoards();


}
