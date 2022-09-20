package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class day9 {
    public static ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> basinMap = new ArrayList<ArrayList<Integer>>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day9.txt"));
        String s = in.nextLine();
        int counter = 0;

        while (true) {
            grid.add(new ArrayList<>());

            for (char x : s.toCharArray()) {
                grid.get(counter).add(Character.getNumericValue(x));
            }

            if (!in.hasNextLine())
                break;
            s = in.nextLine();
            counter++;
        }

        // System.out.println(getLowPoints());
        System.out.println(getBasins());
    }

    // Only works for square grids
    public static int getLowPoints() {
        int sum = 0, point;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                point = grid.get(i).get(j);
                sum += isLowPoint(point, j, i) ? point + 1 : 0;
            }
        }

        return sum;
    }

    public static boolean isLowPoint(int point, int x, int y) {
        int up = y - 1;
        int down = y + 1;
        int left = x - 1;
        int right = x + 1;

        if (up >= 0)
            if (point >= grid.get(up).get(x))
                return false;
        if (down <= grid.size() - 1)
            if (point >= grid.get(down).get(x))
                return false;
        if (left >= 0)
            if (point >= grid.get(y).get(left))
                return false;
        if (right <= grid.get(y).size() - 1)
            if (point >= grid.get(y).get(right))
                return false;

        return true;

    }

    public static int getBasins() {
        for (int i = 0; i < grid.size(); i++) {
            basinMap.add(new ArrayList<>());
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) != 9)
                    basinMap.get(i).add(0);
                else
                    basinMap.get(i).add(1);
            }
        }

        int first = 0, second = 0, third = 0;
        int basinSize;

        for (int i = 0; i < basinMap.size(); i++) {
            for (int j = 0; j < basinMap.get(i).size(); j++) {
                if (basinMap.get(i).get(j) == 0) {
                    basinSize = dfs(j, i, 0);
                    if (basinSize > first) {
                        third = second;
                        second = first;
                        first = basinSize;
                    } else if (basinSize > second) {
                        third = second;
                        second = basinSize;
                    } else if (basinSize > third)
                        third = basinSize;
                }
            }
        }

        return first * second * third;
    }

    // Only works for square grids
    // Breadth first search probably would have been faster
    public static int dfs(int x, int y, int total) {
        basinMap.get(y).set(x, 1);
        total++;

        int up = y - 1;
        int down = y + 1;
        int left = x - 1;
        int right = x + 1;

        if (up >= 0 && basinMap.get(up).get(x) == 0)
            total = dfs(x, up, total);
        if (down <= basinMap.size() - 1 && basinMap.get(down).get(x) == 0)
            total = dfs(x, down, total);
        if (left >= 0 && basinMap.get(y).get(left) == 0)
            total = dfs(left, y, total);
        if (right <= basinMap.get(y).size() - 1 && basinMap.get(y).get(right) == 0)
            total = dfs(right, y, total);

        return total;
    }
}
