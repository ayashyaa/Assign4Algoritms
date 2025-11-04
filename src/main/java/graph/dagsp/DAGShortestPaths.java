package graph.dagsp;

import java.util.*;

/**
 * Computes shortest paths in a DAG given topological order.
 */
public class DAGShortestPaths {

    public Map<Integer, Integer> shortestPath(Map<Integer, List<int[]>> graph, int source, List<Integer> topoOrder) {
        Map<Integer, Integer> dist = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Integer.MAX_VALUE);
        dist.put(source, 0);

        for (int u : topoOrder) {
            if (dist.get(u) != Integer.MAX_VALUE) {
                for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                    int v = edge[0];
                    int w = edge[1];
                    if (dist.get(v) > dist.get(u) + w) {
                        dist.put(v, dist.get(u) + w);
                    }
                }
            }
        }
        return dist;
    }

    public Map<Integer, Integer> longestPath(Map<Integer, List<int[]>> graph, int source, List<Integer> topoOrder) {
        Map<Integer, Integer> dist = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Integer.MIN_VALUE);
        dist.put(source, 0);

        for (int u : topoOrder) {
            if (dist.get(u) != Integer.MIN_VALUE) {
                for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                    int v = edge[0];
                    int w = edge[1];
                    if (dist.get(v) < dist.get(u) + w) {
                        dist.put(v, dist.get(u) + w);
                    }
                }
            }
        }
        return dist;
    }
}
