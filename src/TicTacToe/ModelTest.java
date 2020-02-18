package TicTacToe;

import net.sf.saxon.trans.Mode;
import org.junit.Assert;
import org.junit.Test;

public class ModelTest {

    @Test
    public void boardSetGet() {
        Model m = new Model(3, 3, 3);
        int player1 = 1;

        // check 1
        Model.Pos pos = new Model.Pos(0, 0);
        Assert.assertTrue(m.boardGet(pos) == 0);
        m.boardSet(pos, player1);
        Assert.assertTrue(m.boardGet(pos) == player1);

        // check 2
        int player2 = 2;
        pos = new Model.Pos(2, 2);
        Assert.assertTrue(m.boardGet(pos) == 0);
        m.boardSet(pos, player2);
        Assert.assertTrue(m.boardGet(pos) == player2);

        // check consistency
        Assert.assertTrue(m.boardGet(0, 0) == player1);
    }

    @Test
    public void validPos() {
        Model m = new Model(3, 3, 3);
        // 1. is a valid place on the board
        // corners True (0 index)
        Assert.assertTrue(m.validPos(new Model.Pos(0, 0)));
        Assert.assertTrue(m.validPos(new Model.Pos(2, 0)));
        Assert.assertTrue(m.validPos(new Model.Pos(0, 2)));
        Assert.assertTrue(m.validPos(new Model.Pos(2, 2)));

        // invalid, N/E/S/W
        Assert.assertFalse(m.validPos(new Model.Pos(1, -1)));
        Assert.assertFalse(m.validPos(new Model.Pos(3, 1)));
        Assert.assertFalse(m.validPos(new Model.Pos(1, 3)));
        Assert.assertFalse(m.validPos(new Model.Pos(-1, 1)));
    }

    @Test
    public void validMove() {
        Model m = new Model(3, 3, 3);
        // 1. is a valid place on the board
        // corners True (0 index)
        Assert.assertTrue(m.validMove(new Model.Pos(0, 0)));
        Assert.assertTrue(m.validMove(new Model.Pos(2, 0)));
        Assert.assertTrue(m.validMove(new Model.Pos(0, 2)));
        Assert.assertTrue(m.validMove(new Model.Pos(2, 2)));

        // invalid, N/E/S/W
        Assert.assertFalse(m.validMove(new Model.Pos(1, -1)));
        Assert.assertFalse(m.validMove(new Model.Pos(3, 1)));
        Assert.assertFalse(m.validMove(new Model.Pos(1, 3)));
        Assert.assertFalse(m.validMove(new Model.Pos(-1, 1)));

        // 2. isn't occupied
        m.boardSet(0, 0, 1);
        m.boardSet(2, 0, 1);

        // corners again
        Assert.assertFalse(m.validMove(new Model.Pos(0, 0)));
        Assert.assertFalse(m.validMove(new Model.Pos(2, 0)));
        Assert.assertTrue(m.validMove(new Model.Pos(0, 2)));
        Assert.assertTrue(m.validMove(new Model.Pos(2, 2)));
    }

    @Test
    public void checkWin() {
        // horizontal
        for (int i = 0; i < 3; i ++) {
            Model m = new Model(3, 3, 3);
            int player1 = 1;
            m.boardSet(0, 0, player1);
            Assert.assertFalse(m.checkWin(new Model.Pos(0, 0))); // no win
            m.boardSet(1, 0, player1);
            Assert.assertFalse(m.checkWin(new Model.Pos(1, 0))); // no win
            m.boardSet(2, 0, player1);
            Assert.assertTrue(m.checkWin(new Model.Pos(i, 0)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Model.Pos[] endPts = m.getWinningEndpoints();
            Model.Pos left = new Model.Pos(0,0);
            Model.Pos right = new Model.Pos(2,0);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // vertical
        for (int i = 0; i < 3; i ++) {
            Model m = new Model(3, 3, 3);
            int player1 = 1;
            m.boardSet(0, 0, player1);
            Assert.assertFalse(m.checkWin(new Model.Pos(0, 0))); // no win
            m.boardSet(0, 1, player1);
            Assert.assertFalse(m.checkWin(new Model.Pos(0, 1))); // no win
            m.boardSet(0, 2, player1);
            Assert.assertTrue(m.checkWin(new Model.Pos(0, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Model.Pos[] endPts = m.getWinningEndpoints();
            Model.Pos left = new Model.Pos(0,0);
            Model.Pos right = new Model.Pos(0,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // diagonal topleft -> bottomright
        for (int i = 0; i < 3; i ++) {
            Model m = new Model(3, 3, 3);
            int player1 = 1;
            m.boardSet(0, 0, player1);
            Assert.assertFalse(m.checkWin(new Model.Pos(0, 0))); // no win
            m.boardSet(1, 1, player1);
            Assert.assertFalse(m.checkWin(new Model.Pos(1, 1))); // no win
            m.boardSet(2, 2, player1);
            Assert.assertTrue(m.checkWin(new Model.Pos(i, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Model.Pos[] endPts = m.getWinningEndpoints();
            Model.Pos left = new Model.Pos(0,0);
            Model.Pos right = new Model.Pos(2,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
        // diagonal bottom left -> top right
        for (int i = 0; i < 3; i ++) {
            Model m = new Model(3, 3, 3);
            int player1 = 1;
            m.boardSet(2, 0, player1);
            Assert.assertFalse(m.checkWin(new Model.Pos(2, 0))); // no win
            m.boardSet(1, 1, player1);
            Assert.assertFalse(m.checkWin(new Model.Pos(1, 1))); // no win
            m.boardSet(0, 2, player1);
            Assert.assertTrue(m.checkWin(new Model.Pos(2 - i, i)));
            Assert.assertTrue(m.isWin()); // win variable
            Assert.assertEquals(m.getWinner(), player1); // winner variable
            // end points check
            Model.Pos[] endPts = m.getWinningEndpoints();
            Model.Pos left = new Model.Pos(2,0);
            Model.Pos right = new Model.Pos(0,2);
            Assert.assertTrue(endPts[0].equals(left) || endPts[1].equals(left));
            Assert.assertTrue(endPts[0].equals(right) || endPts[1].equals(right));
        }
    }

    @Test
    public void makeMove() {
        Model m = new Model(3, 3, 3);
        Assert.assertFalse(m.makeMove(Model.X, 1, 1));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(Model.O, 1, 0));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(Model.X, 2, 1));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(Model.O, 0, 1));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(Model.X, 2, 0));
        System.out.println(m.displayBoardString());
        Assert.assertFalse(m.makeMove(Model.O, 0, 2));
        System.out.println(m.displayBoardString());
        Assert.assertTrue(m.makeMove(Model.X, 2, 2));
        System.out.println(m.displayBoardString());
    }
}