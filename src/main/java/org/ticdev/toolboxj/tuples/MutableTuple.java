package org.ticdev.toolboxj.tuples;

/**
 * MutableTuple interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the type of the tuple
 */
public interface MutableTuple<T extends MutableTuple<T>> {

    /**
     * Copies the elements from the given source to this tuple.
     * 
     * @param source
     *            the non-null source
     * @return this instance
     */
    T copyFrom(T source);

}
