package org.ticdev.toolboxj.collections;

import java.util.List;

/**
 * Declaration for sized collections that provide int indexed read write access
 * to their elements.
 *
 * @param <E> the type of elements in the collection
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface IntIndexedGetterSetter<E>
        extends
        IntIndexedGetter<E>,
        IntIndexedSetter<E>,
        IntSized {

    /**
     * Creates and returns a new instance wrapped around a list.
     *
     * @param list the list
     * @param <E>  the type of elements in the list
     * @return the new instance
     */
    static <E> IntIndexedGetterSetter<E> of(final List<E> list) {
        return new IntIndexedGetterSetter<E>() {
            @Override
            public E get(int index)
                    throws
                    IndexOutOfBoundsException {
                return list.get(index);
            }

            @Override
            public void set(int index, E value) {
                list.set(index, value);
            }

            @Override
            public int size() {
                return list.size();
            }
        };
    }

    /**
     * Creates and returns a new instance wrapped around a slice of a list.
     *
     * @param list   the list
     * @param offset the offset
     * @param length the number of elements
     * @param <E>    the type of elements in the list
     * @return the new instance
     */
    static <E> IntIndexedSetter<E> of(
            final List<E> list, final int offset, final int length) {
        return new IntIndexedGetterSetter<E>() {

            @Override
            public E get(int index)
                    throws
                    IndexOutOfBoundsException {
                return list.get(index + offset);
            }

            @Override
            public void set(int index, E value) {
                list.set(index + offset, value);
            }

            @Override
            public int size() {
                return length;
            }
        };
    }

    /**
     * Creates and returns a new instance wrapped around an array.
     *
     * @param arr the array
     * @param <E> the type of elements in the array
     * @return the new instance
     */
    static <E> IntIndexedGetterSetter<E> of(final E[] arr) {
        return new IntIndexedGetterSetter<E>() {

            @Override
            public E get(int index)
                    throws
                    IndexOutOfBoundsException {
                return arr[index];
            }

            @Override
            public void set(int index, E value) {
                arr[index] = value;
            }

            @Override
            public int size() {
                return arr.length;
            }
        };
    }

    /**
     * Creates and returns a new instance wrapped around a slice of an
     * array.
     *
     * @param arr    the array
     * @param offset the offset
     * @param length the number of elements
     * @param <E>    the type of elements in the array
     * @return the new instance
     */
    static <E> IntIndexedGetterSetter<E> of(
            final E[] arr, final int offset, final int length) {
        return new IntIndexedGetterSetter<E>() {
            @Override
            public E get(int index)
                    throws
                    IndexOutOfBoundsException {
                return arr[index + offset];
            }

            @Override
            public void set(int index, E value) {
                arr[index + offset] = value;
            }

            @Override
            public int size() {
                return length;
            }
        };
    }

}
