package Advent2021;

import Advent2021.HelperClasses.Entry;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class day13 {
    private static String[][] paper;
    private static ArrayList<Entry> instructions = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        parseInput();
        for (Entry fold : instructions) {
            if (fold.dir.equals("y"))
                foldUp(fold.val);
            else
                foldLeft(fold.val);
            System.out.println(fold);
            System.out.println(numDots());
        }

        printPaper();
    }

    public static void parseInput() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day13.txt"));
        String s = in.nextLine();
        int maxX = 0, maxY = 0;

        while (!s.equals("")) {
            s = in.nextLine();
        }

        while(in.hasNextLine()) {
            s = in.nextLine();
            String[] temp = s.split("=");
            Entry newInstruction = new Entry(temp[0].substring(temp[0].length() - 1), Integer.parseInt(temp[1]));
            if (newInstruction.dir.equals("y")) {
                if (newInstruction.val > maxY)
                    maxY = newInstruction.val * 2;
            }
            else {
                if (newInstruction.val > maxX) {
                    maxX = newInstruction.val * 2;
                }
            }
            instructions.add(newInstruction);
        }

        paper = new String[maxY + 1][maxX + 1];
        for (String[] row : paper)
            Arrays.fill(row, ".");

        in = new Scanner(new FileReader("advent-2021-input/day13.txt"));
        s = in.nextLine();
        while (!s.equals("")) {
            String[] coords = s.split(",");
            paper[Integer.parseInt(coords[1])][Integer.parseInt(coords[0])] = "#";
            s = in.nextLine();
        }
    }

    public static void foldUp(int val) {
        String[][] newPaper = new String[val][paper[0].length];
        for (int i = 0; i < newPaper.length; i++) {
            for (int j = 0; j < newPaper[i].length; j++) {
                String coord1 = paper[i][j];
                String coord2 = paper[paper.length - i - 1][j];
                if (coord1.equals("#") || coord2.equals("#"))
                    newPaper[i][j] = "#";
                else
                    newPaper[i][j] = " ";
            }
        }
        paper = newPaper;
    }

    public static void foldLeft(int val) {
        String[][] newPaper = new String[paper.length][val];
        for (int i = 0; i < newPaper.length; i++) {
            for (int j = 0; j < newPaper[i].length; j++) {
                String coord1 = paper[i][j];
                String coord2 = paper[i][paper[0].length - j - 1];
                if (coord1.equals("#") || coord2.equals("#"))
                    newPaper[i][j] = "#";
                else
                    newPaper[i][j] = " ";
            }
        }
        paper = newPaper;
    }

    public static int numDots() {
        int numDots = 0;
        for (String[] row : paper)
            for (String val : row)
                if (val.equals("#"))
                    numDots++;
        return numDots;
    }

    public static void printPaper() {
        for (int i = 0; i < paper.length; i++) {
            for (int j = 0; j < paper[i].length; j++)
                System.out.print(paper[i][j]);
            System.out.println();
        }
    }
}
