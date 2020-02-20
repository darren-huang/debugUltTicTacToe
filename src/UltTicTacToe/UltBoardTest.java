package UltTicTacToe;

import org.junit.Assert;
import org.junit.Test;

public class UltBoardTest {

    @Test
    public void boardSetGet() {
        UltBoard b = new UltBoard(3, 3, 3);

        Pos gPos1 = new Pos(0, 0);
        Pos gPos2 = new Pos(1, 0);
        Pos lPos = new Pos(0, 0);
        Assert.assertTrue(b.getBoard(gPos1) == b.getBoard(gPos1));

        // check 1
        b.set(gPos1, lPos, UltBoard.X);
        Assert.assertTrue(b.get(gPos1, lPos) == UltBoard.X);
        Assert.assertTrue(b.get(gPos2, lPos) == 0); // unset & untouched

        // check 1
        b.set(gPos2, lPos, UltBoard.O);
        Assert.assertTrue(b.get(gPos1, lPos) == UltBoard.X); // untouched
        Assert.assertTrue(b.get(gPos2, lPos) == UltBoard.O); // set correctly

    }

    @Test
    public void validPos() {
        UltBoard b = new UltBoard(3, 3, 3);

        // 1. is a valid place on the board
        // corners True (0 index)
        Assert.assertTrue(b.validPos(new Pos(0, 0)));
        Assert.assertTrue(b.validPos(new Pos(2, 0)));
        Assert.assertTrue(b.validPos(new Pos(0, 2)));
        Assert.assertTrue(b.validPos(new Pos(2, 2)));

        // invalid, N/E/S/W
        Assert.assertFalse(b.validPos(new Pos(1, -1)));
        Assert.assertFalse(b.validPos(new Pos(3, 1)));
        Assert.assertFalse(b.validPos(new Pos(1, 3)));
        Assert.assertFalse(b.validPos(new Pos(-1, 1)));
    }

    @Test
    public void validMove() {
        UltBoard b = new UltBoard(3, 3, 3);

        // 1. is a valid place on the board
        // corners True (0 index)
        Assert.assertTrue(b.validMove(new Pos(0, 0), new Pos(0, 0)));
        Assert.assertTrue(b.validMove(new Pos(2, 0), new Pos(2, 0)));
        Assert.assertTrue(b.validMove(new Pos(0, 2), new Pos(0, 2)));
        Assert.assertTrue(b.validMove(new Pos(2, 2), new Pos(2, 2)));

        // invalid, N/E/S/W
        Assert.assertFalse(b.validMove(new Pos(0, 0), new Pos(1, -1)));
        Assert.assertFalse(b.validMove(new Pos(0, 0), new Pos(3, 1)));
        Assert.assertFalse(b.validMove(new Pos(0, 0), new Pos(1, 3)));
        Assert.assertFalse(b.validMove(new Pos(0, 0), new Pos(-1, 1)));
        Assert.assertFalse(b.validMove(new Pos(1, -1), new Pos(0, 0)));
        Assert.assertFalse(b.validMove(new Pos(3, 1), new Pos(0, 0)));
        Assert.assertFalse(b.validMove(new Pos(1, 3), new Pos(0, 0)));
        Assert.assertFalse(b.validMove(new Pos(-1, 1), new Pos(0, 0)));

        // 2. isn't occupied
        b.set(0, 0, 0, 0, UltBoard.X);
        b.set(2, 0, 2, 0, UltBoard.X);

        // corners again
        Assert.assertFalse(b.validMove(new Pos(0, 0), new Pos(0, 0)));
        Assert.assertFalse(b.validMove(new Pos(2, 0), new Pos(2, 0)));
        Assert.assertTrue(b.validMove(new Pos(2, 0), new Pos(0, 0)));
        Assert.assertTrue(b.validMove(new Pos(0, 0), new Pos(2, 0)));
    }

