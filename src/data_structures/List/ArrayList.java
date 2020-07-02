package data_structures.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {
    public static final int CAPACITY = 16;
    private E[] data;
    private int size = 0;
    public ArrayList(){this(CAPACITY);}
    public ArrayList(int capacity){
        data = (E[]) new Object[capacity];
    }

    private class ArrayIterator implements Iterator<E>{

        private int j = 0;
        private boolean removable = false;

        @Override
        public boolean hasNext() {
            return j < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (j == size)
                throw new NoSuchElementException("No Next Element");
            removable = true;
            return data[j++];
        }

        @Override
        public void remove() throws IllegalStateException{
            if (!removable)
                throw new IllegalStateException("Nothing to Remove");
            ArrayList.this.remove(j-1);
            j--;
            removable = false;
        }
    }
    public Iterator<E> iterator(){
        return new ArrayIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        checkIndex(i, this.size);
        return data[i];
    }

    @Override
    public E set(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, this.size);
        E temp = this.data[i];
        this.data[i] = e;
        return temp;
    }

    @Override
    public void add(int i, E e) throws IndexOutOfBoundsException, IllegalStateException {
        checkIndex(i, (this.size + 1));
        if (this.size == this.data.length)
            resize(2 * data.length);
        for (int j = size-1; j >= i; j--) {
            data[j+1] = data[j];
        }
        data[i] = e;
        this.size++;
    }

    @Override
    public E remove(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E temp = data[i];
        for (int k = i; k < size-1; k++) {
            data[k] = data[k+1];
        }
        data[size-1] = null;
        this.size--;
        return temp;
    }

    protected void checkIndex(int i, int n)throws IndexOutOfBoundsException{
        if(i < 0 || i >= n)
            throw new IndexOutOfBoundsException("Illegal index: " + i);
    }
    protected void resize(int capacity){
        E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }
        data = temp;
    }
}
