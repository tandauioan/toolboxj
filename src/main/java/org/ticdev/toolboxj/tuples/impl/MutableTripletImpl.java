package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.MutableTriplet;
import org.ticdev.toolboxj.tuples.Tuples;

/**
 * Concrete implementation of {@link MutableTriplet}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <T2>
 *            the type of the second element
 * @param <T3>
 *            the type of the third element
 */
public class MutableTripletImpl<T1, T2, T3>
    implements
    MutableTriplet<T1, T2, T3> {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the first element
     */
    private T1 item1;

    /**
     * The second element
     */
    private T2 item2;

    /**
     * The third element
     */
    private T3 item3;

    /**
     * Class constructor.
     * 
     * @param item1
     *            the first element
     * @param item2
     *            the second element
     * @param item3
     *            the third element
     */
    public MutableTripletImpl(
        T1 item1,
        T2 item2,
        T3 item3) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }

    /**
     * Default constructor initializing all the elements with null
     */
    public MutableTripletImpl() {
        this.item1 = null;
        this.item2 = null;
        this.item3 = null;
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
    public T3 item3() {
        return item3;
    }

    @Override
    public MutableTriplet<T1, T2, T3> item1(T1 item1) {
        this.item1 = item1;
        return this;
    }

    @Override
    public MutableTriplet<T1, T2, T3> item2(T2 item2) {
        this.item2 = item2;
        return this;
    }

    @Override
    public MutableTriplet<T1, T2, T3> item3(T3 item3) {
        this.item3 = item3;
        return this;
    }

    @Override
    public int hashCode() {
        return Tuples.hashCode(item1, item2, item3);
    }

    @Override
    public boolean equals(Object obj) {
        return Tuples.tripletEquals(this, obj);
    }

    @Override
    public MutableTriplet<T1, T2, T3>
        copyFrom(MutableTriplet<T1, T2, T3> source) {
        item1 = source.item1();
        item2 = source.item2();
        item3 = source.item3();
        return this;
    }

}
