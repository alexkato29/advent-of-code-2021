package Advent2021;

import Advent2021.HelperClasses.Point;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class day15 {
    private static final int REPEAT = 5;
    private static final int TRUELEN = 100;
    private static final int GRIDSIZE = TRUELEN * REPEAT; // Change for part 1 or 2
    private static int[][] grid = new int[GRIDSIZE][GRIDSIZE];
    private static int[][] costs = new int[GRIDSIZE][GRIDSIZE];

    public static void parseInput () throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day15.txt"));
        int counter = 0;

        while (in.hasNextLine()) {
            String[] s = in.nextLine().split("");
            for (int i = 0; i < s.length; i++)
                grid[counter][i] = Integer.parseInt(s[i]);
            counter++;
        }
    }

    public static void parseInput2() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day15.txt"));
        int counter = 0;

        while (in.hasNextLine()) {
            String[] s = in.nextLine().split("");
            for (int i = 0; i < s.length; i++)
                for (int yFac = 0; yFac < REPEAT; yFac++) {
                    for (int xFac = 0; xFac < REPEAT; xFac++) {
                        int num = Integer.parseInt(s[i]) + yFac + xFac;
                        if (num > 9)
                            grid[counter + (TRUELEN * yFac)][i + (TRUELEN * xFac)] = (num) % 9;
                        else
                            grid[counter + (TRUELEN * yFac)][i + (TRUELEN * xFac)] = num;

                    }
                }
            counter++;
        }
    }

    public static void printArray(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++)
                System.out.print(a[i][j]);
            System.out.println();
        }
    }

    /*
    HOW THE ALGO WORKS: we are guaranteed to start at (0, 0). We also know that our first move must be down or left
    as these are the only valid directions. You also know that you MUST make a move somewhere at any given point.
    That said, the only moves we are guaranteed to make are the absolute lowest cost moves. To find lowest cost way
    of reaching position (x, y), we use the following algorithm:

    1) Create a list of nodes we currently can get to
    2) Add the starting position to this list
    3) For every node in the list, calculate the cost of moving to all of its neighbors
    4) Make the move (or moves) that have the absolute lowest cost of all the nodes
    5) Add these new nodes to the list
    6) Remove a node from the list when all four of its neighboring nodes are either nonexistent or already reached
    7) OPTIONAL: Stop when you reach the end. Backtrack along the lowest cost route (greedily) to get the optimal path
     */
    public static void findCosts() {
        ArrayList<Point> currentNodes = new ArrayList<>();
        Point end = new Point(GRIDSIZE - 1, GRIDSIZE - 1);

        for (int i = 0; i < GRIDSIZE; i++)
            for (int j = 0; j < GRIDSIZE; j++)
                costs[i][j] = -1;

        costs[0][0] = 0;

        currentNodes.add(new Point(0, 0));

        while (!currentNodes.isEmpty()) {
            ArrayList<Point> tempRem = new ArrayList<>();
            int lowVal = Integer.MAX_VALUE;
            int cost;

            Point lowPos = new Point(-1, -1);

            for (Point p : currentNodes) {

                int numFailedNeighbors = 0;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (isValid(i, j, p) == -1) {
                            cost = grid[p.y + i][p.x + j] + costs[p.y][p.x];
                            if (cost < lowVal) {
                                lowVal = cost;
                                lowPos = new Point(p.x + j, p.y + i);
                            }
                        } else {
                            numFailedNeighbors++;
                        }
                    }
                }

                if (numFailedNeighbors == 9)
                    tempRem.add(p);
            }
            costs[lowPos.y][lowPos.x] = lowVal;
            currentNodes.add(lowPos);
            currentNodes.removeAll(tempRem);
            if (lowPos.equals(end))
                break;
        }
    }

    public static int isValid(int y, int x, Point p) {

        boolean notDiag = !(Math.abs(x) == Math.abs(y));

        y += p.y;
        x += p.x;

        boolean validY = y >= 0 && y < GRIDSIZE;
        boolean validX = x >= 0 && x < GRIDSIZE;

        if (validY && validX && notDiag)
            return costs[y][x];

        return -2;
    }

    public static int calcCost() {
        int cost = grid[GRIDSIZE - 1][GRIDSIZE - 1];
        Point cur = new Point(GRIDSIZE - 1, GRIDSIZE - 1);

        while (cur.x != 0 || cur.y != 0) {
            int lowest = Integer.MAX_VALUE;
            Point temp = new Point(-10, -10);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (isValid(i, j, cur) >= 0) {
                        int val = costs[cur.y + i][cur.x + j];
                        if (val < lowest && val != -1) {
                            temp = new Point(cur.x + j, cur.y + i);
                            lowest = val;
                        }
                    }
                }
            }
            cur = temp;
            cost += grid[cur.y][cur.x];

        }

        cost -= grid[0][0];
        return cost;
    }

    public static void main(String[] args) throws Exception {
        parseInput2();
        findCosts();
        System.out.println(calcCost());
    }
}
