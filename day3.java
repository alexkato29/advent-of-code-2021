package Advent2021;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day3 {
    public static void main(String[] args) throws IOException {
        // Part 1
        BufferedReader br = new BufferedReader(new FileReader("advent-2021-input/day3.txt"));
        String s = br.readLine();
        int lineSize = s.length(), temp = 0;
        double totalLines = 1;

        int[] gamma = new int[lineSize];
        int[] epsilon = new int[lineSize];
        char[] input = new char[lineSize];

        while (s != null) {
            input = s.toCharArray();
            for (int i = 0; i < lineSize; i++) {
                temp = Character.getNumericValue(input[i]);
                gamma[i] += temp;
            }
            totalLines++;
            s = br.readLine();
        }

        for (int i = 0; i < gamma.length; i++) {
            gamma[i] = (int) (gamma[i] / totalLines + 0.5);
            epsilon[i] = 1 - gamma[i];
        }

        // Part 2
        StringBuilder o2Temp = new StringBuilder();
        StringBuilder co2Temp = new StringBuilder();

        br = new BufferedReader(new FileReader("advent-2021-input/day3.txt"));
        s = br.readLine();
        lineSize = s.length();
        int common1 = 0, common2 = 0;
        boolean go1 = true, go2 = true;
        String recent1 = "", recent2 = "";

        for (int i = 0; i < lineSize; i++) {
            int total1 = 0, total2 = 0;
            double totalLines1 = 0, totalLines2 = 0;
            while (s != null) {
                if (go1 && o2Temp.indexOf(s.substring(0, i)) != -1) {
                    total1 += Character.getNumericValue(s.charAt(i));
                    totalLines1++;
                    recent1 = s;
                }
                if (go2 && co2Temp.indexOf(s.substring(0, i)) != -1) {
                    total2 += Character.getNumericValue(s.charAt(i));
                    totalLines2++;
                    recent2 = s;
                }
                s = br.readLine();
            }

            common1 = (int) (total1 / totalLines1 + 0.5);
            common2 = (int) (total2 / totalLines2 + 0.5);

            if (go1)
                o2Temp.append(common1);
            else
                o2Temp = new StringBuilder(recent1);
            if (go2)
                co2Temp.append(1 - common2);
            else
                co2Temp = new StringBuilder(recent2);

            go1 = totalLines1 != 1;
            go2 = totalLines2 != 1;

            br = new BufferedReader(new FileReader("advent-2021-input/day3.txt"));
            s = br.readLine();
        }

        int[] o2 = new int[lineSize];
        int[] co2 = new int[lineSize];
        char[] t1 = o2Temp.toString().toCharArray();
        char[] t2 = co2Temp.toString().toCharArray();

        for (int i = 0; i < lineSize; i++) {
            o2[i] = Character.getNumericValue(t1[i]);
            co2[i] = Character.getNumericValue(t2[i]);
        }

        System.out.println(base2to10(o2, 0) * base2to10(co2, 0));
    }

    public static int base2to10(int[] num, int bit) {
        if (bit == num.length - 1) {
            return num[num.length - 1];
        }
        return base2to10(num, bit + 1) + (int) Math.pow(2, num.length - bit - 1) * num[bit];
    }
}
