import java.util.*;

public class BinaryMinHeap<Key extends Comparable<Key>, V> implements BinaryMinHeapInterface<Key, V> {
    HashMap<V, Integer> map;
    ArrayList<Entry<Key, V>> list;

    //Constructor
    public BinaryMinHeap() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    //Return size of heap
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean containsValue(V value) {
        return map.keySet().contains(value);
    }

    //Adds new Entry to the correct spot in the heap
    @Override
    public void add(Key key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (containsValue(value)) {
            throw new IllegalArgumentException("heap already contains value");
        }
        Entry x = new Entry(key, value);
        map.put(value, list.size());
        list.add(x);
        addHelper(x, list.size() - 1);
    }

    private void addHelper(Entry x, int i) {
        if (list.size() == 1) {
            return;
        } else {
            while (((i - 1) / 2 >= 0) &&
                    list.get(i).getKey().compareTo(list.get((i - 1) / 2).getKey()) < 0) {
                Entry temp = list.get((i - 1) / 2);
                V xValue = list.get(i).getValue();
                V tempValue = list.get((i - 1) / 2).getValue();
                list.set((i - 1) / 2, x);
                list.set(i, temp);
                map.replace(xValue, (i - 1) / 2);
                map.replace(tempValue, i);
                i = (i - 1) / 2;
            }
        }
    }

    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!containsValue(value)) {
            throw new NoSuchElementException("value is not in the heap.");
        }
        if (newKey == null) {
            throw new IllegalArgumentException("newKey is null");
        }
        int i = map.get(value);
        if (newKey.compareTo(list.get(i).getKey()) > 0) {
            throw new IllegalArgumentException("newKey is larger than original key.");
        }
        list.get(i).setKey(newKey);
        Entry x = list.get(i);
        addHelper(x, i);
    }


    @Override
    public V peek() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("heap is empty.");
        }
        return list.get(0).getValue();
    }


    @Override
    public V extractMin() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("heap is empty.");
        }
        V ans = list.get(0).getValue();
        map.replace(list.get(size() - 1).getValue(), 0);
        map.remove(ans);
        Entry temp = list.remove(list.size() - 1);
        if (list.size() == 0) {
            return ans;
        } else {
            list.set(0, temp);
            extractMinHelper(0);
            return ans;
        }
    }

    private void extractMinHelper(int i) {
        if ((2 * i) + 1 > list.size() - 1) {
            return;
        } else if ((2 * i) + 1 == list.size() - 1) {
            if (list.get((2 * i) + 1).getKey().compareTo(list.get(i).getKey()) < 0) {
                Entry temp = list.get((2 * i) + 1);
                V xValue = list.get(i).getValue();
                V tempValue = list.get((2 * i) + 1).getValue();
                list.set((2 * i) + 1, list.get(i));
                list.set(i, temp);
                map.replace(xValue, (2 * i) + 1);
                map.replace(tempValue, i);
                return;
            }
        } else {
            if (list.get((2 * i) + 1).getKey().compareTo(list.get(i).getKey()) < 0) {
                Entry temp = list.get((2 * i) + 1);
                V xValue = list.get(i).getValue();
                V tempValue = list.get((2 * i) + 1).getValue();
                list.set((2 * i) + 1, list.get(i));
                list.set(i, temp);
                map.replace(xValue, (2 * i) + 1);
                map.replace(tempValue, i);
                extractMinHelper((2 * i) + 1);
            }
            if (list.get((2 * i) + 2).getKey().compareTo(list.get(i).getKey()) < 0) {
                Entry temp = list.get((2 * i) + 2);
                V xValue = list.get(i).getValue();
                V tempValue = list.get((2 * i) + 2).getValue();
                list.set((2 * i) + 2, list.get(i));
                list.set(i, temp);
                map.replace(xValue, (2 * i) + 2);
                map.replace(tempValue, i);
                extractMinHelper((2 * i) + 2);
            }
        }
    }


    @Override
    public Set<V> values() {
        Set<V> ans = new HashSet<>();
        for (Entry x: list) {
            if (x.getValue() == null) {
                ans.add(null);
            } else {
                ans.add((V) x.getValue());
            }
        }
        return ans;
    }

    class Entry<A, B> {

        private A key;
        private B value;

        public Entry(A key, B value) {
            this.value = value;
            this.key = key;
        }

        /**
         * @return  the value stored in the entry
         */
        public B getValue() {
            return value;
        }

        /**
         * @return  the key stored in the entry
         */
        public A getKey() {
            return key;
        }

        /**
         * Changes the key of the entry.
         *
         * @param key  the new key
         * @return  the old key
         */
        public A setKey(A key) {
            A oldKey = this.key;
            this.key = key;
            return oldKey;
        }

    }

}