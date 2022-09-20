package Advent2021;

import java.util.ArrayList;
import java.util.HashMap;

public class day21 {
    public static HashMap<ArrayList<Integer>, long[]> games = new HashMap<>();
    public static final int[] FACTORS = new int[] {1, 3, 6, 7, 6, 3, 1};

    public static long[] part2(int p1Pos, int p1Score, int p2Pos, int p2Score) {
        if (p1Score >= 21)
            return new long[] {1, 0};
        if (p2Score >= 21)
            return new long[] {0, 1};

        int[] t = new int[] {p1Pos, p1Score, p2Pos, p2Score};
        ArrayList<Integer> gameCode = new ArrayList<>();
        for (int x : t)
            gameCode.add(x);

        if (games.containsKey(gameCode)) {
            return games.get(gameCode);
        }

        long[] wins = new long[] {0, 0};

        for (int i = 3; i <= 9; i++) {
            int newPos = (p1Pos + i - 1) % 10 + 1;
            int newScore = p1Score + newPos;

            long[] results = part2(p2Pos, p2Score, newPos, newScore);
            wins[0] += results[1] * FACTORS[i - 3];
            wins[1] += results[0] * FACTORS[i - 3];
        }

        games.put(gameCode, wins);
        return wins;
    }

    public static void main(String[] args) {
        int p1Score = 0, p2Score = 0, rolls = 0;
        // This is the input
        int p1Pos = 9, p2Pos = 4;

        // This while loop is part1
        /*while (true) {
            p1Pos = (p1Pos + (++rolls % 100) + (++rolls % 100) + (++rolls % 100) - 1) % 10 + 1;
            p1Score += p1Pos;
            if (p1Score >= 1000) {
                System.out.println(p2Score * rolls);
                break;
            }

            p2Pos = (p2Pos + (++rolls % 100) + (++rolls % 100) + (++rolls % 100) - 1) % 10 + 1;
            p2Score += p2Pos;
            if (p2Score >= 1000) {
                System.out.println(p1Score * rolls);
                break;
            }
        }*/

        // Code to run part2
        part2(p1Pos, 0, p2Pos, 0);

        int[] t = new int[] {p1Pos, 0, p2Pos, 0};
        ArrayList<Integer> gameCode = new ArrayList<>();
        for (int x : t)
            gameCode.add(x);
        long[] results = games.get(gameCode);

        System.out.println(results[0]);
        System.out.println(results[1]);

    }
}
