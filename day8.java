package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class day8 {
    private static final int NUMKEYS = 10;
    private static final int NUMRES = 4;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day8.txt"));
        String s = in.nextLine();

        // Part 1
        int count = 0;
        ArrayList<Integer> looking = new ArrayList<>(Arrays.asList(1, 4, 7, 8));

        // Part 2
        int total = 0;

        while (true) {
            String[] keys = new String[NUMKEYS];
            String[] results = new String[NUMRES];

            String[] divide = s.split(" ");

            for (int i = 0; i < divide.length; i++) {
                if (i < NUMKEYS)
                    keys[i] = divide[i];
                else if (i > NUMKEYS)
                    results[i - NUMKEYS - 1] = divide[i];
            }

            specialSort(keys);
            // Have to sort the results differently as to not mess up the order of the code
            for (int i = 0; i < results.length; i++) {
                char[] temp = results[i].toCharArray();
                Arrays.sort(temp);
                results[i] = new String(temp);
            }

            ArrayList<String> key = createKey(keys);

            int x = 0;
            for (String res : results) {
                int num = key.indexOf(res);
                if (looking.contains(num))
                    count++;
                x += num;
                x *= 10;
            }

            total += x / 10;

            if (!in.hasNextLine())
                break;
            s = in.nextLine();
        }

        // System.out.println(count);
        System.out.println(total);
    }

    // Algorithm makes two components to discern which one is which
    /*
    0: 6 Letter w/0 part2
    1: Only two letter
    2: 5 Letter that isn't 3 or 5
    3: 5 Letter w/ part1
    4: Only four letter
    5: 5 Letter w/ part2
    6: 6 Letter w/o part 1
    7: Only three letter
    8: Only seven letter
    9: 6 Letter that isn't 0 or 6

    part1: |
           |

    part2: |__
     */
    public static ArrayList<String> createKey(String[] input) {
        ArrayList<String> toRet = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            toRet.add("");
        ArrayList<String> fives = new ArrayList<>();
        ArrayList<String> sixes = new ArrayList<>();

        String part1 = input[0];
        String part2 = remove(input[2], part1);

        toRet.set(1, input[0]);
        toRet.set(4, input[2]);
        toRet.set(7, input[1]);
        toRet.set(8, input[9]);

        for (String s : input) {
            if (s.length() == 5)
                fives.add(s);
            else if (s.length() == 6)
                sixes.add(s);
        }

        for (String five : fives) {
            if (remove(five, part2).length() == 3)
                toRet.set(5, five);
            else if (remove(five, part1).length() == 3)
                toRet.set(3, five);
            else
                toRet.set(2, five);
        }

        for (String six : sixes) {
            if (remove(six, part2).length() != 4)
                toRet.set(0, six);
            else if (remove(six, part1).length() != 4)
                toRet.set(6, six);
            else
                toRet.set(9, six);
        }

        return toRet;
    }

    public static void specialSort(String[] s) {
        for (int i = 0; i < s.length; i++) {
            char[] temp = s[i].toCharArray();
            Arrays.sort(temp);
            s[i] = new String(temp);
        }

        Arrays.sort(s, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
    }

    // Returns str1 "minus" str2
    public static String remove(String str1, String str2) {
        StringBuilder sb = new StringBuilder(str1);
        for (char ch : str2.toCharArray()) {
            int i = sb.indexOf(String.valueOf(ch));
            if (i >= 0) {
                sb.deleteCharAt(i);
            }
        }
        return sb.toString();
    }
}
