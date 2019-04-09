package datatypes;

import exceptions.InterpreterException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractDictionary<K, V> {
    protected Map<K, V> map;

    public AbstractDictionary() {
        this.map = new HashMap<>();
    }

    public void add(K key, V value) {
        if (map.containsKey(key))
            throw new InterpreterException("Duplicate key!");
        map.put(key, value);
    }

    public void remove(K key) {
        if (!map.containsKey(key))
            throw new InterpreterException("Key not found!");
        map.remove(key);

    }
    public boolean isEmpty(){
        return map.isEmpty();
    }
    public V getValue(K key) {
        return map.get(key);
    }

    public void update(K key, V value) {
        if (!map.containsKey(key))
            throw new InterpreterException("Key not found!");
        map.put(key, value);
    }

    public boolean contains(K key) {
        return map.containsKey(key);
    }

    public Iterable<K> getAll() {
        return map.keySet();
    }
    public Collection<V> values(){
        return map.values();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public void setContent(Map<K, V> newmap) {
        this.map = newmap;
    }

}
