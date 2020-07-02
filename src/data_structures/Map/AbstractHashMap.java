package data_structures.Map;

import data_structures.List.ArrayList;
import data_structures.PriorityQueue.Entry;

import java.util.Random;

public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {
    protected int n = 0;
    protected int capacity;
    private int prime;
    private long scale, shift;

    public AbstractHashMap(int cap, int p){
        prime = p;
        capacity = cap;
        Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }
    public AbstractHashMap(int cap){
        this(cap, 109345121);
    }
    public AbstractHashMap(){}

    public int size(){
        return n;
    }
    public V get(K key){
        return bucketGet(hashValue(key), key);
    }
    public V remove(K key){
        return bucketRemove(hashValue(key), key);
    }
    public V put(K key, V value){
        V answer = bucketPut(hashValue(key), key, value);
        if(n > capacity / 2)
            resize(2 * capacity - 1);
        return answer;
    }
    private int hashValue(K key){
        return (int) ((Math.abs(key.hashCode()*scale + shift)%prime)%capacity);
    }
    private void resize(int newCap){
        int i=0;
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
        for (Entry<K,V> e : entrySet())
            buffer.add(i++, e);
        capacity = newCap;
        createTable();
        n = 0;
        int j = 0;
        while(j < newCap){
            put(buffer.get(j).getKey(), buffer.get(j).getValue());
            j++;
        }
    }
    protected abstract void createTable();
    protected abstract V bucketGet(int h, K key);
    protected abstract V bucketRemove(int h, K key);
    protected abstract V bucketPut(int h, K key, V value);
}
