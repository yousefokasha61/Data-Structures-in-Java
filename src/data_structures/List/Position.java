package data_structures.List;

public interface Position<E> {
    // Returns the element stored in that position
    E getElement()throws IllegalStateException;
}
