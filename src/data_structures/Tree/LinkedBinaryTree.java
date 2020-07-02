package data_structures.Tree;

import data_structures.List.Position;

import java.util.Iterator;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r){
            element = e;
            parent = p;
            left = l;
            right = r;
        }
        public E getElement(){
            return element;
        }
        public Node<E> getParent() {
            return parent;
        }
        public Node<E> getLeft() {
            return left;
        }
        public Node<E> getRight() {
            return right;
        }
        public void setElement(E element) {
            this.element = element;
        }
        public void setParent(Node<E> parent) {
            this.parent = parent;
        }
        public void setLeft(Node<E> left) {
            this.left = left;
        }
        public void setRight(Node<E> right) {
            this.right = right;
        }
    }

    protected Node<E> createNode(E e, Node<E> p, Node<E> l, Node<E> r){
        return new Node<E>(e, p, l, r);
    }

    private Node<E> root = null;
    private int size = 0;

    public LinkedBinaryTree(){}

    protected Node<E> validate(Position<E> p) throws IllegalArgumentException{
        if (!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;
        if (node.getParent() == node)
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }
    public int size(){
        return size;
    }
    public Position<E> root(){
        return root;
    }
    public void setRoot(Node<E> r){
        this.root = r;
    }
    public Position<E> parent(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getParent();
    }
    public Position<E> left(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getLeft();
    }
    public Position<E> right(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getRight();
    }
    public Position<E> addRoot(E e) throws IllegalArgumentException{
        if (!isEmpty())
            throw new IllegalArgumentException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);
        if (parent.getLeft() != null)
            throw new IllegalArgumentException("p already has a left child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);
        if (parent.getRight() != null)
            throw new IllegalArgumentException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }
    public E set(Position<E> p, E e)throws IllegalArgumentException{
        Node<E> node = validate(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)throws IllegalArgumentException{

        Node<E> node = validate(p);
        if (isInternal(p))
            throw new IllegalArgumentException("p must be a leaf");
        size += t1.size() + t2.size();
        if (!t1.isEmpty()){
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (!t2.isEmpty()){
            t2.root.setParent(node);
            node.setLeft(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }
    public E remove(Position<E> p)throws IllegalArgumentException{
        Node<E> node = validate(p);
        if (numChildren(p) == 2)
            throw new IllegalArgumentException("p has two children");
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null)
            child.setParent(node.getParent());
        if (node == root)
            root = child;
        else{
            Node<E> parent = node.getParent();
            if (node == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setRight(null);
        node.setLeft(null);
        node.setParent(null);
        return temp;
    }
    private class ElementIterator implements Iterator<E>{
        Iterator<Position<E>> posIterator = positions().iterator();
        public boolean hasNext( ) {
            return posIterator.hasNext( );
        }
        public E next( ) {
            return posIterator.next( ).getElement( );
        }
        public void remove( ) {
            posIterator.remove( );
        }
    }
    public Iterator<E> iterator(){
        return new ElementIterator();
    }
    public Iterable<Position<E>> positions( ) {
        return preorder( );
    }

}
