package org.ticdev.toolboxj.collections;

/**
 * Declaration for collections that provide int indexed write access to their
 * elements.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <E> the type of elements in the collection
 */
public interface IntIndexedSetter<E> {

    /**
     * Sets the value at the given index
     *
     * @param index the index
     * @param value the new value
     */
    void set(int index, E value);

}
