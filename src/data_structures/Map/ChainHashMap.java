package data_structures.Map;

import data_structures.List.ArrayList;
import data_structures.PriorityQueue.Entry;

public class ChainHashMap<K,V> extends AbstractHashMap<K,V> {

    private UnsortedMap<K,V>[] table;
    public ChainHashMap(){
        super();
    }
    public ChainHashMap(int cap){
        super(cap);
    }
    public ChainHashMap(int cap, int prime){
        super(cap, prime);
    }
    protected void createTable(){
        table = (UnsortedMap<K,V>[]) new UnsortedMap[capacity];
    }
    protected V bucketGet(int h, K key){
        UnsortedMap<K,V> bucket = table[h];
        if (bucket == null)
            return null;
        return bucket.get(key);
    }
    protected V bucketPut(int h, K key, V value){
        UnsortedMap<K,V> bucket = table[h];
        if (bucket == null)
            bucket = table[h] = new UnsortedMap<>();
        int oldSize = bucket.size();
        V answer = bucket.put(key, value);
        n += (bucket.size() - oldSize);
        return answer;
    }
    protected V bucketRemove(int h, K key){
        UnsortedMap<K,V> bucket = table[h];
        if (bucket == null)
            return null;
        int oldSize = bucket.size();
        V answer = bucket.remove(key);
        n -= (oldSize - bucket.size());
        return answer;
    }
    public Iterable<Entry<K,V>> entrySet(){
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        int i=0;
        for (int h=0;h<capacity;h++)
            if (table[h] != null)
                for (Entry<K,V> entry : table[h].entrySet())
                    buffer.add(i++, entry);

        return (Iterable<Entry<K, V>>) buffer;
    }
}
