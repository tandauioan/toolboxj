package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.MutableSingle;
import org.ticdev.toolboxj.tuples.Tuples;

/**
 * Concrete implementation of {@link MutableSingle}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 */
public class MutableSingleImpl<T1>
    implements
    MutableSingle<T1> {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

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
     * Default class constructor initializing elements with null.
     */
    public MutableSingleImpl() {
        this.item1 = null;
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

    @Override
    public MutableSingle<T1> copyFrom(MutableSingle<T1> source) {
        item1 = source.item1();
        return this;
    }

}
