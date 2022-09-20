package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class day14 {
    private static StringBuilder polymer;
    private static HashMap<String, Long> numPolymers;
    private static HashMap<String, String> pairs;
    private static final int STEPS = 40;

    public static void main(String[] args) throws Exception {
        parseInput();
        part2();
    }

    public static void part1() {
        for (int step = 0; step < STEPS; step++) {
            int counter = 0;
            while (counter < polymer.length() - 1) {
                String pair = polymer.substring(counter, counter + 2);
                if (!pairs.getOrDefault(pair, "").equals("")) {
                    polymer.insert(++counter, pairs.get(pair));
                }
                counter++;
            }
        }

        HashMap<String, Integer> occurrences = new HashMap<>();

        for (int i = 0; i < polymer.length(); i++) {
            String s = polymer.substring(i, i + 1);
            occurrences.put(s, occurrences.getOrDefault(s,0) + 1);
        }

        int max = 0;
        int min = Integer.MAX_VALUE;

        for (Integer val : occurrences.values()) {
            if (val > max)
                max = val;
            if (val < min)
                min = val;
        }

        System.out.println(occurrences);

        System.out.println(max);
        System.out.println(min);

        System.out.println(max - min);
    }

    public static void part2() {
        // Loop through and quickly simulate each generation
        for (int step = 0; step < STEPS; step++) {
            HashMap<String, Long> tempMap = new HashMap<>();
            for (String pol : numPolymers.keySet()) {
                if (numPolymers.get(pol) > 0) {
                    String pol1 = pol.charAt(0) + pairs.get(pol);
                    String pol2 = pairs.get(pol) + pol.charAt(1);

                    tempMap.put(pol1, tempMap.getOrDefault(pol1, (long) 0) + numPolymers.get(pol));
                    tempMap.put(pol2, tempMap.getOrDefault(pol2, (long) 0) + numPolymers.get(pol));
                }
            }
            numPolymers = tempMap;
        }

        // Convert these to actual letter occurrences
        HashMap<Character, Long> occurrences = new HashMap<>();

        for (String pol : numPolymers.keySet()) {
            char first = pol.charAt(0);
            occurrences.putIfAbsent(first, (long) 0);
            occurrences.put(first, occurrences.get(first) + numPolymers.get(pol));
        }

        // Add one for the NOT double counted first and last character
        char first = polymer.charAt(0);
        char second = polymer.charAt(polymer.length() - 1);

        occurrences.put(first, occurrences.get(first) + 1);
        occurrences.put(second, occurrences.get(second) + 1);

        long max = 0;
        long min = Long.MAX_VALUE;

        for (long val : occurrences.values()) {
            if (val > max)
                max = val;
            if (val < min)
                min = val;
        }

        System.out.println(max - min);
    }

    public static void parseInput() throws FileNotFoundException {
        polymer = new StringBuilder();
        pairs = new HashMap<>();
        numPolymers = new HashMap<>();

        Scanner in = new Scanner(new FileReader("advent-2021-input/day14.txt"));

        // Get first polymer
        String s = in.nextLine();
        polymer.append(s);
        s = in.nextLine();

        while (in.hasNextLine()) {
            s = in.nextLine();
            s = s.replaceAll("\s", "");
            String[] mapping = s.split("->");
            pairs.putIfAbsent(mapping[0], mapping[1]);
            numPolymers.putIfAbsent(mapping[0], (long) 0);
        }

        for (int i = 0; i < polymer.length() - 1; i++) {
            numPolymers.put(polymer.substring(i, i + 2), numPolymers.get(polymer.substring(i, i + 2)) + 1);
        }

        System.out.println(polymer);
    }
}
