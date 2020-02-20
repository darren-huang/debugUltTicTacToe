package UltTicTacToe;

import org.junit.Assert;
import org.junit.Test;

public class TicTacToeBoardTest {

    @Test
    public void boardSetGet() {
        TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
        int player1 = 1;

        // check 1
        Pos pos = new Pos(0, 0);
        Assert.assertTrue(m.get(pos) == 0);
        m.set(pos, player1);
        Assert.assertTrue(m.get(pos) == player1);

        // check 2
        int player2 = 2;
        pos = new Pos(2, 2);
        Assert.assertTrue(m.get(pos) == 0);
        m.set(pos, player2);
        Assert.assertTrue(m.get(pos) == player2);

        // check consistency
        Assert.assertTrue(m.get(0, 0) == player1);
    }

    @Test
    public void validPos() {
        TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
        // 1. is a valid place on the board
        // corners True (0 index)
        Assert.assertTrue(m.validPos(new Pos(0, 0)));
        Assert.assertTrue(m.validPos(new Pos(2, 0)));
        Assert.assertTrue(m.validPos(new Pos(0, 2)));
        Assert.assertTrue(m.validPos(new Pos(2, 2)));

        // invalid, N/E/S/W
        Assert.assertFalse(m.validPos(new Pos(1, -1)));
        Assert.assertFalse(m.validPos(new Pos(3, 1)));
        Assert.assertFalse(m.validPos(new Pos(1, 3)));
        Assert.assertFalse(m.validPos(new Pos(-1, 1)));
    }

    @Test
    public void validMove() {
        TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
        // 1. is a valid place on the board
        // corners True (0 index)
        Assert.assertTrue(m.validMove(new Pos(0, 0)));
        Assert.assertTrue(m.validMove(new Pos(2, 0)));
        Assert.assertTrue(m.validMove(new Pos(0, 2)));
        Assert.assertTrue(m.validMove(new Pos(2, 2)));

        // invalid, N/E/S/W
        Assert.assertFalse(m.validMove(new Pos(1, -1)));
        Assert.assertFalse(m.validMove(new Pos(3, 1)));
        Assert.assertFalse(m.validMove(new Pos(1, 3)));
        Assert.assertFalse(m.validMove(new Pos(-1, 1)));

        // 2. isn't occupied
        m.set(0, 0, 1);
        m.set(2, 0, 1);

        // corners again
        Assert.assertFalse(m.validMove(new Pos(0, 0)));
        Assert.assertFalse(m.validMove(new Pos(2, 0)));
        Assert.assertTrue(m.validMove(new Pos(0, 2)));
        Assert.assertTrue(m.validMove(new Pos(2, 2)));
    }

    @Test
    public void checkWin() {
        // horizontal
        for (int i = 0; i < 3; i ++) {
            TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
            int player1 = 1;
            m.set(0, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(0, 0))); // no win
            m.set(1, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(1, 0))); // no win
            m.set(2, 0, player1);
            Assert.assertTrue(m.checkWin(new Pos(i, 0)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Pos[] endPts = m.getWinningEndpoints();
            Pos left = new Pos(0,0);
            Pos right = new Pos(2,0);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // vertical
        for (int i = 0; i < 3; i ++) {
            TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
            int player1 = 1;
            m.set(0, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(0, 0))); // no win
            m.set(0, 1, player1);
            Assert.assertFalse(m.checkWin(new Pos(0, 1))); // no win
            m.set(0, 2, player1);
            Assert.assertTrue(m.checkWin(new Pos(0, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Pos[] endPts = m.getWinningEndpoints();
            Pos left = new Pos(0,0);
            Pos right = new Pos(0,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // diagonal topleft -> bottomright
        for (int i = 0; i < 3; i ++) {
            TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
            int player1 = 1;
            m.set(0, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(0, 0))); // no win
            m.set(1, 1, player1);
            Assert.assertFalse(m.checkWin(new Pos(1, 1))); // no win
            m.set(2, 2, player1);
            Assert.assertTrue(m.checkWin(new Pos(i, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Pos[] endPts = m.getWinningEndpoints();
            Pos left = new Pos(0,0);
            Pos right = new Pos(2,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // diagonal bottom left -> top right
        for (int i = 0; i < 3; i ++) {
            TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
            int player1 = 1;
            m.set(2, 0, player1);
            Assert.assertFalse(m.checkWin(new Pos(2, 0))); // no win
            m.set(1, 1, player1);
            Assert.assertFalse(m.checkWin(new Pos(1, 1))); // no win
            m.set(0, 2, player1);
            Assert.assertTrue(m.checkWin(new Pos(2 - i, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Pos[] endPts = m.getWinningEndpoints();
            Pos left = new Pos(2,0);
            Pos right = new Pos(0,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
    }

    @Test
    public void makeMove() {
        TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
        Assert.assertFalse(m.makeMove(1, 1, TicTacToeBoard.X));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(1, 2, TicTacToeBoard.O));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(2, 1, TicTacToeBoard.X));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(0, 1, TicTacToeBoard.O));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(2, 2, TicTacToeBoard.X));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(2, 0, TicTacToeBoard.O));
        System.out.println(m.displayBoardString());
        Assert.assertTrue(m.makeMove(0, 0, TicTacToeBoard.X));
        System.out.println(m.displayBoardString());
        try {
            m.makeMove(1, 1, TicTacToeBoard.X);
            Assert.fail();
        } catch (RuntimeException e) {

        }
        // post win checking
        Assert.assertTrue(m.isWin()); // win variable
        Assert.assertEquals(m.getWinner(), TicTacToeBoard.X); // winner variable
        // end points check
        Pos[] endPts = m.getWinningEndpoints();
        Pos left = new Pos(0,0);
        Pos right = new Pos(2,2);
        Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
        Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));

        m.makeMove(0, 2, TicTacToeBoard.O); // after win move
        System.out.println(m.displayBoardString());

        //win parameters shouldn't change
        Assert.assertTrue(m.isWin()); // win variable
        Assert.assertEquals(m.getWinner(), TicTacToeBoard.X); // winner variable
        // end points check
        endPts = m.getWinningEndpoints();
        left = new Pos(0,0);
        right = new Pos(2,2);
        Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
        Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
    }

    @Test
    public void isFilled() {
        TicTacToeBoard m = new TicTacToeBoard(3, 3, 3);
        Assert.assertFalse(m.makeMove(1, 1, TicTacToeBoard.X));
        Assert.assertFalse(m.isFilled());
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(1, 0, TicTacToeBoard.O));
        Assert.assertFalse(m.isFilled());
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(2, 1, TicTacToeBoard.X));
        Assert.assertFalse(m.isFilled());
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(0, 1, TicTacToeBoard.O));
        Assert.assertFalse(m.isFilled());
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(2, 0, TicTacToeBoard.X));
        Assert.assertFalse(m.isFilled());
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(0, 2, TicTacToeBoard.O));
        Assert.assertFalse(m.isFilled());
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(1, 2, TicTacToeBoard.X));
        Assert.assertFalse(m.isFilled());
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(2, 2, TicTacToeBoard.O));
        Assert.assertFalse(m.isFilled());
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(0, 0, TicTacToeBoard.X));
        Assert.assertTrue(m.isFilled());
        System.out.println(m.displayBoardString());

    }
}