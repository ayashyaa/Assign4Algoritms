package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import graph.scc.SCCFinder;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGShortestPaths;
import graph.metrics.Metrics;

import java.util.*;

public class TestGraphAlgorithms {

    @Test
    public void testSCCFinderSimple() {
        Map<Integer, List<Integer>> g = new HashMap<>();
        g.put(0, Arrays.asList(1));
        g.put(1, Arrays.asList(2));
        g.put(2, Arrays.asList(0, 3));
        g.put(3, Arrays.asList());

        Metrics metrics = new Metrics();
        SCCFinder scc = new SCCFinder(g, metrics);
        List<List<Integer>> sccs = scc.findSCCs();

        assertTrue(sccs.stream().anyMatch(c -> c.containsAll(Arrays.asList(0, 1, 2))));
        assertTrue(sccs.stream().anyMatch(c -> c.contains(3)));
    }

    @Test
    public void testTopologicalSort() {
        Map<Integer, List<Integer>> g = new HashMap<>();
        g.put(0, Arrays.asList(1, 2));
        g.put(1, Arrays.asList(3));
        g.put(2, Arrays.asList(3));
        g.put(3, new ArrayList<>());

        TopologicalSort topo = new TopologicalSort();
        List<Integer> order = topo.kahnSort(g);

        assertEquals(4, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(3));
    }

    @Test
    public void testDAGShortestPaths() {
        Map<Integer, List<int[]>> wg = new HashMap<>();
        wg.put(0, Arrays.asList(new int[]{1, 5}, new int[]{2, 3}));
        wg.put(1, Arrays.asList(new int[]{3, 1}));
        wg.put(2, Arrays.asList(new int[]{3, 7}));
        wg.put(3, new ArrayList<>());

        DAGShortestPaths sp = new DAGShortestPaths();
        List<Integer> topo = Arrays.asList(0, 1, 2, 3);
        DAGShortestPaths.Result res = sp.shortestPath(wg, 0, topo);

        assertEquals(0, res.dist.get(0));
        assertEquals(5, res.dist.get(1));
        assertEquals(3, res.dist.get(2));
        assertEquals(6, res.dist.get(3)); // 0->1->3 = 6
    }
}
