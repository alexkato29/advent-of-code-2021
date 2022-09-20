package Advent2021;

import Advent2021.HelperClasses.Point;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class day5 {
    // Board size is known to be 1000
    private static final int GRIDSIZE = 1000;

    public static void main(String[] args) throws FileNotFoundException {
        int[][] grid = new int[GRIDSIZE][GRIDSIZE];
        int overlaps = 0;

        Scanner in = new Scanner(new FileReader("advent-2021-input/day5.txt"));
        String s = in.nextLine();

        while (true) {
            String[] points = s.split("->");
            Point p1 = new Point(points[0].split(",")[0].strip(), points[0].split(",")[1].strip());
            Point p2 = new Point(points[1].split(",")[0].strip(), points[1].split(",")[1].strip());

            if (!p2.isGreater(p1)) {
                Point temp = new Point(p1.x, p1.y);
                p1 = new Point(p2.x, p2.y);
                p2 = temp;
            }

            double slope = slope(p1, p2);

            while (!p1.equals(p2)) {
                grid[p1.x][p1.y] += 1;
                if (grid[p1.x][p1.y] == 2)
                    overlaps++;
                increment(p1, slope);
            }
            grid[p1.x][p1.y] += 1;
            if (grid[p1.x][p1.y] == 2)
                overlaps++;

            if (!in.hasNextLine())
                break;
            s = in.nextLine();
        }

        System.out.println(overlaps);
    }

    // Returns slope between two points or UNDEFINED if the slope does not exist
    private static double slope(Point p1, Point p2) {
        double delX = p1.x - p2.x;
        double delY = p1.y - p2.y;
        if (delX != 0)
            return delY / delX;
        return Point.UNDEFINED;
    }


    private static void increment(Point p, double slope) {
        if (slope != Point.UNDEFINED) {
            if (slope != 0 && slope < 1 && slope > -1) {
                p.x += 1/slope;
                p.y += 1;
            } else {
                p.x += 1;
                p.y += slope;
            }
        } else {
            p.y += 1;
        }
    }
}
