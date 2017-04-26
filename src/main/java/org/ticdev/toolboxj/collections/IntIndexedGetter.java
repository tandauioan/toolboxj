package org.ticdev.toolboxj.collections;

import java.util.List;

/**
 * Declaration for collections that provide int indexed read access to their
 * elements.
 *
 * @param <E> the type of elements in the collection
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface IntIndexedGetter<E> {

    /**
     * Returns the element at the given index
     *
     * @param index the index
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    E get(int index)
            throws
            IndexOutOfBoundsException;

    /**
     * Creates and returns a new instance wrapped around a list.
     *
     * @param list the list
     * @param <E>  the type of elements in the list
     * @return the new instance
     */
    static <E> IntIndexedGetter<E> of(final List<E> list) {
        return list::get;
    }

    /**
     * Creates and returns a new instance wrapped around a slice of a list.
     *
     * @param list   the list
     * @param offset the offset
     * @param <E>    the type of elements in the list
     * @return the new instance
     */
    static <E> IntIndexedGetter<E> of(final List<E> list, int offset) {
        return index -> list.get(index + offset);
    }

    /**
     * Creates and returns a new instance wrapped around an array
     *
     * @param arr the array
     * @param <E> the type of elements in the array
     * @return the new instance
     */
    static <E> IntIndexedGetter<E> of(E[] arr) {
        return index -> arr[index];
    }

    /**
     * Creates and returns a new instance wrapped around a slice of an
     * array.
     *
     * @param arr    the array
     * @param offset offset in the array
     * @param <E>    the type of elements in the array
     * @return the new instance
     */
    static <E> IntIndexedGetter<E> of(
            final E[] arr, final int offset) {
        return index -> arr[offset + index];
    }

}
