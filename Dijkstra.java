
import java.util.LinkedList;

/**
 * Provides access to Dijkstra's algorithm for a weighted, directed graph.
 * 
 */
public class Dijkstra {
    private Dijkstra() {}

    /**
     * Computes the shortest path between two nodes in a weighted, directed graph
     *
     * @param g the graph to compute the shortest path on
     * @param src the source node
     * @param tgt the target node
     * @return an Iterable containing the nodes in the path, including the start and end nodes. If
     *         the start and end nodes are the same, or if there is no path from the start node to
     *         the end node, it returns an empty Iterable.
     * @throws IllegalArgumentException if g is null
     * @throws IllegalArgumentException if src is not in g
     * @throws IllegalArgumentException if tgt is not in g
     */

    public static Iterable<Integer> getShortestPath(WDGraph g, int src, int tgt) {
        if (g == null) {
            throw new IllegalArgumentException("g is null");
        }
        if (src < 0 || src > g.getSize()) {
            throw new IllegalArgumentException("src is out of bounds");
        }
        if (tgt < 0 || tgt > g.getSize()) {
            throw new IllegalArgumentException("tgt is out of bounds");
        }

        double[] distances = new double[g.getSize()];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
        }
        distances[src] = 0;


        Integer[] parent = new Integer[g.getSize()];

        BinaryMinHeap<Double, Integer> queue = new BinaryMinHeap();
        queue.add(distances[src], src);
        while (!queue.isEmpty()) {
            int x = queue.extractMin();
            for (int i: g.outNeighbors(x)) {
                if (distances[i] > distances[x] + g.getWeight(x, i)) {
                    distances[i] = distances[x] + g.getWeight(x, i);
                    parent[i] = x;
                    if (queue.containsValue(i)) {
                        queue.decreaseKey(i, distances[i]);
                    } else {
                        queue.add(distances[i], i);
                    }
                }
            }
        }
        LinkedList<Integer> ans = new LinkedList<>();
        int i = tgt;
        while (i != src) {
            ans.add(0, i);
            i = parent[i];
        }
        ans.add(0, src);
        return ans;
    }

}
