package Advent2021;

import Advent2021.HelperClasses.Point;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class day17 {
    private static int xLow;
    private static int xHigh;
    private static int yLow;
    private static int yHigh;

    public static void parseBounds() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day17.txt"));
        String[] s = in.nextLine().substring(13).split(", ");

        // I don't fully understand these regex. The purpose of the [2] is because the regex splits three (??) times
        xLow = Integer.parseInt(s[0].split("[..]")[0].substring(2));
        xHigh = Integer.parseInt(s[0].split("[..]")[2]);
        yLow = Integer.parseInt(s[1].split("[..]")[0].substring(2));
        yHigh = Integer.parseInt(s[1].split("[..]")[2]);
    }

    public static boolean inTarget(Point p) {
        return p.x >= xLow && p.x <= xHigh && p.y >= yLow && p.y <= yHigh;
    }

    public static boolean overshoot(Point p) {
        return p.x > xHigh || p.y < yLow;
    }

    public static Point[] makeStep(Point pos, Point vel) {
        Point newPos = new Point(pos.x + vel.x, pos.y + vel.y);
        Point newVel = new Point(vel.x, vel.y - 1);

        if (vel.x != 0) {
            if (vel.x < 0)
                newVel.x += 1;
            else
                newVel.x -= 1;
        }

        return new Point[] {newPos, newVel};
    }

    public static int part1(int yVel) {
        return (int) ((Math.pow(yVel, 2) + yVel) / 2.0);
    }

    public static boolean part2(Point vel) {
        Point pos = new Point(0, 0);

        while (!overshoot(pos)) {
            Point[] res = makeStep(pos, vel);
            pos = res[0];
            vel = res[1];

            if (inTarget(pos)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        parseBounds();
        /*
        Part 1
        xVel is the solution to xMax = Sum(k) when k is n (solving for n) rounded down.
        This essentially maximizes the number of turns we have to move upward while not overshooting the target.

        yVel is abs(yMin + 1). Not all y-values are attainable from the origin. This is because adding 1 to yVel
        increases max height by yVel + 1, NOT by 1. Thus, due to the nature of the yVel decay being -1, any probe
        launched from y=0 will eventually re-hit y=0. We also want to maximize falling speed, as maximum falling speed
        implies a maximum attained height. Thus, when we hit y=0 AGAIN, our falling speed should be such that we exactly
        hit the bottom row of the target space. This means our falling speed will be yMin. However, we are going
        negative one step, so the actual height will be Sum(k) n = yMin + 1.
        */
        int maxYVel = Math.abs(yLow + 1);

        System.out.println(part1(maxYVel));

        /*
        Part 2
        xVel has a range between the minimum xVel (see part 1 - except xMin) and the xHigh of the target zone (a direct shot).
        yVel has a range between the minimum yVel (a direct shot) and the maximum yVel (see part 1)

        Try all of these points. Number that hit is the solution.
         */

        int minXVel = (int) ((-1 + Math.sqrt(1 + (4 * xLow * 2))) / 2.0);

        int totalHits = 0;

        for (int x = minXVel; x <= xHigh; x++) {
            for (int y = yLow; y <= maxYVel; y++) {
                Point vel = new Point(x, y);
                if (part2(vel))
                    totalHits++;
            }
        }

        System.out.println(totalHits);
    }
}
