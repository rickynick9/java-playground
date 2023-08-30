package ztm.section11;

import java.util.*;

public class AdjacencyList {

    private int noOfNodes;
    private final Map<Integer, List<Integer>> map;

    public AdjacencyList() {
        this.noOfNodes = 0;
        this.map = new HashMap<>();
    }

    public void addVertex(Integer vertex) {
        this.map.put(vertex, new ArrayList<>());
        this.noOfNodes++;
    }

    public void addEdge(Integer n1, Integer n2) {
        this.map.get(n1).add(n2);
        this.map.get(n2).add(n1); // since its undirected
    }

    public void showConnections() {
        // 0 --> 1 2
        // 1 --> 3 2 0
        this.map.forEach((k,v) ->{
            System.out.println(k + " -----> " + Arrays.toString(v.toArray()));
//            v.forEach(n -> {
//                System.out.println(k + " -----> " +n);
//            });
        });
    }

    public static void main(String[] args) {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);

        graph.addEdge(3, 1);
        graph.addEdge(3, 4);
        graph.addEdge(4, 2);
        graph.addEdge(4, 5);
        graph.addEdge(1, 2);
        graph.addEdge(1, 0);
        graph.addEdge(0, 2);
        graph.addEdge(6, 5);

        graph.showConnections();

    }
}
