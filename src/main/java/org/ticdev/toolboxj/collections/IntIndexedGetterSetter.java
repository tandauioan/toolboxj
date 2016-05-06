package org.ticdev.toolboxj.collections;

/**
 *
 * Declaration for sized collections that provide int indexed read write access
 * to their elements.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <E> the type of elements in the collection
 */
public interface IntIndexedGetterSetter<E>
        extends
        IntIndexedGetter<E>,
        IntIndexedSetter<E>,
        IntSized {

}
