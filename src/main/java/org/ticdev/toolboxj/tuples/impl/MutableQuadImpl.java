package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.MutableQuad;
import org.ticdev.toolboxj.tuples.Quad;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete implementation of {@link MutableQuad}.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @param <T4> the type of the fourth element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class MutableQuadImpl<T1, T2, T3, T4>
        implements MutableQuad<T1, T2, T3, T4> {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the first element
     */
    private T1 item1;

    /**
     * the second element
     */
    private T2 item2;

    /**
     * the third element
     */
    private T3 item3;

    /**
     * the fourth element
     */
    private T4 item4;

    /**
     * Class constructor.
     *
     * @param item1 the first element
     * @param item2 the second element
     * @param item3 the third element
     * @param item4 the fourth element
     */
    public MutableQuadImpl(T1 item1, T2 item2, T3 item3, T4 item4) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

    /**
     * Copy constructor.
     *
     * @param source the quad from which to copy the values.
     */
    public MutableQuadImpl(Quad<T1, T2, T3, T4> source) {
        this(source.item1(), source.item2(), source.item3(),
             source.item4());
    }

    @Override
    public MutableQuad<T1, T2, T3, T4> item1(T1 item1) {
        this.item1 = item1;
        return this;
    }

    @Override
    public MutableQuad<T1, T2, T3, T4> item2(T2 item2) {
        this.item2 = item2;
        return this;
    }

    @Override
    public MutableQuad<T1, T2, T3, T4> item3(T3 item3) {
        this.item3 = item3;
        return this;
    }

    @Override
    public MutableQuad<T1, T2, T3, T4> item4(T4 item4) {
        this.item4 = item4;
        return this;
    }

    @Override
    public MutableQuad<T1, T2, T3, T4> copyFrom(
            Quad<T1, T2, T3, T4> source) {
        this.item1 = source.item1();
        this.item2 = source.item2();
        this.item3 = source.item3();
        this.item4 = source.item4();
        return this;
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
    public T4 item4() {
        return item4;
    }

    @Override
    public int hashCode() {
        return TupleSupport.hashCode(item1, item2, item3, item4);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Quad<?, ?, ?, ?>) &&
               TupleSupport.quadEquals(this, (Quad<?, ?, ?, ?>) obj);
    }
}
