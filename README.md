#  Assign4Algorithms — Directed Graph Algorithms (Java)

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
Assign4Algorithms/
│
├── data/
│ ├── small_graph_1.json
│ ├── small_graph_2.json
│ ├── small_graph_3.json
│ ├── medium_graph_1.json
│ ├── large_graph_1.json
│ └── ... (test graphs)
│
├── src/
│ ├── main/java/
│ │ ├── graph/
│ │ │ ├── dagsp/ → DAGShortestPaths.java
│ │ │ ├── metrics/ → Metrics.java
│ │ │ ├── scc/ → SCCFinder.java, Condensation.java
│ │ │ └── topo/ → TopologicalSort.java
│ │ └── org/example/ → GraphLoader.java, Main.java
│ │
│ └── test/java/org/example/ → TestGraphAlgorithms.java
│
├── pom.xml
└── README.md


---

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


