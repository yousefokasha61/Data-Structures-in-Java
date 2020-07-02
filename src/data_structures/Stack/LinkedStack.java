package data_structures.Stack;

import data_structures.LinkedList.SingleLinkedList;

public class LinkedStack<E> implements Stack<E> {

    private SingleLinkedList<E> list = new SingleLinkedList<>();
    public LinkedStack(){}

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public E top() {
        return list.first();
    }

    @Override
    public E pop() {
        return list.removeFirst();
    }
}
