package Advent2021;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day2 {
    public static void main(String[] args) throws IOException {
        int vert = 0, hor = 0;
        BufferedReader br = new BufferedReader(new FileReader("advent-2021-input/day2.txt"));
        ArrayList<Integer> nums = new ArrayList<>();
        String s = br.readLine();
        char start = ' ';
        int inc = 0;


        /* Part 1
        while (s != null) {
            start = s.charAt(0);
            inc = Integer.parseInt(Character.toString(s.charAt(s.length() - 1)));
            if (start == 'f') {
               hor += inc;
            } else {
                vert += start == 'u' ? -1 * inc : inc;
            }
            s = br.readLine();
        }
        */

        // Part 2
        int aim = 0;
        while (s != null) {
            start = s.charAt(0);
            inc = Integer.parseInt(Character.toString(s.charAt(s.length() - 1)));
            if (start == 'f') {
                hor += inc;
                vert += aim * inc;
            } else {
                aim += start == 'u' ? -1 * inc : inc;
            }
            s = br.readLine();
        }
        System.out.println(vert * hor);
    }
}
