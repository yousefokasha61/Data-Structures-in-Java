package data_structures.SearchTree;

import data_structures.List.Position;
import data_structures.PriorityQueue.Entry;

import java.util.Comparator;

public class RedBlackTreeMap<K,V> extends TreeMap<K,V> {
    public RedBlackTreeMap(){
        super();
    }
    public RedBlackTreeMap(Comparator<K> comp){
        super(comp);
    }
    private boolean isBlack(Position<Entry<K,V>> p){
        return tree.getAux(p) == 0;
    }
    private boolean isRed(Position<Entry<K,V>> p){
        return tree.getAux(p) == 1;
    }
    private void makeBlack(Position<Entry<K,V>> p){
        tree.setAux(p, 0);
    }
    private void makeRed(Position<Entry<K,V>> p){
        tree.setAux(p, 1);
    }
    private void setColor(Position<Entry<K,V>> p, boolean toRed){
        tree.setAux(p, toRed ? 1 : 0);
    }
    protected void rebalanceInsert(Position<Entry<K,V>> p){
        if (!isRoot(p)){
            makeRed(p);
            resolveRed(p);
        }
    }
    private void resolveRed(Position<Entry<K,V>> p){
        Position<Entry<K,V>> parent, uncle, middle, grand;
        parent = parent(p);
        if (isRed(parent)){
            uncle = sibling(parent);
            if (isBlack(uncle)){
                middle = restructure(p);
                makeBlack(middle);
                makeRed(left(middle));
                makeRed(right(middle));
            }
            else{
                makeBlack(parent);
                makeBlack(uncle);
                grand = parent(parent);
                if (!isRoot(grand)){
                    makeRed(grand);
                    resolveRed(grand);
                }
            }
        }
    }
    @Override
    protected void rebalanceDelete(Position<Entry<K,V>> p) {
        if (isRed(p))                        // deleted parent was black
            makeBlack(p);                      // so this restores black depth
        else if (!isRoot(p)) {
            Position<Entry<K,V>> sib = sibling(p);
            if (isInternal(sib) && (isBlack(sib) || isInternal(left(sib))))
                remedyDoubleBlack(p);            // sib's subtree has nonzero black height
        }
    }
    private void remedyDoubleBlack(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> z = parent(p);
        Position<Entry<K,V>> y = sibling(p);
        if (isBlack(y)) {
            if (isRed(left(y)) || isRed(right(y))) { // Case 1: trinode restructuring
                Position<Entry<K,V>> x = (isRed(left(y)) ? left(y) : right(y));
                Position<Entry<K,V>> middle = restructure(x);
                setColor(middle, isRed(z));
                makeBlack(left(middle));
                makeBlack(right(middle));
            } else {
                makeRed(y);
                if (isRed(z))
                    makeBlack(z);
                else if (!isRoot(z))
                    remedyDoubleBlack(z);
            }
        } else {
            rotate(y);
            makeBlack(y);
            makeRed(z);
            remedyDoubleBlack(p);
        }
    }
}
