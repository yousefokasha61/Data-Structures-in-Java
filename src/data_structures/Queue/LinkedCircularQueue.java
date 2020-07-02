package data_structures.Queue;

import data_structures.LinkedList.CircularLinkedList;

public class LinkedCircularQueue<E> implements CircularQueue<E>{
    private CircularLinkedList<E> list = new CircularLinkedList<>();

    @Override
    public void rotate() {
        list.rotate();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        list.addLast(e);
    }

    @Override
    public E first() {
        return list.first();
    }

    @Override
    public E dequeue() {
        return list.removeFirst();
    }
}
