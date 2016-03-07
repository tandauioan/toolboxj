package org.ticdev.toolboxj.collections;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.ticdev.toolboxj.functions.UnaryConsumer;

/**
 * Concrete implementation of a reusable iterator on an array.
 * 
 * <p>
 * The underlying array can be changed at any time using the reset methods. The
 * implementation is not thread-safe.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the type of elements in the array.
 */
public class ReusableArrayIterator<T>
    implements
    Iterator<T> {

    /**
     * Current array
     */
    private T[] elements = null;

    /**
     * Current offset
     */
    private int off = 0;

    /**
     * Current length
     */
    private int len = 0;

    /**
     * Private class constructor
     */
    private ReusableArrayIterator() {
    }

    /**
     * Resets the iterator to the given array, offset and length.
     * 
     * @param elements
     *            the new array
     * @param off
     *            the offset in the array
     * @param len
     *            the length
     * @return this instance
     */
    public ReusableArrayIterator<T> reset(T[] elements, int off, int len) {
        this.elements = elements;
        this.off = off;
        this.len = len;
        return this;
    }

    /**
     * Resets the iterator to the given array, 0 offset, and array length.
     * 
     * @param elements
     *            the new array
     * @return this instance
     */
    public ReusableArrayIterator<T> reset(T[] elements) {
        this.elements = elements;
        this.off = 0;
        this.len = elements.length;
        return this;
    }

    /**
     * Returns the number of elements remaining to iterate over.
     * 
     * @return the number of elements remaining to iterate over.
     */
    public int remaining() {
        return len;
    }

    @Override
    public boolean hasNext() {
        return elements != null && len > 0;
    }

    @Override
    public T next() {
        T result = elements[off++];
        --len;
        return result;
    }

    /**
     * Returns a new, uninitialized instance of the iterator
     * 
     * @return a new, uninitialized instance of the iterator
     */
    public static <T> ReusableArrayIterator<T> newInstance() {
        return new ReusableArrayIterator<>();
    }

    /**
     * Returns a new, initialized instance of the iterator.
     * 
     * @param elements
     *            the object array
     * @param off
     *            the offset
     * @param len
     *            the length
     * @return a new, initialized instance of the iterator.
     */
    public static <T> ReusableArrayIterator<T>
        of(T[] elements, int off, int len) {
        return ReusableArrayIterator.<T> newInstance().reset(elements, off,
            len);
    }

    /**
     * Returns a new, initialized instance of the iterator.
     * 
     * @param elements
     *            the object array
     * @return a new, initialized instance of the iterator.
     */
    public static <T> ReusableArrayIterator<T> of(T[] elements) {
        return ReusableArrayIterator.<T> newInstance().reset(elements);
    }

    /**
     * Returns a stream from the current state of the iterator.
     * 
     * @return a stream from the current state of the iterator.
     */
    public Stream<T> stream() {
        return StreamSupport
            .stream(Spliterators.spliterator(this, len,
                Spliterator.SUBSIZED | Spliterator.SIZED
                    | Spliterator.IMMUTABLE | Spliterator.ORDERED),
                false);
    }


}
