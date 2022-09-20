package Advent2021;

import Advent2021.HelperClasses.Matrix;
import Advent2021.HelperClasses.Vector;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class Day19Scanner {
    public ArrayList<Vector> beacons;
    public int num;

    public Day19Scanner(ArrayList<Vector> beacons, int num) {
        this.beacons = beacons;
        this.num = num;
    }

    public Day19Scanner() {
        beacons = new ArrayList<>();
        num = -1;
    }

    public Day19Scanner applyRotation(Matrix A) throws Exception {
        ArrayList<Vector> rotated = new ArrayList<>();

        for (Vector v : beacons) {
            rotated.add(Matrix.multiply(A, v));
        }

        return new Day19Scanner(rotated, -1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nScanner: ");
        sb.append(num);

        for (Vector v : beacons) {
            sb.append("\n");
            sb.append(v);
        }

        return sb.toString();
    }
}

public class day19 {
    private static final Matrix[] rotations = new Matrix[24];
    private static final int OVERLAP = 3;

    public static void generateRotations() throws Exception {
        // I manually found these. Just draw i, j, and k standard basis vectors and convert to a matrix
        rotations[0] = new Matrix(new int[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}); // Positive X
        rotations[4] = new Matrix(new int[][] {{-1, 0, 0}, {0, -1, 0}, {0, 0, 1}}); // Negative X
        rotations[8] = new Matrix(new int[][] {{0, -1, 0}, {1, 0, 0}, {0, 0, 1}}); // Positive Y
        rotations[12] = new Matrix(new int[][] {{0, 1, 0}, {-1, 0, 0}, {0, 0, 1}}); // Negative Y
        rotations[16] = new Matrix(new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 0, 0}}); // Positive Z
        rotations[20] = new Matrix(new int[][] {{0, 1, 0}, {0, 0, -1}, {-1, 0, 0}}); // Negative Z

        for (int i = 1; i < rotations.length; i += 8) {
            Matrix A = rotations[i - 1];
            Vector v1 = new Vector(A.getCol(2).scale(-1));
            Vector v2 = new Vector(A.getCol(1));
            Vector temp = new Vector(v1);

            Matrix rot1 = new Matrix(A);
            rot1.setCol(1, v1);
            rot1.setCol(2, v2);
            rotations[i] = rot1;

            v1 = new Vector(v2);
            v2 = new Vector(temp);
            v1.scale(-1);
            temp = new Vector(v1);

            Matrix rot2 = new Matrix(A);
            rot2.setCol(1, v1);
            rot2.setCol(2, v2);
            rotations[i + 1] = rot2;

            v1 = new Vector(v2);
            v2 = new Vector(temp);
            v1.scale(-1);

            Matrix rot3 = new Matrix(A);
            rot3.setCol(1, v1);
            rot3.setCol(2, v2);
            rotations[i + 2] = rot3;
        }

        for (int i = 5; i < rotations.length; i += 8) {
            Matrix A = rotations[i - 1];
            Vector v1 = new Vector(A.getCol(2));
            Vector v2 = new Vector(A.getCol(1)).scale(-1);
            Vector temp = new Vector(v1);

            Matrix rot1 = new Matrix(A);
            rot1.setCol(1, v1);
            rot1.setCol(2, v2);
            rotations[i] = rot1;

            v1 = new Vector(v2);
            v2 = new Vector(temp);
            v2.scale(-1);
            temp = new Vector(v1);

            Matrix rot2 = new Matrix(A);
            rot2.setCol(1, v1);
            rot2.setCol(2, v2);
            rotations[i + 1] = rot2;

            v1 = new Vector(v2);
            v2 = new Vector(temp);
            v2.scale(-1);

            Matrix rot3 = new Matrix(A);
            rot3.setCol(1, v1);
            rot3.setCol(2, v2);
            rotations[i + 2] = rot3;
        }
    }

    public static ArrayList<Day19Scanner> parseInput() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day19.txt"));
        in.nextLine();

        ArrayList<Day19Scanner> scanners = new ArrayList<>();
        ArrayList<Vector> v = new ArrayList<>();
        int num = 0;

        while (in.hasNextLine()) {
            String s = in.nextLine();

            if (s.equals("")) {
                scanners.add(new Day19Scanner(v, num++));
                v = new ArrayList<>();
                in.nextLine();
                s = in.nextLine();
            }
            v.add(new Vector(s.split(",")));
        }

        scanners.add(new Day19Scanner(v, num));
        return scanners;
    }

    public static int part2(ArrayList<Vector> locations) throws Exception {
        int largest = 0;

        for (Vector u : locations) {
            for (Vector v : locations) {
                int manDist = 0;
                Vector dist = Vector.add(u, v.copy().scale(-1));
                for (int i : dist.getVector()) {
                    manDist += Math.abs(i);
                }
                if (manDist > largest)
                    largest = manDist;
            }
        }
        return largest;
    }

    /*
    This is ridiculously slow.
    I believe the best way to improve is to implement the following:

    1) Do NOT check scanners that don't have 12 potential overlaps
    2) See if I can find a way to orient the points without checking ALL matrices

    Right now it is pretty brute force and a runtime of 1 min is pretty obscene.
     */
    public static void part1(ArrayList<Day19Scanner> scanners) throws Exception {
        Day19Scanner overallMap = scanners.get(0);
        ArrayList<Vector> locations = new ArrayList<>();
        locations.add(new Vector(new int[] {0, 0, 0}));

        while (scanners.size() > 1) {
            boolean foundMatch = false;
            Day19Scanner match = new Day19Scanner();
            ArrayList<Vector> unseen = new ArrayList<>();

            for (int i = 1; i < scanners.size(); i++) {
                Day19Scanner s = scanners.get(i);
                for (Matrix A : rotations) {
                    Day19Scanner temp = s.applyRotation(A);

                    for (Vector v1 : overallMap.beacons) {
                        for (Vector v2 : temp.beacons) {
                            Vector change = Vector.add(v1, v2.copy().scale(-1));
                            int counter = 0;
                            unseen = new ArrayList<>();
                            for (Vector v3 : temp.beacons) {
                                Vector modifiedV3 = Vector.add(v3, change);
                                if (!overallMap.beacons.contains(modifiedV3)) {
                                    unseen.add(modifiedV3);
                                } else {
                                    if (++counter == OVERLAP) {
                                        match = s;
                                        locations.add(change);
                                        foundMatch = true;
                                    }
                                }
                            }
                            if (foundMatch)
                                break;
                        }
                        if (foundMatch)
                            break;
                    }
                    if (foundMatch)
                        break;
                }
                if (foundMatch)
                    break;
            }
            if (foundMatch) {
                overallMap.beacons.addAll(unseen);
                scanners.remove(match);
            }
        }

        System.out.println(part2(locations));
        System.out.println(overallMap.beacons.size());
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Day19Scanner> scanners = parseInput();
        generateRotations();

        part1(scanners);
    }
}
