package data_structures.Queue;

import data_structures.LinkedList.SingleLinkedList;

public class LinkedQueue<E> implements Queue<E> {
    SingleLinkedList<E> list = new SingleLinkedList<>();
    public LinkedQueue(){}

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
