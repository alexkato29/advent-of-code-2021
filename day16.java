package Advent2021;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

class Packet implements Comparable<Packet> {
    public long typeID;
    public long subType;
    public long subLen;
    public long value;
    public int bitLen;
    public int packetsLen;

    public Packet(long typeID, long subType, long subLen, long value, int bitLen) {
        this.typeID = typeID;
        this.bitLen = bitLen;
        this.packetsLen = 1;
        if (typeID != 4) {
            this.value = -1;
            this.subLen = subLen;
            this.subType = subType;
        } else {
            this.value = value;
            this.subLen = -1;
            this.subType = -1;
        }
    }

    public String toString() {
        return "Type: " + typeID + " Value: " + value + " subType: " + subType + " subLen: " + subLen + " Bit Length: " + bitLen + " Packet Length: " + packetsLen + "\n";
    }

    @Override
    public int compareTo(Packet p) {
        // Use many comparisons instead of subtracting because they are longs and casting them to an int can ruin things
        if (this.value > p.value)
            return 1;
        else if (this.value == p.value)
            return 0;
        return -1;
    }
}

public class day16 {
    private static Stack<Packet> operators = new Stack<>();
    private static ArrayList<Packet> packets = new ArrayList<>();
    private static ArrayList<Long> toRet = new ArrayList<>(List.of((long) 0));

    public static String hexToBinary(String hex) {
        HashMap<Character, String> vals = new HashMap<>();
        StringBuilder binary = new StringBuilder();

        vals.put('0', "0000");
        vals.put('1', "0001");
        vals.put('2', "0010");
        vals.put('3', "0011");
        vals.put('4', "0100");
        vals.put('5', "0101");
        vals.put('6', "0110");
        vals.put('7', "0111");
        vals.put('8', "1000");
        vals.put('9', "1001");
        vals.put('A', "1010");
        vals.put('B', "1011");
        vals.put('C', "1100");
        vals.put('D', "1101");
        vals.put('E', "1110");
        vals.put('F', "1111");

        for (char c : hex.toCharArray()) {
            binary.append(vals.get(c));
        }

        return binary.toString();
    }

    public static long binToDec(String binary) {
        return Long.parseLong(binary, 2);
    }

    public static long parseBinaryPart1(String binary) {
        long versionSum = 0;

        if (binary.length() < 11)
            return versionSum;

        long version = binToDec(binary.substring(0, 3));
        long typeID = binToDec(binary.substring(3, 6));

        binary = binary.substring(6);

        versionSum += version;

        // If the binary is a literal value
        if (typeID == 4) {
            int pos = 0;
            StringBuilder value = new StringBuilder();
            String leadingBit = binary.substring(pos, pos + 1);

            while (!leadingBit.equals("0")) {
                value.append(binary, pos + 1, pos + 5);
                pos += 5;
                leadingBit = binary.substring(pos, pos + 1);
            }

            value.append(binary, pos + 1, pos + 5);
            toRet.add(binToDec(value.toString()));
            versionSum += parseBinaryPart1(binary.substring(pos + 5));
        }

        // If the binary is an operator
        else {
            long i = binToDec(binary.substring(0, 1));

            if (i == 0) {
                binary = binary.substring(16);
            } else {
                binary = binary.substring(12);
            }

            versionSum += parseBinaryPart1(binary);
        }

        return versionSum;
    }

    public static void parseInput(String binary) {
        if (binary.length() < 11)
            return;

        long typeID = binToDec(binary.substring(3, 6));
        binary = binary.substring(6);
        int len = 6;

        // If the binary is a literal value
        if (typeID == 4) {
            int pos = 0;
            StringBuilder value = new StringBuilder();
            String leadingBit = binary.substring(pos, pos + 1);

            while (!leadingBit.equals("0")) {
                value.append(binary, pos + 1, pos + 5);
                pos += 5;
                leadingBit = binary.substring(pos, pos + 1);
            }

            value.append(binary, pos + 1, pos + 5);
            Packet p = new Packet(typeID, -1, -1, binToDec(value.toString()), len + pos + 5);
            packets.add(p);
            parseInput(binary.substring(pos + 5));
        }

        // If the binary is an operator
        else {
            long i = binToDec(binary.substring(0, 1));
            long subLen;

            if (i == 0) {
                subLen = binToDec(binary.substring(1, 16));
                binary = binary.substring(16);
                len += 16;
            } else {
                subLen = binToDec(binary.substring(1, 12));
                binary = binary.substring(12);
                len += 12;
            }
            Packet p = new Packet(typeID, i, subLen, -1, len);
            packets.add(p);
            operators.push(p);
            parseInput(binary);
        }
    }

    public static long operate(Packet cur, ArrayList<Packet> subP, long total) {
        long change = 0;
        long type = cur.typeID;

        if (type == 0) {
            for (Packet p : subP) {
                change += p.value;
            }
        } else if (type == 1) {
            change = 1;
            for (Packet p : subP) {
                change *= p.value;
            }
        } else if (type == 2) {
            change = Collections.min(subP).value;
        } else if (type == 3) {
            change = Collections.max(subP).value;
        } else if (type == 5) {
            change = subP.get(0).value > subP.get(1).value ? 1 : 0;
        } else if (type == 6) {
            change = subP.get(0).value < subP.get(1).value ? 1 : 0;
        } else if (type == 7) {
            change = subP.get(0).value == subP.get(1).value ? 1 : 0;
        }

        packets.removeAll(subP);

        for (Packet p : subP) {
            cur.bitLen += p.bitLen;
        }

        cur.value = change;

        return change;
    }

    public static long part2(String binary) {
        parseInput(binary);
        long total = 0;

        System.out.println(packets);

        while (!operators.isEmpty()) {
            Packet cur = operators.pop();
            int index = packets.indexOf(cur);

            if (cur.subType == 0) {
                ArrayList<Packet> subsequentP = new ArrayList<>();
                int bits = 0;
                int i = 1;
                while (bits < cur.subLen) {
                    Packet nextP = packets.get(index + i);
                    i++;
                    bits += nextP.bitLen;
                    subsequentP.add(nextP);
                }
                total = operate(cur, subsequentP, total);
            }

            else if (cur.subType == 1) {
                if (cur.typeID == 7)
                    System.out.println(packets);
                ArrayList<Packet> subsequentP = new ArrayList<>();
                for (int i = 1; i <= cur.subLen;) {
                    Packet nextP = packets.get(index + i);
                    i += nextP.packetsLen;
                    subsequentP.add(nextP);
                }
                total = operate(cur, subsequentP, total);
            }
        }

        return total;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String in = new Scanner(new FileReader("advent-2021-input/day16.txt")).nextLine();
        String binary = hexToBinary(in);
        // System.out.println(parseBinaryPart1(binary));
        System.out.println(part2(binary));
    }
}
