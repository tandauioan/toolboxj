package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.MutableSingle;
import org.ticdev.toolboxj.tuples.Single;
import org.ticdev.toolboxj.tuples.Tuples;

/**
 * Concrete implementation of {@link MutableSingle}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            argument type
 */
public class MutableSingleImpl<T1>
    implements
    MutableSingle<T1> {

    /**
     * item 1
     */
    private T1 item1;

    /**
     * Class constructor.
     * 
     * @param item1
     *            the initial value for the item
     */
    public MutableSingleImpl(
        T1 item1) {
        this.item1 = item1;
    }

    /**
     * Default class constructor initializing the object with null.
     */
    public MutableSingleImpl() {
        this.item1 = null;
    }

    /**
     * Copy constructor.
     * 
     * @param source
     *            the single from which to copy the value. Must be non-null.
     */
    public MutableSingleImpl(
        Single<T1> source) {
        this.item1 = source.item1();
    }

    @Override
    public T1 item1() {
        return item1;
    }

    @Override
    public MutableSingle<T1> item1(T1 item1) {
        this.item1 = item1;
        return this;
    }

    @Override
    public int hashCode() {
        return Tuples.hashCode(item1);
    }

    @Override
    public boolean equals(Object obj) {
        return Tuples.singleEquals(this, obj);
    }

}
