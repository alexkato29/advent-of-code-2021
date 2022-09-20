package Advent2021;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class day4 {
    private static final int BOARD_SIZE = 5;

    public static void main(String[] args) throws IOException {
        // Read File
        Scanner in = new Scanner(new FileReader("advent-2021-input/day4.txt"));
        String s = in.nextLine();

        // Split the first line by commas to get all bingo picks in order
        ArrayList<int[][]> boards = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();

        // Make the queue
        String[] nums = s.split(",");
        for (String x : nums) {
            q.add(Integer.parseInt(x));
        }

        // Create all the boards
        while (in.hasNextLine()) {
            int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    int temp = in.nextInt();
                    board[i][j] = temp;
                }
            }
            boards.add(board);
        }

        // Play some bingo
        while (q.size() != 0) {
            int cur = q.poll();
            for (int i = 0; i < boards.size(); i++) {
                int[][] b = boards.get(i);
                for (int row = 0; row < BOARD_SIZE; row++) {
                    for (int col = 0; col < BOARD_SIZE; col++) {
                        if (b[row][col] == cur) {
                            b[row][col] = -1;
                            int res = isWinner(b);
                            if (res != -1) {
                                boards.remove(b);
                                i--;
                                if (boards.size() == 0)
                                    System.out.println(res * cur);
                            }
                        }
                    }
                }
            }
        }
    }

    public static int isWinner(int[][] board) {
        int rowVal = 0, colVal = 0;
        boolean winner = false;

        // Go through the middle row and column of the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            int tempVal1 = 0, tempVal2 = 0;
            colVal = board[2][i];
            rowVal = board[i][2];

            // If the middle row and column x is -1 check for a winning column
            if (colVal == -1) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    tempVal1 += board[j][i];
                }
            }

            if (rowVal == -1)
                for (int j = 0; j < BOARD_SIZE; j++) {
                    tempVal2 += board[i][j];
                }

            if (tempVal1 == -1 * BOARD_SIZE || tempVal2 == -1 * BOARD_SIZE)
                winner = true;

            if (winner) {
                int sum = 0;
                for (int k = 0; k < BOARD_SIZE; k++)
                    for (int j = 0; j < BOARD_SIZE; j++)
                        sum += board[k][j] != -1 ? board[k][j] : 0;
                return sum;
            }
        }
        return -1;
    }
}