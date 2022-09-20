package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class day20 {
    private static final int ENCHANCEMENTS = 50;
    private static char[] key;
    private static ArrayList<ArrayList<Character>> image = new ArrayList<>();

    public static void parseInput() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day20.txt"));
        key = in.nextLine().toCharArray();
        in.nextLine();

        while (in.hasNextLine()) {
            ArrayList<Character> temp = new ArrayList<>();
            for (char c : in.nextLine().toCharArray())
                temp.add(c);
            image.add(temp);
        }

        growImage('.');
    }

    public static void growImage(char c) {
        for (ArrayList<Character> line : image) {
            line.add(c);
            line.add(c);
            line.add(0, c);
            line.add(0, c);
        }


        int lineSize = image.get(0).size();
        ArrayList<Character> temp = new ArrayList<>();

        for (int i = 0; i < lineSize; i++) {
            temp.add(c);
        }

        image.add(new ArrayList<>(temp));
        image.add(new ArrayList<>(temp));
        image.add(0, new ArrayList<>(temp));
        image.add(0, new ArrayList<>(temp));
    }

    public static void showPic() {
        for (ArrayList<Character> line : image) {
            System.out.println(line);
        }
    }

    public static char enhance(String s) {
        int index = 0;
        for (int i = s.length() - 1; i >= 0; i--)
            if (s.charAt(i) == '#') {
                index += Math.pow(2, 8 - i);
            }
        return key[index];
    }

    public static void enhanceEdges() {
        if (key[0] == '.') {
            growImage('.');
            return;
        }


        char c;
        if (image.get(0).get(0) == '#')
            c = '.';
        else
            c = '#';

        for (int i = 0; i < image.get(0).size(); i++) {
            image.get(0).set(i, c);
        }

        for (int i = 0; i < image.get(0).size(); i++) {
            image.get(i).set(0, c);
        }

        for (int i = 0; i < image.get(0).size(); i++) {
            image.get(image.size() - 1).set(i, c);
        }

        for (int i = 0; i < image.get(0).size(); i++) {
            image.get(i).set(image.get(0).size() - 1, c);
        }

        growImage(c);
    }

    public static void enhanceImage() {
        ArrayList<ArrayList<Character>> temp = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            temp.add(new ArrayList<>());
            for (int j = 0; j < image.get(i).size(); j++) {
                temp.get(i).add(image.get(i).get(j));
            }
        }

        for (int i = 1; i < image.size() - 1; i++) {
            for (int j = 1; j < image.get(i).size() - 1; j++) {
                StringBuilder sb = new StringBuilder();
                for (int x = -1; x <= 1; x++)
                    for (int y = -1; y <= 1; y++)
                        sb.append(image.get(i + x).get(j + y));

                temp.get(i).set(j, enhance(sb.toString()));
            }
        }

        image = temp;
        //Change edges from . to # or vice versa
        enhanceEdges();
    }

    public static void part1() {
        int count = 0;
        for (ArrayList<Character> line : image) {
            for (char c : line) {
                if (c == '#')
                    count++;
            }
        }
        System.out.println(count);
    }

    public static void main(String[] args) throws Exception {
        parseInput();

        for (int i = 0; i < ENCHANCEMENTS; i++) {
            enhanceImage();
        }

        part1();
    }
}
