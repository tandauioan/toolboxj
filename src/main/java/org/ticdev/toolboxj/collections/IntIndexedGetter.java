package org.ticdev.toolboxj.collections;

/**
 *
 * Declaration for collections that provide int indexed read access to their
 * elements.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <E> the type of elements in the collection
 */
public interface IntIndexedGetter<E> {

    /**
     * Returns the element at the given index
     *
     * @param index the index
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    E get(int index) throws IndexOutOfBoundsException;

}
