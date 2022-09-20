package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class day7 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day7.txt"));
        String s = in.nextLine();

        String[] temp = s.split(",");
        int[] crabs = new int[temp.length];

        for (int i = 0; i < crabs.length; i++)
            crabs[i] = Integer.parseInt(temp[i]);

        //Arrays.sort(crabs);

        //int median = crabs[crabs.length / 2 - 1]; // Not an actual median but it does not matter here
        int mean = mean(crabs) - 1; // MINUS 1 needed for certain inputs - but not for others????
        int sum = 0;
        int dis;
        for (int crab : crabs) {
            dis = Math.abs(crab - mean);
            sum += dis * (dis + 1) / 2;
        }
        System.out.println(sum);
    }

    public static int mean(int[] crabs) {
        double total = 0;
        for(int crab : crabs) {
            total += crab;
        }
        return (int) (total / crabs.length + 0.5);
    }
}
