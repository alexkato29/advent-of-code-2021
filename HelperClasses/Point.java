package Advent2021.HelperClasses;

public class Point {
    public static final int UNDEFINED = Integer.MAX_VALUE;
    public int x;
    public int y;

    public Point(String x, String y) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Returns true if this point is left of p. If it's equally left, returns true if this point is below p
    public boolean isGreater(Point p) {
        double left = x - p.x;
        if (left == 0)
            return y > p.y;
        return left > 0;

    }

    public boolean equals(Point p) {
        return (this.x == p.x) && (this.y == p.y);
    }

    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
