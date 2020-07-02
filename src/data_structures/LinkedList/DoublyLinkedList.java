package data_structures.LinkedList;

public class DoublyLinkedList<E> {

    private static class Node<E>{
        private E element;
        private Node<E> next;
        private Node<E> prev;
        public Node(E e, Node<E> n, Node<E> p){
            element = e;
            next = n;
            prev = p;
        }
        public E getElement( ) { return element; }
        public Node<E> getPrev( ) { return prev; }
        public Node<E> getNext( ) { return next; }
        public void setPrev(Node<E> p) { prev = p; }
        public void setNext(Node<E> n) { next = n; }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;
    public DoublyLinkedList(){
        this.head = new Node<>(null, null, null);
        this.tail = new Node<>(null, head, null);
        head.setNext(tail);
    }
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return this.size == 0;
    }
    public E first(){
        if (isEmpty())
            return null;
        return head.getNext().getElement();
    }
    public E last(){
        if (isEmpty())
            return null;
        return tail.getPrev().getElement();
    }
    private void addBetween(E e, Node<E> predecessor, Node<E> successor){
        Node<E> newest = new Node<>(e,predecessor,successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size++;
    }
    private E remove(Node<E> node){
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        return node.getElement();
    }
    public void addFirst(E e){
        addBetween(e, head, head.getNext());
    }
    public void addLast(E e){
        addBetween(e, tail.getPrev(),tail);
    }
    public E removeFirst(){
        if (isEmpty())
            return null;
        return remove(head.getNext());
    }
    public E removeLast(){
        if (isEmpty())
            return null;
        return remove(tail.getPrev());
    }
}
