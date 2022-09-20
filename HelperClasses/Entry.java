package Advent2021.HelperClasses;

public class Entry {
    public int val;
    public String dir;

    public Entry(String dir, int val) {
        this.val = val;
        this.dir = dir;
    }

    public String toString() {
        return "Fold Along " + dir + " = " + val;
    }
}