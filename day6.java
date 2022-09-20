package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class day6 {
    private static final int CYCLE = 9;
    private static final int DAYS = 256;
    public static void main(String[] args) throws FileNotFoundException {
        long[] lanternfish = new long[CYCLE];
        Scanner in = new Scanner(new FileReader("advent-2021-input/day6.txt"));
        String s = in.nextLine();

        String[] initialFish = s.split(",");

        for (String fish : initialFish) {
            int daysLeft = Integer.parseInt(fish);
            lanternfish[daysLeft]++;
        }

        for (int i = 0; i < DAYS; i++) {
            long[] temp = new long[CYCLE];
            for (int j = CYCLE - 1; j > 0; j--) {
                temp[j - 1] = lanternfish[j];
            }
            temp[CYCLE - 1] += lanternfish[0];
            temp[CYCLE - 3] += lanternfish[0];

            lanternfish = temp;
        }

        long total = 0;
        for (long c : lanternfish)
            total += c;

        System.out.println(total);
    }
}
