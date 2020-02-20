package UltTicTacToe;

public class UltBoard {
    public static final int X = TicTacToeBoard.X;
    public static final int O = TicTacToeBoard.O;
    // used for checking winning directions
    public static final Pos[] dirs = {new Pos(1, 0), new Pos(1, 1), new Pos(0, 1), new Pos(1, -1)};

    // instance variables
    private int width;
    private int height;
    private int n;
    private TicTacToeBoard[][] boards;
    private int num_pieces;
    private boolean win;
    private int winner;
    private Pos[] winningGlobalEndpoints = new Pos[2];

    public boolean isWin() {
        return win;
    }

    public int getWinner() {
        return winner;
    }

    public Pos[] getWinningGlobalEndpoints() {
        return winningGlobalEndpoints;
    }

    /** checks if there is room for any more moves (needed to detect ties) */
    public boolean isFilled() {
        return width * width * height * height == num_pieces;
    }

    /** Ultimate Tic Tac Toe:
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
        this.num_pieces = 0;
        this.win = false;
        this.winner = 0;
        this.boards = new TicTacToeBoard[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.boards[x][y] = new TicTacToeBoard(width, height, n);
            }
        }
    }

    /** Get Methods:
    *   returns the value stored at a given global and local position
    * */
    int get(int gx, int gy, int lx, int ly) {
        return boards[gx][gy].get(lx, ly);
    }

    int get(Pos globalPos, Pos localPos) {
        return get(globalPos.x, globalPos.y, localPos.x, localPos.y);
    }

    /** GetBoard Methods:
     *   returns the TicTacToe board stored at a certain global position
     * */
    TicTacToeBoard getBoard(Pos globalPos) {
        return boards[globalPos.x][globalPos.y];
    }

    TicTacToeBoard getBoard(int gx, int gy) {
        return getBoard(new Pos(gx, gy));
    }

    /** Set Methods:
    *   directly changes the value stored at a given global and local position
    *   NOTE: this does NOT change any win values (ie. doesn't update win states)
    * */
    void set(int gx, int gy, int lx, int ly, int player) {
        boards[gx][gy].set(lx, ly, player);
    }

    void set(Pos globalPos, Pos localPos, int player) {
        set(globalPos.x, globalPos.y, localPos.x, localPos.y, player);
    }

    /** valid position: checks the if the following is satisfied
     *   1. is a valid place on the board ie. coordinates are within bounds
     *      Note: works for both local and global positions
     * */
    boolean validPos(Pos pos) {
        boolean validPosition = 0 <= pos.x && pos.x < width && 0 <= pos.y && pos.y < height;
        return validPosition;
    }

    /** valid move: checks the if the following is satisfied
     *   1. is a valid place on the board ie. coordinates are within bounds
     *   2. isn't already occupied by any player
     * */
    public boolean validMove(Pos globalPos, Pos localPos) {
        return validPos(globalPos) && getBoard(globalPos).validMove(localPos); // also needs space unoccupied
    }

    /** valid global move: checks if the following is satisfied
     *   1. is a valid place on the board ie. coordinates are within bounds
     *   2. isn't a filled board ie. there is room on the TicTacToe board at globalPos for another local move
     * */
    public boolean validGlobalMove(Pos globalPos) {
        return validPos(globalPos) && !getBoard(globalPos).isFilled(); // also needs space unoccupied
    }

    /** check if the latest move (given by "move") causes a win || assumes win == false
     * For Ultimate Tic Tac Toe, a win means "n" TicTacToe boards in a row are won by the same player
     * This means in either a row, column, or diagonal of TicTacToe boards all are either won by X or O
     * */
    boolean checkWin(Pos globalPos) {
        // get player to check win for
        int player = getBoard(globalPos).getWinner();

        // check valid player win (if no winner, player = 0 != UltBoard.X && != UltBoard.O)
        if (player != UltBoard.X && player != UltBoard.O) {
            return false;
        }
        for (Pos dir: dirs) {
            // check moves in positive direction
            int streakCount = 1;
            Pos globalPtr1 = globalPos.addWith(dir);
            while (validPos(globalPtr1) && getBoard(globalPtr1).getWinner() == player) {
                streakCount += 1;
                globalPtr1 = globalPtr1.addWith(dir);
            }
            // check moves in negative direction
            Pos globalPtr2 = globalPos.addWith(dir.mulWith(-1));
            while (validPos(globalPtr2) && getBoard(globalPtr2).getWinner() == player) {
                streakCount += 1;
                globalPtr2 = globalPtr2.addWith(dir.mulWith(-1));
            }
            // check if we have enough of a win streak
            if (streakCount >= this.n) {
                this.win = true;
                this.winner = player;
                this.winningGlobalEndpoints = new Pos[]{globalPtr1.addWith(dir.mulWith(-1)), globalPtr2.addWith(dir)};
                return win;
            }
        }
        return win;
    }

