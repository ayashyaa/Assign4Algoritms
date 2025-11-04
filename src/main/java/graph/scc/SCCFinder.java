package graph.scc;

import java.util.*;

/**
 * Finds Strongly Connected Components (SCCs) in a directed graph using Tarjan's algorithm.
 */
public class SCCFinder {
    private int time = 0;
    private final Map<Integer, List<Integer>> graph;
    private final Map<Integer, Integer> disc = new HashMap<>();
    private final Map<Integer, Integer> low = new HashMap<>();
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Set<Integer> inStack = new HashSet<>();
    private final List<List<Integer>> sccList = new ArrayList<>();

    public SCCFinder(Map<Integer, List<Integer>> graph) {
        this.graph = graph;
    }

    public List<List<Integer>> findSCCs() {
        for (Integer v : graph.keySet()) {
            if (!disc.containsKey(v)) {
                dfs(v);
            }
        }
        return sccList;
    }

    private void dfs(int u) {
        disc.put(u, time);
        low.put(u, time);
        time++;
        stack.push(u);
        inStack.add(u);

        for (int v : graph.getOrDefault(u, Collections.emptyList())) {
            if (!disc.containsKey(v)) {
                dfs(v);
                low.put(u, Math.min(low.get(u), low.get(v)));
            } else if (inStack.contains(v)) {
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }

        // Root of SCC
        if (Objects.equals(low.get(u), disc.get(u))) {
            List<Integer> scc = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                inStack.remove(w);
                scc.add(w);
            } while (w != u);
            sccList.add(scc);
        }
    }
}
