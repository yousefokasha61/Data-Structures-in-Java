package data_structures.Map;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMultiMap<K,V> {
    Map<K, List<V>> map = new HashMap<>();
    int total = 0;

    public HashMultiMap(){}

    public int size(){
        return total = 0;
    }
    public boolean isEmpty(){
        return total == 0;
    }
    Iterable<V> get(K key){
        List<V> buffer = map.get(key);
        if (buffer != null)
            return buffer;
        return new ArrayList<>();
    }
    public void put(K key, V value){
        List<V> buffer = map.get(key);
        if (buffer == null){
            buffer = new ArrayList<>();
            map.put(key, buffer);
        }
        buffer.add(value);
        total++;
    }
    public boolean remove(K key, V value){
        boolean removed = false;
        List<V> buffer = map.get(key);
        if (buffer != null){
            removed = buffer.remove(value);
            if (removed){
                total--;
                if (buffer.isEmpty())
                    map.remove(key);
            }
        }
        return removed;
    }
    public Iterable<V> removeAll(K key){
        List<V> buffer = map.get(key);
        if (buffer != null){
            total -= buffer.size();
            map.remove(key);
        }else
            buffer = new ArrayList<>();
        return buffer;
    }
    public Iterable<Map.Entry<K,V>> entries(){
        List<Map.Entry<K,V>> result = new ArrayList<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()){
            K key = entry.getKey();
            for (V value : entry.getValue())
                result.add(new AbstractMap.SimpleEntry<K,V>(key, value));
        }
        return result;
    }

}
