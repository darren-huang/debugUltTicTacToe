package UltTicTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicTacToeGame {
    static Pattern p = Pattern.compile("\\D*(\\d+)\\D+(\\d+)\\D*");

    public static void interactiveGame(int width, int height, int n) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TicTacToeBoard m = new TicTacToeBoard(width, height, n);
        boolean isPlayerX = true;
        System.out.println(m.displayBoardString());

        // loop till game is done
        while (!m.isWin() && !m.isFilled()) {
            // get user input
            if (isPlayerX) {
               System.out.print("Player X's move (x,y): ");
            } else {
                System.out.print("Player O's move (x,y): ");
            }
            String input = reader.readLine();
            Matcher mat = p.matcher(input);

            if (mat.matches()) { // parse user input with Regex
                Pos move = new Pos(Integer.parseInt(mat.group(1)), Integer.parseInt(mat.group(2)));

                if (m.validMove(move)) { // check move validity, if so make move
                    if (isPlayerX) {
                        m.makeMove(TicTacToeBoard.X, move);
                    } else {
                        m.makeMove(TicTacToeBoard.O, move);
                    }
                    isPlayerX = !isPlayerX; // update player
                } else {
                    System.out.println("move not valid!");
                }
            } else {
                System.out.println("input format not recognized!");
            }
            System.out.println(m.displayBoardString());
        }

        System.out.println(m.displayWinString());
    }

    public static void main(String[] args) throws IOException {
        TicTacToeGame.interactiveGame(3, 3, 3);
    }
}
