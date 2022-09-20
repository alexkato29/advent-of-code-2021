package Advent2021;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class day1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("advent-2021-input/day1.txt"));
        ArrayList<Integer> nums = new ArrayList<>();
        String s = br.readLine();

        while (s != null) {
            nums.add(Integer.parseInt(s));
            s = br.readLine();
        }

        // Part 1
        int counter = 0;
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) > nums.get(i - 1))
                counter++;
        }

        // Part 2
        int windowSize = 3;
        counter = 0;
        for (int i = 0; i < nums.size() - windowSize; i++) {
            counter += nums.get(i + windowSize) > nums.get(i) ? 1 : 0;
        }

        System.out.println(counter);
    }
}
