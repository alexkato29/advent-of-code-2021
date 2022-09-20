package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class day10 {
    public static void main(String[] args) throws FileNotFoundException {
        HashMap<Character, Integer> points = new HashMap<>();
        HashMap<Character, Integer> points2 = new HashMap<>();
        HashMap<Character, Character> cor = new HashMap<>();

        points.put(')', 3);
        points.put(']', 57);
        points.put('}', 1197);
        points.put('>', 25137);
        points2.put('(', 1);
        points2.put('[', 2);
        points2.put('{', 3);
        points2.put('<', 4);
        cor.put(')', '(');
        cor.put(']', '[');
        cor.put('}', '{');
        cor.put('>', '<');

        int penalty = 0;
        ArrayList<Long> scores = new ArrayList<>();

        Scanner in = new Scanner(new FileReader("advent-2021-input/day10.txt"));

        while (in.hasNextLine()) {
            Stack<Character> stack = new Stack<>();
            String s = in.nextLine();
            char[] line = s.toCharArray();

            for (char c : line) {
                if (cor.containsValue(c))
                    stack.push(c);
                else {
                    if (stack.pop() != cor.get(c)) {
                        penalty += points.get(c);
                        stack.clear();
                        break;
                    }
                }
            }

            long score = 0;
            while (!stack.empty()) {
                score *= 5;
                score += points2.get(stack.pop());
            }

            if (score != 0)
                scores.add(score);
        }

        Collections.sort(scores);
        System.out.println(scores);
        System.out.println(scores.get(scores.size()/2));
    }
}
