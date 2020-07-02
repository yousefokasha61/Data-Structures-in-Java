package data_structures.PriorityQueue;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {
    protected static class PQEntry<K, V> implements Entry<K, V>{
        private K key;
        private V value;
        public PQEntry(K k, V v){
            key = k;
            value = v;
        }
        @Override
        public K getKey() {
            return key;
        }
        protected void setKey(K key) {
            this.key = key;
        }
        @Override
        public V getValue() {
            return value;
        }
        protected void setValue(V value) {
            this.value = value;
        }
    }
    private Comparator<K> comp;

    protected AbstractPriorityQueue(Comparator<K> c){
        comp = c;
    }
    protected AbstractPriorityQueue(){
        this(new DefaultComparator<>());
    }
    protected int compare(Entry<K, V> a, Entry<K, V> b){
        return comp.compare(a.getKey(), b.getKey());
    }
    protected boolean checkKey(K key) throws IllegalArgumentException{
        try {
            return (comp.compare(key, key) == 0);
        }catch (ClassCastException e){
            throw new IllegalArgumentException("Incompatible key");
        }
    }
    public boolean isEmpty(){
        return size() == 0;
    }
}
