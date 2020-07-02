package data_structures.SearchTree;

import data_structures.List.Position;
import data_structures.Map.AbstractSortedMap;
import data_structures.PriorityQueue.Entry;
import data_structures.Tree.LinkedBinaryTree;

import java.util.ArrayList;
import java.util.Comparator;

public class TreeMap<K,V> extends AbstractSortedMap<K,V> {

    protected static class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Entry<K,V>>{

        protected static class BSTNode<E> extends Node<E>{
            int aux = 0;
            BSTNode(E e, Node<E> parent,Node<E> left, Node<E> right){
                super(e, parent, left,right);
            }
            public int getAux(){
                return aux;
            }
            public void setAux(int x){
                aux = x;
            }
        }
        public int getAux(Position<Entry<K,V>> p){
            return ((BSTNode<Entry<K,V>>) p).getAux();
        }
        public void setAux(Position<Entry<K,V>> p, int value){
            ((BSTNode<Entry<K,V>>) p).setAux(value);
        }
        @Override
        protected Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent, Node<Entry<K,V>> left, Node<Entry<K,V>> right){
            return new BSTNode<>(e, parent, left, right);
        }
        private void relink(Node<Entry<K,V>> parent, Node<Entry<K,V>> child, boolean makeLeftChild){
            child.setParent(parent);
            if (makeLeftChild)
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        public void rotate(Position<Entry<K,V>> p){
            Node<Entry<K,V>> x = validate(p);
            Node<Entry<K,V>> y = x.getParent();
            Node<Entry<K,V>> z = y.getParent();
            if (z == null){
                setRoot(x);
                x.setParent(null);
            }
            else
                relink(z, x, y == z.getLeft());
            if (x == y.getLeft()){
                relink(y, x.getRight(), true);
                relink(x,y,false);
            }
            else {
                relink(y, x.getLeft(), false);
                relink(x, y, true);
            }
        }
        public Position<Entry<K,V>> restructure(Position<Entry<K,V>> x){
            Position<Entry<K,V>> y = parent(x);
            Position<Entry<K,V>> z = parent(y);
            if ((x == right(y)) == (y == right(z))){
                rotate(y);
                return y;
            }
            else {
                rotate(x);
                rotate(x);
                return x;
            }
        }
    }

    protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();

