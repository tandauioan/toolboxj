package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.MutablePair;
import org.ticdev.toolboxj.tuples.Tuples;

/**
 * Concrete implementation of {@link MutablePair}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * 
 * @param <T2>
 *            the type of the second element
 */
public class MutablePairImpl<T1, T2>
    implements
    MutablePair<T1, T2> {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * The first element
     */
    private T1 item1;

    /**
     * The second element
     */
    private T2 item2;

    /**
     * Class constructor.
     * 
     * @param item1
     *            the first element
     * @param item2
     *            the second element
     */
    public MutablePairImpl(
        T1 item1,
        T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    /**
     * Default class constructor initializing elements with null.
     */
    public MutablePairImpl() {
        this.item1 = null;
        this.item2 = null;
    }

    @Override
    public T1 item1() {
        return item1;
    }

    @Override
    public T2 item2() {
        return item2;
    }

    @Override
    public MutablePair<T1, T2> item1(T1 item1) {
        this.item1 = item1;
        return this;
    }

    @Override
    public MutablePair<T1, T2> item2(T2 item2) {
        this.item2 = item2;
        return this;
    }

    @Override
    public int hashCode() {
        return Tuples.hashCode(item1, item2);
    }

    public boolean equals(Object obj) {
        return Tuples.pairEquals(this, obj);
    }

    @Override
    public MutablePair<T1, T2> copyFrom(MutablePair<T1, T2> source) {
        item1 = source.item1();
        item2 = source.item2();
        return this;
    };

}
