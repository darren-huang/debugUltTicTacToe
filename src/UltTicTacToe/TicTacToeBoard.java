package UltTicTacToe;

public class TicTacToeBoard {
    public static final int X = 1;
    public static final int O = 2;

    // used for checking winning directions
    public static final Pos[] dirs = {new Pos(1, 0), new Pos(1, 1), new Pos(0, 1), new Pos(1, -1)};

    private int width, height; //the board will be width x height in dimensions
    private int n; //to win you must get 'n' symbols in a row/column/diagonal
    private int[][] board; //holds the contents of the board
    private int num_pieces;
    private boolean win;
    private int winner;
    private Pos[] winningEndpoints = new Pos[2];

    public boolean isWin() {
        return win;
    }

    public Pos[] getWinningEndpoints() {
        return winningEndpoints;
    }

    public int getWinner() {
        return winner;
    }

    /** checks if there is room for any more moves (needed to detect ties) */
    public boolean isFilled() {
        return width * height == num_pieces;
    }

    /** The Classic Tic Tac Toe board:
     *  *-----------*
     * |2| -  -  -  |
     * |1| X  -  -  |
     * |0| -  -  -  |
     *  *--0--1--2--*   X is at (x = 0, y = 1)
     *  you can specify any dimensions and "n" which is the number of X's or O's in a row needed in order to win
     *  width & height specify the dimensions of the board
     *  Players will alternate turn, but the move order will be enforced by TicTacToeGame.java
     *
     *  the main method to interact with this object is makeMove(int x, int y, int player) which places a "player" piece
     *  at the specified (x,y). This also checks if that player wins and will update variables accordingly if so.
     */
    public TicTacToeBoard(int width, int height, int n) {
        this.width = width;
        this.height = height;
        this.n = n;
        this.board = new int[height][width];
        this.win = false;
        this.winner = 0;
        this.num_pieces = 0;
    }

    /** Get Methods:
     *   returns the value stored at a given global and local position
     * */
    int get(int x, int y){
        return get(new Pos(x, y));
    }

    int get(Pos pos){
        return this.board[pos.y][pos.x];
    }

    /** Set Methods:
     *   directly changes the value stored at a given global and local position
     *   NOTE: this does NOT change any win values (ie. doesn't update win states)
     * */
    void set(int x, int y, int player){
        set(new Pos(x, y), player);
    }

    void set(Pos pos, int player) {
        this.board[pos.y][pos.x] = player;
    }

    /** valid position: checks the if the following is satisfied
     *   1. is a valid place on the board ie. coordinates are within bounds
     * */
    boolean validPos(Pos pos) {
        boolean validPosition = 0 <= pos.x && pos.x < width && 0 <= pos.y && pos.y < height;
        return validPosition; // also needs space unoccupied
    }

    /** valid move: checks the if the following is satisfied
     *   1. is a valid place on the board ie. coordinates are within bounds
     *   2. isn't already occupied by any player
     * */
    boolean validMove(Pos pos) {
        return validPos(pos) && get(pos) == 0; // also needs space unoccupied
    }

    // check if the latest move (given by "move") causes a win || assumes win == false
    boolean checkWin(Pos move) {
        // get player to check win for
        int player = get(move);

        // check valid player win (if no winner
        if (player != TicTacToeBoard.X && player != TicTacToeBoard.O) {
            return false;
        }
        for (Pos dir: dirs) {
            // check moves in positive direction
            int streakCount = 1;
            Pos pointer1 = move.addWith(dir);
            while (validPos(pointer1) && get(pointer1) == player) {
                streakCount += 1;
                pointer1 = pointer1.addWith(dir);
            }
            // check moves in negative direction
            Pos pointer2 = move.addWith(dir.mulWith(-1));
            while (validPos(pointer2) && get(pointer2) == player) {
                streakCount += 1;
                pointer2 = pointer2.addWith(dir.mulWith(-1));
            }
            // check if we have enough of a win streak
            if (streakCount >= this.n) {
                this.win = true;
                this.winner = player;
                this.winningEndpoints = new Pos[]{pointer1.addWith(dir.mulWith(-1)), pointer2.addWith(dir)};
                return win;
            }
        }
        return win;
    }

    /** see other "makeMove" */
    public boolean makeMove(int x, int y, int player) {
        return makeMove(new Pos(x, y), player);
    }

    /** makes a move for a given player (player is either TicTacToe.Model.X or TicTacToe.Model.O)
    * returns whether or not that player won */
    public boolean makeMove(Pos move, int player) {
        // check valid move
        if (!validMove(move)) {
            throw new RuntimeException("move is invalid");
        }

        // check valid player
        if (player != TicTacToeBoard.X && player != TicTacToeBoard.O) {
            throw new RuntimeException("player is invalid");
        }

        // make move
        set(move, player);

        if (!win) {
            // check for win
            checkWin(move);
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
        if (winner == TicTacToeBoard.O) {
            winString += " O";
        } else {
            winString += " X";
        }
        winString += " has won!\nThe winning line is from ";
        winString += "(" + winningEndpoints[0].x + ", " + winningEndpoints[0].y + ") to ";
        winString += "(" + winningEndpoints[1].x + ", " + winningEndpoints[1].y + ")";

        return winString;
    }

    public String[] displayBoardStringList(boolean showNum) {
        // init retlist
        String[] retList = new String[height + 2];

        //construct top and bottom border
        String numBorder = " *-";
        String border = " *-";
        for (int i = 0; i < width; i ++) {
            numBorder += "-" + i + "-";
            border += "---";
        }
        border += "-*";
        numBorder += "-*";
        retList[0] = border;
        retList[height + 1] = showNum ? numBorder : border;

        // visualize board
        for (int y=height - 1; y >= 0; y--) {
            String boardString = showNum ? "|" + y + "|" : " | ";
            for (int x=0; x < width; x++) {
                int val = get(x, y);
                if (val == TicTacToeBoard.X) {
                    boardString += " X ";
                } else if (val == TicTacToeBoard.O) {
                    boardString += " O ";
                } else {
                    boardString += " - ";
                }
            }
//            boardString += " | \n";
            retList[height - y] = boardString + " |";
        }
        return retList;
    }

    // Prints out the board
    public String displayBoardString() {
        String displayString = "";
        String[] listStrings = displayBoardStringList(true);
        for (int i = 0; i < listStrings.length; i++) {
            displayString += listStrings[i] + (i == listStrings.length - 1 ? "" : "\n");
        }
        return displayString;
    }

    public static void main(String[] args) {
        TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
        System.out.println(m.displayBoardString());
//        m.set(0, 0, TicTacToeBoard.X);
//        m.set(1, 0, TicTacToeBoard.X);
//        m.set(2, 0, TicTacToeBoard.X);
//        m.set(3, 0, TicTacToeBoard.X);
//        m.set(4, 0, TicTacToeBoard.X);
//        System.out.println(m.checkWin(new Pos(4,0)));

        System.out.println(m.displayBoardString());
        System.out.println(m.displayWinString());
    }
}
