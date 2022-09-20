package Advent2021.HelperClasses;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<String, Set<String>> myGraph;

    public Graph(String[] data) {
        myGraph = new HashMap<>();

        for (String datum : data) {
            String[] connections = datum.split("-");
            String v1 = connections[0];
            String v2 = connections[1];
            myGraph.putIfAbsent(v1, new HashSet<>());
            myGraph.get(v1).add(v2);
            myGraph.putIfAbsent(v2, new HashSet<>());
            myGraph.get(v2).add(v1);
        }
        System.out.println(myGraph);
    }

    public Set<String> get(String s) {
        return myGraph.get(s);
    }

    public Set<String> keySet() {
        return myGraph.keySet();
    }
}
