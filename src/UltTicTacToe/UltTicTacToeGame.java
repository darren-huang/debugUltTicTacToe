package UltTicTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UltTicTacToeGame {
    static Pattern p = Pattern.compile("\\D*(\\d+)\\D+(\\d+)\\D*");

    // asks for user input until a valid global move is chosen
    private static Pos getValidGlobalMove(UltBoard m, boolean isPlayerX, BufferedReader reader) throws IOException {
        while (true) {
            // get user input
            System.out.println("Select which sub-board to play in");
            if (isPlayerX) {
                System.out.print("Player X's global move (x,y): ");
            } else {
                System.out.print("Player O's global move (x,y): ");
            }
            String input = reader.readLine();
            System.out.println("\n");
            Matcher mat = p.matcher(input);

            if (mat.matches()) { // parse user input with Regex
                Pos globalMove = new Pos(Integer.parseInt(mat.group(1)), Integer.parseInt(mat.group(2)));

                if (m.validGlobalMove(globalMove)) { // check move validity, if so return move
                    return globalMove;
                } else {
                    System.out.println("move not valid!");
                }
            } else {
                System.out.println("input format not recognized!");
            }
            System.out.println(m.displayGlobalBoardString());
        }
    }

    // asks for user input until a valid local move is chosen
    private static Pos getValidLocalMove(UltBoard m, boolean isPlayerX, Pos globalMove,
                                         BufferedReader reader) throws IOException {
        while (true) {
            System.out.println(m.displayLocalBoardString(globalMove.x, globalMove.y));
            // get user input
            System.out.println("Playing in the board located at (" + globalMove.x + ", " + globalMove.y + ")");
            if (isPlayerX) {
                System.out.print("Player X's local move (x,y): ");
            } else {
                System.out.print("Player O's local move (x,y): ");
            }
            String input = reader.readLine();
            System.out.println("\n");
            Matcher mat = p.matcher(input);

            if (mat.matches()) { // parse user input with Regex
                Pos localMove = new Pos(Integer.parseInt(mat.group(1)), Integer.parseInt(mat.group(2)));

                if (m.validMove(globalMove, localMove)) { // check move validity, if so return move
                    return localMove;
                } else {
                    System.out.println("move not valid!");
                }
            } else {
                System.out.println("input format not recognized!");
            }
        }
    }

    public static void interactiveGame(int width, int height, int n) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        UltBoard m = new UltBoard(width, height, n);
        boolean isPlayerX = true;
        Pos gMove = null;
        Pos lMove = null;
        System.out.println(m.displayGlobalBoardString());

        // loop till game is done
        while (!m.isWin() && !m.isFilled()) {
            // get user global move (prev local move becomes next global move)
            if (lMove != null && m.validGlobalMove(lMove)) {
                System.out.println("Last local move was at " + lMove.toString() );
                System.out.println("Next global move must be at " + lMove.toString());
                gMove = lMove;
            } else {
                System.out.println(lMove != null ? "board at " + lMove.toString() + " is filled!" : "first move!:");
                gMove = getValidGlobalMove(m, isPlayerX, reader);
            }

            // get user local
            lMove = getValidLocalMove(m, isPlayerX, gMove, reader);

            //make move
            if (isPlayerX) {
                m.makeMove(gMove, lMove, TicTacToeBoard.X);
            } else {
                m.makeMove(gMove, lMove, TicTacToeBoard.O);
            }

            //update player
            isPlayerX = !isPlayerX;

            // re-print
            System.out.println(m.displayGlobalBoardString() + "\n\n");
        }

        System.out.println(m.displayWinString());
    }

    public static void main(String[] args) throws IOException {
        interactiveGame(2, 2, 2);
    }
}
