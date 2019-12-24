import java.util.*;

/**
 * Contains methods to run DFS on a {@link WDGraph}.
 *
 * Iterative implementations of DFS MUST use your ResizingDeque as the DFS stack. 
 *
 */
public final class DFS {
    private DFS() {}

    /**
     * Runs depth-first search on the input graph {@code g} and returns the list of nodes explored
     * in reverse order of node finishing time. For the purposes of testing, we also ask you to
     * adhere to the following constraints:
     * <ul>
     * <li>When visiting a node's neighbors, the neighbors should be visited in increasing order.
     * You may use {@link java.util.Collections#sort(List)} for this.</li>
     * <li>If DFS finishes on the source/root node but not the entire graph has been explored, DFS
     * should start again on the smallest node that hasn't yet been visited.</li>
     * </ul>
     * Do NOT modify this method header.
     *
     * @param g the graph
     * @param src the vertex from which to begin search
     * @return a list containing all vertices of the graph in reverse order of finish time
     * @throws IllegalArgumentException if {@code src} is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static List<Integer> dfsReverseFinishingTime(WDGraph g, int src) {
        if (g == null) {
            throw new IllegalArgumentException("graph is null");
        }
        if (src < 0 || src > g.getSize()) {
            throw new IllegalArgumentException("src is out of bounds");
        }

        List<Integer> ans = new LinkedList<>();

        boolean[] discovered = new boolean[g.getSize()];

        dfsReverseFinishingTimeHelper(g, src, discovered, ans);

        for (int i = 0; i < discovered.length; i++) {
            if (!discovered[i]) {
                dfsReverseFinishingTimeHelper(g, i, discovered, ans);
            }
        }

        return ans;
    }

    static void dfsReverseFinishingTimeHelper(WDGraph g, int x, boolean[] discovered,
                                              List<Integer> ans) {
        discovered[x] = true;
        List<WDGraph.Edge> orderedAdjList = g.adjOutListArr[x];
        Collections.sort(orderedAdjList);

        for (int i = 0; i < orderedAdjList.size(); i++) {
            if (!discovered[orderedAdjList.get(i).neighbor]) {
                dfsReverseFinishingTimeHelper(g, orderedAdjList.get(i).neighbor,
                        discovered, ans);
            }
        }
        ans.add(0, x);
    }

    /**
     * Runs depth-first search on the input graph {@code g} and returns the set of nodes reachable
     * from {@code src}.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param g the graph
     * @param src the vertex from which to begin search
     * @return a set containing all vertices reachable from {@code src}, including {@code src}
     * @throws IllegalArgumentException if {@code src} is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static Set<Integer> dfsExploreComponent(WDGraph g, int src) {
        if (g == null) {
            throw new IllegalArgumentException("graph is null");
        }
        if (src < 0 || src > g.getSize()) {
            throw new IllegalArgumentException("src is out of bounds");
        }

        Set<Integer> ans = new HashSet<>();

        boolean[] discovered = new boolean[g.getSize()];

        dfsExploreComponentHelper(g, src, discovered, ans);

        return ans;
    }

    static void dfsExploreComponentHelper(WDGraph g, int x, boolean[] discovered,
                                          Set<Integer> ans) {
        discovered[x] = true;
        ans.add(x);

        Set<Integer> adjList = g.outNeighbors(x);

        Iterator<Integer> iter = adjList.iterator();

        while (iter.hasNext()) {
            Integer i = iter.next();
            if (!discovered[i]) {
                dfsExploreComponentHelper(g, i, discovered, ans);
            }
        }
    }
}
