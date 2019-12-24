import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class HashMap<K, V> extends BaseAbstractMap<K, V> {
    
    // The default initial capacity - MUST be a power of two.
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // The maximum capacity, used if a higher value is implicitly specified by
    // either of the constructors with arguments. Here, we define capacity to be
    // the number of buckets in the hash table.
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    // The load factor used when not specified in constructor.
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    // The load factor for the hash table.
    private final float loadFactor;
    
    // The table, resized as necessary. Length MUST always be a power of two.
    // The Entry class is really a primitive linked list; each Entry object has
    // a pointer to the next item of the linked list, or null if that Entry
    // corresponds to the "end" of the linked list.
    private Entry<K, V>[] table;
    
    // The number of key-value mappings contained in this map.
    private int size;
    
    // The next size value at which to resize (capacity * load factor).
    private int threshold;

    /**
     * Constructs an empty HashMap with the specified initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is non-positive, or the load factor
     *                                  is non-positive or NaN
     */
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException(
                    "Illegal load factor: " + loadFactor);
        }

        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity <<= 1;
        }

        this.loadFactor = loadFactor;
        this.threshold = (int) (capacity * loadFactor);
        this.table = new Entry[capacity];
    }

    /**
     * Constructs an empty HashMap with the specified initial capacity and the default load factor
     * (0.75).
     *
     * @param initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is non-positive.
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty HashMap with the default initial capacity (16) and the default load
     * factor (0.75).
     */
    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Applies a supplemental hash function to a given hashCode, which defends against poor quality
     * hash functions. Null keys always map to hash 0, thus index 0.
     * Additionally, this method truncates the hash to be a valid bucket based on the length
     * parameter, which represents the number of hash table buckets.
     */
    private static int hash(int h, int length) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        h ^= (h >>> 7) ^ (h >>> 4);
        return h & (length - 1);
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(Object key) {
        int index;
        if (key == null) {
            index = 0;
        } else {
            index = hash(key.hashCode(), table.length);
        }
        Entry<K, V> x = table[index];
        while (x != null) {
            if (key == null && x.getKey() == null) {
                return x.getValue();
            }
            if (key != null && key.equals(x.getKey())) {
                return x.getValue();
            } else {
                x = x.next;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        int index;
        if (key == null) {
            index = 0;
        } else {
            index = hash(key.hashCode(), table.length);
        }
        Entry<K, V> x = table[index];
        while (x != null) {
            if (key == null && x.getKey() == null) {
                return true;
            }
            if (key != null && key.equals(x.getKey())) {
                return true;
            } else {
                x = x.next;
            }
        }
        return false;
    }

    @Override
    public V put(K key, V value) {
        if (size + 1 >= threshold) {
            resize(table.length * 2);
        }
        int index;
        if (key == null) {
            index = 0;
        } else {
            index = hash(key.hashCode(), table.length);
        }
        Entry<K, V> x = table[index];

        if (x == null) {
            Entry<K, V> temp =  new Entry<>(key, value, null);
            table[index] = temp;
            size++;
            return null;
        } else {
            while (x != null) {
                if (key == null && x.getKey() == null) {
                    V ret = x.value;
                    x.value = value;
                    return ret;
                } else if (key != null && key.equals(x.getKey())) {
                    V ret = x.value;
                    x.value = value;
                    return ret;
                } else {
                    x = x.next;
                }
            }
            table[index] = new Entry(key, value, table[index]);
            size++;
            return null;
        }
    }

    /**
     * Rehashes the contents of this map into a new array with a larger capacity. This method
     * should be called automatically when the number of keys in this map reaches its threshold.
     * If current capacity is MAXIMUM_CAPACITY, this method should not resize the map, but instead
     * set threshold to Integer.MAX_VALUE. This has the effect of preventing future calls.
     *
     * @param newCapacity the new capacity, MUST be a power of two; must be greater than current
     *                    capacity unless current capacity is MAXIMUM_CAPACITY
     */
    void resize(int newCapacity) {
        if (newCapacity >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry<K, V>[] tempTable = table;
        table = new Entry[newCapacity];
        this.threshold = (int) (newCapacity * loadFactor);
        this.size = 0;
        for (int i = 0; i < tempTable.length; i++) {
            Entry<K, V> x = tempTable[i];
            while (x != null) {
                put(x.getKey(), x.getValue());
                x = x.next;
            }
        }
    }

    @Override
    public V remove(Object key) {
        // Note that you should not resize down.
        int index;
        if (key == null) {
            index = 0;
        } else {
            index = hash(key.hashCode(), table.length);
        }
        Entry<K, V> x = table[index];
        if (key == null && x.getKey() == null) {
            V ans = x.getValue();
            table[index] = x.next;
            size--;
            return ans;
        } else if (key.equals(x.getKey())) {
            V ans = x.getValue();
            table[index] = x.next;
            size--;
            return ans;
        }

        if (key == null) {
            while (x.next.getKey() != null && x.next != null) {
                x = x.next;
            }
        } else {
            while (!key.equals(x.next.getKey()) && x.next != null) {
                x = x.next;
            }
        }

        if (key == null && x.next.getKey() == null) {
            V ans = x.next.getValue();
            x.next = x.next.next;
            size--;
            return ans;
        } else if (key.equals(x.next.getKey())) {
            V ans = x.next.getValue();
            x.next = x.next.next;
            size--;
            return ans;
        } else {
            return null;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        // Perform a naive search over each entry of each bucket of the hash table
        // and return true if you have found a matching value.
        for (Entry entry : table) {
            Entry x = entry;
            while (x != null) {
                if (value.equals(x.getValue())) {
                    return true;
                } else {
                    x = x.next;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        // Clear each bucket of the hash table. In order for clear() to be done in O(1),
        // you can definitely use a table of smaller size, as long as it's a power of two.
        // DEFAULT_INITIAL_CAPACITY is a good size to use.
        table = new Entry[DEFAULT_INITIAL_CAPACITY];
        size = 0;
        threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    @Override
    protected Iterator<Map.Entry<K, V>> entryIterator() {
        // Implement an iterator that walks through every single entry in the
        // hash map. Your iterator should NOT support the remove operation.
        // Your iterator MUST be lazy.
        Iterator iter = new Iterator() {
            int nextIndex = 0;
            Entry<K, V> x;

            @Override
            public boolean hasNext() {
                if (x == null) {
                    for (int i = nextIndex; i < table.length; i++) {
                        if (table[i] != null) {
                            return true;
                        }
                    }
                    return false;
                } else if (x.next == null) {
                    for (int i = nextIndex + 1; i < table.length; i++) {
                        if (table[i] != null) {
                            return true;
                        }
                    }
                    return false;
                }
                return true;
            }

            @Override
            public Object next() {
                if (hasNext()) {
                    if (x == null) {
                        for (int i = nextIndex; i < table.length; i++) {
                            if (table[i] != null) {
                                x = table[i];
                                nextIndex = i;
                                return x;
                            }
                        }
                        return false;
                    } else if (x.next == null) {
                        for (int i = nextIndex + 1; i < table.length; i++) {
                            if (table[i] != null) {
                                x = table[i];
                                nextIndex = i;
                                return x;
                            }
                        }
                        return false;
                    } else {
                        x = x.next;
                        return x;
                    }
                } else {
                    return null;
                }
            }
        };
        return iter;
    }

    static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        Entry<K, V> next;

        /**
         * Creates new entry.
         */
        Entry(K k, V v, Entry<K, V> n) {
            value = v;
            next = n;
            key = k;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(key, entry.key)
                && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return "Entry{"
                + "key=" + key
                + ", value="
                + value
                + '}';
        }
    }
}