    /** see other "makeMove" */
    public boolean makeMove(int gx, int gy, int lx, int ly, int player) {
        return makeMove(new Pos(gx, gy), new Pos(lx, ly), player);
    }

    /** makes a move for a given player (player is either UltBoard.X or UltBoard.O)
    * returns whether or not that player won
    * NOTE: this correctly updates win state for both UltBoard and the inner TicTacToeBoard */
    public boolean makeMove(Pos globalMove, Pos localMove, int player) {
        // check valid move
        if (!validMove(globalMove, localMove)) {
            throw new RuntimeException("move is invalid");
        }

        // check valid player
        if (player != TicTacToeBoard.X && player != TicTacToeBoard.O) {
            throw new RuntimeException("player is invalid");
        }

        // make move
        getBoard(globalMove).makeMove(localMove, player);

        // check for win
        if (!win) {
            checkWin(globalMove);
        }

        num_pieces += 1;

        return win;
    }

    /** -----------------------------------------------------------------------------------------------------------------
     * -----------------------------------      "GUI" Functions       --------------------------------------------------
     * -----------------------------------------------------------------------------------------------------------------*/
    public String displayWinString() {
        if (!win) {
            if (isFilled()) {
                return "it's a Tie!";
            }
            return "ERROR: there is no winner atm";
        }
        String winString = "Player";
        if (winner == UltBoard.O) {
            winString += " O";
        } else {
            winString += " X";
        }
        winString += " has won!\nThe winning line is from ";
        winString += "(" + winningGlobalEndpoints[0].x + ", " + winningGlobalEndpoints[0].y + ") to ";
        winString += "(" + winningGlobalEndpoints[1].x + ", " + winningGlobalEndpoints[1].y +
                ") (note: these are global positions)";

        return winString;
    }

    // prints out the board with global numberings
    public String displayLocalBoardString(int gx, int gy) {
        String retStr = "";
        for (int y = height - 1; y >= 0; y--) {
            String[][] rowStringLists = new String[width][0];
            for (int x = 0; x < width; x ++) {
                rowStringLists[x] = getBoard(x, y).displayBoardStringList(x == gx && y == gy);
            }
            for (int i = 0; i < rowStringLists[0].length; i++) {
                retStr += i == rowStringLists[0].length/2 ? " " + y + " " : "   ";
                for (int j = 0; j < rowStringLists.length; j++) {
                    retStr += rowStringLists[j][i];
                }
                retStr += "\n";
            }
        }
        //spacing for bottom numbers
        retStr += "   "; // 3
        for (int i = 0; i < width; i++) {
            retStr += "   "; // 3
            for (int j = 0; j < width; j++) {
                retStr += j == width/2 ? " " + i + " " : "   ";
            }
            retStr += "  "; // 3
        }
        return retStr;
    }

    // prints out the board with global numberings
    public String displayGlobalBoardString() {
        String retStr = "";
        for (int y = height - 1; y >= 0; y--) {
            String[][] rowStringLists = new String[width][0];
            for (int x = 0; x < width; x ++) {
                rowStringLists[x] = getBoard(x, y).displayBoardStringList(false);
            }
            for (int i = 0; i < rowStringLists[0].length; i++) {
                retStr += i == rowStringLists[0].length/2 ? " " + y + " " : "   ";
                for (int j = 0; j < rowStringLists.length; j++) {
                    retStr += rowStringLists[j][i];
                }
                retStr += "\n";
            }
        }

        //spacing for bottom numbers
        retStr += "   "; // 3
        for (int i = 0; i < width; i++) {
            retStr += "   "; // 3
            for (int j = 0; j < width; j++) {
                retStr += j == width/2 ? " " + i + " " : "   ";
            }
            retStr += "  "; // 3
        }
        return retStr;
    }

    public static void main(String[] args) {
        UltBoard m = new UltBoard(3, 3, 3);
        System.out.println(m.displayGlobalBoardString() + "\n\n");
        System.out.println(m.displayLocalBoardString(1, 1));
    }
}
