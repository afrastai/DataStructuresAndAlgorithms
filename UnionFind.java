import java.util.*;

/**
 * A data structure for tracking a set of elements partitioned into disjoint subsets. We can
 * <em>union</em> these subsets together and then <em>find</em> which set an element belongs to. In
 * particular, we can determine when two elements belong to the same set.
 *
 */
public class UnionFind {
    class Element {
        int rank;
        int parent;

        public Element(Integer val) {
            this.parent = val;
        }
    }
    int numElements;
    ArrayList<Element> elementList;
    /**
     * Initializes a new union-find structure for the specified number of elements.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param n the number of singleton sets with which to start
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public UnionFind(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("invalid n is negative.");
        }
        numElements = n;
        elementList = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            Element e = new Element(i);
            elementList.add(i, e);
        }
    }

    /**
     * Joins two sets.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u a vertex in the first set
     * @param v a vertex in the second set
     * @throws NoSuchElementException if {@code u < 0} or {@code u >= n} or {@code v < 0} o {@code v
     * >= n}
     */
    public void union(int u, int v) {
        if (u < 0 || u >= numElements) {
            throw new IllegalArgumentException("u is out of bounds.");
        }
        if (v < 0 || v >= numElements) {
            throw new IllegalArgumentException("v is out of bounds.");
        }
        int uRoot = find(u);
        int vRoot = find(v);
        if (uRoot == vRoot) {
            return;
        }
        if (elementList.get(u).rank < elementList.get(v).rank) {
            elementList.get(uRoot).parent = vRoot;
            if (elementList.get(uRoot).rank == elementList.get(vRoot).rank) {
                elementList.get(vRoot).rank++;
            }
        } else {
            elementList.get(vRoot).parent = uRoot;
            if (elementList.get(uRoot).rank == elementList.get(vRoot).rank) {
                elementList.get(uRoot).rank++;
            }
        }
    }


    /**
     * Finds which set a vertex belongs to. We represent a vertex's set by the index of its tree's
     * root node. If for some {@code v}, {@code find(u) == find(v)},
     * then {@code u} and {@code v} are in the same set.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u the vertex
     * @return the root of the set to which the input vertex belongs
     * @throws NoSuchElementException if {@code u < 0} or {@code u >= n}
     */
    public int find(int u) {
        if (u < 0 || u >= numElements) {
            throw new IllegalArgumentException("u is out of bounds.");
        }
        if (u != elementList.get(u).parent) {
            elementList.get(u).parent = find(elementList.get(u).parent);
        }
        return elementList.get(u).parent;
    }
}
