import java.util.*;

public class TrieMap<V> extends AbstractTrieMap<V> {
    
    /**
     * The size of our key alphabet or character set. Here, I use 26 for the standard lowercase
     * alphabet.
     */
    private static final int BRANCH_FACTOR = 26;

    /**
     * The root node of the trie.
     */
    private Node<V> root;

    /**
     * The size of the trie.
     */
    private int size;

    /**
     * Constructs an empty TrieMap.
     */
    public TrieMap() {
        root = new Node<>(null);
    }

    /**
     * Converts a {@code char} into an array index.
     * <p>
     * Effectively maps {@code a -> 0, b -> 1, ..., z -> 25}.
     *
     * @param c the character
     * @return the array index corresponding to the specified character
     * @throws IllegalArgumentException if the specified character is not valid as an index
     */
    private static int convertToIndex(char c) {
        if (c < 'a' || c > 'z') {
            throw new IllegalArgumentException("Character must be in the range [a..z]");
        }
        return c - 'a';
    }

    /**
     * Converts an array index into a {@code char} in the key.
     * <p>
     * Effectively maps {@code 0 -> a, b -> 1, ..., 25 -> z}.
     *
     * @param i the index
     * @return the character corresponding to the specified array index
     * @throws IllegalArgumentException if the specified index is out of bounds
     */
    private static char convertToChar(int i) {
        if (i < 0 || i >= BRANCH_FACTOR) {
            throw new IllegalArgumentException("Index must be in the range [0..BRANCH_FACTOR]");
        }
        return (char) (i + 'a');
    }

    public Node<V> getRoot() {
        return root;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws IllegalArgumentException if the specified key contains characters other than
     *                                  lowercase letters
     */
    @Override
    public V put(CharSequence key, V value) {
        // Uses a Node reference to iteratively walk down the trie
        // to where you want to store the value.
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }

        for (int i = 0; i < key.length(); i++) {
            if (convertToIndex(key.charAt(0)) < 0 || convertToIndex(key.charAt(0)) > 25) {
                throw new IllegalArgumentException("key contains characters " +
                        "other than lowercase letters");
            }
        }
        Node<V> walker = root;
        for (int i = 0; i < key.length(); i++) {
            int x = convertToIndex(key.charAt(i));
            if (walker.children == null) {
                walker.initChildren();
            }
            if (walker.children[x] != null) {
                walker = walker.children[x];
            } else {
                walker.children[x] = new Node(null);
                walker = walker.children[x];
            }
            if (i == key.length() - 1) {
                V ans = (V) walker.value;
                walker.value = value;
                size++;
                return ans;
            }
        }
        return null;
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws IllegalArgumentException if the specified key contains characters other than
     *                                  lowercase letters
     */
    @Override
    public V get(CharSequence key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        for (int i = 0; i < key.length(); i++) {
            if (convertToIndex(key.charAt(0)) < 0 || convertToIndex(key.charAt(0)) > 25) {
                throw new IllegalArgumentException("key contains characters " +
                        "other than lowercase letters");
            }
        }
        Node walker = root;
        for (int i = 0; i < key.length(); i++) {
            int x = convertToIndex(key.charAt(i));
            if (i == key.length() - 1) {
                return (V) walker.children[x].value;
            } else if (walker.children[x] == null) {
                return null;
            } else {
                walker = walker.children[x];
            }
        }
        return null;
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws IllegalArgumentException if the specified key contains characters other than
     *                                  lowercase letters
     */
    @Override
    public boolean containsKey(CharSequence key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }

        for (int i = 0; i < key.length(); i++) {
            if (convertToIndex(key.charAt(0)) < 0 || convertToIndex(key.charAt(0)) > 25) {
                throw new IllegalArgumentException("key contains characters " +
                        "other than lowercase letters");
            }
        }
        Node walker = root;
        for (int i = 0; i < key.length(); i++) {
            int x = convertToIndex(key.charAt(i));
            if (i == key.length() - 1) {
                return walker.children[x].value != null;
            } else if (walker.children[x] == null) {
                return false;
            } else {
                walker = walker.children[x];
            }
        }
        return false;
    }

    /**
     * @throws IllegalArgumentException if the value provided is null
     */
    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        Queue<Node<V>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node x = queue.poll();
            if (x.value == null || !x.value.equals(value)) {
                if (x.children == null) {
                    continue;
                }
                for (Node n: x.children) {
                    if (n != null) {
                        queue.add(n);
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws IllegalArgumentException if the specified key contains characters other than
     *                                  lowercase letters
     */
    @Override
    public V remove(CharSequence key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }

        for (int i = 0; i < key.length(); i++) {
            if (convertToIndex(key.charAt(0)) < 0 || convertToIndex(key.charAt(0)) > 25) {
                throw new IllegalArgumentException("key contains characters " +
                        "other than lowercase letters");
            }
        }

        if (!containsKey(key)) {
            return null;
        }
        Node walker = root;
        Node dontDelete = root;
        int firstDelete = 0;
        V ans;
        for (int i = 0; i < key.length(); i++) {
            int x = convertToIndex(key.charAt(i));

            if (i == key.length() - 1) {
                ans = (V) walker.children[x].value;
                if (numChildren(walker) == 0) {
                    dontDelete.children[firstDelete] = null;
                } else {
                    walker.children[x].value = null;
                }
                size--;
                return ans;
            } else if (numChildren(walker) > 1 || walker.children[x].value != null) {
                dontDelete = walker;
                firstDelete = x;
            }
            walker = walker.children[x];
        }
        return null;
    }

    private int numChildren(Node walker) {
        int ans = 0;
        for (int i = 0; i < walker.children.length; i++) {
            if (walker.children[i] != null) {
                ans++;
            }
        }
        return ans;
    }


    @Override
    public void clear() {
        Node<V> n = new Node<>(null);
        n.initChildren();
        this.root = n;
        size = 0;
    }


    @Override
    public Iterator<Entry<CharSequence, V>> entryIterator() {
        throw new UnsupportedOperationException("TODO: implement");
    }



    /**
     * Carrier for a value and an array of children.
     */
    static class Node<V> {
        private Node<V>[] children;
        private V value;

        Node(V value) {
            this.value = value;
            this.children = null;
        }

        @SuppressWarnings("unchecked")
        public void initChildren() {
            this.children =
                    (Node<V>[]) new Node<?>[BRANCH_FACTOR];
        }

        /**
         * @return {@code true} if this node has child nodes
         */
        public boolean hasChildren() {
            return children != null &&
                    Arrays.stream(children).anyMatch(Objects::nonNull);
        }

        /**
         * @param c the character
         * @return the child node for the specified character, or {@code null} if there is no such
         * child
         */
        public Node<V> getChild(char c) {
            if (children == null) {
                return null;
            }
            return children[convertToIndex(c)];
        }
        
        /**
         * Sets the child node corresponding to the specified character to the specified node.
         * @param c the character corresponding to the child to set
         * @param node the node to add as a child
         */
        public void setChild(char c, Node<V> node) {
            if (children == null) {
                initChildren();
            }
            children[convertToIndex(c)] = node;
        }

        /**
         * @return {@code true} if this node has a value
         */
        public boolean hasValue() {
            return value != null;
        }

        /**
         * @return the value at this node
         */
        public V getValue() {
            return value;
        }
    }
}
