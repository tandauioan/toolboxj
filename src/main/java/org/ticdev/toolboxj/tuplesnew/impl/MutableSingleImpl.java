package org.ticdev.toolboxj.tuplesnew.impl;

import org.ticdev.toolboxj.tuplesnew.MutableSingle;
import org.ticdev.toolboxj.tuplesnew.Single;
import org.ticdev.toolboxj.tuplesnew.TupleSupport;

/**
 * Concrete mutable single implementation.
 *
 * @param <T1> the type of the first element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class MutableSingleImpl<T1>
        implements MutableSingle<T1> {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the first item
     */
    private T1 item1;

    /**
     * Class constructor.
     *
     * @param item1 the first element
     */
    public MutableSingleImpl(T1 item1) {
        this.item1 = item1;
    }

    /**
     * Copy constructor.
     *
     * @param source the single from which to copy the value
     */
    public MutableSingleImpl(Single<T1> source) {
        this(source.item1());
    }

    @Override
    public MutableSingle<T1> item1(T1 item1) {
        this.item1 = item1;
        return this;
    }

    @Override
    public MutableSingle<T1> copyFrom(
            Single<T1> source) {
        return this.item1(source.item1());
    }

    @Override
    public T1 item1() {
        return item1;
    }

    @Override
    public int hashCode() {
        return TupleSupport.hashCode(item1);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Single<?>) &&
               TupleSupport.singleEquals(this, (Single<?>) obj);
    }
}
