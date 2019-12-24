import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A {@link java.util.Deque} implemented by a resizing array.
 *
 * @param <E> the type of the elements in the deque
 */
public interface ResizingDequeInterface<E> extends Iterable<E> {

    /**
     * Returns the size of this list.
     *
     * @return the number of elements in this list
     */
    public int size();

    /**
     * @return The underlying array of the deque implementation directly.
     */
    public E[] getArray();

    /**
     * Inserts the specified element at the front of this deque.
     *
     * @param e the element to add
     */
    public void addFirst(E e);

    /**
     * Inserts the specified element at the end of this deque.
     *
     * @param e the element to add
     */
    public void addLast(E e);

    /**
     * Retrieves and removes the first element of this deque.
     *
     * @throws NoSuchElementException if the deque is empty.
     */
    public E pollFirst();

    /**
     * Retrieves and removes the last element of this deque.
     *
     * @throws NoSuchElementException if the deque is empty.
     */
    public E pollLast();

    /**
     * Retrieves, but does not remove, the first element of this deque.
     *
     * @throws NoSuchElementException if the deque is empty.
     */
    public E peekFirst();

    /**
     * Retrieves, but does not remove, the last element of this deque
     *
     * @throws NoSuchElementException if the deque is empty.
     */
    public E peekLast();

    /**
     * Returns an iterator over the elements in this deque, ordered from
     * first to last.
     *
     * @return an iterator over the elements in this deque
     */
    public Iterator<E> iterator();
}
