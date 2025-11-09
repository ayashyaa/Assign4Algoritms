package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class GraphLoader {

    public static Map<Integer, List<Integer>> loadGraph(String filePath) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<Integer, List<Integer>>>() {}.getType();

        try (FileReader reader = new FileReader(filePath)) {
            Map<Integer, List<Integer>> g = gson.fromJson(reader, type);
            if (g == null) return Collections.emptyMap();
     
            Set<Integer> keys = new HashSet<>(g.keySet());
            for (List<Integer> list : g.values()) {
                for (Integer v : list) {
                    if (!g.containsKey(v)) g.put(v, new ArrayList<>());
                }
            }
            return g;
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

    public static Map<Integer, List<int[]>> toWeighted(Map<Integer, List<Integer>> graph, Random r) {
        Map<Integer, List<int[]>> weighted = new HashMap<>();
        for (int u : graph.keySet()) {
            List<int[]> edges = new ArrayList<>();
            for (int v : graph.getOrDefault(u, Collections.emptyList())) {
                edges.add(new int[]{v, r.nextInt(9) + 1});
            }
            weighted.put(u, edges);
        }
        return weighted;
    }
}
