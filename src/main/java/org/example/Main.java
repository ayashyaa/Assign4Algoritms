package org.example;

import graph.scc.SCCFinder;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGShortestPaths;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Example graph for SCC
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(1));
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(0, 3));
        graph.put(3, Arrays.asList(4));
        graph.put(4, Collections.emptyList());

        SCCFinder sccFinder = new SCCFinder(graph);
        System.out.println("SCCs: " + sccFinder.findSCCs());

        // Example DAG for topo sort and shortest path
        Map<Integer, List<Integer>> dag = new HashMap<>();
        dag.put(0, Arrays.asList(1, 2));
        dag.put(1, Arrays.asList(3));
        dag.put(2, Arrays.asList(3));
        dag.put(3, Arrays.asList());

        TopologicalSort topo = new TopologicalSort();
        List<Integer> order = topo.kahnSort(dag);
        System.out.println("Topological order: " + order);

        // Weighted DAG for shortest path
        Map<Integer, List<int[]>> weighted = new HashMap<>();
        weighted.put(0, Arrays.asList(new int[]{1, 2}, new int[]{2, 4}));
        weighted.put(1, Arrays.asList(new int[]{3, 1}));
        weighted.put(2, Arrays.asList(new int[]{3, 3}));
        weighted.put(3, Arrays.asList());

        DAGShortestPaths sp = new DAGShortestPaths();
        Map<Integer, Integer> shortest = sp.shortestPath(weighted, 0, order);
        System.out.println("Shortest distances: " + shortest);

        Map<Integer, Integer> longest = sp.longestPath(weighted, 0, order);
        System.out.println("Longest distances: " + longest);
    }
}
