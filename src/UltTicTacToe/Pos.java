package UltTicTacToe;

import java.util.Objects;

public class Pos {
    public int x,y;

    public Pos(int x, int y) {
        this.x = x; // moves along x-axis, origin is top-left
        this.y = y; // moves along y-axis, origin is top-left
    }

    //non-destructive
    public Pos plus(Pos other) {
        return new Pos(this.x + other.x, this.y + other.y);
    }

    //non-destructive
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
