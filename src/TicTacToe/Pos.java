package TicTacToe;

import java.util.Objects;

/** Pos class
 *   this a simple object to story x and y coordinate pairs
 *   It has a couple methods for adding & multiplying different x,y pairs
 *
 *   Each Pos object holds a int x and int y which we use to find a position on the
 *   TicTacToe board with board[pos.x][pos.y]
 */
public class Pos {
    public int x,y;

    public Pos(int x, int y) {
        this.x = x; // moves along x-axis, origin is top-left
        this.y = y; // moves along y-axis, origin is top-left
    }

    /** Plus method! (note: non-destructive)
     * adds the x's and y's separately
     *    example: Pos(1,2).plus(Pos(10,10) = Pos(11, 12)
     */
    public Pos plus(Pos other) {
        return new Pos(this.x + other.x, this.y + other.y);
    }

    /** Multiply method! (note: non-destructive)
     * multiplies the x's and y's separately
     *    example: Pos(1,2).plus(Pos(10,10) = Pos(10, 20)
     */
    public Pos times(int i) {
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
