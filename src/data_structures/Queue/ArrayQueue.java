package data_structures.Queue;

public class ArrayQueue<E> implements Queue<E> {
    public static final int CAPACITY = 1000;
    private E[] data;
    private int front = 0;
    private int size = 0;

    public ArrayQueue(){this(CAPACITY);}
    public ArrayQueue(int capacity){
        data = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    @Override
    public void enqueue(E e) throws  IllegalStateException{
        if(this.size == data.length)
            throw new IllegalStateException("Queue if Full");
        int avail = (this.front + this.size) % this.data.length;
        data[avail] = e;
        this.size++;
    }

    @Override
    public E first() {
        if (isEmpty())
            return null;
        return this.data[front];
    }

    @Override
    public E dequeue() {
        if (isEmpty())
            return null;
        E answer = this.data[this.front];
        this.data[this.front] = null;
        this.front = (this.front + 1) % this.data.length;
        this.size--;
        return answer;
    }
}
