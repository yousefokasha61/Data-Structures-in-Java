package data_structures.Tree;

import data_structures.List.LinkedPositionalList;
import data_structures.List.Position;
import data_structures.List.PositionalList;
import data_structures.Queue.LinkedQueue;
import data_structures.Queue.Queue;

public abstract class AbstractTree<E> implements Tree<E> {
    public boolean isInternal(Position<E> p) {
        return numChildren(p) > 0;
    }
    public boolean isExternal(Position<E> p) {

        return numChildren(p) == 0;
    }
    public boolean isRoot(Position<E> p) {

        return p == root();
    }
    public boolean isEmpty( ) {

        return size() == 0;
    }
    public int depth(Position<E> p) {
        if (isRoot(p))
            return 0;
        else
            return 1 + depth(parent(p));
    }
    private int heightBad() {
        int h = 0;
        for (Position<E> p : positions())
            if (isExternal(p))
                h = Math.max(h, depth(p));
        return h;
    }
    public int height(Position<E> p) {
        int h = 0;
        for (Position<E> c : children(p))
            h = Math.max(h, 1 + height(c));
        return h;
    }
    private void preorderSubtree(Position<E> p, PositionalList<Position<E>> snapshot){
        snapshot.addLast(p);
        for (Position<E> c : children(p))
            preorderSubtree(c, snapshot);
    }
    public Iterable<Position<E>> preorder(){
        PositionalList<Position<E>> snapshot = new LinkedPositionalList<>();
        if (!isEmpty())
            preorderSubtree(root(), snapshot);
        return (Iterable<Position<E>>) snapshot;
    }
    private void postorderSubtree(Position<E> p, PositionalList<Position<E>> snapshot){
        for (Position<E> c : children(p))
            preorderSubtree(c, snapshot);
        snapshot.addLast(p);
    }
    public Iterable<Position<E>> postorder(){
        PositionalList<Position<E>> snapshot = new LinkedPositionalList<>();
        if (!isEmpty())
            postorderSubtree(root(), snapshot);
        return (Iterable<Position<E>>) snapshot;
    }
    public Iterable<Position<E>> bfs(){
        PositionalList<Position<E>> snapshot = new LinkedPositionalList<>();
        if(!isEmpty()){
            Queue<Position<E>> q = new LinkedQueue<>();
            q.enqueue(root());
            while (!q.isEmpty()){
                Position<E> p = q.dequeue();
                snapshot.addLast(p);
                for (Position<E> c : children(p))
                    q.enqueue(c);
            }
        }
        return (Iterable<Position<E>>) snapshot;
    }

}
