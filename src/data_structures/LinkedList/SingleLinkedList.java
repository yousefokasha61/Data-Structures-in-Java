package data_structures.LinkedList;

public class SingleLinkedList<E> implements Cloneable{
    private static class Node<E>{
        private E element;
        private Node<E> next;
        public Node(E e, Node<E> n){
            this.element = e;
            this.next = n;
        }
        public E getElement(){
            return this.element;
        }
        public Node<E> getNext(){
            return this.next;
        }
        public void setNext(Node<E> n){
            this.next = n;
        }
    }

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;
    public SingleLinkedList(){}
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public Node<E> getHead(){
        return this.head;
    }
    public Node<E> getTail(){
        return this.tail;
    }

    public E first(){
        if (isEmpty())
            return null;
        return head.getElement();
    }
    public E last(){
        if (isEmpty())
            return null;
        return tail.getElement();
    }
    public void addFirst(E e){
        head = new Node<>(e, head);
        if(size == 0)
            tail = head;
        size++;
    }
    public void addLast(E e){
        Node<E> newest = new Node<>(e, null);
        if (isEmpty())
            head = newest;
        else
            tail.setNext(newest);
        tail = newest;
        size++;
    }
    public E removeFirst(){
        if(isEmpty())
            return null;
        E result = head.getElement();
        head = head.getNext();
        size--;
        if (size==0)
            tail = null;
        return result;
    }
    public void print(){
        Node<E> temp = this.head;
        System.out.println("Elements of the list: ");
        while (temp != null){
            System.out.println(temp.element + " ");
            temp = temp.getNext();
        }
    }
    public void addAfter(Node<E> prev_node, E e){
        if (prev_node == null)
            return;
        Node<E> newest = new Node<>(e,null);
        newest.setNext(prev_node.getNext());
        prev_node.setNext(newest);
    }
    public void deleteElement(E element){
        Node<E> temp = this.head, prev = null;
        if(temp != null && temp.getElement() == element){
            head = temp.getNext();
            return;
        }
        while (temp != null && temp.getElement()!=element){
            prev = temp;
            temp = temp.getNext();
        }
        if (temp == null)
            return;
        //assert prev != null;
        prev.setNext(temp.getNext());
    }
    public boolean searchIterative(E e){
        Node<E> current = this.head;
        while (current != null){
            if (current.getElement() == e)
                return true;
            current = current.getNext();
        }
        return false;
    }
    public boolean search(Node<E> node, E e){
        if (node == null)
            return false;
        if (node.getElement() == e)
            return true;
        return search(node.getNext(), e);
    }
    public boolean searchRecursive(E e){
        return search(this.head, e);
    }
    public void deleteByPosition(int position){
        if (head == null){
            return;
        }
        Node<E> temp = this.head;
        if (position == 0){
            head = temp.getNext();
            return;
        }
        for (int i=0;temp!=null && i<position-1;++i){
            temp = temp.getNext();
        }
        if (temp == null || temp.getNext() == null){
            return;
        }
        Node<E> next = temp.getNext().getNext();
        temp.setNext(next);
    }
    public void deleteList(){
        this.head = null;
    }
    public int length(){
        Node<E> temp = this.head;
        int counter = 0;
        while (temp != null){
            counter++;
            temp = temp.getNext();
        }
        return counter;
    }
    public E getNth(int position){
        Node<E> temp = this.head;
        int counter = 0;
        while (temp != null){
            if(counter == position)
                return temp.getElement();
            counter++;
            temp = temp.getNext();
        }
        assert(false);
        return null;
    }
    public E getNthFromLast(int position){
        int length = this.length();
        Node<E> temp = this.head;
        if (length < position)
            return null;
        for (int i=0;i<length-position-1;++i){
            temp = temp.getNext();
        }
        return temp.getElement();
    }
    public E getMid(){
        Node<E> mid = this.head;
        Node<E> temp = this.head;
        int counter = 0;
        while (temp != null){
            if (counter % 2 == 1)
                mid = mid.getNext();
            counter++;
            temp = temp.getNext();
        }
        if (mid != null)
            return mid.getElement();
        return null;
    }
    public int numberOfOccurrences(E e){
        Node<E> temp = this.head;
        int counter = 0;
        while (temp != null){
            if(temp.getElement() == e)
                counter++;
            temp = temp.getNext();
        }
        return counter;
    }

}