    @Test
    public void validGlobalMove() {
        UltBoard b = new UltBoard(2, 2, 2);
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 1)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 1)));

        b.getBoard(0, 0).makeMove(0, 0, UltBoard.X); // mv 1
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 1)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 1)));

        b.getBoard(0, 0).makeMove(1, 0, UltBoard.X);// mv 2
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 1)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 1)));

        b.getBoard(0, 0).makeMove(0, 1, UltBoard.X); // mv 3
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 1)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 1)));

        b.getBoard(0, 0).makeMove(1, 1, UltBoard.X);// filled!
        Assert.assertFalse(b.validGlobalMove(new Pos(0, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 0)));
        Assert.assertTrue(b.validGlobalMove(new Pos(0, 1)));
        Assert.assertTrue(b.validGlobalMove(new Pos(1, 1)));
    }

    private static void playerWin(UltBoard m, int gx, int gy, int player) {
        // assumes 3, 3, 3 board
        m.getBoard(gx, gy).makeMove(0,0, player);
        m.getBoard(gx, gy).makeMove(1,0, player);
        m.getBoard(gx, gy).makeMove(2,0, player);
    }

    @Test
    public void checkWin() {
        // horizontal
        for (int i = 0; i < 3; i ++) {
            UltBoard m = new UltBoard(3, 3, 3);
            int player1 = 1;
            playerWin(m, 0, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(0, 0))); // no win
            playerWin(m, 1, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(1, 0))); // no win
            playerWin(m, 2, 0, player1);
            Assert.assertTrue(m.checkWin(new Pos(i, 0)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Pos[] endPts = m.getWinningGlobalEndpoints();
            Pos left = new Pos(0,0);
            Pos right = new Pos(2,0);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // vertical
        for (int i = 0; i < 3; i ++) {
            UltBoard m = new UltBoard(3, 3, 3);
            int player1 = 1;
            playerWin(m, 0, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(0, 0))); // no win
            playerWin(m, 0, 1, player1);
            Assert.assertFalse(m.checkWin(new Pos(0, 1))); // no win
            playerWin(m, 0, 2, player1);
            Assert.assertTrue(m.checkWin(new Pos(0, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Pos[] endPts = m.getWinningGlobalEndpoints();
            Pos left = new Pos(0,0);
            Pos right = new Pos(0,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // diagonal topleft -> bottomright
        for (int i = 0; i < 3; i ++) {
            UltBoard m = new UltBoard(3, 3, 3);
            int player1 = 1;
            playerWin(m, 0, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(0, 0))); // no win
            playerWin(m, 1, 1, player1);
            Assert.assertFalse(m.checkWin(new Pos(1, 1))); // no win
            playerWin(m, 2, 2, player1);
            Assert.assertTrue(m.checkWin(new Pos(i, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Pos[] endPts = m.getWinningGlobalEndpoints();
            Pos left = new Pos(0,0);
            Pos right = new Pos(2,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // diagonal bottom left -> top right
        for (int i = 0; i < 3; i ++) {
            UltBoard m = new UltBoard(3, 3, 3);
            int player1 = 1;
            playerWin(m, 2, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(2, 0))); // no win
            playerWin(m, 1, 1, player1);
            Assert.assertFalse(m.checkWin(new Pos(1, 1))); // no win
            playerWin(m, 0, 2, player1);
            Assert.assertTrue(m.checkWin(new Pos(2 - i, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Pos[] endPts = m.getWinningGlobalEndpoints();
            Pos left = new Pos(2,0);
            Pos right = new Pos(0,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
    }

    @Test
    public void makeMove() {
        UltBoard m = new UltBoard(2, 2, 2);
        Assert.assertFalse(m.makeMove(0, 0, 1, 1, TicTacToeBoard.X)); // move shouldn't win
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.makeMove(1, 1, 0, 0, TicTacToeBoard.O)); // move shouldn't win
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.makeMove(0, 0, 1, 0, TicTacToeBoard.X)); // move shouldn't win
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.makeMove(1, 0, 0, 1, TicTacToeBoard.O)); // move shouldn't win
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.makeMove(0, 1, 1, 1, TicTacToeBoard.X)); // move shouldn't win
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.makeMove(1, 1, 0, 1, TicTacToeBoard.O)); // move shouldn't win
        System.out.println(m.displayGlobalBoardString());
        Assert.assertTrue(m.makeMove(0, 1, 0, 1, TicTacToeBoard.X)); // move SHOULD win
        System.out.println(m.displayGlobalBoardString());
        try {
            m.makeMove(0, 0, 1, 1, TicTacToeBoard.X);
            Assert.fail();
        } catch (RuntimeException e) {

        }
    }

    @Test
    public void isFilled() {
        UltBoard m = new UltBoard(2, 2, 2);

        // 0 0
        Assert.assertFalse(m.makeMove(0, 0, 0, 0, TicTacToeBoard.X));
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        Assert.assertFalse(m.makeMove(0, 0, 0, 1, TicTacToeBoard.X));
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        Assert.assertFalse(m.makeMove(0, 0, 1, 0, TicTacToeBoard.X));
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        Assert.assertFalse(m.makeMove(0, 0, 1, 1, TicTacToeBoard.X));
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());

        // 1 0
        Assert.assertFalse(m.makeMove(1, 0, 0, 0, TicTacToeBoard.X));
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(1, 0, 0, 1, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(1, 0, 1, 0, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(1, 0, 1, 1, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());

        // 0 1
        m.makeMove(0, 1, 0, 0, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(0, 1, 0, 1, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(0, 1, 1, 0, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(0, 1, 1, 1, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());

        // 1 0
        m.makeMove(1, 1, 0, 0, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(1, 1, 0, 1, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(1, 1, 1, 0, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertFalse(m.isFilled());
        m.makeMove(1, 1, 1, 1, TicTacToeBoard.X);
        System.out.println(m.displayGlobalBoardString());
        Assert.assertTrue(m.isFilled());
    }
}