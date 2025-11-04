package graph.topo;

import java.util.*;

/**
 * Performs topological sorting on a directed acyclic graph (DAG) using Kahn's algorithm.
 */
public class TopologicalSort {

    public List<Integer> kahnSort(Map<Integer, List<Integer>> graph) {
        Map<Integer, Integer> indegree = new HashMap<>();

        // Calculate indegree of each node
        for (int node : graph.keySet()) indegree.put(node, 0);
        for (List<Integer> edges : graph.values()) {
            for (int v : edges) indegree.put(v, indegree.getOrDefault(v, 0) + 1);
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int node : indegree.keySet()) if (indegree.get(node) == 0) q.add(node);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : graph.getOrDefault(u, Collections.emptyList())) {
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) q.add(v);
            }
        }

        if (order.size() != graph.size())
            throw new RuntimeException("Graph has a cycle!");
        return order;
    }
}
