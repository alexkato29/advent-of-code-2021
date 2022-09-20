package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class Pair {
    public Object left;
    public Object right;

    public Pair(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    public Pair(String s) {
        Stack<Pair> stack = new Stack<>();
        boolean right = false;
        Pair last = new Pair(null, null);
        for (int i = 0; i < s.toCharArray().length; i++) {
            char c = s.toCharArray()[i];
            if (c == '[') {
                Pair p = new Pair(null, null);;
                if (right) {
                    stack.peek().right = p;
                    right = false;
                } else {
                    if (stack.size() != 0)
                        stack.peek().left = p;
                }
                stack.push(p);
            } else if (c == ',') {
                right = true;
            } else if (c == ']') {
                right = false;
                last = stack.pop();
            } else {
                if (right) {
                    if (stack.peek().right == null) {
                        stack.peek().right = Character.getNumericValue(c);
                    }
                    else
                        stack.peek().right = (Integer) stack.peek().right * 10 + Character.getNumericValue(c);
                } else {
                    if (stack.peek().left == null) {
                        stack.peek().left = Character.getNumericValue(c);
                    }
                    else
                        stack.peek().left = (Integer) stack.peek().left * 10 + Character.getNumericValue(c);
                }
            }
        }
        this.left = last.left;
        this.right = last.right;
    }

    public static Pair add(Object p1, Object p2) {
        Pair p = new Pair(p1, p2);
        p = reduce(p);
        return p;
    }

    public static Pair reduce(Pair p) {
        p = explode(p);
        p = split(p);
        return p;
    }

    public static Pair explode(Pair p) {
        boolean found = false;
        String eq = p.toString();
        int parens = 0;
        int tempStart = 0, tempEnd = 0, leftVal = 0, rightVal = 0, lengthDiff = 0;
        int[] leftNeighbor = new int[] {0, 0, -1};
        int[] rightNeighbor = new int[] {0, 0, -1};

        for (int i = 0; i < eq.length(); i++) {
            char c = eq.charAt(i);
            if (c == '[')
                parens++;
            else if (c == ']' && parens <= 4)
                parens--;
            else if (c == ']') {
                parens--;
                tempEnd = i;
            } else if (Character.isDigit(c) && parens > 4 && !found) {
                found = true;
                leftVal = getDigit(eq, i)[0];
                tempStart = i - 1;
            } else if (c == ',' && tempStart != 0 && tempEnd == 0) {
                rightVal = getDigit(eq, i + 1)[0];
            } else if (Character.isDigit(c) && tempStart == 0) {
                int[] info = getDigit(eq, i);
                leftNeighbor = new int[] {i, info[1] + i, info[0]};
                i += info[1] - 1;
            } else if (Character.isDigit(c) && tempEnd != 0) {
                int[] info = getDigit(eq, i);
                rightNeighbor = new int[] {i, info[1] + i, info[0]};
                break;
            }
        }

        if (tempStart != 0) {
            StringBuilder sb = new StringBuilder(p.toString());
            if (rightNeighbor[2] != -1)
                sb.replace(rightNeighbor[0], rightNeighbor[1], String.valueOf(rightVal + rightNeighbor[2]));

            if (leftNeighbor[2] != -1) {
                sb.replace(leftNeighbor[0], leftNeighbor[1], String.valueOf(leftVal + leftNeighbor[2]));
                lengthDiff = String.valueOf(leftVal + leftNeighbor[2]).length() - String.valueOf(leftNeighbor[2]).length();
            }
            sb.replace(tempStart + lengthDiff, tempEnd + lengthDiff + 1, "0");
            return explode(new Pair(sb.toString()));
        }
        return p;
    }

    public static int[] getDigit(String s, int index) {
        int[] toRet = new int[] {Character.getNumericValue(s.charAt(index)), 1};
        for (int i = index + 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c))
                break;
            else {
                toRet[0] *= 10;
                toRet[0] += Character.getNumericValue(c);
                toRet[1]++;
            }
        }
        return toRet;
    }

    public static Pair split(Pair p) {
        Stack<Pair> s = new Stack<>();
        s.push(p);
        boolean right = false;
        while (!s.isEmpty()) {
            Pair cur = s.peek();
            if (!right && cur.left instanceof Pair) {
                s.push((Pair) cur.left);
            } else if (!right && cur.left instanceof Integer && (Integer) cur.left >= 10) {
                cur.left = split((Integer) cur.left);
                p = reduce(p);
            } else if (cur.right instanceof Integer) {
                s.pop();
                right = true;
                if ((Integer) cur.right >= 10) {
                    cur.right = split((Integer) cur.right);
                    p = reduce(p);
                }
            }  else if (cur.right instanceof Pair) {
                right = false;
                s.pop();
                s.push((Pair) cur.right);
            }
        }

        return p;
    }

    public static Pair split(Integer x) {
        return new Pair((int) Math.floor(x / 2.0), (int) Math.ceil(x / 2.0));
    }

    public int getMagnitude() {
        return magHelper(this);
    }

    public int magHelper(Pair p) {
        int mag = 0;
        if (p.left instanceof Integer)
            mag += 3 * (Integer) p.left;
        else
            mag += 3 * magHelper((Pair) p.left);
        if (p.right instanceof Integer)
            mag += 2 * (Integer) p.right;
        else
            mag += 2 * magHelper((Pair) p.right);
        return mag;
    }

    @Override
    public String toString() {
        return "[" + left.toString() + "," + right.toString() + "]";
    }
}
public class day18 {
    public static ArrayList<Pair> parseInput() throws FileNotFoundException {
        ArrayList<Pair> pairs = new ArrayList<>();
        Scanner in = new Scanner(new FileReader("advent-2021-input/day18.txt"));

        while (in.hasNextLine()) {
            String s = in.nextLine();
            Pair p = new Pair(s);
            pairs.add(p);
        }

        return pairs;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Pair> pairs = parseInput();

        // Part 1
        Pair sum = pairs.get(0);
        for (int i = 1; i < pairs.size(); i++) {
            sum = Pair.add(sum, pairs.get(i));
        }
        System.out.println(sum.getMagnitude());

        // Part 2
        int max = 0;
        for (int i = 0; i < pairs.size(); i++) {
            for (int j = 0; j < pairs.size(); j++) {
                if (i == j)
                    continue;
                int mag = Pair.add(pairs.get(i), pairs.get(j)).getMagnitude();
                if (mag > max)
                    max = mag;
            }
        }
        System.out.println(max);
    }
}
