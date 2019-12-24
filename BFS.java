import java.util.LinkedList;
import java.util.List;

/**
 * Facade for computing an unweighted shortest path between two vertices in a graph. We represent
 * paths as ordered lists of integers corresponding to vertices.
 *
 */
public final class BFS {
    private BFS() {}

    /**
     * Returns a shortest path from {@code src} to {@code tgt} by executing a breadth-first search.
     * If there are multiple shortest paths, this method may return any one of them. Please note, 
     * you MUST use your ResizingDeque implementation as the BFS queue for this method. 
     * <p/>
     * Do NOT modify this method header.
     *
     * @param g the graph
     * @param src the vertex from which to search
     * @param tgt the vertex to find via {@code src}
     * @return an ordered list of vertices on a shortest path from {@code src} to {@code tgt}, or an
     *         empty list if there is no path from {@code src} to {@code tgt}. The first element
     *         should be {@code src} and the last element should be {@code tgt}. If
     *         {@code src == tgt}, a list containing just that element is returned.
     * @throws IllegalArgumentException if {@code src} or {@code tgt} is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static List<Integer> getShortestPath(Graph g, int src, int tgt) {
        if (g == null) {
            throw new IllegalArgumentException("graph is null");
        }
        if (src < 0 || src >= g.getSize() || tgt < 0 || tgt >= g.getSize()) {
            throw new IllegalArgumentException("src or tgt are not in the graph.");
        }
        boolean[] discovered = new boolean[g.getSize()];
        Integer[] parents = new Integer[g.getSize()];
        ResizingDequeImpl<Integer> queue = new ResizingDequeImpl();
        queue.addLast(src);
        discovered[src] = true;

        while (queue.size() != 0) {
            Integer v = queue.pollFirst();
            for (Integer u: g.getNeighbors(v)) {
                if (!discovered[u]) {
                    discovered[u] = true;
                    queue.addLast(u);
                    parents[u] = v;
                }
            }
        }
        List<Integer> ans = new LinkedList<>();
        Integer i = tgt;
        while (i != src) {
            if (i == null) {
                return new LinkedList<>();
            }
            ans.add(0, i);
            i = parents[i];
        }
        ans.add(0, src);
        return ans;
    }
}
