import java.util.*;

/**
 * Facade for finding the minimal spanning tree of a graph. Kruskal's algorithm uses a union-find
 * data structure to find the minimum spanning tree of a graph. A minimum spanning tree is
 * represented as just another graph.
 *
 */
public final class Kruskal {
    private Kruskal() {}

    /**
     * Finds a minimum spanning tree of the specified graph. If there are multiple possible minimum
     * spanning trees, this method may return any one of them. If the specified graph is empty, then
     * an empty tree is returned. If the graph is disconnected, then a minimum spanning forest is
     * returned.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param g the graph
     * @return a new {@link Graph} containing the same vertices as the specified graph, but with
     *         only the edges on the minimum spanning tree.
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static Graph getMST(Graph g) {
        if (g == null) {
            throw new IllegalArgumentException("graph is null");
        }
        Graph ans = new Graph(g.getSize());
        UnionFind disjointSets = new UnionFind(g.getSize());
        Set<Edge> edges = getEdges(g);
        List<Edge> sortedEdges = new ArrayList();
        sortedEdges.addAll(edges);
        Collections.sort(sortedEdges);
        for (Edge e: sortedEdges) {
            if (disjointSets.find(e.u) != disjointSets.find(e.v)) {
                ans.addEdge(e.u, e.v, e.weight);
                disjointSets.union(e.u, e.v);
            }
        }
        return ans;
    }

    /**
     * Returns the set of edges in this graph. Since this is an undirected graph, if an edge {@code
     * u-v} exists in the graph, be sure that you return either an Edge object for the {@code u-v}
     * direction <em>or</em> an {@link Edge} object for the {@code v-u} direction, but not both.
     * <p/>
     *
     * @param g the graph
     * @return a set containing this graph's edges
     */
    static Set<Edge> getEdges(Graph g) {
        if (g == null) {
            throw new IllegalArgumentException("g is null");
        }
        Set<Edge> ans = new HashSet<>();
        for (int i = 0; i < g.getSize(); i++) {
            Set<Integer> neighbors = g.getNeighbors(i);
            Iterator<Integer> iter = neighbors.iterator();
            while (iter.hasNext()) {
                Integer x = iter.next();
                Edge j = new Edge(i, x, g.getWeight(i, x));
                Edge k = new Edge(x, i, g.getWeight(x, i));
                if (!ans.contains(j) && !ans.contains(k)) {
                    ans.add(j);
                }
            }
        }
        return ans;
    }

    /**
     * A simple representation of a graph edge. Note that a {@code List<Edge>} can be easily be
     * sorted by edge weights.
     * 
     * DO NOT modify this inner class.
     * <p/>
     */
    static final class Edge implements Comparable<Edge> {
        final int u;
        final int v;
        final int weight;

        /**
         * Creates an Edge by first standardizing the edges so the smaller vertex comes first.
         */
        public Edge(int u, int v, int weight) {
            if (u > v) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            this.u = u;
            this.v = v;
            this.weight = weight;
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
            return Objects.equals(u, edge.u) && Objects.equals(v, edge.v)
                            && Objects.equals(weight, edge.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(u, v, weight);
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }
}
