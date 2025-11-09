package graph.topo;

import graph.metrics.Metrics;

import java.util.*;

public class TopologicalSort {

    private final Metrics metrics;

    
    public TopologicalSort(Metrics metrics) {
        this.metrics = metrics;
    }

    
    public TopologicalSort() {
        this(null);
    }

    public List<Integer> kahnSort(Map<Integer, List<Integer>> graph) {
        Map<Integer, Integer> indegree = new HashMap<>();

        for (int node : graph.keySet()) indegree.put(node, 0);
        for (List<Integer> edges : graph.values()) {
            for (int v : edges) indegree.put(v, indegree.getOrDefault(v, 0) + 1);
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int node : indegree.keySet()) {
            if (indegree.get(node) == 0) {
                q.add(node);
                if (metrics != null) metrics.incKahnPushes();
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            if (metrics != null) metrics.incKahnPops();
            order.add(u);
            for (int v : graph.getOrDefault(u, Collections.emptyList())) {
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) {
                    q.add(v);
                    if (metrics != null) metrics.incKahnPushes();
                }
            }
        }

        if (order.size() != indegree.size()) {
            throw new RuntimeException("Graph has a cycle!");
        }
        return order;
    }
}
