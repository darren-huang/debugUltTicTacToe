package UltTicTacToe;

public class UltBoard {
    private int width;
    private int height;
    private int n;
    private TicTacToeBoard[][] boards;

    /* Ultimate Tic Tac Toe:
    * Global vs. Local position:
    *     -  -  - || -  -  - || -  -  -
    *  2  -  -  - || -  -  - || -  -  -
    *     -  -  - || -  -  - || -  -  -
    *    ===============================
    *     -  -  - || -  -  - || -  -  -
    *  1  -  -  - || -  -  - || -  -  -
    *     X  -  - || -  -  - || -  -  -
    *    ===============================
    *     -  -  - || -  -  - || -  -  -
    *  0  -  -  - || -  -  - || -  -  -
    *     -  -  - || -  -  - || -  -  -
    *        0          1          2
    * here: X's global position is (0,1) telling us which inner board to look at
    *       X's local position is (0,0) telling us where inside the inner board is X
    *
    *
    *       1. given a width and a height, plays on a width^2 x height^2 board
    *           (can think of this as a width x height board of width x height boards)
    *                      example will be width = 3, height = 3, thus we use a 9x9 grid
    *                      that can also be thought of a 3x3 grid of 3x3 grids
    *       2. player X picks any position on the board for their move
    *       3. The last move's local location in the board, must be the next move's global location on the board
    *                      example: if X moves to global position (0,2) and local position (1,1), then O must
    *                               move to global position (1,1) and any unfilled local position
    *
     */
    public UltBoard(int width, int height, int n) {
        this.width = width;
        this.height = height;
        this.n = n;
        this.boards = new TicTacToeBoard[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.boards[x][y] = new TicTacToeBoard(width, height, n);
            }
        }
    }

    int boardGet(int gx, int gy, int lx, int ly){
        return boards[gx][gy].boardGet(lx, ly);
    }

    int boardGet(Pos globalPos, Pos localPos){
        return boardGet(globalPos.x, globalPos.y, localPos.x, localPos.y);
    }

    void boardSet(int gx, int gy, int lx, int ly, int player){
        boards[gx][gy].boardSet(lx, ly, player);
    }

    void boardSet(Pos globalPos, Pos localPos, int player){
        boardSet(globalPos.x, globalPos.y, localPos.x, localPos.y, player);
    }

    /* valid position
     *   1. is a valid place on the board ie. exists
     * */
    boolean validPos(Pos pos) {
        boolean validPosition = 0 <= pos.x && pos.x < width && 0 <= pos.y && pos.y < height;
        return validPosition;
    }

    /* valid position for a move
     *   1. is a valid place on the board
     *   2. isn't occupied by another player
     * */
    boolean validMove(Pos pos) {
        return validPos(pos) && boardGet(pos) == 0; // also needs space unoccupied
    }
}
