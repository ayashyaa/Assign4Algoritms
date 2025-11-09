package graph.metrics;

public class Metrics {
    private long startTimeNs;
    private long endTimeNs;
    private long relaxations = 0;
    private long dfsVisits = 0;
    private long dfsEdges = 0;
    private long kahnPushes = 0;
    private long kahnPops = 0;

    public void startTimer() { startTimeNs = System.nanoTime(); }
    public void stopTimer() { endTimeNs = System.nanoTime(); }
    public long elapsedNs() { return endTimeNs - startTimeNs; }

    public void incRelaxations() { relaxations++; }
    public void incDfsVisits() { dfsVisits++; }
    public void incDfsEdges() { dfsEdges++; }
    public void incKahnPushes() { kahnPushes++; }
    public void incKahnPops() { kahnPops++; }

    public long getRelaxations() { return relaxations; }
    public long getDfsVisits() { return dfsVisits; }
    public long getDfsEdges() { return dfsEdges; }
    public long getKahnPushes() { return kahnPushes; }
    public long getKahnPops() { return kahnPops; }
}
