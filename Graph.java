import java.util.*;
/**
 * Contains the API necessary for an undirected, (optionally) weighted graph. We use ints to
 * identify vertices (i.e. the vertices are labeled 0 through n-1).
 * <p/>
 * We call the graph "optionally weighted" because it can be used by algorithms that use weights
 * (like Kruskal's) and by algorithms that do not (like BFS). An algorithm like BFS would simply
 * ignore any weights present.
 *
 * This Graph can use at most O(m + n) space. Please DO NOT use adjacency matrices!
 * 
 * Also note that the runtimes given are worst-case runtimes. As a result, you shouldn't be 
 * implementing your graph using a HashMap as the primary data structure for the adjacency list
 * (you may use HashMaps/HashSets in other places as long as you meet the provided runtimes). 
 *
 */
public class Graph {
    class UndirectedEdge {
        int src;
        int dest;
        int weight;

        public UndirectedEdge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        public UndirectedEdge(int src, int dest) {
            this.src = src;
            this.dest = dest;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UndirectedEdge edge = (UndirectedEdge) o;
            return src == edge.src &&
                    dest == edge.dest;
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dest);
        }

        @Override
        public String toString() {
            return "UndirectedEdge{" +
                    "src=" + src +
                    ", dest=" + dest +
                    ", weight=" + weight +
                    '}';
        }
    }


    private int numVert;
    LinkedList<UndirectedEdge>[] adjListArr;
    /**
     * Initializes a graph of size {@code n}. All valid vertices in this graph thus have integer
     * indices in the half-open range {@code [0, n)}.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param n the number of vertices in the graph
     * @throws IllegalArgumentException if {@code n} is negative
     * @implSpec This constructor should run in O(n) time.
     */
    public Graph(int n) {
        numVert = n;
        adjListArr = new LinkedList[n];
        for (int i = 0; i < numVert; i++) {
            adjListArr[i] = new LinkedList<>();
        }
    }

    /**
     * Returns the number of vertices in the graph.
     * <p/>
     * Do NOT modify this method header.
     *
     * @return the number of vertices in the graph
     * @implSpec This method should run in O(1) time.
     */
    public int getSize() {
        return numVert;
    }

    /**
     * Determines if an edge exists between two vertices.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u a vertex
     * @param v a vertex
     * @return {@code true} if the {@code u-v} edge is in this graph
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in O(max(deg(u), deg(v))) time.
     */
    public boolean hasEdge(int u, int v) {
        if (u >= adjListArr.length || u < 0) {
            throw new IllegalArgumentException("vertex u does not exist.");
        }
        if (v >= adjListArr.length || v < 0) {
            throw new IllegalArgumentException("vertex v does not exist.");
        }
        UndirectedEdge x = new UndirectedEdge(u, v);
        UndirectedEdge y = new UndirectedEdge(v, u);
        return adjListArr[u].contains(x) && adjListArr[v].contains(y);
    }

    /**
     * Creates an edge between {@code u} and {@code v} if it does not already exist. A call to this
     * method should <em>not</em> modify the edge weight if the {@code u-v} edge already exists.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u one vertex to connect
     * @param v the other vertex to connect
     * @param weight the edge weight
     * @return {@code true} if the graph changed as a result of this call, false otherwise (i.e. if
     *         the edge is already present)
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in O(max(deg(u), deg(v))) time.
     */
    public boolean addEdge(int u, int v, int weight) {
        if (u >= adjListArr.length || u < 0) {
            throw new IllegalArgumentException("vertex u does not exist.");
        }
        if (v >= adjListArr.length || v < 0) {
            throw new IllegalArgumentException("vertex v does not exist.");
        }
        if (hasEdge(u, v)) {
            return false;
        } else {
            UndirectedEdge x = new UndirectedEdge(u, v, weight);
            UndirectedEdge y = new UndirectedEdge(v, u, weight);
            adjListArr[u].add(x);
            adjListArr[v].add(y);
            return true;
        }
    }

    /**
     * Returns the weight of an edge.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u a vertex
     * @param v a vertex
     * @return the edge weight of {@code u-v}
     * @throws NoSuchElementException if the {@code u-v} edge does not exist
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in O(max(deg(u), deg(v))) time.
     */
    public int getWeight(int u, int v) {
        if (u >= adjListArr.length || u < 0) {
            throw new IllegalArgumentException("vertex u does not exist.");
        }
        if (v >= adjListArr.length || v < 0) {
            throw new IllegalArgumentException("vertex v does not exist.");
        }

        if (!hasEdge(u, v)) {
            throw new NoSuchElementException("edge u-v does not exist.");
        }

        int i = adjListArr[u].indexOf(new UndirectedEdge(u, v));
        return adjListArr[u].get(i).weight;
    }

    /**
     * Returns the neighbors of the specified vertex.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param v the vertex
     * @return all neighbors of the specified vertex or an empty set if there are no neighbors. If
     *         there is a self-loop on v, include v in the neighbor set.
     * @throws IllegalArgumentException if the specified vertex does not exist
     * @implSpec This method should run in expected O(deg(v)) time.
     */
    public Set<Integer> getNeighbors(int v) {
        if (v >= adjListArr.length || v < 0) {
            throw new IllegalArgumentException("vertex v does not exist.");
        }
        Set<Integer> ans = new HashSet<>();
        for (UndirectedEdge x: adjListArr[v]) {
            ans.add(x.dest);
        }
        return ans;
    }
}
