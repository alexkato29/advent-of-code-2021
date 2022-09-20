package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class day11 {
    private static final int STEPS = 500;
    private static final int GRIDSIZE = 10;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day11.txt"));
        int[][] octopus = new int[GRIDSIZE][GRIDSIZE];
        int lines = 0;

        // Create 2d input array
        while (in.hasNextLine()) {
            int pos = 0;
            String s = in.nextLine();
            char[] line = s.toCharArray();
            for (char c : line) {
                octopus[lines][pos++] = Character.getNumericValue(c);
            }
            lines++;
        }

        int flashes = 0;
        int thisStep = 0;

        for (int i = 0; i < STEPS; i++) {
            increment(octopus);
            thisStep = chainFlashes(octopus);
            if (thisStep == GRIDSIZE * GRIDSIZE)
                System.out.println(i + 1);
            flashes += thisStep;
            setZeros(octopus);
        }

        System.out.println(flashes);
    }

    public static void increment(int[][] grid) {
        for (int i = 0; i < GRIDSIZE; i++)
            for (int j = 0; j < GRIDSIZE; j++)
                grid[i][j]++;
    }

    public static int chainFlashes(int[][] grid) {
        int flashes = 0;

        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                if (grid[i][j] == 10) {
                    grid[i][j] = 11;
                    flashes++;

                    // Increment dumbos around the flashing one
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            int newX = j + x;
                            int newY = i + y;
                            boolean xInBounds = newX >= 0 && newX < GRIDSIZE;
                            boolean yInBounds = newY >= 0 && newY < GRIDSIZE;

                            if (xInBounds && yInBounds) {
                                if (grid[newY][newX] != 10)
                                    grid[newY][newX]++;
                            }
                        }
                    }
                }
            }
        }


        if (flashes != 0)
            flashes += chainFlashes(grid);

        return flashes;
    }

    public static void setZeros(int[][] grid) {
        for (int i = 0; i < GRIDSIZE; i++)
            for (int j = 0; j < GRIDSIZE; j++)
                if (grid[i][j] > 9)
                    grid[i][j] = 0;
    }

    public static void printArray(int[][] octopus) {
        for(int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++)
                System.out.print(octopus[i][j]);
            System.out.println();
        }
        System.out.println();
    }
}
