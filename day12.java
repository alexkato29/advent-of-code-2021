package Advent2021;

import Advent2021.HelperClasses.Graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class day12 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("advent-2021-input/day12.txt"));
        ArrayList<String> connections = new ArrayList<>();
        while (in.hasNextLine()) {
            String s = in.nextLine();
            connections.add(s);
        }

        // Part 1
        Graph caves = new Graph(connections.toArray(new String[0]));
        int res = numPathsP1(caves, new ArrayList<String>(List.of("start")), new ArrayList<String>(), "start");
        System.out.println(res);

        // Part 2
        int total = res;
        for (String s : caves.keySet()) {
            if (visitOnlyOnce(s) && !s.equals("start") && !s.equals("end")) {
                // The def removes the double count
                total -= res;
                total += numPathsP2(caves, new ArrayList<String>(List.of("start")),"start", s);
            }
        }
        System.out.println(total);
    }

    public static boolean visitOnlyOnce(String s) {
        return !Character.isUpperCase(s.charAt(0));
    }

    public static int numPathsP1(Graph g, ArrayList<String> vis, ArrayList<String> path, String cur) {
        int paths = 0;
        // All path references can be removed - solely for debugging.
        //path.add(cur);
        for (String adj : g.get(cur)) {
            if (adj.equals("end")) {
                // System.out.println(path);
                paths++;
            }
            else if (!vis.contains(adj)) {
                if (visitOnlyOnce(adj))
                    vis.add(adj);
                paths += numPathsP1(g, new ArrayList<>(vis), path, adj);
                if (visitOnlyOnce(adj))
                    vis.remove(adj);
            }
        }

        //path.remove(path.size() - 1);
        return paths;
    }

    public static int numPathsP2(Graph g, ArrayList<String> vis, String cur, String visTwice) {
        int paths = 0;
        for (String adj : g.get(cur)) {
            int freq = Collections.frequency(vis, adj);
            if (adj.equals(visTwice))
                freq--;

            if (adj.equals("end")) {
                paths++;
            } else if (freq < 1) {
                if (visitOnlyOnce(adj))
                    vis.add(adj);
                paths += numPathsP2(g, new ArrayList<>(vis), adj, visTwice);
                if (visitOnlyOnce(adj))
                    vis.remove(adj);
            }
        }
        return paths;
    }
}
