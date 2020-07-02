package data_structures.Tree;

import data_structures.List.*;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

    public Position<E> sibling(Position<E> p){
        Position<E> parent = parent(p);
        if (parent == null)
            return null;
        if (p == left(parent))
            return right(parent);
        else
            return left(parent);
    }
    public int numChildren(Position<E> p){
        int counter = 0;
        if(left(p) != null)
            counter++;
        if (right(p) != null)
            counter++;
        return counter;
    }
    @SuppressWarnings("uncheked")
    public Iterable<Position<E>> children(Position<E> p){
        List<Position<E>> snapshot = new ArrayList<>(2);
        if (left(p) != null)
            snapshot.add(0, left(p));
        if (right(p) != null)
            snapshot.add(1, right(p));
        return (Iterable<Position<E>>) snapshot;
    }
    private void inorderSubtree(Position<E> p, PositionalList<Position<E>> snapshot){
        if (left(p) != null)
            inorderSubtree(left(p), snapshot);
        snapshot.addLast(p);
        if (right(p) != null)
            inorderSubtree(right(p), snapshot);
    }
    public Iterable<Position<E>> inorder(){
        PositionalList<Position<E>> snapshot = new LinkedPositionalList<>();
        if (!isEmpty())
            inorderSubtree(root(), snapshot);
        return (Iterable<Position<E>>) snapshot;
    }
    public Iterable<Position<E>> positions(){
        return inorder();
    }
}
