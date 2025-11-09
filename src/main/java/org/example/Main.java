package org.example;

import graph.scc.SCCFinder;
import graph.scc.Condensation;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGShortestPaths;
import graph.dagsp.DAGShortestPaths.Result;
import graph.metrics.Metrics;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = null;

        if (args != null && args.length > 0 && args[0] != null && !args[0].isBlank()) {
            filePath = args[0];
            System.out.println("Using graph file from argument: " + filePath);
        } else {
          
            filePath = findFirstGraphFile("data ");
            if (filePath != null) {
                System.out.println("Auto-discovered graph file: " + filePath);
            } else {
                System.err.println("No graph JSON found in data/ — please add one or pass path as an argument.");
 
                System.err.println("Example: Run with argument 'data/small_graph_1.json' or set Run configuration working directory to project root.");
                System.exit(0);
            }
        }

        Map<Integer, List<Integer>> graph = GraphLoader.loadGraph(filePath);
        if (graph == null || graph.isEmpty()) {
            System.err.println("Failed to load graph (empty or null). Check JSON format and path: " + filePath);
            System.exit(0);
        }
        System.out.println("Loaded graph: nodes=" + graph.size());

        Metrics metrics = new Metrics();
        metrics.startTimer();
        SCCFinder sccFinder = new SCCFinder(graph, metrics);
        List<List<Integer>> sccs = sccFinder.findSCCs();
        metrics.stopTimer();

        System.out.println("\nSCCs found: " + sccs.size());
        for (int i = 0; i < sccs.size(); i++) {
            System.out.println("  comp " + i + ": " + sccs.get(i));
        }
        System.out.println(String.format("SCC time: %.3f ms, dfs visits: %d, edges: %d",
                metrics.elapsedNs() / 1_000_000.0, metrics.getDfsVisits(), metrics.getDfsEdges()));

        Map<String, Object> cond = Condensation.buildCondensation(graph, sccs);
        @SuppressWarnings("unchecked")
        Map<Integer, List<Integer>> condensation = (Map<Integer, List<Integer>>) cond.get("condensation");
        System.out.println("\nCondensation DAG: components=" + condensation.size());

        TopologicalSort topo = new TopologicalSort(metrics);
        List<Integer> topoOrder;
        try {
            topoOrder = topo.kahnSort(condensation);
            System.out.println("Topological order of components: " + topoOrder);
            System.out.println("Kahn pushes: " + metrics.getKahnPushes() + ", pops: " + metrics.getKahnPops());
        } catch (RuntimeException e) {
            System.out.println("Condensation graph has a cycle (unexpected). Will continue without topo order.");
            topoOrder = new ArrayList<>(condensation.keySet());
        }

        Map<Integer, List<int[]>> weighted = GraphLoader.toWeighted(condensation, new Random(42));
        DAGShortestPaths dsp = new DAGShortestPaths();
        int source = topoOrder.isEmpty() ? 0 : topoOrder.get(0);
        Result shortest = dsp.shortestPath(weighted, source, topoOrder);
        Result longest = dsp.longestPath(weighted, source, topoOrder);

        System.out.println("\nShortest distances from component " + source + ": " + shortest.dist);
        System.out.println("Longest distances from component " + source + ": " + longest.dist);

        if (!topoOrder.isEmpty()) {
            int target = topoOrder.get(topoOrder.size() - 1);
            List<Integer> p = longest.reconstructPath(target);
            System.out.println("Example longest path to component " + target + ": " + p);
        }

        System.out.println("\nProgram finished.");
    }


    private static String findFirstGraphFile(String dataDir) {
        File d = new File(dataDir);
        if (!d.exists() || !d.isDirectory()) return null;
        File[] files = d.listFiles((dir, name) -> name.toLowerCase().endsWith(".json") && name.toLowerCase().contains("graph"));
        if (files == null || files.length == 0) return null;
        Arrays.sort(files, Comparator.comparing(File::getName)); 
        return files[0].getPath();
    }
}
