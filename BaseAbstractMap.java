import java.util.*;
public abstract class BaseAbstractMap<K, V> extends AbstractMap<K, V> {

    protected abstract Iterator<Map.Entry<K, V>> entryIterator();

    @Override
    public abstract int size();

    @Override
    public abstract boolean containsValue(Object value);

    @Override
    public abstract boolean containsKey(Object key);

    @Override
    public abstract V get(Object key);

    @Override
    public abstract V put(K key, V value);

    @Override
    public abstract V remove(Object key);

    @Override
    public abstract void clear();

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return entryIterator();
        }

        @Override
        public int size() {
            return BaseAbstractMap.this.size();
        }
    }
}
