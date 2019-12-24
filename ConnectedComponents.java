import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Provides access to the strongly connected components for a graph.
 */
public class ConnectedComponents {
    private ConnectedComponents() {}
    
    /**
     * Computes the strongly connected components of the specified graph.
     *
     * HINT: In order to compute the transpose graph of G, it will be helpful to use the wrapper
     * class below that transposes a graph in constant time.
     *
     * @param graph the graph
     * @return an immutable set of sets of vertices that comprise the strongly connected components
     *         of the specified graph
     * @throws IllegalArgumentException if the graph is null
     */
    public static Set<Set<Integer>> stronglyConnectedComponents(WDGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        Set<Set<Integer>> ans = new HashSet<>();
        List<Integer> reverseFinishTime = DFS.dfsReverseFinishingTime(graph, 0);
        TransposeGraph gTrans = new TransposeGraph(graph);

        boolean[] discovered = new boolean[graph.getSize()];

        while (reverseFinishTime.size() != 0) {
            Integer x = reverseFinishTime.remove(0);
            if (!discovered[x]) {
                discovered[x] = true;
                Set<Integer> explored = DFS.dfsExploreComponent(gTrans, x);
                Set<Integer> xConComp = new HashSet<>();
                xConComp.add(x);
                stronglyConnectedComponentsHelper(discovered, x, explored, xConComp);
                ans.add(xConComp);
            }
        }

        return ans;
    }

    static void stronglyConnectedComponentsHelper(boolean[] discovered,
                                                  Integer x, Set<Integer> explored,
                                                  Set<Integer> xConComp) {
        Iterator<Integer> iter = explored.iterator();
        while (iter.hasNext()) {
            Integer k = iter.next();
            if (!discovered[k]) {
                discovered[k] = true;
                xConComp.add(k);
            }
        }
    }


    // A little hacky since we didn't use interface files, so we're providing it to you.
    // This wrapper class transposes the graph in O(1) time.
    static class TransposeGraph extends WDGraph {
        private WDGraph g;
        
        public TransposeGraph(WDGraph g) {
            super(); // constant time, but is required since we're extending the WDGraph class.
            this.g = g;
        }
        
        @Override
        public Set<Integer> outNeighbors(int v) {
            return g.inNeighbors(v); // Transposes edges
        }
        
        @Override
        public Set<Integer> inNeighbors(int v) {

            return g.outNeighbors(v); // Transposes edges
        }
        
        @Override
        public int getSize() {

            return g.getSize();
        }
        
        @Override
        public boolean addEdge(int u, int v, double weight) {

            return g.addEdge(v, u, weight);
        }
        
        @Override
        public double getWeight(int u, int v) {

            return g.getWeight(v, u);
        }
    }
}
