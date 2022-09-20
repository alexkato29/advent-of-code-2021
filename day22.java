package Advent2021;

import Advent2021.HelperClasses.Vector;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

class Instruction {
    public int sign; // -1 or 1 depending on if it is to be added or subtracted. 0 represents off.
    public Vector lowerCorner;
    public Vector upperCorner;

    public Instruction(int sign, Vector lowerCorner, Vector upperCorner) {
        this.sign = sign;
        this.lowerCorner = lowerCorner;
        this.upperCorner = upperCorner;
    }

    public long volume() {
        return ((long) (upperCorner.getVal(0) - lowerCorner.getVal(0)) + 1) *
                ((long) (upperCorner.getVal(1) - lowerCorner.getVal(1)) + 1) *
                ((long) (upperCorner.getVal(2) - lowerCorner.getVal(2)) + 1);
    }

    // Returns the cuboid of intersection
    public Instruction overlap(Instruction instruction) {
        Vector lower = new Vector();
        lower.append(Math.max(lowerCorner.getVal(0), instruction.lowerCorner.getVal(0)));
        lower.append(Math.max(lowerCorner.getVal(1), instruction.lowerCorner.getVal(1)));
        lower.append(Math.max(lowerCorner.getVal(2), instruction.lowerCorner.getVal(2)));

        Vector upper = new Vector();
        upper.append(Math.min(upperCorner.getVal(0), instruction.upperCorner.getVal(0)));
        upper.append(Math.min(upperCorner.getVal(1), instruction.upperCorner.getVal(1)));
        upper.append(Math.min(upperCorner.getVal(2), instruction.upperCorner.getVal(2)));

        if (lower.getVal(0) > upper.getVal(0) || lower.getVal(1) > upper.getVal(1) || lower.getVal(2) > upper.getVal(2))
            return null;

        return new Instruction(instruction.sign * -1, lower, upper);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sign: ");
        sb.append(sign);
        sb.append(", ");
        sb.append(lowerCorner);
        sb.append(", ");
        sb.append(upperCorner);
        return sb.toString();
    }
}

public class day22 {
    public static void part1() throws FileNotFoundException {
        HashSet<Vector> onPositions = new HashSet<>();

        // Part 1
        Scanner in = new Scanner(new FileReader("advent-2021-input/day22.txt"));
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            String instruction = line[0];
            String[] bounds = line[1].split("[.,]");

            int lowX = Math.max(Integer.parseInt(bounds[0].substring(2)), -50);
            int highX = Math.min(Integer.parseInt(bounds[2]), 50);
            int lowY = Math.max(Integer.parseInt(bounds[3].substring(2)), -50);
            int highY = Math.min(Integer.parseInt(bounds[5]), 50);
            int lowZ = Math.max(Integer.parseInt(bounds[6].substring(2)), -50);
            int highZ = Math.min(Integer.parseInt(bounds[8]), 50);

            for (int x = lowX; x <= highX; x++) {
                for (int y = lowY; y <= highY; y++) {
                    for (int z = lowZ; z <= highZ; z++) {
                        Vector v = new Vector();
                        v.append(x);
                        v.append(y);
                        v.append(z);

                        if (instruction.equals("on"))
                            onPositions.add(v);
                        else
                            onPositions.remove(v);
                    }
                }
            }
        }

        System.out.println(onPositions.size());
    }

    /*
    Idea behind part 2:
    AvB = A + B - A^B
    (AvB)vC = (A + B - A^B) + C - A^C - B^C + A^B^C

    This pattern continues to repeat. The sign is always flipping.
    Go through and calculate all overlapping regions and store them.
    At the end, add them all up!

    For "on" cores this is simple, just literally add them in this format.

    For "off" cores, we need to REMOVE the intersection of the on and off. Simply do what we normally do, only
    do NOT add their base value - just intersections.
     */
    public static void part2() throws FileNotFoundException {
        ArrayList<Instruction> cuboids = new ArrayList<>();
        Scanner in = new Scanner(new FileReader("advent-2021-input/day22.txt"));

        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            String[] bounds = line[1].split("[.,]");

            int lowX = Integer.parseInt(bounds[0].substring(2));
            int highX = Integer.parseInt(bounds[2]);
            int lowY = Integer.parseInt(bounds[3].substring(2));
            int highY = Integer.parseInt(bounds[5]);
            int lowZ = Integer.parseInt(bounds[6].substring(2));
            int highZ = Integer.parseInt(bounds[8]);

            int command = line[0].equals("on") ? 1 : 0;
            Vector lower = new Vector();
            lower.append(lowX); lower.append(lowY); lower.append(lowZ);
            Vector upper = new Vector();
            upper.append(highX); upper.append(highY); upper.append(highZ);

            Instruction i = new Instruction(command, lower, upper);
            ArrayList<Instruction> newIntersects = new ArrayList<>();

            if (i.sign == 1) {
                newIntersects.add(i);
                //System.out.println("Added: " + i);
            }

            for (Instruction inst : cuboids) {
                Instruction newCuboid = i.overlap(inst);
                if (newCuboid != null) {
                    newIntersects.add(newCuboid);
                    //System.out.println("Added: " + i.overlap(inst));
                }
            }

            cuboids.addAll(newIntersects);
        }

        long totalOn = 0;
        for (Instruction cuboid : cuboids)
            totalOn += cuboid.sign * cuboid.volume();
        System.out.println(totalOn);
    }

    public static void main(String[] args) throws FileNotFoundException {
        //part1();
        part2();
    }
}
