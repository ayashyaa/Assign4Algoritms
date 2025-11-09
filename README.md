#  Assign4Algorithms — Directed Graph Algorithms 

###  Author: Ayazhan Aetova  
Assignment #4 — *Algorithms and Data Structures*

---

##  Project Overview

This project implements key algorithms for **directed graphs**, including:

- 🔹 Strongly Connected Components (**SCC**) — *Tarjan’s Algorithm*  
- 🔹 **Condensation Graph** construction (SCC compression)  
- 🔹 **Topological Sort** — *Kahn’s Algorithm*  
- 🔹 **Shortest and Longest Paths** in a DAG  
- 🔹 **Performance Metrics** tracking (time, visits, edges, relaxations, queue operations)

All algorithms are modular and designed for clarity, efficiency, and reusability.

---

## 📁 Project Structure
## Project structure (table)

| Path | Description |
|---|---|
| `data/` | Input JSON graphs (`small_*`, `medium_*`, `large_*`) |
| `src/main/java/graph/dagsp/DAGShortestPaths.java` | DAG shortest/longest path algorithms |
| `src/main/java/graph/scc/SCCFinder.java` | Tarjan’s SCC algorithm |
| `src/main/java/graph/scc/Condensation.java` | Build condensation DAG |
| `src/main/java/graph/topo/TopologicalSort.java` | Kahn’s topological sort |
| `src/main/java/graph/metrics/Metrics.java` | Performance metrics collection |
| `src/main/java/org/example/GraphLoader.java` | JSON graph loader (Gson) |
| `src/main/java/org/example/Main.java` | Demo / entry point |
| `src/test/java/org/example/TestGraphAlgorithms.java` | Unit tests (JUnit 5) |
| `pom.xml` | Maven build + dependencies |
| `README.md` | Project documentation |


## ⚙️ Implemented Algorithms

### 1️⃣ **Strongly Connected Components (SCC)**
**Class:** `graph.scc.SCCFinder`  
**Method:** `findSCCs()`  
**Algorithm:** Tarjan’s DFS-based SCC detection

**Description:**
- Uses discovery time (`disc[]`) and low-link values (`low[]`).
- Each vertex is pushed into a stack during DFS.
- When a root node is reached, a full SCC is extracted.

**Time Complexity:** `O(V + E)`

---

### 2️⃣ **Condensation Graph**
**Class:** `graph.scc.Condensation`  
**Method:** `buildCondensation(Map<Integer, List<Integer>>, List<List<Integer>>)`  

**Description:**  
Builds a DAG of SCC components:
- Each SCC becomes a single node.
- Edges between SCCs are preserved.
- The resulting graph is acyclic (DAG).

---

### 3️⃣ **Topological Sort**
**Class:** `graph.topo.TopologicalSort`  
**Method:** `kahnSort(Map<Integer, List<Integer>>)`  
**Algorithm:** Kahn’s Algorithm

**Description:**
- Calculates in-degrees for each node.
- Iteratively removes nodes with zero in-degree.
- Produces a valid topological order for DAGs.

**Time Complexity:** `O(V + E)`

---

### 4️⃣ **Shortest & Longest Paths in DAG**
**Class:** `graph.dagsp.DAGShortestPaths`  
**Methods:**  
- `shortestPath(graph, source, topoOrder)`  
- `longestPath(graph, source, topoOrder)`

**Description:**
- Works on DAGs using precomputed topological order.  
- Dynamic programming approach: each vertex is relaxed once.  
- Also supports **path reconstruction** via parent tracking.

**Time Complexity:** `O(V + E)`

---

### 5️⃣ **Metrics Tracking**
**Class:** `graph.metrics.Metrics`

**Monitors:**
- Execution time (nanoseconds)
- DFS visits and explored edges
- Queue pushes/pops in topological sort
- Relaxations in shortest/longest paths

---

### 6️⃣ **Graph Loading**
**Class:** `org.example.GraphLoader`  
**Library:** `com.google.gson`  

**JSON Format Example:**
```json
{
  "0": [1, 2],
  "1": [2],
  "2": []
}


