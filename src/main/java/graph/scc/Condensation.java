package graph.scc;

import java.util.*;

public class Condensation {
  
    public static Map<String, Object> buildCondensation(Map<Integer, List<Integer>> original, List<List<Integer>> sccs) {
        Map<Integer, Integer> nodeToComp = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++) {
            for (int v : sccs.get(i)) nodeToComp.put(v, i);
        }

        Map<Integer, Set<Integer>> compAdjSet = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++) compAdjSet.put(i, new HashSet<>());

        for (int u : original.keySet()) {
            int cu = nodeToComp.get(u);
            for (int v : original.getOrDefault(u, Collections.emptyList())) {
                int cv = nodeToComp.get(v);
                if (cu != cv) compAdjSet.get(cu).add(cv);
            }
        }

        Map<Integer, List<Integer>> compAdj = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> e : compAdjSet.entrySet()) {
            compAdj.put(e.getKey(), new ArrayList<>(e.getValue()));
        }

        Map<String, Object> res = new HashMap<>();
        res.put("condensation", compAdj);
        res.put("nodeToComp", nodeToComp);
        return res;
    }
}
