public class Model {
    public static final int X = 1;
    public static final int O = 2;

    // used for checking winning directions
    public static final Pos[] dirs = {new Model.Pos(1, 0), new Model.Pos(1, 1), new Model.Pos(0, 1)};

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
    }

    private int width, height; //the board will be width x height in dimensions
    private int n; //to win you must get 'n' symbols in a row/column/diagonal
    private int[][] board; //holds the contents of the board
    private boolean win;
    private int winner;
    private Pos[] winningEndpoints;

    public Model(int width, int height, int n) {
        this.width = width;
        this.height = height;
        this.n = n;
        this.board = new int[height][width];
        this.win = false;
        this.winner = 0;
        this.winningEndpoints = new Pos[2];
    }

    private int boardGet(Pos pos){
        return this.board[pos.y][pos.x];
    }

    private void boardSet(Pos pos, int player) {
        this.board[pos.y][pos.x] = player;
    }

    private boolean validMove(Pos move) {
        boolean validPosition = 0 <= move.x && move.x < width && 0 <= move.y && move.y < height;
        boolean posNotOccupied = boardGet(move) == 0;
        return validPosition && posNotOccupied;
    }

    // check if the latest move (given by "move") causes a win || assumes win == false
    private void checkWin(Pos move) {
        int player = boardGet(move);
        for (Pos dir: dirs) {
            int streakCount = 1;
            Pos pointer1 = move.addWith(dir);
            while (boardGet(pointer1) == player) {
                streakCount += 1;
                pointer1 = pointer1.addWith(dir);
            }
            Pos pointer2 = move.addWith(dir.mulWith(-1));
            while (boardGet(pointer2) == player) {
                streakCount += 1;
                pointer2 = pointer2.addWith(dir.mulWith(-1));
            }
            if (streakCount >= this.n) {
                this.win = true;
                this.winner = player;
                this.winningEndpoints = new Pos[]{pointer1.addWith(dir.mulWith(-1)), pointer2.addWith(dir)};
                return;
            }
        }

    }

    // makes a move for a given player (player is either Model.X or Model.O)
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
        if (win) {
            return "ERROR: there is no winner atm";
        }
        String winString = "Player";
        if (winner == Model.O) {
            winString += " O";
        } else {
            winString += " X";
        }
        winString += " has won!\n The winning line is from";


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
        for (int i=0; i < height; i++) {
            int[] row = this.board[i];
            boardString += i + " | ";
            for (int val: row) {
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
        System.out.println(m.displayBoardString());
    }
}
