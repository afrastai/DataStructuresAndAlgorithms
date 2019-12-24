import java.util.*;

/**
 * Type for a simple, weighted directed graph. By convention, the n vertices will be labeled
 * 0,1,...,n-1. The edge weights can be any double value. Self loops and parallel edges are not
 * allowed.
 *
 */
public class WDGraph {
    class Edge implements Comparable {
        int neighbor;
        double weight;

        public Edge(int neighbor, double weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }

        public Edge(int neighbor) {
            this.neighbor = neighbor;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Edge edge = (Edge) o;
            return neighbor == edge.neighbor;
        }

        @Override
        public int hashCode() {
            return Objects.hash(neighbor);
        }

        @Override
        public int compareTo(Object o) {
            Edge x = (Edge) o;
            return neighbor - x.neighbor;
        }
    }

    int numVert;
    ArrayList<Edge>[] adjOutListArr;
    ArrayList<Edge>[] adjInListArr;

    public WDGraph() {} // Do NOT delete/modify this constructor!
    
    /**
     * Initializes a graph of size {@code n}. All valid vertices in this graph thus have integer
     * indices in the half-open range {@code [0, n)}.
     *
     * @param n the number of vertices in the graph
     * @throws IllegalArgumentException if {@code n} is negative
     * @implSpec This method should run in O(n) time
     */
    public WDGraph(int n) {
        numVert = n;
        adjInListArr = new ArrayList[numVert];
        adjOutListArr = new ArrayList[numVert];
        for (int i = 0; i < numVert; i++) {
            adjOutListArr[i] = new ArrayList<>();
            adjInListArr[i] = new ArrayList<>();
        }
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices in the graph
     * @implSpec This method should run in O(1) time.
     */
    public int getSize() {
        return numVert;
    }

    /**
     * Creates an edge from {@code u} to {@code v} if it does not already exist. A call to this
     * method should <em>not</em> modify the edge weight if the {@code u-v} edge already exists.
     *
     * @param u the source vertex to connect
     * @param v the target vertex to connect
     * @param weight the edge weight
     * @return {@code true} if the graph changed as a result of this call, false otherwise (i.e. if
     *         the edge is already present)
     * @throws IllegalArgumentException if a specified vertex does not exist or if u == v
     * @implSpec This method should run in O(deg(u)) time
     */
    public boolean addEdge(int u, int v, double weight) {
        if (u == v) {
            throw new IllegalArgumentException("no self-loops allowed");
        }
        if (u >= adjOutListArr.length || u < 0) {
            throw new IllegalArgumentException("vertex u does not exist.");
        }
        if (v >= adjOutListArr.length || v < 0) {
            throw new IllegalArgumentException("vertex v does not exist.");
        }
        if (hasEdge(u, v)) {
            return false;
        } else {
            Edge x = new Edge(v, weight);
            Edge y = new Edge(u, weight);
            adjOutListArr[u].add(x);
            adjInListArr[v].add(y);
            return true;
        }
    }

    boolean hasEdge(int u, int v) {
        if (u >= adjOutListArr.length || u < 0) {
            throw new IllegalArgumentException("vertex u does not exist.");
        }
        if (v >= adjOutListArr.length || v < 0) {
            throw new IllegalArgumentException("vertex v does not exist.");
        }
        Edge x = new Edge(v);
        return adjOutListArr[u].contains(x);
    }

    /**
     * Returns the weight of an edge.
     *
     * @param u source vertex
     * @param v target vertex
     * @return the edge weight of {@code u-v}
     * @throws NoSuchElementException if the {@code u-v} edge does not exist
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in O(deg(u)) time.
     */
    public double getWeight(int u, int v) {
        if (u >= adjOutListArr.length || u < 0) {
            throw new IllegalArgumentException("vertex u does not exist.");
        }
        if (v >= adjOutListArr.length || v < 0) {
            throw new IllegalArgumentException("vertex v does not exist.");
        }
        if (!hasEdge(u, v)) {
            throw new NoSuchElementException("edge u-v does not exist.");
        }
        int i = adjOutListArr[u].indexOf(new Edge(v));
        return adjOutListArr[u].get(i).weight;
    }

    /**
     * Returns the out-neighbors of the specified vertex.
     *
     * @param v the vertex
     * @return all out neighbors of the specified vertex or an empty set if there are no out
     *         neighbors
     * @throws IllegalArgumentException if the specified vertex does not exist
     * @implSpec This method should run in O(outdeg(v)) time.
     */
    public Set<Integer> outNeighbors(int v) {
        Set<Integer> ans = new HashSet<>();
        for (Edge x: adjOutListArr[v]) {
            ans.add(x.neighbor);
        }
        return ans;
    }

    /**
     * Returns the in-neighbors of the specified vertex.
     *
     * @param v the vertex
     * @return all in neighbors of the specified vertex or an empty set if there are no in neighbors
     * @throws IllegalArgumentException if the specified vertex does not exist
     * @implSpec This method should run in O(indeg(v)) time.
     */
    public Set<Integer> inNeighbors(int v) {
        Set<Integer> ans = new HashSet<>();
        for (Edge x: adjInListArr[v]) {
            ans.add(x.neighbor);
        }
        return ans;
    }

}
