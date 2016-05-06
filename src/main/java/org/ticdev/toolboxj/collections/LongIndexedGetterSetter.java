package org.ticdev.toolboxj.collections;

/**
 * Declaration for sized collections that provide long indexed read write access
 * to their elements.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <E> the type of elements in the collection
 */
public interface LongIndexedGetterSetter<E>
        extends
        LongIndexedGetter<E>,
        LongIndexedSetter<E>,
        LongSized {

}
