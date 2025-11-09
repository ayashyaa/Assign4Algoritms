package graph.dagsp;

import java.util.*;
public class DAGShortestPaths {

    public static class Result {
        public final Map<Integer, Integer> dist;
        public final Map<Integer, Integer> parent;
        public Result(Map<Integer,Integer> d, Map<Integer,Integer> p) { dist = d; parent = p; }
        public List<Integer> reconstructPath(int target) {
            if (!dist.containsKey(target)) return Collections.emptyList();
            if (dist.get(target) == Integer.MAX_VALUE || dist.get(target) == Integer.MIN_VALUE) return Collections.emptyList();
            LinkedList<Integer> path = new LinkedList<>();
            Integer cur = target;
            while (cur != null) {
                path.addFirst(cur);
                cur = parent.get(cur);
            }
            return path;
        }
    }

    public Result shortestPath(Map<Integer, List<int[]>> graph, int source, List<Integer> topoOrder) {
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Integer.MAX_VALUE);
        dist.put(source, 0);
        parent.put(source, null);

        for (int u : topoOrder) {
            Integer du = dist.get(u);
            if (du != null && du != Integer.MAX_VALUE) {
                for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                    int v = edge[0];
                    int w = edge[1];
                    if (!dist.containsKey(v)) dist.put(v, Integer.MAX_VALUE);
                    if (dist.get(v) > du + w) {
                        dist.put(v, du + w);
                        parent.put(v, u);
                    }
                }
            }
        }
        return new Result(dist, parent);
    }

    public Result longestPath(Map<Integer, List<int[]>> graph, int source, List<Integer> topoOrder) {
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Integer.MIN_VALUE);
        dist.put(source, 0);
        parent.put(source, null);

        for (int u : topoOrder) {
            Integer du = dist.get(u);
            if (du != null && du != Integer.MIN_VALUE) {
                for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                    int v = edge[0];
                    int w = edge[1];
                    if (!dist.containsKey(v)) dist.put(v, Integer.MIN_VALUE);
                    if (dist.get(v) < du + w) {
                        dist.put(v, du + w);
                        parent.put(v, u);
                    }
                }
            }
        }
        return new Result(dist, parent);
    }
}
