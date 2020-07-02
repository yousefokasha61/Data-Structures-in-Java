package data_structures.List;

public interface List<E> {
    int size();

    boolean isEmpty();

    // Returns (but does not remove) the element at index i
    E get(int i) throws IndexOutOfBoundsException;

    // Replaces the element at index i with e, and returns the replaced element.
    E set(int i, E e) throws IndexOutOfBoundsException;

    // Inserts element e to be at index i, shifting all subsequent elements later.
    void add(int i, E e) throws IndexOutOfBoundsException;

    // Removes/returns the element at index i, shifting subsequent elements earlier.
    E remove(int i) throws IndexOutOfBoundsException;
}
