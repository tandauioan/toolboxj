package org.ticdev.toolboxj.collections;

import java.util.List;

/**
 * Declaration for collections that provide int indexed write access to their
 * elements.
 *
 * @param <E> the type of elements in the collection
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface IntIndexedSetter<E> {

    /**
     * Sets the value at the given index
     *
     * @param index the index
     * @param value the new value
     */
    void set(int index, E value);

    /**
     * Creates and returns a new instance wrapped around a list
     *
     * @param list the list
     * @param <E>  the type of elements in the list
     * @return the new instance
     */
    static <E> IntIndexedSetter<E> of(final List<E> list) {
        return list::set;
    }

    /**
     * Creates and returns a new instance wrapped around a slice of a list.
     *
     * @param list   the list
     * @param offset the offset
     * @param <E>    the type of elements in the list
     * @return the new instance
     */
    static <E> IntIndexedSetter<E> of(
            final List<E> list, final int offset) {
        return (index, value) -> list.set(index + offset, value);
    }

    /**
     * Creates and returns a new instance wrapped around an array
     *
     * @param arr the array
     * @param <E> the type of elements in the array
     * @return the new instance
     */
    static <E> IntIndexedSetter<E> of(final E[] arr) {
        return (index, value) -> arr[index] = value;
    }

    /**
     * Creates and returns a new instance wrapped around a slice of an
     * array.
     *
     * @param arr    the array
     * @param offset the offset
     * @param <E>    the type of elements in the array
     * @return the new instance
     */
    static <E> IntIndexedSetter<E> of(final E[] arr, final int offset) {
        return (index, value) -> arr[index + offset] = value;
    }

}
