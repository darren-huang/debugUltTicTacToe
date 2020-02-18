package TicTacToe;

import com.sun.tools.internal.xjc.reader.RawTypeSet;

import java.util.Objects;

public class Model {
    public static final int X = 1;
    public static final int O = 2;

    // used for checking winning directions
    public static final Pos[] dirs = {new Pos(1, 0), new Pos(1, 1), new Pos(0, 1), new Pos(1, -1)};

    public static class Pos {
        public int x,y;

        public Pos(int x, int y) {
            this.x = x; // moves along x-axis, origin is top-left
            this.y = y; // moves along y-axis, origin is top-left
        }

        public Pos addWith(Pos other) {
            return new Pos(this.x + other.x, this.y + other.y);
        }

        public Pos mulWith(int i) {
            return new Pos(this.x * i, this.y * i);
        }

        public String toString(){
            return "(" + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return x == pos.x &&
                    y == pos.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private int width, height; //the board will be width x height in dimensions
    private int n; //to win you must get 'n' symbols in a row/column/diagonal
    private int[][] board; //holds the contents of the board
    private boolean win;
    private int winner;
    private Pos[] winningEndpoints = new Pos[2];

    public Model(int width, int height, int n) {
        this.width = width;
        this.height = height;
        this.n = n;
        this.board = new int[height][width];
        this.win = false;
        this.winner = 0;
    }

    int boardGet(int x, int y){
        return boardGet(new Pos(x, y));
    }

    int boardGet(Pos pos){
        return this.board[pos.y][pos.x];
    }

    void boardSet(int x, int y, int player){
        boardSet(new Pos(x, y), player);
    }

    void boardSet(Pos pos, int player) {
        this.board[pos.y][pos.x] = player;
    }

    /* valid position
     *   1. is a valid place on the board ie. exists
     * */
    boolean validPos(Pos pos) {
        boolean validPosition = 0 <= pos.x && pos.x < width && 0 <= pos.y && pos.y < height;
        return validPosition; // also needs space unoccupied
    }

    /* valid position for a move
     *   1. is a valid place on the board
     *   2. isn't occupied by another player
     * */
    boolean validMove(Pos pos) {
        return validPos(pos) && boardGet(pos) == 0; // also needs space unoccupied
    }

    public boolean isWin() {
        return win;
    }

    public Pos[] getWinningEndpoints() {
        return winningEndpoints;
    }

    public int getWinner() {
        return winner;
    }

    // check if the latest move (given by "move") causes a win || assumes win == false
    boolean checkWin(Pos move) {
        int player = boardGet(move);
        if (player <= 0) {
            return false;
        }
        for (Pos dir: dirs) {
            int streakCount = 1;
            Pos pointer1 = move.addWith(dir);
            while (validPos(pointer1) && boardGet(pointer1) == player) {
                streakCount += 1;
                pointer1 = pointer1.addWith(dir);
            }
            Pos pointer2 = move.addWith(dir.mulWith(-1));
            while (validPos(pointer2) && boardGet(pointer2) == player) {
                streakCount += 1;
                pointer2 = pointer2.addWith(dir.mulWith(-1));
            }
            if (streakCount >= this.n) {
                this.win = true;
                this.winner = player;
                this.winningEndpoints = new Pos[]{pointer1.addWith(dir.mulWith(-1)), pointer2.addWith(dir)};
                return win;
            }
        }
        return win;
    }

    // makes a move for a given player (player is either TicTacToe.Model.X or TicTacToe.Model.O)
    // returns whether or not that player won
    public boolean makeMove(int player, int x, int y) {
        return makeMove(player, new Pos(x, y));
    }

    // makes a move for a given player (player is either TicTacToe.Model.X or TicTacToe.Model.O)
    // returns whether or not that player won
    public boolean makeMove(int player, Pos move) {
        // check valid move
        if (!validMove(move)) {
            throw new RuntimeException("move is invalid");
        }

        // check valid player
        if (player != Model.X && player != Model.O) {
            throw new RuntimeException("player is invalid");
        }

        // check game isn't over
        if (win) {
            throw new RuntimeException("game is over");
        }

        // make move
        boardSet(move, player);

        // check for win
        checkWin(move);

        return win;
    }

    public String displayWinString() {
        if (!win) {
            return "ERROR: there is no winner atm";
        }
        String winString = "Player";
        if (winner == Model.O) {
            winString += " O";
        } else {
            winString += " X";
        }
        winString += " has won!\nThe winning line is from ";
        winString += "(" + winningEndpoints[0].x + ", " + winningEndpoints[0].y + ") to ";
        winString += "(" + winningEndpoints[1].x + ", " + winningEndpoints[1].y + ")";

        return winString;
    }

    // Prints out the board
    public String displayBoardString() {
        String boardString = "";

        //construct top and bottom border
        String border = "  * ";
        for (int i = 0; i < width; i ++) {
            border += "-" + i + "-";
        }
        border += " * \n";

        // visualize board
        for (int y=0; y < height; y++) {
            boardString += y + " | ";
            for (int x=0; x < width; x++) {
                int val = boardGet(x, y);
                if (val == Model.X) {
                    boardString += " X ";
                } else if (val == Model.O) {
                    boardString += " O ";
                } else {
                    boardString += " - ";
                }
            }
            boardString += " | \n";
        }
        return border + boardString + border;
    }

    public static void main(String[] args) {
        Model m = new Model(10, 5, 4);
        m.boardSet(0, 0, Model.X);
        m.boardSet(1, 0, Model.X);
        m.boardSet(2, 0, Model.X);
        m.boardSet(3, 0, Model.X);
        m.boardSet(4, 0, Model.X);
        System.out.println(m.checkWin(new Pos(4,0)));

        System.out.println(m.displayBoardString());
        System.out.println(m.displayWinString());
    }
}