    public TreeMap(){
        super();
        tree.addRoot(null);
    }
    public TreeMap(Comparator<K> comp){
        super(comp);
        tree.addRoot(null);
    }
    public int size(){
        return (tree.size() - 1) / 2;
    }
    private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry){
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }
    protected Position<Entry<K,V>> root() {
        return tree.root();
    }
    protected Position<Entry<K,V>> parent(Position<Entry<K,V>> p) {
        return tree.parent(p);
    }
    protected Position<Entry<K,V>> left(Position<Entry<K,V>> p) {
        return tree.left(p);
    }
    protected Position<Entry<K,V>> right(Position<Entry<K,V>> p) {
        return tree.right(p);
    }
    protected Position<Entry<K,V>> sibling(Position<Entry<K,V>> p) {
        return tree.sibling(p);
    }
    protected boolean isRoot(Position<Entry<K,V>> p) {
        return tree.isRoot(p);
    }
    protected boolean isExternal(Position<Entry<K,V>> p) {
        return tree.isExternal(p);
    }
    protected boolean isInternal(Position<Entry<K,V>> p) {
        return tree.isInternal(p);
    }
    protected void set(Position<Entry<K,V>> p, Entry<K,V> e) {
        tree.set(p, e);
    }
    protected Entry<K,V> remove(Position<Entry<K,V>> p) {
        return tree.remove(p);
    }
    protected void rotate(Position<Entry<K,V>> p) {
        tree.rotate(p);
    }
    protected Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) {
        return tree.restructure(x);
    }

    private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key){
        if (isExternal(p))
            return p;
        int comp = compare(key, p.getElement());
        if (comp == 0){
            return p;
        }
        else if (comp < 0)
            return treeSearch(left(p), key);
        else
            return treeSearch(right(p), key);
    }

    protected void rebalanceInsert(Position<Entry<K,V>> p) {}
    protected void rebalanceDelete(Position<Entry<K,V>> p) {}
    protected void rebalanceAccess(Position<Entry<K,V>> p) {}

    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);                          // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        rebalanceAccess(p);                     // hook for balanced tree subclasses
        if (isExternal(p))
            return null;         // unsuccessful search
        return p.getElement().getValue();       // match found
    }
    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);                          // may throw IllegalArgumentException
        Entry<K,V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isExternal(p)) {                    // key is new
            expandExternal(p, newEntry);
            rebalanceInsert(p);                   // hook for balanced tree subclasses
            return null;
        } else {                                // replacing existing key
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p);                   // hook for balanced tree subclasses
            return old;
        }
    }
    protected Position<Entry<K,V>> treeMin(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> walk = p;
        while (isInternal(walk))
            walk = left(walk);
        return parent(walk);              // we want the parent of the leaf
    }
    protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> walk = p;
        while (isInternal(walk))
            walk = right(walk);
        return parent(walk);              // we want the parent of the leaf
    }
    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);                          // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isExternal(p)) {                    // key not found
            rebalanceAccess(p);                   // hook for balanced tree subclasses
            return null;
        } else {
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) { // both children are internal
                Position<Entry<K,V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            } // now p has at most one child that is an internal node
            Position<Entry<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K,V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);                            // sib is promoted in p's place
            rebalanceDelete(sib);                 // hook for balanced tree subclasses
            return old;
        }
    }
    @Override
    public Entry<K,V> firstEntry() {
        if (isEmpty()) return null;
        return treeMin(root()).getElement();
    }
    @Override
    public Entry<K,V> lastEntry() {
        if (isEmpty()) return null;
        return treeMax(root()).getElement();
    }
    @Override
    public Entry<K,V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);                              // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p)) return p.getElement();   // exact match
        while (!isRoot(p)) {
            if (p == left(parent(p)))
                return parent(p).getElement();          // parent has next greater key
            else
                p = parent(p);
        }
        return null;                                // no such ceiling exists
    }
    @Override
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key);                              // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p)) return p.getElement();   // exact match
        while (!isRoot(p)) {
            if (p == right(parent(p)))
                return parent(p).getElement();          // parent has next lesser key
            else
                p = parent(p);
        }
        return null;                                // no such floor exists
    }
    @Override
    public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);                              // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(left(p)))
            return treeMax(left(p)).getElement();     // this is the predecessor to p
        // otherwise, we had failed search, or match with no left child
        while (!isRoot(p)) {
            if (p == right(parent(p)))
                return parent(p).getElement();          // parent has next lesser key
            else
                p = parent(p);
        }
        return null;                                // no such lesser key exists
    }
    @Override
    public Entry<K,V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key);                               // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(right(p)))
            return treeMin(right(p)).getElement();     // this is the successor to p
        // otherwise, we had failed search, or match with no right child
        while (!isRoot(p)) {
            if (p == left(parent(p)))
                return parent(p).getElement();           // parent has next lesser key
            else
                p = parent(p);
        }
        return null;                                 // no such greater key exists
    }
    @Override
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K,V>> p : tree.inorder())
            if (isInternal(p))
                buffer.add(p.getElement());
        return buffer;
    }
    @Override
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        checkKey(fromKey);                                // may throw IllegalArgumentException
        checkKey(toKey);                                  // may throw IllegalArgumentException
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        if (compare(fromKey, toKey) < 0)                  // ensure that fromKey < toKey
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }
    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p,
                               ArrayList<Entry<K,V>> buffer) {
        if (isInternal(p))
            if (compare(p.getElement(), fromKey) < 0)
                // p's key is less than fromKey, so any relevant entries are to the right
                subMapRecurse(fromKey, toKey, right(p), buffer);
            else {
                subMapRecurse(fromKey, toKey, left(p), buffer); // first consider left subtree
                if (compare(p.getElement(), toKey) < 0) {       // p is within range
                    buffer.add(p.getElement());                      // so add it to buffer, and consider
                    subMapRecurse(fromKey, toKey, right(p), buffer); // right subtree as well
                }
            }
    }


}
